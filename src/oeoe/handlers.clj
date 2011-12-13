(ns oeoe.handlers
  (:use [clojure.pprint]
        [hiccup.core]
        [hiccup.form-helpers]
        [oeoe.config]
        [oeoe.middleware :only [get-session]]
        [oeoe.twitter]
        [oeoe.util]
        [oeoe.views]
        [ring.util.response]
        [twitter.api.restful]
        [twitter.oauth :only [make-oauth-creds]])
  (:import java.util.Calendar))


(defn logged-in []
  (:logged-in (get-session)))


(defn login-form []
  (form-to {:rel "external" :data-ajax "false"}
           [:post "/login"]
           [:button {:type "submit"} "login with twitter"]))


(defn logout-form []
  (form-to [:post "/logout"]
           [:button {:type "submit"} "logout"]))


(defn index-get [req]
  (default-layout
    {:title "oeoe"
     :content [[:h1 "oeoe"]
               (if (logged-in)
                 [:p "Hi! " (:logged-in (get-session))]
                 (login-form))
               (form-to [:post "/"]
                        [:button (if (logged-in) {:type "submit"} {:type "submit" :disabled "disabled"})
                         "oe〜"])
               #_[:pre (escape-html (with-out-str (pprint req)))]
               #_[:pre (escape-html (with-out-str (pprint (make-creds req))))]]
     :footer [(if (logged-in)
                (logout-form)
                (login-form))]}))


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
     :content [[:h1 "oeoe"]
               (form-to {:class "ui-hide-label"}
                        [:post "/login"]
                        (label :name "Screen Name")
                        (text-field {:placeholer "Screen Name"} :name "")
                        [:button {:type "submit"} "login"])]}))


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
