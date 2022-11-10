(ns huckleberry.env-test
  (:require [clojure.test :as t :refer [deftest is testing]]
            [huckleberry.env  :refer [def-var set-var get-var]]
            [huckleberry.error :refer [error?]]))

(deftest env-test
  (testing "def-var creates values"
    (is (= (def-var [] :x 3) [{:x 3}]))
    (is (= (def-var [{:x 2} {}] :x 3) [{:x 2} {:x 3}])))

  (testing "get-var gets values"
    (is (= (get-var [] :x) nil))
    (is (= (get-var [{:x 2} {:x 3}] :x) 3))
    (is (= (get-var [{:x 2} {}] :x) 2)))

  (testing "set-var sets values only if they exist"
    (is (= (error? (set-var [] :x 3)) true)) ;; Should throw error
    (is (= (set-var [{:x 2} {:x 3}] :x 5) [{:x 2} {:x 5}]))
    (is (= (set-var [{:x 2} {}] :x 5) [{:x 5} {}]))))
