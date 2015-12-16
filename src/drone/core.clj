(ns drone.core
  (:require [clojure.java.io :refer [file]]
            [clojure.pprint :refer [pprint]]
            [drone.ai :refer [run]]
            [drone.map :refer [init-config show-map]]
            [drone.cmd :as cmd])
  (:gen-class))

(def config (atom {}))

(defn maps
  ([]
   (let [dir (file "E:\\Development\\projects\\drone\\maps")]
     (rest
       (map #(.toString %) (file-seq dir)))))
  ([i]
   (nth (maps) i)))

(defn take-turn
  [conf]
  (swap! conf #(assoc % :objects (run %)))
  (println " ")
  (show-map @conf))

(defn -main
  "The main function"
  []
  (println "Availabel maps:")
  (cmd/printlst (maps))
  (let [nrm (dec (cmd/read-nr "Select map nr:"))
        nri (dec (cmd/read-nr "Nr. of iterations: "))]
    (reset! config (init-config (maps nrm)))
    (pprint @config)
    (show-map @config)
    (doall
      (for [i (range nri)]
        (take-turn config)))))
