(ns oeoe.core
  (:use compojure.core
        compojure.handler
        compojure.route
        (hiccup core
                form-helpers
                page-helpers
                )
        ring.adapter.jetty
        )
  )

(defn index-get [req]
  "index-get"
  )


(defn index-post [req]
  "index-post"
  )


(defn login-get [req]
  "login-get"
  )


(defn login-post [req]
  "login-post"
  )


(defn callback-get [req]
  "callback-get"
  )

;; integration

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

(defroutes oeoe
  (GET "/" req (index-get req))
  (POST "/" req (index-post req))
  (GET "/login" req (login-get req))
  (POST "/login" req (login-post req))
  (GET "/callback" req (callback-get req))
  (not-found "not found"))

(wrap! oeoe (:charset "utf8"))

(def oeoe-site (site oeoe))
