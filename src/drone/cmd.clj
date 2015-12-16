(ns drone.cmd)

(defn printlst
  [lst]
  (let [n (count lst)]
    (loop [i 1
           m (first lst)]
      (println (str i ".") m)
      (if (< i n)
        (recur (inc i) (nth lst i))))))

(defn read-val
  [msg]
  (print msg)
  (flush)
  (let [v (read-line)]
    (println)
    v))

(defn read-nr
  [msg]
  (read-string (read-val msg)))
