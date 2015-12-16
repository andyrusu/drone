(ns drone.map
  (:require [clojure.core.matrix :refer [array pm]]
            [clojure.data.json :refer [read-str]]
            [clojure.string :refer [lower-case]]
            [drone.ai :as ai]))

;;Works with json
(defn load-json
  "Loads the json from the file and returns it converted into a clojure map."
  [path]
  (read-str (slurp path) :key-fn keyword))

(defn model-object
  "Transform the json objects into the maps that we use."
  [object]
  (cond
    (ai/main? object)     (assoc object :identifier "@")
    (ai/obstacle? object) (assoc object :type :obstacle
                                        :identifier (get ai/obj-ids (:type object)))
    (ai/citizen? object)  (assoc object :type :citizen
                                        :direction (keyword (lower-case (:direction object))))
    (ai/drone? object)    (assoc object :type :drone)
    :else object))

(defn get-dims
  "Get map dimentions fron json."
  [{{:keys [rows cols]} :map}]
  [rows cols])

(defn get-target
  "Model target to an object map."
  [{{:keys [target]} :map}]
  {:type :target
   :position target
   :identifier (:target ai/obj-ids)})

(defn get-objects
  "Get all objects from the json."
  [{:keys [simulatedDrone] {:keys [objects]} :map}]
  (let [objects (conj objects simulatedDrone)]
    (vec
      (map
        model-object
        objects))))

(defn init-config
  "Initialize the config atom from the map json."
  [path]
  (let [json (load-json path)
        new-map (get-dims json)]
    (assoc (zipmap [:rows :cols] new-map)
      :objects (conj (get-objects json) (get-target json)))))

;;Works with config atom
(defn find-object
  "Get the identifier from the config objects, based on their position on the map."
  [objects mx my]
  (first
    (for [obj objects
          :let [{{:keys [x y]} :position} obj]
          :when (and (= mx x) (= my y))]
      (:identifier obj))))

(defn show-map
  "Show the map in the console as a matrix."
  [conf]
  (let [objects (:objects conf)
        rrows (range (:rows conf))
        rcols (range (:cols conf))]
    (pm
      (array
        (for [i rrows]
          (for [j rcols]
            (let [mark (find-object objects j i)]
              (if (nil? mark) "O" mark))))))))

