(defproject oeoe "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring "1.2.1"]
                 [twitter-api "1.0.0"]
                 [mongoika "0.8.7"]]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler oeoe.core/oeoe-site}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
