(ns oeoe.middleware)

(def ^:dynamic session)

(defn get-session [] session)

(defn wrap-dynamic-session
  [handler]
  (fn [req]
    (binding [session (:session req)]
      (handler req))))


(def ^:dynamic request)

(defn get-request [] request)

(defn wrap-dynamic-request
  [handler]
  (fn [req]
    (binding [request req]
      (handler req))))


(defn wrap-charset [handler charset]
  (fn [request]
    (if-let [response (handler request)]
      (if-let [content-type (get-in response [:headers "Content-Type"])]
        (if (.contains content-type "charset")
          response
          (assoc-in response
                    [:headers "Content-Type"]
                    (str content-type "; charset=" charset)))
        response))))
