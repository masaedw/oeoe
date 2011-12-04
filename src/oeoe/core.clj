(ns oeoe.core
  (:use compojure.core)
  (:use compojure.handler)
  (:use oeoe.routes)
  (:use ring.adapter.jetty)
  )

(defn wrap-charset [handler charset]
  (fn [request]
    (if-let [response (handler request)]
      (if-let [content-type (get-in response [:headers "Content-Type"])]
        (if (.contains content-type "charset")
          response
          (assoc-in response
                    [:headers "Content-Type"]
                    (str content-type "; charset=" charset)))
        response))))

(wrap! oeoe (:charset "utf8"))

(def oeoe-site (site oeoe))

(defn -main []
  (let [port (Integer/parseInt (System/getenv "PORT"))]
    (run-jetty oeoe-site {:port port})))
