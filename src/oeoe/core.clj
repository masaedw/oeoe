(ns oeoe.core
  (:use [compojure.handler :only [site]]
        [oeoe.routes]
        [oeoe.middleware]
        [ring.adapter.jetty :only [run-jetty]]))

(def oeoe-site
  (-> oeoe
      wrap-dynamic-request
      wrap-dynamic-session
      (wrap-charset "utf8")
      site
      ))

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (run-jetty oeoe-site {:port port})))
