"use strict"

function Const(first) {
    this.constValue = Number(first);
    
    this.evaluate = function() {
        return this.constValue;
    }
    
    this.toString = function() {
        return this.constValue.toString();
    }

    this.toInfix = function() {
        return this.constValue.toString();
    }
    
    this.diff = function(x) {
        return new Const(0);
    }

    this.create = function(first) {
        return new Const(first);
    }

    this.simplify = function() {
        return this;
    }
}

var mapOfConversions = {
    "x" : 0,
    "y" : 1,
    "z" : 2,
}

function Variable(first) {
    this.variableName = first;

    this.evaluate = function() {
        return arguments[mapOfConversions[this.variableName]];
    }
    
    this.toString = function() {
        return this.variableName.toString();
    }

    this.toInfix = function() {
        return this.variableName.toString();
    }
    
    this.diff = function(x) {
        if (this.variableName === x) {
            return new Const(1);
        }
        return new Const(0);
    }

    this.create = function(first) {
        return new Variable(first);
    }

    this.simplify = function() {
        return this;
    }
}

function UnaryOperator(first, action) {
    this.first = first;
    this.action = action;
}
UnaryOperator.prototype.evaluate = function() {
    return this.apply(this.first.evaluate.apply(this.first, arguments));
}
UnaryOperator.prototype.toString = function() {
    return this.first + " " + this.action;
}
UnaryOperator.prototype.toInfix = function() {
    return this.action + "(" + this.first.toInfix() + ")";
}
UnaryOperator.prototype.simplify = function() {
    var first = this.first.simplify();
    if (first.constValue !== undefined) {
        return new Const(this.apply(first.constValue));
    }
    return this.create(first);
}

function BinaryOperator(first, second, action) {
    this.first = first;
    this.second = second;
    this.action = action;
}
BinaryOperator.prototype.evaluate = function() {
    return this.apply(this.first.evaluate.apply(this.first, arguments), this.second.evaluate.apply(this.second, arguments));
}
BinaryOperator.prototype.toString = function() {
    return this.first + " " + this.second + " " + this.action;
}
BinaryOperator.prototype.toInfix = function() {
    return "(" + this.first.toInfix() + " " + this.action + " " + this.second.toInfix() + ")";
}
BinaryOperator.prototype.simplify = function() {
    var first = this.first.simplify();
    var second = this.second.simplify();
    if (first.constValue !== undefined && second.constValue !== undefined) {
        return new Const(this.apply(first.constValue, second.constValue));
    }
    if (this._simplify !== undefined) {
        return this._simplify(first, second);
    }
    return this.create(first, second);
}

function Negate(first) {
    UnaryOperator.call(this, first, "negate");

    this.apply = function(first) {
        return -first;
    }

    this.diff = function(x) {
        return new Negate(this.first.diff(x));
    }

    this.create = function(first) {
        return new Negate(first);
    }
}
Negate.prototype = Object.create(UnaryOperator.prototype);

function Sin(first) {
    UnaryOperator.call(this, first, "sin");

    this.apply = function(first) {
        return Math.sin(first);
    }

    this.diff = function(x) {
        return new Multiply(this.first.diff(x), new Cos(this.first));
    }

    this.create = function(first) {
        return new Sin(first);
    }
}
Sin.prototype = Object.create(UnaryOperator.prototype);

function Cos(first) {
    UnaryOperator.call(this, first, "cos");

    this.apply = function(first) {
        return Math.cos(first);
    }

    this.diff = function(x) {
        return new Multiply(this.first.diff(x), new Negate(new Sin(this.first)));
    }

    this.create = function(first) {
        return new Cos(first);
    }
}
Cos.prototype = Object.create(UnaryOperator.prototype);

function Add(first, second) {
    BinaryOperator.call(this, first, second, "+");
    
    this.apply = function(first, second) {
        return first + second;
    }
    
    this.diff = function(x) {
        return new Add(first.diff(x), second.diff(x));
    }
    
    this.create = function(first, second) {
        return new Add(first, second);
    }

    this._simplify = function(first, second) {
        if (first.constValue === 0) {
            return second;
        }
        if (second.constValue === 0) {
            return first;
        }
        return new Add(first, second);
    }
}
Add.prototype = Object.create(BinaryOperator.prototype);

