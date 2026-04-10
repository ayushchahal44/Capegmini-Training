// ---------------------- setTimeout & setInterval ----------------------

/*
setTimeout → runs once after delay
setInterval → runs repeatedly after interval
*/


// Store interval ID
let intervalId = setInterval(() => {
  console.log("Sir, I am here to attend the class");
}, 2000);


// Runs immediately (synchronous)
console.log("This will be printed immediately");
console.log("This will also be printed immediately");


// Stop interval after 5 seconds
setTimeout(() => {
  clearInterval(intervalId);
  console.log("Interval stopped");
}, 5000);


// setTimeout example (runs once after 3 sec)
setTimeout(() => {
  console.log("Its been 3 seconds, I am going to attend the class now");
}, 3000);