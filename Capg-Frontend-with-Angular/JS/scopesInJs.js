// ---------------------- VARIABLE SCOPES IN JAVASCRIPT ----------------------

/*
Global Scope:
Variables declared outside any function/block
Accessible everywhere
- var, let, const
*/

var message1 = "Heyy";
console.log(message1); // Output: Heyy


/*
Function Scope:
Variables declared inside a function
Accessible only within that function
- var, let, const
*/

function sum() {
  var value01 = 1;
  var value02 = 2;
  let value03 = 3;
  const value04 = 4;
}

sum();

// console.log(value01); // ❌ ReferenceError
// console.log(value02); // ❌ ReferenceError
// console.log(value03); // ❌ ReferenceError
// console.log(value04); // ❌ ReferenceError


/*
Block Scope:
Variables declared inside a block {}
Accessible only within that block
- let, const
*/

if (true) {
  var message2 = "Keep quiet"; // var is NOT block scoped
}

console.log(message2); // Output: Keep quiet


// Correct block scope example
if (true) {
  let message3 = "Push all the tasks to git.";
  const message4 = "Push all the tasks to git.";
}

// console.log(message3); // ❌ ReferenceError
// console.log(message4); // ❌ ReferenceError