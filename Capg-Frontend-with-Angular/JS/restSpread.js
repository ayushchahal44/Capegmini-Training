// ================= REST OPERATOR =================
// Rest operator (...) collects remaining values into an array

function receiveValues(a, b, c, ...rest) {
  // First 3 values go into a, b, c
  console.log("a:", a)
  console.log("b:", b)
  console.log("c:", c)

  // Remaining values go into 'rest' as an array
  console.log("rest:", rest)
}

// Calling function with multiple arguments
receiveValues(1, 2, 3, 4, 5, 6, 7, 8, 9)



// ================= SPREAD OPERATOR =================
// Spread operator (...) expands array/object values

let fruits1 = ['apple', 'banana', 'orange']

let fruits2 = ['grape', 'melon', 'kiwi', ...fruits1]

console.log("fruits2:", fruits2)

// Merge multiple arrays
let fruits3 = [...fruits1, ...fruits2, 'Pineapple', 'Mango']

console.log("fruits3:", fruits3)



// ================= OBJECT SPREAD =================
let obj1 = { name: 'Aryan', age: 21 }
let obj2 = { work: 'Student', city: 'Bijnor' }

// Combine objects using spread
let obj3 = { ...obj1, ...obj2 }

console.log("obj3:", obj3)

// IMPORTANT: If same key exists, last one overrides
let obj4 = { name: 'Aryan' }
let obj5 = { name: 'Ayush' }

let obj6 = { ...obj4, ...obj5 }
console.log("override example:", obj6)
// Output: { name: 'Ayush' }



// ================= ARRAY DESTRUCTURING =================
// Extract values from array into variables

let arr1 = ['Ayush', 'Aryan', 'Navdeep', 'Sneh', 'Shiva']

// Normal destructuring
let [stu1, stu2, stu3, stu4, stu5] = arr1

console.log(stu1) // Ayush
console.log(stu2) // Aryan
console.log(stu3) // Navdeep
console.log(stu4) // Sneh
console.log(stu5) // Shiva

// Using rest in destructuring
let [first, second, ...others] = arr1

console.log("first:", first)   // Ayush
console.log("second:", second) // Aryan
console.log("others:", others) // ['Navdeep','Sneh','Shiva']



// ================= OBJECT DESTRUCTURING =================
// Extract values from object into variables

let o1 = {
  name: 'Ayush',
  city: 'Bijnor',
  Phone: 9760203187,
}

// Basic destructuring
let { name, city, Phone } = o1

console.log("name:", name)   // Ayush
console.log("city:", city)
console.log("Phone:", Phone)

// With default value (if property missing)
let { country = "India" } = o1
console.log("country:", country)



// ================= BONUS =================
// Spread → expands values
let nums = [10, 20, 30]
console.log(...nums) // 10 20 30

// Rest → collects values
function demo(...args) {
  console.log(args)
}

demo(10, 20, 30, 40)



let arr = [
  [1, 2, 3],
  [2, 3],
  {
    name: "Ayush",
    obj1: {
      clg: "Lpu"
    }
  },
  (a, b) => a + b
];

let [[a, b, c], [x, y], { name:n, obj1: { clg } }, add] = arr;

console.log(a, b, c); // 1 2 3
console.log(x, y);    // 2 3
console.log(n);    // Ayush
console.log(clg);     // Lpu
console.log(add(10, 5)); // 15