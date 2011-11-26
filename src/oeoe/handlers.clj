(ns oeoe.handlers
  (:use clojure.pprint)
  (:use hiccup.core)
  (:use hiccup.form-helpers)
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
            (form-to [:post "/login"]
                     [:div {:class "clearfix"}
                      (label :name "name")
                      [:div {:class "input"}
                       (text-field :name "")]]
                     [:div {:class "actions"}
                      [:button {:type "submit" :class "btn primary"} "login"]
                      "&nbsp;"
                      [:button {:type "reset" :class "btn"} "cancel"]])]})
  )


(defn login-post [req]
  (default-layout
    {:title "login"
     :body [[:h1 "oeoe"]
            [:pre (escape-html (with-out-str (pprint req)))]]})
  )


(defn callback-get [req]
  "callback-get"
  )
