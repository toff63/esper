(ns clj-esp.esper
  (:import [com.espertech.esper.client Configuration
                                       UpdateListener
                                       EPServiceProviderManager]))


(def attr-types
 {:int Integer
  :long Long
  :double Double
  :string String})

(defn new-event
  [event-name attrs]
  (let [event {:name event-name :attrs {}}]
    (reduce (fn [m [k v]]
      (assoc-in m [:attrs (name k)] (attr-types v))) event attrs)))

(defn create-service
  [service-name config]
  (EPServiceProviderManager/getProvider service-name config))

(defn new-statement
  [service statement]
  (let [admin (.getEPAdministrator service)]
    (.createEPL admin statement)))

(defn destroy-statement
  [statement]
  (.destroy statement))

(defn create-listener
  [listener]
  (proxy [UpdateListener] []
    (update [newEvents oldEvents]
      (apply listener newEvents oldEvents))))

(defn add-listener
  [statement listener]
  (.addListener statement listener))
 
(defn remove-listener
  [statement listener]
  (.removeListener statement listener))

(defn configuration
  [& events]
  (let [config (Configuration.)]
    (doseq [{:keys [name attrs]} events]
      (.addEventType config name attrs))
    config))