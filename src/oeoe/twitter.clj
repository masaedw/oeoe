(ns oeoe.twitter
  (:use [oeoe.config])
  (:require [oauth.client :as oauth]))

(defn twitter-consumer []
  (oauth/make-consumer *app-consumer-key*
                       *app-consumer-secret*
                       "https://twitter.com/oauth/request_token"
                       "https://twitter.com/oauth/access_token"
                       "https://twitter.com/oauth/authenticate"
                       :hmac-sha1))

(defn twitter-request-token [consumer]
  (oauth/request-token consumer *callback-url*))

(defn twitter-user-approval-uri [consumer request-token]
  (oauth/user-approval-uri consumer (:oauth_token request-token)))

(defn twitter-access-token [consumer request-token verifier]
  (oauth/access-token consumer request-token verifier))
