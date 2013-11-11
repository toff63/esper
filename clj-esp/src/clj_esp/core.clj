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


(defn average-order-value-handler
  [new-events]
  (doseq [e new-events]
    (println (str "Average order value for " (.get e "symbol")
                  ": " (format "%.2f" (.get e "asize"))))))

(def average-order-value-statement
  (str "SELECT symbol, avg(price * size) as asize FROM "
       "MarketDataEvent.win:time(5 sec) group by symbol "
       "output last every 1 seconds"))

(defn large-order-handler
  [new-events]
  (let [event (first new-events)
        [sym price size side] (map #(.get event %)
                                   ["symbol" "price" "size" "side"])]
    (println (str "Large " side " on " sym ": " size " shares at "
                   (format "%.2f" price)))))

(def bidding-up-statement
  (str "select * from pattern [every "
       "(e1=MarketDataEvent(side = 'bid') -> "
       "e2=MarketDataEvent(e2.price > e1.price,"
       "side = e1.side, symbol = e1.symbol) ->"
       "e3=MarketDataEvent(e3.price > e2.price,"
       "side = e2.side, symbol = e2.symbol))]"))
 
(defn bidding-up-handler
  [events]
  (let [es (first events)
        [e1 e2 e3] (map #(.get es %) ["e1" "e2" "e3"])
        sym (.get e1 "symbol")]
    (println (str sym ": ") (map #(format "%.2f" (.get % "price"))
                           [e1 e2 e3]))))