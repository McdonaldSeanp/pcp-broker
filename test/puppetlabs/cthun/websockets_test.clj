(ns puppetlabs.cthun.websockets-test
  (:require [clojure.test :refer :all]
            [puppetlabs.cthun.websockets :refer :all])
  (:import (org.eclipse.jetty.server Server)))

(deftest websocket-handlers-test
  (testing "All the handler functions are defined"
    (let [handlers (websocket-handlers)]
      (is (fn? (handlers :on-connect)))
      (is (fn? (handlers :on-error)))
      (is (fn? (handlers :on-close)))
      (is (fn? (handlers :on-text)))
      (is (fn? (handlers :on-bytes))))))

(deftest start-jetty-test
  (with-redefs [puppetlabs.trapperkeeper.services.webserver.jetty9-config/pem-ssl-config->keystore-ssl-config (fn [config] {})
                ring.adapter.jetty9/run-jetty (fn [app arg-map] (Server.))]
    (testing "It starts Jetty"
      (start-jetty "app" "/cthun" "localhost" 8080 {})
      ;; just confirming that we got here and no exceptions were thrown
      (is (true? true)))))
