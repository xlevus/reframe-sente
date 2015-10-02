(ns demo.server
  (:require [demo.handler :refer [app start-sync!]]
            [environ.core :refer [env]]
            [org.httpkit.server :refer [run-server]])
  (:gen-class))

(defn -main [& args]
  (let [port (Integer/parseInt (or (env :port) "3000"))]
    (run-server app {:port port :join? false})))
