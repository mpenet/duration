(ns s-exp.duration-test
  (:require [clojure.test :refer :all]
            [s-exp.duration :refer :all]))

(deftest test-duration-basic-numeric
  (testing "duration with plain numeric value (ms)"
    (is (= (duration 1000) 1000))
    (is (= (duration 0) 0))))

(deftest test-duration-single-unit
  (testing "duration parses single unit strings"
    (is (= (duration "1ms") 1))
    (is (= (duration "1s") 1000))
    (is (= (duration "1m") 60000))
    (is (= (duration "1h") 3600000))
    (is (= (duration "1d") 86400000))
    (is (= (duration "1w") 604800000))
    (is (= (duration "1M") 2592000000))
    (is (= (duration "1Y") 31104000000))))

(deftest test-duration-multi-unit
  (testing "duration parses multi-unit strings"
    (is (= (duration "1h30m") (+ 3600000 1800000)))
    (is (= (duration "2d5h") (+ (* 2 86400000) (* 5 3600000))))))

(deftest test-duration-invalid
  (testing "duration throws on invalid strings"
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"Invalid duration value 'foo'" (duration "foo")))
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"Invalid duration value '1x'" (duration "1x")))
    (is (thrown-with-msg? clojure.lang.ExceptionInfo #"Invalid duration value '-1'" (duration -1)))))
