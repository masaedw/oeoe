(ns oeoe.twitter
  (:use oeoe.config)
  )

(def twitter-oauth-url
     (str "https://api.twitter.com/oauth/authenticate?oauth_token=" *app-consumer-key*))
