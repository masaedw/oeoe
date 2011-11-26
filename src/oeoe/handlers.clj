(ns oeoe.handlers
  (:use clojure.pprint)
  (:use hiccup.core)
  (:use oeoe.views)
  (:use ring.util.response)
  )

(defn logged-in []
  true)

(defn index-get [req]
  (if (not (logged-in))
    (redirect (str "/login/"))
    (default-layout
      {:title "index"
       :body [[:h1 "oeoe"]
              [:pre (escape-html (with-out-str (pprint req)))]]})))


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
