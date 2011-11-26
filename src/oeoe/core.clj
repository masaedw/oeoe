(ns oeoe.core
  (:use oeoe.routes
        )
  )

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

(wrap! oeoe (:charset "utf8"))

(def oeoe-site (site oeoe))
