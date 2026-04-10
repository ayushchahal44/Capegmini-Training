// ---------------------- LOGICAL OPERATORS ----------------------

/*
Logical operators:
&& → AND (both true)
|| → OR (any one true)
!  → NOT (reverse value)
*/


// ---------------------- AND (&&)

let check = (isSirPresent, isStudentPresent) => {
  if (isSirPresent && isStudentPresent) {
    console.log("Class could happen");
  } else {
    console.log("Class cannot happen");
  }
};

check(true, true);   // happen
check(true, false);  // cannot
check(false, true);  // cannot
check(false, false); // cannot


// ---------------------- OR (||)

console.log(true || false);  // true
console.log(false || false); // false


// ---------------------- NOT (!)

console.log(!true);  // false
console.log(!false); // true


// Example use case
let isDarkTheme = false;
console.log("Dark mode?", isDarkTheme);


// QUICK NOTES
/*
&& → all true
|| → any true
!  → opposite
*/