function Subtract(first, second) {
    BinaryOperator.call(this, first, second, "-");
    
    this.apply = function(first, second) {
        return first - second;
    }
    
    this.diff = function(x) {
        return new Subtract(first.diff(x), second.diff(x));
    }
    
    this.create = function(first, second) {
        return new Subtract(first, second);
    }

    this._simplify = function(first, second) {
        if (first.constValue === 0) {
            return new Negate(second);
        }
        if (second.constValue === 0) {
            return first;
        }
        return new Subtract(first, second);
    }
}
Subtract.prototype = Object.create(BinaryOperator.prototype);

function Multiply(first, second) {
    BinaryOperator.call(this, first, second, "*");
    
    this.apply = function(first, second) {
        return first * second;
    }
    
    this.diff = function(x) {
        return new Add(
            new Multiply(first.diff(x), second), 
            new Multiply(first, second.diff(x))
        );
    }
    
    this.create = function(first, second) {
        return new Multiply(first, second);
    }

    this._simplify = function(first, second) {
        if (first.constValue === 0 || second.constValue === 0) {
            return new Const(0);
        }
        if (first.constValue === 1) {
            return second;
        }
        if (second.constValue === 1) {
            return first;
        }
        return new Multiply(first, second);
    }
}
Multiply.prototype = Object.create(BinaryOperator.prototype);

function Divide(first, second) {
    BinaryOperator.call(this, first, second, "/");
    
    this.apply = function(first, second) {
        return first / second;
    }
    
    this.diff = function(x) {
        return new Divide(
            new Subtract(
                new Multiply(first.diff(x), second),
                new Multiply(first, second.diff(x))
            ),
            new Multiply(second, second)
        );
    }
    
    this.create = function(first, second) {
        return new Divide(first, second);
    }

    this._simplify = function(first, second) {
        if (first.constValue === 0) {
            return new Const(0);
        }
        if (second.constValue === 1) {
            return first;
        }
        return new Divide(first, second);
    }
}
Divide.prototype = Object.create(BinaryOperator.prototype);

var mapOfVariables = {
    "x" : null,
    "y" : null,
    "z" : null,
}

var mapOfUnaryOperators = {
    "negate" : Negate,
    "sin"    : Sin,
    "cos"    : Cos,
}

var mapOfBinaryOperators = {
    "+"  : Add,
    "-"  : Subtract,
    "*"  : Multiply,
    "/"  : Divide,
}

var pIsNumber = /\d+/;

function parse(expr) {
    expr = expr.split(/\s+/);

    var stack = [];
    for (var i = 0; i < expr.length; i++) {
        var current = expr[i];
        if (current === "") {
            //pass
        } else if (pIsNumber.test(current)) {
            stack.push(new Const(current));
        } else if (current in mapOfVariables) {
            stack.push(new Variable(current));
        } else if (current in mapOfUnaryOperators) {
            if (stack.length < 1) {
                throw new Error("Wrong expression: no number in stack for unary operator '" + current + "' at " + i + "th position");
            }
            var a = stack.pop();
            stack.push(new mapOfUnaryOperators[current](a));
        } else if (current in mapOfBinaryOperators) {
            if (stack.length < 2) {
                throw new Error("Wrong expression: no number in stack for binary operator '" + current + "' at " + i + "th position");
            }
            var b = stack.pop();
            var a = stack.pop();
            stack.push(new mapOfBinaryOperators[current](a, b));
        } else {
            throw new Error("Wrong token: " + current + " at " + i + "th position");
        }
    }
    if (stack.length != 1) {
        throw new Error("Wrong expression: " + " error occured at " + expr.length + "th position");
    }
    return stack.pop();
}

println(parse("x y z * /").diff("y").toInfix())
println(parse("x y z * /").diff("y").simplify().toInfix())