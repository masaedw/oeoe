(ns oeoe.views
  (:use [hiccup.core]
        [hiccup.form-helpers]
        [hiccup.page-helpers]))

(defn default-layout [content]
  (html5
   [:head
    [:title (:title content)]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    (include-css "http://code.jquery.com/mobile/1.0/jquery.mobile-1.0.min.css")
    (include-js "http://code.jquery.com/jquery-1.6.4.min.js"
                "http://code.jquery.com/mobile/1.0/jquery.mobile-1.0.min.js")
    ]
   [:body
    (let [{:keys [title header content footer]} content
          header (if header `[:div {:data-role "header"} ~@header])
          content (if content `[:div {:data-role "content"} ~@content])
          footer (if footer `[:div {:data-role "footer" :data-position "fixed"} ~@footer])]
      `[:div {:data-role "page" :data-title ~title}
        ~header
        ~content
        ~footer])]))
