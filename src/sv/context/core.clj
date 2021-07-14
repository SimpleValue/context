(ns sv.context.core)

(def ^:dynamic *context*
  nil)

(def ^:dynamic *path*
  nil)

(defn add!
  [key value & kvs]
  (when *path*
    (swap! *context*
           update-in
           *path*
           (fn [m]
             (apply assoc m key value kvs))))
  value)

(defmacro in
  [key & body]
  `(binding [*context* (or *context*
                           (atom {}))
             *path* (conj (or *path*
                              [])
                          ~key)]
     ~@body
     ))


(comment
  (in :a
    (add! :b 1)
    (in :c
      (println *path*)
      (add! :d 2))
    (println @*context*)
    )
  )
