(ns com.indoles.clj.mapdb-utils
  (:import (java.io File)
           (org.mapdb DBMaker TxMaker)))

(defn create
  ([path]
     (-> (File. path) (DBMaker/newFileDB) (.closeOnJvmShutdown) (.makeTxMaker)))
  ([]
     (-> (DBMaker/newMemoryDB) (.closeOnJvmShutdown) (.makeTxMaker))))

(defmacro try-with->
  [type my-sym txm name & forms]
  `(let [tx# (.makeTx ~txm)
         tm# (~type tx# ~name)
         result# (try
                   (let [~my-sym tm#]
                     ~@forms)
                   (catch Exception e#
                     (.rollback tx#)
                     {:exception e#}))]
     (.commit tx#)
     result#))

(defn exception-result?
  [result]
  (and (map? result) (:exception result)))

(defmacro try-with-map->
  [my-sym txm name & forms]
  `(try-with-> .getTreeMap ~my-sym ~txm ~name ~@forms))

(defmacro try-with-q->
  [my-sym txm name & forms]
  `(try-with-> .getQueue ~my-sym ~txm ~name ~@forms))

(defmacro try-with-stack->
  [my-sym txm name & forms]
  `(try-with-> .getStack ~my-sym ~txm ~name ~@forms))

(defmacro try-with-set->
  [my-sym txm name & forms]
  `(try-with-> .getTreeSet ~my-sym ~txm ~name ~@forms))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
