(ns oeoe.routes
  (:use
   [compojure.core]
   [compojure.route]
   [oeoe.handlers]
   [oeoe.session]
   )
  )

(defroutes oeoe
  (GET "/" req
       (with-session (:session req) (index-get req)))
  (POST "/" req
        (with-session (:session req) (index-post req)))
  (GET "/login" req
       (with-session (:session req) (login-get req)))
  (POST "/login" req
        (with-session (:session req) (login-post req)))
  (POST "/logout" req
        (with-session (:session req) (logout-post req)))
  (GET "/callback" req
       (with-session (:session req) (callback-get req)))
  (not-found "not found"))
