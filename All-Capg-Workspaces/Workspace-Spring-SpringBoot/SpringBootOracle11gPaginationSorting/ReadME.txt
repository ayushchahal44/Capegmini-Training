
CREATE TABLE EMPLOYEE (
    ID NUMBER PRIMARY KEY,
    NAME VARCHAR2(50),
    DEPARTMENT VARCHAR2(50),
    SALARY NUMBER
);

INSERT ALL
  INTO EMPLOYEE (ID, NAME, DEPARTMENT, SALARY) VALUES (1, 'Ramesh', 'IT', 50000)
  INTO EMPLOYEE (ID, NAME, DEPARTMENT, SALARY) VALUES (2, 'Suresh', 'HR', 40000)
  INTO EMPLOYEE (ID, NAME, DEPARTMENT, SALARY) VALUES (3, 'Mahesh', 'Finance', 60000)
  INTO EMPLOYEE (ID, NAME, DEPARTMENT, SALARY) VALUES (4, 'Kiran', 'IT', 55000)
  INTO EMPLOYEE (ID, NAME, DEPARTMENT, SALARY) VALUES (5, 'Anitha', 'Admin', 45000)
SELECT * FROM DUAL;
commit;

Pagination
GET 
http://localhost:9090/employees?page=0&size=3
Sorting
GET 
http://localhost:9090/employees?sort=name&dir=ASC
Sorting DESC
GET 
http://localhost:9090/employees?sort=salary&dir=DESC
 Pagination + Sorting
GET
 http://localhost:9090/employees?page=1&size=3&sort=salary&dir=DESC