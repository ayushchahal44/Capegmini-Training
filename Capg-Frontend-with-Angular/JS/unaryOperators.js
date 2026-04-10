// ---------------------- UNARY OPERATORS ----------------------

/*
Unary → works on ONE operand

Example:
++x, x--, +x
*/


// ---------------------- INCREMENT (++) ----------------------

/*
Increases value by 1

Prefix (++x)  → increase first, then use
Postfix (x++) → use first, then increase
*/

let value01 = 10;
console.log(++value01); // 11 (updated first)

let value02 = 10;
console.log(value02++); // 10 (used first)
console.log(value02);   // 11 (updated after)



// ---------------------- DECREMENT (--) ----------------------

/*
Decreases value by 1
Same behavior as increment (prefix & postfix)
*/

function decrement(v1, v2) {
  console.log(--v1, v2--); // prefix, postfix
  console.log(v1, v2);     // updated values
}

decrement(20, 30);
// Output:
// 19 30
// 19 29



// ---------------------- ASSIGNMENT OPERATORS ----------------------

/*
Used to assign/update values

+= → add and assign
-= → subtract and assign
*= → multiply and assign
/= → divide and assign
%= → remainder and assign
*/

let thisYear = 2026;

thisYear += 5;  // 2026 + 5
console.log(thisYear); // 2031

thisYear -= 10; // 2031 - 10
console.log(thisYear); // 2021

thisYear *= 2;  // 2021 * 2
console.log(thisYear); // 4042

thisYear /= 4;  // 4042 / 4
console.log(thisYear); // 1010.5

thisYear %= 3;  // remainder of 1010.5 / 3
console.log(thisYear); // 2.5



// ---------------------- QUICK NOTES ----------------------

/*
++ / -- → increase or decrease by 1
Prefix → first change, then use
Postfix → first use, then change
Assignment operators → shortcut for operations
*/