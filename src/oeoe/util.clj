(ns oeoe.util
  )

(defn update-attrs [node f]
  (if (map? (get node 1))
    (let [[tag attrs & children] node]
      (apply vector tag (f attrs) children))
    (let [[tag & children] node]
      (apply vector tag (f {}) children))))

#_(update-attrs [:a [:div] [:div]] #(assoc % :class "hoge"))
#_(update-attrs [:a {:name "name"} [:div] [:div]] #(assoc % :class "hoge"))


(defn add-class [node class]
  (letfn [(impl [{c :class :as attrs}]
                (assoc attrs
                  :class
                  (if (nil? c)
                    class
                    (str c " " class))))]
    (update-attrs node impl)))

#_(= (add-class [:p] "hoge")
     [:p {:class "hoge"}])
#_(= (add-class [:p {:class "fuga"}] "hoge")
     [:p {:class "fuga hoge"}])
