(ns oeoe.handlers
  (:use clojure.pprint)
  (:use hiccup.core)
  (:use hiccup.form-helpers)
  (:use oeoe.config)
  (:use oeoe.session)
  (:use oeoe.twitter)
  (:use oeoe.util)
  (:use oeoe.views)
  (:use ring.util.response)
  )

(defn logged-in []
  (:logged-in (get-session)))

(declare login-logout-form)

(defn index-get [req]
  (default-layout
    {:title "index"
     :body [[:h1 "oeoe"]
            [:div {:class "row"}
             [:div {:class "span4"}
              (login-logout-form)]
             [:div {:class "span8"}
              "oeoe"
              (form-to [:post "/"]
                       [:button {:type "submit" :class "btn primary"} "oe〜"])]]
            [:pre (escape-html (with-out-str (pprint req)))]]}))


(defn login-form []
  (-> (form-to [:post "/login"]
               [:div {:class "clearfix"}
                (label :name "name")
                [:div {:class "input"}
                 (-> (text-field :name "")
                     (add-class "medium"))]]
               [:button {:type "submit" :class "btn"} "login"])
      (add-class "form-stacked")))


(defn logout-form []
  [:div {:class "login-logout"}
   [:p "Hi! " (:logged-in (get-session))]
   (form-to [:post "/logout"]
            [:button {:type "submit" :class "btn"} "logout"])])


(defn login-logout-form []
  (if (logged-in)
    (logout-form)
    (login-form)))


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
  (redirect twitter-oauth-url))


(defn logout-post [req]
  (->> (dissoc (get-session) :logged-in)
       (assoc (redirect "/") :session)))


(defn callback-get [req]
  (default-layout
    {:title "callback"
     :body [[:h1 "oeoe"]
            [:pre (escape-html (with-out-str (pprint req)))]]}))
