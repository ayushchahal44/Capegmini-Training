// A function inside another function is called a nested function

function parent(value1, value2) {
  // child function (nested function)
  return function child(value3, value4) {
    return "Child function: " + (value1 + value2 + value3 + value4);
  };
}

// Calling parent() returns child function, then we call it
let result = parent(4, 7)(3, 5);

console.log(result); // Output: Child function: 19