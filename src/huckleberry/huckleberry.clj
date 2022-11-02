(ns huckleberry.huckleberry
  "FIXME: my new org.corfield.new/scratch project.")

(defn exec
  "Invoke me with clojure -X huckleberry.huckleberry/exec"
  [opts]
  (println "exec with" opts))

(defn -main
  "Invoke me with clojure -M -m huckleberry.huckleberry"
  [& args]
  (println "-main with" args))
