(ns demo.ws
  (:require 
            [taoensso.sente :as sente]
            [taoensso.sente.server-adapters.http-kit :refer (sente-web-server-adapter)]
            [clojure.core.async :as async :refer (go-loop <!)]))

(let [{:keys [ch-recv send-fn ajax-post-fn ajax-get-or-ws-handshake-fn
              connected-uids]}
      (sente/make-channel-socket! sente-web-server-adapter {})]
  (def ring-ajax-post                ajax-post-fn)
  (def ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn)
  (def ch-chsk                       ch-recv) ; ChannelSocket's receive channel
  (def chsk-send!                    send-fn) ; ChannelSocket's send API fn
  (def connected-uids                connected-uids) ; Watchable, read-only atom
  )

(defonce shared-db (atom {:count 0}))


(defn start-sync! 
  "Worker to sync the shared db on the server to all clients."
  []
  (println (format "Starting sync loop"))
  (go-loop [i 0]
    (<! (async/timeout 5000))
    (doseq [uid (:any @connected-uids)]
      (chsk-send! uid [:state/sync @shared-db]))
    (recur (inc i))))

(defmulti event-msg-handler :id)

(defn event-msg-handler* [{:as ev-msg :keys [id ?data event]}]
  (event-msg-handler ev-msg))

(defmethod event-msg-handler :default
  [{:as ev-msg :keys [event id ?data ring-req ?reply-fn send-fn]}]
    (println (format "Unhandled event: %s %s" event ?data))
    (when ?reply-fn
      (?reply-fn {:echo event})))



(defmethod event-msg-handler :counter/incr
  ; Increment the counter locally. the sync loop should update the clients.
  ; We should probably be doing a diff of the shared state, and pushing changes
  ; to clients instead.
  [{:as ev-msg :keys [event id ?data ring-req ?reply-fn send-fn]}]
  (reset! shared-db (update-in @shared-db [:count] + (:delta ?data))))


(defonce router (atom nil))
(defn stop-router! [] (when-let [stop-f @router] (stop-f)))
(defn start-router! []
  (stop-router!)
  (reset! router (sente/start-chsk-router! ch-chsk event-msg-handler*)))

(start-sync!) ; No idea where this goes with figwheel. Whatev. Demo.
(start-router!)
