(ns huckleberry.error)

(defrecord HError [message])

(defn error? [v]
  (instance? HError v))