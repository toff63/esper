(defproject clj-esp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
  		 		 [com.espertech/esper "4.10.0" :exclusions [log4j]]]
  :main clj-esp.core)
