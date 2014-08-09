(ns com.indoles.clj.mapdb-utils-test
  (:require [clojure.test :refer :all]
            [com.indoles.clj.mapdb-utils :refer :all]))

(deftest a-test
  (testing "In memory Q"
    (let [txm (create)
          a1 (try-over q-q using txm
                       (.add q-q 1)
                       (.take q-q))]
      (is (= a1 1)))))

(deftest b-test
  (testing "In memory map"
    (let [txm (create)
          a1 (try-over map-m using txm
                       (.put map-m 1 "one")
                       (.put map-m 2 "two")
                       (.get map-m 1))]
      (is (= a1 "one")))))

(deftest c-test
  (testing "In memory set"
    (let [txm (create)
          a1 (try-over test-set-s using txm
                       (.add test-set-s 1)
                       (.add test-set-s 2)
                       (contains? test-set-s 1))]
      (is a1))))

(deftest d-test
  (testing "In memory multiple maps"
    (let [txm (create)
          a1 (try-over m1-m m2-m using txm
                       (.put m1-m 1 "one")
                       (.put m2-m 2
                             (.get m1-m 1))
                       (.get m2-m 2))]
      (is (= "one" a1)))))
