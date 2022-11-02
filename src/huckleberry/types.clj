(ns huckleberry.types)

(defn type-of [v]
  (:htype (meta v)))

(defn with-type [v type]
  (with-meta v {:htype type}))