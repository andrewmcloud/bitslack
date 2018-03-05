(defproject bitslack "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.3.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [clj-http "3.7.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [environ "1.0.0"]
                 [yogthos/config "1.1"]]
  :main ^:skip-aot bitslack.core
  :target-path "target/%s"
  :profiles {:uberjar {:main bitslack.core :aot :all}}
  :plugins [[lein-kibit "0.1.3"]
            [lein-ring "0.9.7"]
            [environ/environ.lein "0.3.1"]]
  :hooks [environ.leiningen.hooks]
  :min-lein-version "2.0.0"
  :uberjar-name "bitslack-standalone.jar"
  :ring {:handler bitslack.server.app/app
         :auto-refresh? true})
