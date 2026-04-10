// ---------------------- JAVASCRIPT EXECUTION CONTEXT ----------------------

/*
JavaScript code runs inside an Execution Context.

Types:
1. Global Execution Context (GEC)
2. Function Execution Context (FEC)
*/


// ---------------------- GLOBAL EXECUTION CONTEXT ----------------------

/*
Each JS file creates a Global Execution Context.

Phases:
1. Memory Creation Phase
2. Execution Phase
*/


// ---------------------- 1. DECLARATION PHASE ----------------------

/*
- var → undefined
- let & const → TDZ (uninitialized)
- functions → fully stored
*/

var a;       // undefined
let b;       // TDZ initially
const c = 30;

function demo() {
  console.log("Hello");
}


// ---------------------- 2. EXECUTION PHASE ----------------------

// Hoisting example (var)
console.log(a); // undefined
a = 10;


// TDZ SAFE example
try {
  console.log(b1); // ❌ ReferenceError
} catch (err) {
  console.log("Error:", err.message);
}
let b1 = 20;


// ---------------------- CALL STACK ----------------------

/*
LIFO (Last In First Out)
*/

function first() {
  console.log("First function");
  second();
}

function second() {
  console.log("Second function");
}

first();


// ---------------------- TEMPORAL DEAD ZONE (TDZ) ----------------------

/*
let & const stay in TDZ until initialized
*/

// Safe TDZ example
try {
  console.log(x); // ❌ ReferenceError
} catch (err) {
  console.log("Error:", err.message);
}
let x = 5;


// ---------------------- IMPORTANT NOTES ----------------------

/*
1. var → hoisted with undefined
2. let/const → hoisted but in TDZ
3. Functions → fully hoisted
4. Avoid var in modern JS
5. Use try-catch for demonstration of errors
*/