(ns demo.handlers
  (:require [re-frame.core :as re-frame]))


(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    {:name "Fred"
     :count 0}))



(re-frame/register-handler
  :increment-count
  (fn [db [_ delta]]
    (update-in db [:count] + delta)))
