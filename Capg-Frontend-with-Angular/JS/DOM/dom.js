// ================= SELECTING ELEMENTS =================

// Select element by ID
let h1 = document.getElementById("heading01");

// Print the whole element
console.log(h1);

// Print only text inside element
console.log(h1.innerText);


// Select using querySelector (CSS style → FIRST match only)
let h2 = document.querySelector("#heading02");
let h3 = document.querySelector("#heading03");


// ================= querySelectorAll =================
// Select ALL elements with same class

let allParas = document.querySelectorAll(".para");

// NodeList → loop through all elements
allParas.forEach((item, index) => {
  console.log("Paragraph", index, ":", item.innerText);

  // Change style for all
  item.style.color = "blue";
});


// ================= CHANGING CONTENT =================

// Change text (safe, only text)
h1.innerText = "Content is changed for H1";

// Change HTML (can add tags)
h2.innerHTML = "<p>Heyy 👋</p>";


// ================= DIFFERENCE =================

// innerText → only text (no HTML parsing)
h3.innerText = "<b>Bold Text</b>"; 
// Output: <b>Bold Text</b>

// innerHTML → parses HTML
h3.innerHTML = "<b>Bold Text</b>"; 
// Output: Bold Text (in bold)


// ================= STYLING =================

// Change color using JS
h1.style.color = "red";
h2.style.backgroundColor = "yellow";


// ================= EXTRA =================

// Change attribute
h1.setAttribute("title", "This is a heading");

// Add class
h1.classList.add("myClass");

// Remove class
h1.classList.remove("myClass");


// ================= BONUS =================
// Access specific element from querySelectorAll

if (allParas.length > 0) {
  console.log("First para:", allParas[0].innerText);
}