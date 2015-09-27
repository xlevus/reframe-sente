(ns demo.core
  (:require-macros
   [cljs.core.async.macros :as asyncm :refer (go go-loop)])
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [taoensso.sente  :as sente :refer (cb-success?)]
              [re-frame.core :as re-frame]
              [cljs.core.async :as async :refer (<! >! put! chan)]
              [demo.handlers]
              [demo.views :as views]
              [demo.subs :as subs])
    (:import goog.History))

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to demo"]])

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [views/main-panel] (.getElementById js/document "app")))

(defn init! []
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
