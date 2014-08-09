(ns com.indoles.clj.mapdb-utils
  (:import (java.io File)
           (org.mapdb DBMaker TxMaker)))

(defn create
  ([path]
     (-> (File. path) (DBMaker/newFileDB) (.closeOnJvmShutdown) (.makeTxMaker)))
  ([]
     (-> (DBMaker/newMemoryDB) (.closeOnJvmShutdown) (.makeTxMaker))))

(defmacro N
  [s]
  `(cond
    (.endsWith ~s "-q") '.getQueue
    (.endsWith ~s "-s") '.getTreeSet
    (.endsWith ~s "-k") '.getStack
    :else '.getTreeMap))

(defmacro try-over
  [& what]
  (let [syms (set (take-while #(not= 'using %) what))
        using (drop-while #(not= 'using %) what)
        txm (fnext using)
        forms (-> using next next)
        tx (gensym "tx")
        l-bindings (loop [r [] b syms]
                     (if (empty? b)
                       r
                       (let [s (first b)
                             n (-> s name)
                             item-name (.substring n 0 (- (.length n) 2))]
                         (recur (conj r s (list (N n) tx item-name))  (next b)))))]
    `(let [~tx (.makeTx ~txm)
           result# (try
                     (let ~l-bindings ~@forms)
                     (catch Exception e#
                       (.rollback ~tx)
                       {:exception e#}))]
       (.commit ~tx)
       result#)))

(comment
  "over my-q my-m using txm transact form1 form2 from3   --->
(let [my-q (.getQueue txm 'my')
      my-m (.getTreeMap txm 'my']
   form1
   form2
   form3")

(defn exception-result?
  [result]
  (and (map? result) (:exception result)))
