(ns com.indoles.clj.mapdb-utils-test
  (:require [clojure.test :refer :all]
            [com.indoles.clj.mapdb-utils :refer :all]))

(deftest a-test
  (testing "In memory Q"
    (let [txm (create)
          a1 (try-with-q-> q txm "test-q" (.add q 1) (.take q))]
      (is (= a1 1)))))

(deftest b-test
  (testing "In memory map"
    (let [txm (create)
          a1 (try-with-map-> m txm "test-map" (.put m 1 "one") (.put m 2 "two") (.get m 1))]
      (is (= a1 "one")))))

(deftest c-test
  (testing "In memeory set"
    (let [txm (create)
          a1 (try-with-set-> s txm "test-set" (.add s 1) (.add s 2) (contains? s 1))]
      (is a1))))
