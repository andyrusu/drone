(defproject drone "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.json "0.2.6"]
                 [net.mikera/core.matrix "0.47.0"]]
  :main drone.core
  :bin {:name "drone"
        :bin-path "bin"
        :bootclasspath true})
