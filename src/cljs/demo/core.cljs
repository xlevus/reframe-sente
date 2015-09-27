(ns demo.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [re-frame.core :as re-frame]
              [demo.handlers]
              [demo.views :as views]
              [demo.subs :as subs]
              [demo.ws :as ws])
    (:import goog.History))

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [views/main-panel] (.getElementById js/document "app")))

(defn init! []
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root)
  (ws/start-router!))
