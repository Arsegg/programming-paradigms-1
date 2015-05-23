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

(deftype _Negate [a]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (- (evaluate a kwargs)))
  (diff [this x]
    (Negate (diff a x))))

(defn Negate [a]
  (_Negate. a))

(deftype _Sin [a]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (StrictMath/sin (evaluate a kwargs)))
  (diff [this x]
    (Multiply (Cos a) (diff a x))))

(defn Sin [a]
  (_Sin. a))

(deftype _Cos [a]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (StrictMath/cos (evaluate a kwargs)))
  (diff [this x]
    (Multiply (Negate (Sin a)) (diff a x))))

(defn Cos [a]
  (_Cos. a))

(deftype _Add [args]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (apply + (map #(evaluate % kwargs) args)))
  (diff [this x]
    (_Add. (map #(diff % x) args))))

(defn Add [& args]
  (_Add. args))

(deftype _Subtract [args]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (apply - (map #(evaluate % kwargs) args)))
  (diff [this x]
    (_Subtract. (map #(diff % x) args))))

(defn Subtract [& args]
  (_Subtract. args))

(deftype _Multiply [args]
  TripleExpression
  clojure.lang.IFn
  (evaluate [this kwargs]
    (apply * (map #(evaluate % kwargs) args)))
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
    (apply / (map #(evaluate % kwargs) args)))
  (diff [this x]
    (let [a (first args)
          _b (rest args)]
      (if (empty? _b)
        (diff a x)
        (let [b (_Multiply. _b)]
          (Divide (Subtract (Multiply (diff a x) b) (Multiply a (diff b x))) (Multiply b b))))
      )
    ))

(defn Divide [& args]
  (_Divide. args))

(def mapOfUnaryOperators {"negate" Negate
                          "sin" Sin
                          "cos" Cos})

(def mapOfBinaryOperators {"+" Add
                           "-" Subtract
                           "*" Multiply
                           "/" Divide})

(defn parse [expr]
  (cond
    (number? expr) (Constant expr)
    (symbol? expr) (Variable (str expr))
    (contains? mapOfUnaryOperators (str (first expr))) (apply (get mapOfUnaryOperators (str (first expr))) (map parse (rest expr)))
    (contains? mapOfBinaryOperators (str (first expr))) (apply (get mapOfBinaryOperators (str (first expr))) (map parse (rest expr)))))

(defn parseObject [expr]
  (parse (read-string expr)))