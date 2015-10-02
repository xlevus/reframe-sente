(ns demo.handlers
  (:require [re-frame.core :as re-frame]
            [taoensso.encore :as encore :refer (debugf)]
            [demo.ws :as ws]
            ))


(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    {:name "Fred"
     :ws/connected false
     :shared {:count 0}}))


(re-frame/register-handler
  :increment-count
  (fn [db [_ delta]]
    (ws/chsk-send! [:counter/incr {:delta delta}])
    (update-in db [:shared :count] + delta)))


(re-frame/register-handler
  :ws/connected
  (fn [db [_ connected?]]
    (assoc db :ws/connected connected?)))


(re-frame/register-handler
  :ws/send
  (fn [db [_  command & data]]
    (debugf "Sending: %s %s" command data)
    (ws/chsk-send! [command data])))

(re-frame/register-handler
  :state/sync
  (fn [db [_ new-db]]
    (debugf "Syncing state %s" new-db)
    (assoc db :shared new-db)))
