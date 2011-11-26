(ns oeoe.views
  (:use hiccup.core)
  (:use hiccup.form-helpers)
  (:use hiccup.page-helpers)
  )

(defn default-layout [content]
  (html5
   [:head
    [:title (:title content)]
    (include-css "http://twitter.github.com/bootstrap/1.4.0/bootstrap.min.css")
    ;;(include-js "http://code.jquery.com/jquery-1.6.min.js")
    ]
   `[:body
     [:div {:class "container"}
      ~@(:body content)]]))
