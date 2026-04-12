const form = document.getElementById("signupForm");

form.addEventListener("submit", function (e) {
  e.preventDefault();

  let username = document.getElementById("username");
  let email = document.getElementById("email");
  let password = document.getElementById("password");
  let confirmPassword = document.getElementById("confirmPassword");

  let isValid = true;

  // Username validation
  if (username.value.trim().length < 3 || username.value.trim().length > 25) {
    setError(username, "Username must be between 3 and 25 characters");
    isValid = false;
  } else {
    setSuccess(username);
  }

  // Email validation
  let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  if (!emailPattern.test(email.value.trim())) {
    setError(email, "Enter a valid email");
    isValid = false;
  } else {
    setSuccess(email);
  }

  // Password validation
  let passPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/;

  if (!passPattern.test(password.value.trim())) {
    setError(
      password,
      "Password must have at least 8 characters, including lowercase, uppercase, number & special character (!@#$%^&*)"
    );
    isValid = false;
  } else {
    setSuccess(password);
  }

  // Confirm password validation
  if (confirmPassword.value.trim() === "") {
    setError(confirmPassword, "Please enter the password again");
    isValid = false;
  } else if (confirmPassword.value !== password.value) {
    setError(confirmPassword, "Passwords do not match");
    isValid = false;
  } else {
    setSuccess(confirmPassword);
  }

  // Final submit
  if (isValid) {
    alert("Form Submitted Successfully!");
  }
});

function setError(input, message) {
  input.classList.add("error");
  input.classList.remove("success");
  input.nextElementSibling.innerText = message;
}

function setSuccess(input) {
  input.classList.add("success");
  input.classList.remove("error");

}