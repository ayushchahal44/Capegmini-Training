// ---------------------- DECISION MAKING IN JAVASCRIPT ----------------------

// Decision making allows us to execute different blocks of code
// based on certain conditions.

// Types of decision-making statements:
// 1. if statement
// 2. if-else statement
// 3. nested if statement
// 4. switch statement
// 5. ternary operator


// ---------------------- TERNARY OPERATOR ----------------------
/*
Ternary operator is a shorthand for if-else.

Syntax:
condition ? value_if_true : value_if_false

Example:
*/
let age = 18;
let result = age >= 18 ? "Adult" : "Minor";
console.log(result); // Output: Adult


// ---------------------- NESTED IF STATEMENT ----------------------

// Scenario: Check if sir is available AND if we are in the mood

let sirIsAvailable = true;
let mood = true;

if (sirIsAvailable) {
  // If sir is available, check mood
  if (mood) {
    // Function to attend class
    function attendClass() {
      console.log("We need to go and attend the class");
    }
    attendClass();
  } else {
    // If no mood
    console.log("Sir is available but no mood to attend class");
    console.log("We will give excuse to the sir");
  }
}


// ---------------------- SIMPLE IF-ELSE ----------------------

// Scenario: Check if food is available

let foodAvailable = true;

if (foodAvailable) {
  console.log("Have some food");
} else {
  console.log("Purchase some food");
}


// ---------------------- TERNARY OPERATOR EXAMPLE ----------------------

// Scenario: Compare ages

let ayushAge = 22;
let chahalAge = 21;

// Using ternary operator for comparison
let resultAge =
  ayushAge > chahalAge
    ? "Ayush is older than Chahal"
    : "Chahal is older than Ayush";

console.log(resultAge);


// ---------------------- IMPORTANT NOTES ----------------------
/*
1. Ternary operators are best for simple conditions.
2. Avoid deep nesting of ternary operators (reduces readability).
3. Use if-else for complex logic.
4. Nested if is useful when multiple conditions depend on each other.
*/