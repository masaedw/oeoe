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
  (:use [twitter.oauth :only [make-oauth-creds]])
  (:use [twitter.api.restful])
  (:import java.util.Calendar)
  )

(defn logged-in []
  (:logged-in (get-session)))

(declare login-logout-form make-creds)

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
                       [:button (if (logged-in)
                                  {:type "submit" :class "btn primary"}
                                  {:type "submit" :class "btn primary" :disabled "disabled"}) "oe〜"])]]
            [:pre (escape-html (with-out-str (pprint req)))]
            [:pre (escape-html (with-out-str (pprint (make-creds req))))]]}))


(defn login-form []
  (-> (form-to [:post "/login"]
               [:button {:type "submit" :class "btn"} "login with twitter"])
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


(defn make-creds [req]
  (let [{:keys [oauth_token oauth_token_secret]} (get-in req [:session :access-token])]
    (make-oauth-creds *app-consumer-key*
                      *app-consumer-secret*
                      oauth_token
                      oauth_token_secret)))


(defn current-status [user-id]
  (-> (show-user :params {:user_id user-id})
      (get-in [:body :status :text])))


(defn index-post [req]
  (let [creds (make-creds req)
        sec (-> (Calendar/getInstance)
                (.get Calendar/SECOND))
        spaces (apply str (take sec (repeat " ")))]
    (try
      (update-status :oauth-creds creds
                     :params {:status (str "おえおえ〜" spaces ".")})
      (catch Exception ex
        (let [status (current-status (get-in (get-session) [:access-token :user_id]))]
          (update-status :oauth-creds creds
                         :params {:status (str status ".")}))))
    (redirect "/")))


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
                      [:button {:type "reset" :class "btn"} "cancel"]])]}))


(defn login-post [req]
  (let [c (twitter-consumer)
        rt (twitter-request-token c)
        uri (twitter-user-approval-uri c rt)]
    (->> {:request-token rt}
         (merge (get-session))
         (assoc (redirect uri) :session))))


(defn logout-post [req]
  (assoc (redirect "/") :session {}))


(defn callback-get [req]
  (let [verifier (get-in req [:params :oauth_verifier])
        token (get-in req [:params :oauth_token])
        request-token (:request-token (get-session))
        consumer (twitter-consumer)
        access-token (twitter-access-token consumer request-token verifier)]
    (->> {:access-token access-token
          :logged-in (:screen_name access-token)}
         (merge (get-session))
         (assoc (redirect "/") :session))))
