// ---------------------- FUNCTION DECLARATION ----------------------

function greet() {
  console.log("Hello");
}

// Calling the function
greet();



// ---------------------- TYPES OF FUNCTIONS ----------------------

// 1. Parameterized Function (takes inputs)
function add(a, b) {
  return a + b;
}
console.log(add(10, 20)); // 30



// 2. Non-Parameterized Function (no inputs)
function sayHi() {
  console.log("Hi");
}
sayHi();



// ---------------------- ARGUMENTS OBJECT ----------------------

function displayListOfAngular() {
  console.log(arguments); // array-like object

  // Different ways to convert arguments → array
  console.log(Array.from(arguments));
  console.log([...arguments]);
  console.log(Array.prototype.slice.call(arguments));
  console.log(Array.prototype.slice.apply(arguments));

  // Accessing values
  console.log(arguments[0]);
  console.log(arguments[1]);
  console.log(arguments[2]);
}

displayListOfAngular("ayush", "aryan", "navdeep");



// ---------------------- RETURN vs NO RETURN ----------------------

function breakForAngularPeople() {
  console.log("This is a break for Angular people");
}

let result = breakForAngularPeople();
console.log(typeof result); // undefined (no return)



function returnForAngularPeople() {
  console.log("This is a return for Angular people");
  return "Angular is great!";
}

let result2 = returnForAngularPeople();
console.log(typeof result2); // string



// ---------------------- FUNCTION EXPRESSION ----------------------

let funfuction = function hello(name1, name2) {
  console.log(`Hello ${name1} and ${name2}`);
};

console.log(typeof funfuction); // function
funfuction("Ayush", "Aryan");



// ---------------------- ANONYMOUS FUNCTION ----------------------

let anonymousFunction = function () {
  console.log("This is an anonymous function");
};

anonymousFunction();



// ---------------------- ARROW FUNCTIONS ----------------------

/*
Definition:
Arrow functions are a shorter (ES6) way to write functions using the => syntax.

Example:
const add = (a, b) => a + b;

Importance / Advantages:

1. Short & Clean Syntax
   - Less code compared to normal functions
   - No need to write 'function' keyword

2. Implicit Return
   - If only one expression, no need for {} or return
   - Example: const square = x => x * x;

3. No 'this' Binding
   - Arrow functions do NOT have their own 'this'
   - They inherit 'this' from parent scope (useful in React, callbacks)

4. No 'arguments' Object
   - Cannot use arguments keyword
   - Use rest operator (...args) instead

5. Best for Callbacks
   - Commonly used in map(), filter(), reduce()

6. Single Parameter Shortcut
   - No parentheses needed for one parameter
   - Example: x => x + 1

7. Cannot be Used as Constructor
   - You cannot use 'new' with arrow functions

8. Not Hoisted
   - Must define before using (like variables)

Conclusion:
Arrow functions are modern, concise, and best for short functions and callbacks,
but not suitable when you need 'this', 'arguments', or constructor behavior.
*/

// Simple arrow function
const arrowFunction = () => 34;
console.log(arrowFunction());


// Arrow function returning string
const arrowFunction02 = () => 45 + " This is a arrow function.";
console.log(arrowFunction02());

let resultFromArrowFunction = arrowFunction02();
console.log("Variable value - " + resultFromArrowFunction);


// Unary + converts string → number
const arrowFunction03 = () => 45 + +"45";
console.log(arrowFunction03()); // 90


// String + number → concatenation
const arrowFunction04 = () => "45" + 45;
console.log(arrowFunction04()); // "4545"

const arrowFunction05 = () => 45 + "45";
console.log(arrowFunction05()); // "4545"


// Single parameter → no parentheses needed
const arrowFunction06 = value01 => value01;
console.log(arrowFunction06("Ayush"));



// ---------------------- ARROW FUNCTION LIMITATIONS ----------------------

const arrowFunction07 = (value01, value02) => {
  console.log(value01);
  console.log(value02);

  // Arrow functions do NOT have their own 'arguments'
  // console.log(arguments); // ReferenceError

  return value01 + " and " + value02;
};

let resultFromArrowFunction07 = arrowFunction07("Ayush", "Aryan");
console.log(resultFromArrowFunction07);



// ---------------------- HIGHER ORDER & CALLBACK FUNCTIONS ----------------------

/*
Definition:

1. Higher Order Function (HOF):
   - A function that either:
     ✔ takes another function as argument
     ✔ OR returns a function

2. Callback Function:
   - A function passed as an argument to another function
   - It gets executed inside the higher order function
*/


// Higher Order Function
function calculator(value1, value2, callback) {
  // callback function is executed here
  return callback(value1, value2);
}


// Passing different callback functions

// Addition
console.log(
  calculator(10, 20, (a, b) => a + b)
); // 30

// Multiplication
console.log(
  calculator(10, 20, (a, b) => a * b)
); // 200

//Subtraction
console.log(
  calculator(10, 20, (a, b) => a - b)
); // -10

// Division
console.log(
  calculator(10, 20, (a, b) => a / b)
); // 0.5

// Modulo
console.log(
  calculator(10, 20, (a, b) => a % b)
); // 10

//Concatenation
console.log(
  calculator("Hello ", "World", (a, b) => a + b)
); // "Hello World"



// Recursion → a function that calls itself

function findTheSumOfTheDigit(num) {
  if(num === 0) return 0;
  return num + findTheSumOfTheDigit(num-1)
}
console.log(findTheSumOfTheDigit(5)); // 15 (5+4+3+2+1)


function factorial(num) {
  if(num === 0) return 1;
  return num * factorial(num-1);
}
console.log(factorial(5)); // 120 (5*4*3*2*1)


// ---------------------- IMPORTANT NOTES ----------------------

// Function Declaration → hoisted
// Function Expression → not hoisted
// arguments → available only in normal functions
// Arrow Function → no arguments, no own 'this'
// Unary + → converts string to number
// String + Number → concatenation