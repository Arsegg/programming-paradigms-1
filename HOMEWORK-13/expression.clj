(declare
  Constant
  Variable
  Negate
  Sin
  Cos
  Add
  Subtract
  Multiply
  Divide
  diff
  evaluate
  parseObject)

(defn diff [expr x]
  (.diff expr x))

(defn evaluate [expr kwargs]
  (.evaluate expr kwargs))

(definterface TripleExpression
  (evaluate [args])
  (diff [x]))

(deftype _Constant [value]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    value)
  (diff [this x]
    (_Constant. 0)))

(defn Constant [value]
  (_Constant. value))

(deftype _Variable [name]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (kwargs name))
  (diff [this x]
    (if (= x name)
      (Constant 1)
      (Constant 0))))

(defn Variable [value]
  (_Variable. value))

(defn applyFunction [function args kwargs]
  (apply function (map #(evaluate % kwargs) args)))

(deftype _Negate [args]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (applyFunction - args kwargs))
  (diff [this x]
    (Negate (diff (first args) x))))

(defn Negate [& args]
  (_Negate. args))

(deftype _Sin [args]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (applyFunction #(StrictMath/sin %) args kwargs))
  (diff [this x]
    (Multiply (Cos (first args)) (diff (first args) x))))

(defn Sin [& args]
  (_Sin. args))

(deftype _Cos [args]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (applyFunction #(StrictMath/cos %) args kwargs))
  (diff [this x]
    (Multiply (Negate (Sin (first args))) (diff (first args) x))))

(defn Cos [& args]
  (_Cos. args))

(deftype _Add [args]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (applyFunction + args kwargs))
  (diff [this x]
    (_Add. (map #(diff % x) args))))

(defn Add [& args]
  (_Add. args))

(deftype _Subtract [args]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (applyFunction - args kwargs))
  (diff [this x]
    (_Subtract. (map #(diff % x) args))))

(defn Subtract [& args]
  (_Subtract. args))

(deftype _Multiply [args]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (applyFunction * args kwargs))
  (diff [this x]
    (let [a (first args)
          _b (rest args)]
      (if (empty? _b)
        (diff a x)
        (let [b (_Multiply. _b)]
          (Add (Multiply (diff a x) b) (Multiply a (diff b x))))))))

(defn Multiply [& args]
  (_Multiply. args))

(deftype _Divide [args]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (applyFunction / args kwargs))
  (diff [this x]
    (let [a (first args)
          _b (rest args)]
      (if (empty? _b)
        (diff a x)
        (let [b (_Multiply. _b)]
          (Divide (Subtract (Multiply (diff a x) b) (Multiply a (diff b x))) (Multiply b b)))))))

(defn Divide [& args]
  (_Divide. args))

(def mapOfOperators {"negate" Negate
                     "sin" Sin
                     "cos" Cos
                     "+" Add
                     "-" Subtract
                     "*" Multiply
                     "/" Divide})

(defn parse [expr]
  (cond
    (number? expr) (Constant expr)
    (symbol? expr) (Variable (str expr))
    (contains? mapOfOperators (str (first expr))) (apply (mapOfOperators (str (first expr))) (map parse (rest expr)))))

(defn parseObject [expr]
  (parse (read-string expr)))

(println (diff (Multiply (Variable "z") (Variable "z")) "z"))