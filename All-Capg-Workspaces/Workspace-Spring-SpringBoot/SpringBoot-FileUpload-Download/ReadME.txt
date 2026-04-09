
CREATE SEQUENCE FILE_SEQ
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

Verify Sequence Exists
-----------------------
SELECT sequence_name
FROM user_sequences
WHERE sequence_name = 'FILE_SEQ';


POST
 http://localhost:9090/files/upload
Body :  form-data
Key: file
Value: choose any file


choose : form-data
 key : file  
   after Key right side  File/Test : Select File 
   then value is : choose the location 
   D:\mydata\product.txt
   out put is  :
   File uploaded successfully. File ID = 1

Postman setup should look like this
----------------------------------
Field	  :Value
Method	  :POST
URL	      :http://localhost:9090/files/upload
Body	  :form-data
Key	      :file
Type	  :File
Value	product.txt

Headers 
Content-Type


File Download
GET 

http://localhost:9090/files/download/1