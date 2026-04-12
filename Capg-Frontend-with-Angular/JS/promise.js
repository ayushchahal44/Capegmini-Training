// promise - A promise is an object that represents the eventual completion (or failure) of an asynchronous operation and its resulting value. It allows you to write asynchronous code in a more synchronous-like manner, making it easier to read and maintain.

// A promise can be in one of three states:
// 1. Pending: The initial state, neither fulfilled nor rejected.
// 2. Fulfilled: The operation completed successfully, and the promise has a value.
// 3. Rejected: The operation failed, and the promise has a reason for the failure.


let promise = new Promise((resolve, reject) => {
    resolve("Promise resolved successfully!");
    // reject("Promise rejected!"); // This will be ignored since resolve is called first
  });

  console.log(promise); // Output: Promise { 'Promise resolved successfully!' }
  promise.then((data)=>{
    console.log(data); // Output: Promise resolved successfully!
  }).catch((error)=>{
    console.log(error); // This will not be called since the promise is resolved
  }).finally(()=>{
    console.log("This will always be executed regardless of the promise outcome.")
  });