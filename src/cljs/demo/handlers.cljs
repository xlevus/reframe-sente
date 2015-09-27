(ns demo.handlers
  (:require [re-frame.core :as re-frame]
            [taoensso.encore :as encore :refer (debugf)]
            [demo.ws :as ws]
            ))


(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    {:name "Fred"
     :count 0
     :ws/connected false}))


(re-frame/register-handler
  :increment-count
  (fn [db [_ delta]]
    (update-in db [:count] + delta)))


(re-frame/register-handler
  :ws/connected
  (fn [db [_ connected?]]
    (assoc db :ws/connected connected?)))


(re-frame/register-handler
  :ws/send
  (fn [db [_  command & data]]
    (debugf "Sending: %s %s" command data)
    (ws/chsk-send! [command data])))

