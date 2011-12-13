(ns oeoe.routes
  (:use [compojure.core :only [defroutes GET POST]]
        [compojure.route :only [not-found]]
        [oeoe.handlers]))


(defroutes oeoe
  (GET  "/" req (index-get req))
  (POST "/" req (index-post req))
  (GET  "/login" req (login-get req))
  (POST "/login" req (login-post req))
  (POST "/logout" req (logout-post req))
  (GET  "/callback" req (callback-get req))
  (not-found "not found"))
