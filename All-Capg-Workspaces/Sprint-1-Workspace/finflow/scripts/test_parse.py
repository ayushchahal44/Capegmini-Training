import requests
import time
email = f"parse_test_{int(time.time())}@example.com"
requests.post("http://localhost:8081/api/auth/signup", json={"email":email,"password":"password123","firstName":"End","lastName":"User"})
token = requests.post("http://localhost:8081/api/auth/login", json={"email":email,"password":"password123"}).json().get("token")
headers = {"Authorization": f"Bearer {token}", "Content-Type": "application/json"}
app_id = requests.post("http://localhost:8082/api/applications/draft", headers=headers).json().get("id")
payload = {
    "fullName": "Test",
    "phone": "123",
    "address": "123",
    "dateOfBirth": "",
    "employer": "E",
    "employmentType": "SALARIED",
    "annualIncome": 1000,
    "loanAmount": 1000,
    "tenureMonths": 12,
    "loanPurpose": "P"
}
resp = requests.put(f"http://localhost:8082/api/applications/{app_id}/draft", json=payload, headers=headers)
print(resp.status_code)
print(resp.text)
