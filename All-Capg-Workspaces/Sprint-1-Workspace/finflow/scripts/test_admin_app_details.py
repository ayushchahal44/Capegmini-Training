import requests
# Get token
email = "admin@gmail.com"
password = "admin@123"
token = requests.post("http://localhost:8081/api/auth/login", json={"email":email,"password":password}).json().get("token")
headers = {"Authorization": f"Bearer {token}"}
resp = requests.get("http://localhost:8080/gateway/applications/5", headers=headers)
print(f"Status: {resp.status_code}")
print(f"Body: {resp.text}")
