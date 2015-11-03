(defn constant [value]
  (fn [kwargs]
    value))

(defn variable [name]
  (fn [kwargs]
    (get kwargs name)))

(defn unaryOperator [action args]
  (fn [kwargs]
    (apply action (map (fn [x] (x kwargs)) args))))

(defn negate [& args]
  (unaryOperator - args))

(defn sin [& args]
  (unaryOperator (fn [x] (Math/sin x)) args))

(defn cos [& args]
  (unaryOperator (fn [x] (Math/cos x)) args))

(defn binaryOperator [action args]
  (fn [kwargs]
    (try
      (apply action (map (fn [x] (double (x kwargs))) args))
      (catch Exception e (Double/POSITIVE_INFINITY)))))

(defn add [& args]
  (binaryOperator + args))

(defn subtract [& args]
  (binaryOperator - args))

(defn multiply [& args]
  (binaryOperator * args))

(defn divide [& args]
  (binaryOperator / args))

(def mapOfUnaryOperators {"negate" negate
                          "sin" sin
                          "cos" cos})

(def mapOfBinaryOperators {"+" add
                           "-" subtract
                           "*" multiply
                           "/" divide})

(defn parse [expr]
  (cond
    (number? expr) (constant expr)
    (symbol? expr) (variable (str expr))
    (contains? mapOfUnaryOperators (str (first expr))) (apply (get mapOfUnaryOperators (str (first expr))) (map parse (rest expr)))
    (contains? mapOfBinaryOperators (str (first expr))) (apply (get mapOfBinaryOperators (str (first expr))) (map parse (rest expr)))))

(defn parseFunction [expr]
  (parse (read-string expr)))

(println ((parseFunction "(+ x x)") {"x" 5}))