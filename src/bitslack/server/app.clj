(ns bitslack.server.app
  (:require
    [compojure.core :refer [GET POST defroutes]]
    [compojure.route :as route]
    [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
    [ring.util.response :refer [response]]
    [clj-http.client :as client]
    [clojure.tools.logging :as log]
    [config.core :refer [env]]))

(defn handle-feedback
  [req]
  (log/info "Request to handle-feedback: " req)
  (let [repo-key (get-in req [:body "repository" "project" "key"])
        name (get-in req [:body "repository" "name"])
        branch (-> (get-in req [:body "refChanges" 0 "refId"])
                   (clojure.string/split #"/")
                   last)
        update-type (get-in req [:body "refChanges" 0 "type"])
        changesets (get-in req [:body "changesets" "values"])
        messages (mapv #(get-in % ["toCommit" "message"]) changesets)
        author (get-in changesets [0 "toCommit" "author" "name"])
        res (client/post
              (:slack-webhook env)
              {:content-type :json :form-params {"text" (str author
                                                             " made changes ("
                                                             update-type
                                                             ") to: "
                                                             repo-key
                                                             " : "
                                                             name
                                                             " : "
                                                             branch
                                                             " - \n\t"
                                                             (apply str (interpose "\n\t" messages)))
                                                 "icon_emoji" ":monkey:"
                                                 "username" "bitslack"}})]
                                                 ;"channel" "#dev"}})]
    (response res)))

(defn splash []
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "hello from bitslack; send bitbucket server post-receive webhooks to the /bitbucket endpoint."})

(defroutes app-routes
           (GET "/" [] (splash))
           (POST "/bitbucket" {body :body} handle-feedback)
           (route/not-found
             (response {:message "Page not found"})))

(def app
  (-> app-routes
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      wrap-json-response
      wrap-json-body))