(ns drone.ai)

(def obj-ids {"Drone"    "D"
              "Obstacle" "#"
              "Cetatean" "C"
              :target    "X"})

;;Checks
(defn is-type?
  [obj & types]
  (let [type (:type obj)]
    (some?
      (some #{type} types))))

(defn citizen?
  [obj]
  (is-type? obj "Cetatean" :citizen))

(defn drone?
  [obj]
  (is-type? obj "Drone" :drone))

(defn obstacle?
  [obj]
  (is-type? obj "Obstacle" :obstacle))

(defn target?
  [obj]
  (is-type? obj :target))

(defn main?
  [obj]
  (= (:identifier obj) "@"))

;;AI
(defn do-citizen
  [obj]
  (let [dir (:direction obj)
        {{:keys [x y]} :position} obj]
    (cond
      (= :up dir)    (assoc obj :position {:x      x  :y (dec y)})
      (= :down dir)  (assoc obj :position {:x      x  :y (inc y)})
      (= :left dir)  (assoc obj :position {:x (dec x) :y      y})
      (= :right dir) (assoc obj :position {:x (inc x) :y      y})
      :else obj)))

(defn run
  [{:keys [objects]}]
  (vec
    (for [obj objects]
      (cond
        (citizen? obj) (do-citizen obj)
        :else obj))))
