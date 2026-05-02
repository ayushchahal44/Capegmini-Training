import mysql.connector

conn = mysql.connector.connect(host='localhost', user='root', password='ayush', database='finflowdb')
cursor = conn.cursor()
cursor.execute("UPDATE users SET role='ADMIN' WHERE email='admin@gmail.com'")
conn.commit()
print('Elevated admin@gmail.com to ADMIN role successfully.')
cursor.close()
conn.close()
