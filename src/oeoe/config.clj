(ns oeoe.config)

(defmacro load-env
  [var env]
  `(do
     (def ~(with-meta var {:dynamic true}) (System/getenv ~env))))

(load-env *app-consumer-key* "OEOE_CONSUMER_KEY")
(load-env *app-consumer-secret* "OEOE_CONSUMER_SECRET")
(load-env *callback-url* "OEOE_CALLBACK_URL")
