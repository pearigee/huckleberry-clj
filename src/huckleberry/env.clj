(ns huckleberry.env
  (:require [huckleberry.error :refer [->HError]]))

(def empty-env [{}])

(defn- last-index-of [v]
  (let [len (count v)]
    (if (zero? len)
      0
      (- len 1))))

(defn- walk-env-for-key [env key result-fn]
  (loop [index (last-index-of env)]
    (let [value (get-in env [index key])]
      (if (not (nil? value))
        (result-fn value index)
        (let [next-index (- index 1)]
          (when (>= next-index 0) (recur next-index)))))))

(defn def-var [env key value]
  (update-in env [(last-index-of env)] assoc key value))

(defn- var-level [env key]
  (walk-env-for-key env key (fn [_ i] i)))

(defn set-var [env key value]
  (let [level (var-level env key)]
    (if (nil? level)
      (->HError {:message (str "var " key " does not exist.")})
      (update-in env [level] assoc key value))))

(defn get-var [env key]
  (walk-env-for-key env key (fn [v _] v)))

(defn extend-env [env]
  (conj env {}))