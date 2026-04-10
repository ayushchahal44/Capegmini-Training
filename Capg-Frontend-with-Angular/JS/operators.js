// ---------------------- OPERATORS ----------------------

/*
Operators perform operations on values

Types:
- Unary → 1 value
- Binary → 2 values
- Ternary → condition ? true : false

JS does automatic type conversion (type coercion)
*/


// ADDITION

console.log(20 + 45); // 65

console.log("Ayush" + " Chahal"); // string join

console.log("Ayush" + 1); // "Ayush1"
console.log(1 + "Ayush"); // "1Ayush"
// string + anything → string


// ➖ SUBTRACTION

console.log(40 - 30); // 10
console.log("Ayush" - 10); // NaN


// ---------------------- ARRAY ----------------------

let arr1 = [10, 20, 30, "Hii"];
let arr2 = [84, 82, 53, "Hello"];

console.log(arr1[0] + arr2[2]); // 63
console.log(arr1[3] + arr2[3]); // "HiiHello"


// ---------------------- UNARY ----------------------

console.log(+"45"); // 45 → string → number

let x = 5;
x++;
console.log(x); // 6


// ---------------------- TERNARY ----------------------

let age = 18;
console.log(age >= 18 ? "Adult" : "Minor");


// ---------------------- EXTRA ----------------------

console.log("Ayush" / "Chahal"); // NaN
console.log("Ayush" % "Chahal"); // NaN


// BOOLEAN → NUMBER
console.log(true + true);   // 2
console.log(true + false);  // 1


// BIGINT
console.log(4656n + 1234n); // 5890n


// OBJECT

let marks = { math: 10, sci: 20, eng: 30.5 };

let avg = (marks.math + marks.sci + marks.eng) / 3;

console.log(avg);
console.log(Math.floor(avg));
console.log(Math.ceil(avg));
console.log(Math.round(avg));


// QUICK NOTES
/*
+ → addition / concatenation
- → numbers only
NaN → invalid number
true = 1, false = 0
*/