(ns demo.handler
  (:require [demo.ws :as ws]
            [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [not-found resources]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [include-js include-css]]
            [prone.middleware :refer [wrap-exceptions]]
            [ring.middleware.reload :refer [wrap-reload]]
            [environ.core :refer [env]]))

(def shared-db (atom {:count 0}))

(def home-page
  (html
   [:html
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1"}]
     (include-css (if (env :dev) "css/site.css" "css/site.min.css"))]
    [:body
     [:div#app
      [:h3 "Loading..."]]]
     (include-js "js/app.js")]))


(defroutes routes
  (GET  "/" [] home-page)
  (GET  "/chsk" req (ws/ring-ajax-get-or-ws-handshake req))
  (POST "/chsk" req (ws/ring-ajax-post req))

  (resources "/")
  (not-found "Not Found"))

(def app
  (let [handler (wrap-defaults #'routes site-defaults)]
    (if (env :dev) 
      (-> handler 
          wrap-exceptions 
          wrap-reload) 
      handler)))

