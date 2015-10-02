(ns demo.views
  (:require [re-frame.core :as re-frame]
            [taoensso.encore :as encore :refer (debugf)]
            ))


(defn hello-world []
  (let [who (re-frame/subscribe [:name])]
    (fn []
      [:div "Not Connected, " @who])))


(defn counter []
  (let [c (re-frame/subscribe [:count])]
    (fn []
      [:div "Count:" @c 
       [:button {:on-click #(re-frame/dispatch [:increment-count 1])} "+" ]
       [:button {:on-click #(re-frame/dispatch [:increment-count -1])} "-" ]])))


(defn main-panel []
  (let [connected? (re-frame/subscribe [:ws/connected])]
    (fn []
      (if connected?
        [:div [counter]]
        [:div [hello-world]]))))
