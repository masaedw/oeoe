(ns oeoe.handlers
  (:use clojure.pprint)
  (:use hiccup.core)
  (:use oeoe.views)
  (:use oeoe.session)
  (:use ring.util.response)
  )

(defn logged-in []
  (:logged-in (get-session)))

(defn index-get [req]
  (if (not (logged-in))
    (redirect (str "/login"))
    (default-layout
      {:title "index"
       :body [[:h1 "oeoe"]
              [:pre (escape-html (with-out-str (pprint req)))]]})))


(defn index-post [req]
  "index-post"
  )


(defn login-get [req]
  (default-layout
    {:title "login"
     :body [[:h1 "oeoe"]
            [:div
             [:button {:class "btn primary"} "Login"]
             "&nbsp;"
             [:button {:class "btn"} "Cancel"]]]})
  )


(defn login-post [req]
  "login-post"
  )


(defn callback-get [req]
  "callback-get"
  )
