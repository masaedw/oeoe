(ns oeoe.session
  )

(declare ^:dynamic session)

(defmacro with-session [session & body]
  `(binding [session ~session]
     ~@body))

(defn get-session []
  session)
