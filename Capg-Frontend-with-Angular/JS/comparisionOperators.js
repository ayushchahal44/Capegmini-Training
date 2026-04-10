// ---------------------- COMPARISON OPERATORS ----------------------

/*
Used to compare values

==   → value only (loose)
===  → value + type (strict)
!=   → not equal (loose)
!==  → not equal (strict)
> < >= <= → numeric comparison
*/


// ---------------------- == (LOOSE)

console.log(10 == "10"); // true
console.log("Ayush" == "Ayush"); // true


// ---------------------- === (STRICT)

console.log(10 === "10"); // false
console.log(true === 1);  // false
console.log(false === 0); // false


// ---------------------- != and !==

console.log(10 != "10");  // false
console.log(10 !== "10"); // true


// ---------------------- GREATER / LESS

let a = 22;
let b = 21;

console.log(a > b);  // true
console.log(a < b);  // false
console.log(a >= b); // true
console.log(a <= b); // false


// ---------------------- COMBINATION

let c = 20;

console.log(c > b && c > a); // false
console.log(c > b || c > a); // true


// QUICK NOTES
/*
== → type ignored
=== → type checked (use this)
!== → safest not equal
*/