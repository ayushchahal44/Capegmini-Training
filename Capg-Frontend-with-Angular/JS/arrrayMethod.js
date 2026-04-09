// ---------------------- ARRAYS ----------------------

let arr01 = [10, 20, 30, 60, 50];
let arr02 = ["Hey", "Hii", "Hello"];


// ---------------------- CONCAT & JOIN ----------------------

// concat() → merges arrays (does NOT change original arrays)
console.log(arr01.concat(arr02)); 
// [10,20,30,60,50,"Hey","Hii","Hello"]


// join() → converts array to string with separator
console.log(arr01.join("-")); // "10-20-30-60-50"
console.log(arr01.join(""));  // "1020306050"
console.log(arr01.join("@")); // "10@20@30@60@50"
console.log(arr01.join(" ")); // "10 20 30 60 50"

// join() always returns string
console.log(typeof arr02.join(" ")); // "string"



// ---------------------- SORTING ----------------------

// sort() → sorts array (MODIFIES original array)
arr01.sort((a, b) => a - b); 
console.log(arr01); 
// [10,20,30,50,60]

// toSorted() → returns NEW sorted array (does NOT modify original)
console.log(arr01.toSorted((a, b) => b - a)); 
// [60,50,30,20,10]



// ---------------------- SEARCHING ----------------------

// indexOf() → returns index of element (-1 if not found)
console.log(arr01.indexOf(50)); // 3



// ---------------------- NESTED ARRAY ----------------------

let arr03 = [
  [10, 20, 30],
  [40, 50, 60],
  [70, 80, 90]
];


// flat() → flattens array (1 level by default)
console.log(arr03.flat()); 
// [10,20,30,40,50,60,70,80,90]



// ---------------------- REVERSE ----------------------

// reverse() → modifies original array
// console.log(arr03.reverse());

// toReversed() → returns new reversed array (safe)
console.log(arr03.toReversed()); 
// [[70,80,90],[40,50,60],[10,20,30]]



// ---------------------- IMPORTANT NOTES ----------------------

// concat() → non-mutating
// join() → always returns string
// sort() → mutates original array
// toSorted() → safe (immutable)
// reverse() → mutates original
// toReversed() → safe
// flat() → removes nesting  