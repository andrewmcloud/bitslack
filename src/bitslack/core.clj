(ns bitslack.core
  (:require [ring.adapter.jetty :as jetty]
            [compojure.handler :refer [site]]
            [bitslack.server.app :refer [app]]
            [environ.core :refer [env]]))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))
