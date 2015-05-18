function cnst(value) {
    return function(x, y, z) {
        return value;
    }
}

function variable(name) {
    return function(x, y, z) {
        if (name === "x") {
            return x;
        }
        if (name === "y") {
            return y;
        }
        if (name === "z") {
            return z;
        }
    }
}

function unaryOperator(first, apply) {
    return function(x, y, z) {
        return apply(first(x, y, z));
    }
}

function binaryOperator(first, second, apply) {
    return function(x, y, z) {
        return apply(first(x, y, z), second(x, y, z));
    }
}

function negate(first) {
    return unaryOperator(first,
        function(a) {
            return -a;
        }
    );
}

function abs(first) {
    return unaryOperator(first,
        function(a) {
            return Math.abs(a);
        }
    );
}

function log(first) {
    return unaryOperator(first,
        function(a) {
            return Math.log(a);
        }
    );
}

function add(first, second) {
    return binaryOperator(first, second,
        function(a, b) {
            return a + b; 
        }
    );
}

function subtract(first, second) {
    return binaryOperator(first, second,
        function(a, b) {
            return a - b; 
        }
    );
}

function multiply(first, second) {
    return binaryOperator(first, second,
        function(a, b) {
            return a * b; 
        }
    );
}

function divide(first, second) {
    return binaryOperator(first, second,
        function(a, b) {
            return a / b; 
        }
    );
}

function mod(first, second) {
    return binaryOperator(first, second,
        function(a, b) {
            return a % b;
        }
    );
}

function power(first, second) {
    return binaryOperator(first, second,
        function(a, b) {
            return Math.pow(a, b);
        }
    );
}

function parse(expr) {
    function isDigit(ch) {
        return /\d/.test(ch);
    }
    
    function isWhitespace(ch) {
        return /\s/.test(ch);
    }
    
    function isAlpha(ch) {
        return /[a-z]/.test(ch);
    }

    function isUnaryOperator(s) {
        return s === "negate" || s === "abs" || s === "log";
    }

    function parseUnaryOperator(first, operator) {
        if (operator === "negate") {
            return negate(first);
        }
        if (operator === "abs") {
            return abs(first);
        }
        if (operator === "log") {
            return log(first);
        }
    }

    function isBinaryOperator(s) {
        return s.length === 1 && "+-*/%".indexOf(s) != -1 || s === "**";
    }

    function parseBinaryOperator(first, second, operator) {
        if (operator === "+") {
            return add(first, second);
        }
        if (operator === "-") {
            return subtract(first, second);
        }
        if (operator === "*") {
            return multiply(first, second);
        }
        if (operator === "/") {
            return divide(first, second);
        }
        if (operator === "%") {
            return mod(first, second);
        }
        if (operator === "**") {
            return power(first, second);
        }
    }
    
    var stack = [];
    for (var i = 0; i < expr.length; i++) {
        var ch = expr[i];
        var operator = "nop";
        if (isWhitespace(ch)) {
            // pass
        } else if (ch === "*" && i + 1 < expr.length && expr[i + 1] === "*") {
            i++;
            operator = "**";
        } else if (isAlpha(ch)) {
            var name = [ch];
            while (i + 1 < expr.length && isAlpha(expr[i + 1])) {
                name.push(expr[++i]);
            }
            name = name.join("");
            if (name.length === 1 && "xyz".indexOf(name) != -1) {
                stack.push(variable(name));
            } else {
                operator = name;
            }
        } else if (isDigit(ch) || ch === "-" && i + 1 < expr.length && isDigit(expr[i + 1])) {
            var number = [ch];
            while (i + 1 < expr.length && isDigit(expr[i + 1])) {
                number.push(expr[++i]);
            }
            stack.push(cnst(parseInt(number.join(""))));
        } else if (isBinaryOperator(ch)) {
            operator = ch;
        } else {
            throw new Error("Wrong token: " + ch + " at " + i + "th position");
        }

        if (operator === "nop") {
            // pass
        } else if (isUnaryOperator(operator)) {
            var a = stack.pop();
            stack.push(parseUnaryOperator(a, operator));
        } else if (isBinaryOperator(operator)) {
            var b = stack.pop();
            var a = stack.pop();
            stack.push(parseBinaryOperator(a, b, operator));
        }
    }
    if (stack.length != 1) {
        throw new Error("Wrong expression: " + " error occured at " + expr.length + "th position");
    }
    return stack.pop();
}

//println(parse("x negate 2 /")(1));

// var expr = subtract(
    // multiply(
        // cnst(2),
        // variable("x")
    // ),
    // cnst(3)
// );
// println(expr(5));