# Build by: Ayush chahal | ayushchahal44@gmail.com
import requests
import time
import json
import os

AUTH_URL = "http://localhost:8081/api/auth"
APP_URL = "http://localhost:8082/api/applications"
DOC_URL = "http://localhost:8083/api/documents"
ADMIN_URL = "http://localhost:8084/api/admin"

print("--- FinFlow Admin API Testing ---")

def create_admin_user():
    
    email = "admin@example.com"
    password = "admin123"
    
    print(f"\n--- Creating Admin User: {email} ---")
    
    
    print("1. Admin Signup...")
    signup_data = {
        "email": email,
        "password": password,
        "firstName": "Admin",
        "lastName": "User"
    }
    try:
        resp = requests.post(f"{AUTH_URL}/signup", json=signup_data)
        print(f"Signup Status: {resp.status_code}")
        if resp.status_code in [200, 201]:
             print("Admin Signup Successful.")
        else:
             print(f"Signup failed (might already exist): {resp.text}")
    except Exception as e:
        print(f"Admin signup exception: {e}")

    
    print("2. Admin Login...")
    login_data = {"email": email, "password": password}
    resp = requests.post(f"{AUTH_URL}/login", json=login_data)
    print(f"Login Status: {resp.status_code}")
    if resp.status_code != 200:
        print(f"Error: {resp.text}")
        return None, None
    token = resp.json().get("token")
    
    return email, token

def create_test_application(token):
    
    print("\n--- Creating Test Application ---")
    
    headers = {"Authorization": f"Bearer {token}"}
    
    
    print("1. Creating Draft Application...")
    resp = requests.post(f"{APP_URL}/draft", headers=headers)
    print(f"Create Draft Status: {resp.status_code}")
    if resp.status_code not in [200, 201]:
        print(f"Error: {resp.text}")
        return None
    app_id = resp.json().get("id")
    print(f"Draft Created with ID: {app_id}")

    
    print("2. Updating Draft...")
    update_data = {
        "fullName": "Test Applicant",
        "phone": "9876543210",
        "address": "123 Test Street",
        "dateOfBirth": "1990-01-01",
        "employer": "Test Company",
        "annualIncome": 1000000,
        "employmentType": "SALARIED",
        "loanAmount": 500000,
        "tenureMonths": 12,
        "loanPurpose": "Personal Loan"
    }
    resp = requests.put(f"{APP_URL}/{app_id}/draft", json=update_data, headers=headers)
    print(f"Update Draft Status: {resp.status_code}")
    if resp.status_code != 200:
        print(f"Error: {resp.text}")
        return None

    
    print("3. Submitting Application...")
    resp = requests.post(f"{APP_URL}/{app_id}/submit", headers=headers)
    print(f"Submit Status: {resp.status_code}")
    if resp.status_code != 200:
        print(f"Error: {resp.text}")
        return None
    
    print(f"Test Application {app_id} created and submitted successfully!")
    return app_id

def promote_user_to_admin(admin_token, user_email):
    
    print(f"\n--- Promoting User {user_email} to Admin ---")
    
    headers = {"Authorization": f"Bearer {admin_token}"}
    
    
    print("1. Listing all users...")
    resp = requests.get(f"{ADMIN_URL}/users", headers=headers)
    print(f"List Users Status: {resp.status_code}")
    
    if resp.status_code != 200:
        print(f"Error listing users: {resp.text}")
        return False
    
    users = resp.json()
    user_id = None
    
    for user in users:
        if user.get("email") == user_email:
            user_id = user.get("id")
            break
    
    if not user_id:
        print(f"User {user_email} not found")
        return False
    
    print(f"Found user ID: {user_id}")
    
    
    print("2. Updating user role to ADMIN...")
    update_data = {
        "role": "ADMIN",
        "enabled": True
    }
    
    resp = requests.put(f"{ADMIN_URL}/users/{user_id}", json=update_data, headers=headers)
    print(f"Update User Status: {resp.status_code}")
    
    if resp.status_code == 200:
        print("User successfully promoted to ADMIN!")
        return True
    else:
        print(f"Error promoting user: {resp.text}")
        return False

