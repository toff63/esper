(ns clj-esp.core
	(:use clj-esp.esper))


(def market-data-event
  (new-event "MarketDataEvent"
    {"symbol" :string
     "price" :double
     "size" :int
     "side" :string}))

(def sides ["bid" "ask"])
(def symbols ["ABC" "XYZ"])

(def esp-service (create-service "MarketAnalysis"
                   (configuration market-data-event)))


(defn send-event
  [service event event-type]
  (.sendEvent (.getEPRuntime service) event event-type))

(defn demo
  [service statement listener num-orders]
  (let [s (new-statement service statement)
        _ (add-listener s (create-listener listener))]
    (doseq [n (range num-orders)]
      (send-event esp-service
        {"symbol" (rand-nth symbols)
         "price" (rand 50.0)
         "size" (rand-int 1200)
         "side" (rand-nth sides)}
         "MarketDataEvent")
         (Thread/sleep 100))
    (destroy-statement s)))

(def large-order-statement
  "SELECT * FROM MarketDataEvent(size > 1000)")

(defn large-order-handler
  [new-events]
  (let [event (first new-events)
        [sym price size side] (map #(.get event %)
                                   ["symbol" "price" "size" "side"])]
    (println (str "Large " side " on " sym ": " size " shares at "
                   (format "%.2f" price)))))