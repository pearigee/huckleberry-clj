(ns huckleberry.parser
  (:require [instaparse.core :as insta]
            [clojure.edn :as edn]
            [clojure.string :as str]
            [huckleberry.types :refer [with-type]]))

(def ^:private parser
  (insta/parser
   "<prog> = arg+
    <arg> = <ws> expr <ws>
    <expr> = list | method | number | symbol | map | vector | keyword
    list = <'('> arg+ <')'>
    method = <'<'> arg arg+ <'>'>
    map = <'{'> (arg arg)* <'}'>
    vector = <'['> arg* <']'>
    number = #'[0-9]+' | #'[0-9]+[.][0-9]+'
    symbol = #'[a-zA-Z]+[a-zA-Z0-9]*:?'
    keyword = #':[a-zA-Z]+[a-zA-Z0-9]*'
    <ws> = #'\\s*'"))

(defn- typed-vec [type]
  (fn [& v]
    (with-type (into [] v) type)))

(defn- typed-map [type]
  (fn [& v]
    (with-type
      (into {} (->> v (partition 2) (map vec) vec))
      type)))

(defn parse [s]
  (->> (parser s)
       (insta/transform
        {:number edn/read-string
         :method (typed-vec :method)
         :list (typed-vec :list)
         :vector (typed-vec :vector)
         :map (typed-map :map)
         :keyword (fn [s] (-> s (subs 1) keyword))
         :symbol (fn [s] (if (str/ends-with? s ":")
                           (symbol (str/join "" (drop-last s)))
                           (symbol s)))})))
(comment
  (parse "<123 dfa> (hello 123 nice) {:test 3 :taco 5} [1 2 3 4]")
  (parse "<1 addTo: 5>"))