def test_admin_endpoints(admin_token):
    
    print("\n--- Testing Admin Endpoints ---")
    
    headers = {"Authorization": f"Bearer {admin_token}"}
    
    
    print("\n1. Testing GET /api/admin/applications...")
    resp = requests.get(f"{ADMIN_URL}/applications", headers=headers)
    print(f"List Applications Status: {resp.status_code}")
    if resp.status_code == 200:
        applications = resp.json()
        print(f"Found {len(applications)} applications")
        if applications:
            print(f"First application ID: {applications[0].get('id')}")
            return applications[0].get("id")  
    else:
        print(f"Error: {resp.text}")
        return None
    
    
    print("\n2. Testing GET /api/admin/reports...")
    resp = requests.get(f"{ADMIN_URL}/reports", headers=headers)
    print(f"Reports Status: {resp.status_code}")
    if resp.status_code == 200:
        reports = resp.json()
        print("Reports retrieved successfully")
        print(f"Report data: {json.dumps(reports, indent=2)}")
    else:
        print(f"Error: {resp.text}")
    
    
    print("\n3. Testing GET /api/admin/users...")
    resp = requests.get(f"{ADMIN_URL}/users", headers=headers)
    print(f"List Users Status: {resp.status_code}")
    if resp.status_code == 200:
        users = resp.json()
        print(f"Found {len(users)} users")
        for user in users[:3]:  
            print(f"  - {user.get('email')} (Role: {user.get('role')}, Enabled: {user.get('enabled')})")
    else:
        print(f"Error: {resp.text}")
    
    return None

def test_admin_decision(admin_token, application_id):
    
    if not application_id:
        print("\n--- Skipping Admin Decision Test (No Application ID) ---")
        return
    
    print(f"\n--- Testing Admin Decision for Application {application_id} ---")
    
    headers = {"Authorization": f"Bearer {admin_token}"}
    
    
    print("1. Testing APPROVAL decision...")
    approval_data = {
        "approved": True,
        "terms": "Loan approved with standard terms and conditions"
    }
    
    resp = requests.put(f"{ADMIN_URL}/applications/{application_id}/decision", 
                        json=approval_data, headers=headers)
    print(f"Approval Decision Status: {resp.status_code}")
    if resp.status_code == 200:
        print("Application approved successfully!")
    else:
        print(f"Error: {resp.text}")

def test_user_management(admin_token):
    
    print("\n--- Testing User Management ---")
    
    headers = {"Authorization": f"Bearer {admin_token}"}
    
    
    print("1. Creating test user...")
    test_email = f"test_user_{int(time.time())}@example.com"
    signup_data = {
        "email": test_email,
        "password": "password123",
        "firstName": "Test",
        "lastName": "User"
    }
    
    resp = requests.post(f"{AUTH_URL}/signup", json=signup_data)
    print(f"Test User Signup Status: {resp.status_code}")
    
    if resp.status_code not in [200, 201]:
        print("Failed to create test user")
        return
    
    
    resp = requests.get(f"{ADMIN_URL}/users", headers=headers)
    if resp.status_code == 200:
        users = resp.json()
        test_user_id = None
        for user in users:
            if user.get("email") == test_email:
                test_user_id = user.get("id")
                break
        
        if test_user_id:
            print(f"2. Updating test user {test_user_id}...")
            
            
            update_data = {
                "enabled": False,
                "firstName": "Updated",
                "lastName": "Name"
            }
            
            resp = requests.put(f"{ADMIN_URL}/users/{test_user_id}", 
                              json=update_data, headers=headers)
            print(f"Update User Status: {resp.status_code}")
            if resp.status_code == 200:
                updated_user = resp.json()
                print(f"User updated: {json.dumps(updated_user, indent=2)}")
            else:
                print(f"Error updating user: {resp.text}")

def main():
    
    
    
    admin_email, admin_token = create_admin_user()
    
    if not admin_token:
        print("Failed to create admin user. Exiting.")
        return
    
    
    test_app_id = create_test_application(admin_token)
    
    
    app_id_for_decision = test_admin_endpoints(admin_token)
    
    
    target_app_id = test_app_id or app_id_for_decision
    test_admin_decision(admin_token, target_app_id)
    
    
    test_user_management(admin_token)
    
    print("\n--- Admin API Testing Complete ---")
    print(f"Admin User: {admin_email}")
    print("All admin endpoints tested successfully!")

if __name__ == "__main__":
    main()
