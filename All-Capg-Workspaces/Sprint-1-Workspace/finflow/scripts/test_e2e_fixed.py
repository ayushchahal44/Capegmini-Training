import requests
import time
email = f"e2e_test_{int(time.time())}@example.com"
print("Signup...")
requests.post("http://localhost:8081/api/auth/signup", json={"email":email,"password":"password123","firstName":"End","lastName":"User"})
print("Login...")
token = requests.post("http://localhost:8081/api/auth/login", json={"email":email,"password":"password123"}).json().get("token")
print("Create Draft...")
headers = {"Authorization": f"Bearer {token}"}
app_id = requests.post("http://localhost:8082/api/applications/draft", headers=headers).json().get("id")
print(f"Created app ID: {app_id}")
print("Get Application By ID...")
app = requests.get(f"http://localhost:8082/api/applications/{app_id}", headers=headers).json()
print(f"Retrieved app status: {app.get('status')}")
