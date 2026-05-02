import requests
import time
email = f"ui_test_{int(time.time())}@example.com"
print(f"Creating {email}")
resp = requests.post("http://localhost:8081/api/auth/signup", json={"email":email,"password":"password123","firstName":"T","lastName":"U"})
token = requests.post("http://localhost:8081/api/auth/login", json={"email":email,"password":"password123"}).json().get("token")
print(requests.get("http://localhost:8082/api/applications/5", headers={"Authorization": f"Bearer {token}"}).text)
