import requests

email = "admin@gmail.com"
password = "admin@123"

# 1. Signup the user
print("Signing up user...")
try:
    resp = requests.post("http://localhost:8081/api/auth/signup", json={
        "email": email,
        "password": password,
        "firstName": "System",
        "lastName": "Admin"
    })
    print(f"Signup response: {resp.status_code} - {resp.text}")
except Exception as e:
    print(f"Error during signup: {e}")

