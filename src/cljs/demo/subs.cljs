(ns demo.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]
            [taoensso.encore :as encore :refer (debugf)]
            ))


(re-frame/register-sub
  :name
  (fn [db]
    (reaction (:name @db))))


(re-frame/register-sub
  :count
  (fn [db]
    (reaction (-> @db
                  :shared
                  :count))))

(re-frame/register-sub
  :ws/connected
  (fn [db]
    (reaction (:ws/connected @db))))

