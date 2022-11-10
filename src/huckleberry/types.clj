(ns huckleberry.types)

(defn type-of [v]
  (:h-type (meta v)))

(defn with-type [v type]
  (with-meta v {:h-type type}))