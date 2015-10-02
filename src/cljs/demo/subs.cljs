(ns demo.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))


(re-frame/register-sub
  :name
  (fn [db]
    (reaction (:name @db))))


(re-frame/register-sub
  :count
  (fn [db]
    (reaction (:count @db))))

(re-frame/register-sub
  :ws/connected
  (fn [db]
    (reaction (:ws/connected @db))))


(re-frame/register-sub
  :ws/recv
  (fn [db]))


