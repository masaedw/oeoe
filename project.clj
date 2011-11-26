(defproject oeoe "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [compojure "0.6.5"]
                 [hiccup "0.3.7"]
                 [ring "0.3.11"]
                 ]
  :dev-dependencies [[lein-ring "0.4.5"]]
  :ring {:handler oeoe.core/oeoe-site}
  )
