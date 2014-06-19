(ns webapp.framework.server.core
  [:require [webapp.server.fns]]
  [:use [webapp.framework.server.systemfns]]
  [:use [ring.middleware.format]]
  [:use [compojure.core]]
  [:use [korma.core]]
  [:use [ring.middleware.json]]
  [:require [ring.middleware.multipart-params :as mp]]
  [:require [compojure.route :as route]]
  [:require [compojure.handler :as handler]]
  [:require [ring.util.response :as resp]]
  [:use [webapp-config.settings]]
  (:use [webapp.framework.server.email-service])
  (:require [clojure.java.io :as io])
  (:use [webapp.framework.server.globals])
  (:require [clojurewerkz.neocons.rest :as rest])
)


(def sa (atom 0))

(defn start-services []
  (do
    (println (str "start-services: " @sa))
    (if (= (swap! sa inc) 1)
      (doseq [init-fn   @init-fns]
        (println (init-fn))
        ))
    []
    ))




(defn parse-params [params]
  (let
    [
       code (str
             "("
             (if (:action params) "webapp.server.fns/" "webapp.framework.server.systemfns/")
             (if (:action params) (:action params) (:systemaction params))
             " "

               (str
                (:params params))
             ")")
      ]
      ;(println ":" code)
      (pr-str (load-string code))
     ))






(defn upload-file
  [file]

  ;(println (str  file))

  (io/copy
   (file :tempfile)
   (io/file "../pictures/a.jpg"))

  "DONE")




(defroutes main-routes
  (POST "/:domain/action" {params :params}
    (parse-params  params))

  (POST "/action" {params :params}
    (parse-params  params))

   (mp/wrap-multipart-params
       (POST "/file" {params :params}
             (do

             (upload-file (get params "file")))))





  (GET "/" []
       (do
         (start-services)
         (resp/resource-response (str *main-page*) {:root "public"})))



  (route/resources "/:domain/")
  (route/resources "/")



  (ANY "/*:name" [name]
       (.replaceFirst
        (slurp (:body
                (resp/resource-response (str *main-page*) {:root "public"})
                ))
       "addparam2" name))
;       (resp/resource-response (str *main-page* "?add=" name) {:root "public"}))
;       (resp/redirect (str *main-page* "?addparam=" name)))
)



(comment .replaceFirst
        (slurp (:body
                (resp/resource-response (str *main-page*) {:root "public"})
                ))
       "ConnectToUs" "xxx")

(comment :body (rest/GET

        (str (clojure.java.io/resource (str "public/" *main-page*)))

        ))


        (str (clojure.java.io/resource (str "public/" *main-page*)))


(defn wrap-dir-index [handler]
  (fn [req]
    (handler
     (update-in req [:uri]
                #(if (= "/" %) (str "/" *main-page*) %)))))





(def app
    (-> (handler/api main-routes)
        (wrap-json-params)
))



(defn init []
    (do
    (println "******************* HHHHH *******************")
    (println "******************* HHHHH *******************")
    (println "******************* HHHHH *******************")
    (println "******************* HHHHH *******************")
    (println "******************* HHHHH *******************")
    (println "******************* HHHHH *******************")
    (comment doseq [init-fn   @init-fns]
      (println (init-fn))
      )
      []
    ))


