import requests
import time
import json
import os

AUTH_URL = "http://localhost:8081/api/auth"
APP_URL = "http://localhost:8082/api/applications"
DOC_URL = "http://localhost:8083/api/documents"
ADMIN_URL = "http://localhost:8084/api/admin"

print("--- FinFlow API Testing ---")

def test_flow():
    email = f"test_{int(time.time())}@example.com"
    password = "password123"
    
    # 1. Signup
    print(f"\n1. Testing Signup for {email}...")
    signup_data = {
        "email": email,
        "password": password,
        "firstName": "Test",
        "lastName": "User"
    }
    try:
        resp = requests.post(f"{AUTH_URL}/signup", json=signup_data)
        print(f"Signup Status: {resp.status_code}")
        if resp.status_code not in [200, 201]:
            print(f"Error: {resp.text}")
            return
        token = resp.json().get("token")
        print("Signup Successful. Token received.")
    except Exception as e:
        print(f"Signup failed: {e}")
        return

    headers = {"Authorization": f"Bearer {token}"}

    # 2. Login
    print("\n2. Testing Login...")
    login_data = {"email": email, "password": password}
    resp = requests.post(f"{AUTH_URL}/login", json=login_data)
    print(f"Login Status: {resp.status_code}")
    if resp.status_code != 200:
        print(f"Error: {resp.text}")
        return
    token = resp.json().get("token")
    headers = {"Authorization": f"Bearer {token}"}

    # 3. Create Draft Application
    print("\n3. Testing Create Draft Application...")
    # Based on ApplicationController, it's POST /api/applications/draft
    resp = requests.post(f"{APP_URL}/draft", headers=headers)
    print(f"Create Draft Status: {resp.status_code}")
    if resp.status_code not in [200, 201]:
        print(f"Error: {resp.text}")
        # Try fallbacks if needed, but let's stick to what we saw in code
        return
    app_id = resp.json().get("id")
    print(f"Draft Created with ID: {app_id}")

    # 4. Update Draft
    print("\n4. Testing Update Draft...")
    update_data = {
        "fullName": "Test User",
        "phone": "9876543210",
        "address": "123 Testing Lane",
        "dateOfBirth": "1990-01-01",
        "employer": "Test Corp",
        "annualIncome": 1000000,
        "employmentType": "SALARIED",
        "loanAmount": 500000,
        "tenureMonths": 12,
        "loanPurpose": "Testing"
    }
    resp = requests.put(f"{APP_URL}/{app_id}/draft", json=update_data, headers=headers)
    print(f"Update Draft Status: {resp.status_code}")
    if resp.status_code != 200:
        print(f"Error: {resp.text}")
        return

    # 5. Submit Application
    print("\n5. Testing Submit Application...")
    resp = requests.post(f"{APP_URL}/{app_id}/submit", headers=headers)
    print(f"Submit Status: {resp.status_code}")
    if resp.status_code != 200:
        print(f"Error: {resp.text}")
        return

    # 6. Upload Document
    print("\n6. Testing Document Upload...")
    # DocumentController: POST /api/documents/upload
    # Params: applicationId, docType, file
    with open("temp_doc.txt", "w") as f:
        f.write("This is a test document.")
    
    data = {
        'applicationId': app_id,
        'docType': 'ID_PROOF'
    }
    with open("temp_doc.txt", "rb") as f_in:
        files = {
            'file': ('temp_doc.txt', f_in, 'text/plain')
        }
        resp = requests.post(f"{DOC_URL}/upload", headers=headers, files=files, data=data)
    
    print(f"Upload Status: {resp.status_code}")
    if resp.status_code not in [200, 201]:
        print(f"Error: {resp.text}")
    
    os.remove("temp_doc.txt")

    # 7. Admin: List Applications
    # Needs ADMIN role. Usually the first user or a special admin user.
    # If the user we just created isn't admin, this will fail 403.
    # I'll check if I can promote the user or if there's a default admin.
    print("\n7. Testing Admin: List Applications (assuming current user has/can get access)...")
    resp = requests.get(f"{ADMIN_URL}/applications", headers=headers)
    print(f"Admin List Status: {resp.status_code}")
    if resp.status_code == 403:
        print("Forbidden: User is not an admin. Attempting to skip admin tests or elevate if possible.")
    elif resp.status_code == 200:
        print("Admin access verified.")
    else:
        print(f"Error: {resp.text}")

    print("\n--- Testing Complete ---")

if __name__ == "__main__":
    test_flow()
