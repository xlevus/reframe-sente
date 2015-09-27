(ns demo.views
  (:require [re-frame.core :as re-frame]))


(defn hello-world []
  (let [who (re-frame/subscribe [:name])]
    (fn []
      [:div "Hello, " @who])))


(defn counter []
  (let [c (re-frame/subscribe [:count])]
    (fn []
      [:div "Count: " @c 
       [:button {:on-click #(re-frame/dispatch [:increment-count 1])} "+" ]
       [:button {:on-click #(re-frame/dispatch [:increment-count -1])} "-" ]])))


(defn main-panel []
  [:div 
   [hello-world]
   [counter]])
