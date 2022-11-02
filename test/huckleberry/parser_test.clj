(ns huckleberry.parser-test
  (:require [clojure.test :as t :refer [deftest is testing]]
            [huckleberry.parser  :refer [parse]]
            [huckleberry.types :refer [type-of]]))

(deftest parser-test
  (testing "parses numbers"
    (is (= (first (parse "32.2")) 32.2)))

  (testing "parses symbols"
    (is (= (first (parse "hello")) 'hello))
    (is (= (first (parse "hello:")) 'hello)))

  (testing "parses keywords"
    (is (= (first (parse ":hello")) :hello)))

  (testing "parses vectors"
    (let [result (first (parse "[1 2 3]"))]
      (is (= result [1 2 3]))
      (is (= (type-of result) :vector))))

  (testing "parses lists"
    (let [result (first (parse "(test 2 3)"))]
      (is (= result ['test 2 3]))
      (is (= (type-of result) :list))))

  (testing "parses methods"
    (let [result (first (parse "<test add 3>"))]
      (is (= result ['test 'add 3]))
      (is (= (type-of result) :method)))))
