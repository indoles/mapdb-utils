(defproject com.indoles.clj/mapdb-utils "0.1.1"
  :description "Easy transactions for mapdp with clojure"
  :url "http://github.com/indoles/mapdb-utils"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repl-options {:init-ns com.indoles.clj.mapdb-utils}
  :pom-addition [:developers [:developer [:name "Indoles"]]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.mapdb/mapdb "1.0.5"]])
