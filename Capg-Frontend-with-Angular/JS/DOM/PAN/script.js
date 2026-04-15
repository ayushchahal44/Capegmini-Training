var pat = /^[a-zA-Z]{5}[0-9]{4}[a-zA-Z]$/;
function validatePan(){
    let pan = document.getElementById("pan").value;
    if(pat.test(pan)){
        document.getElementById("result").innerHTML = "Valid PAN: Congratulations..";
        document.getElementById("result").style.color = "green";
        document.getElementById("pan").style.backgroundColor = "green";
    }else{
        document.getElementById("result").innerHTML = "Invalid PAN: Check once again..";
        document.getElementById("result").style.color = "red";
        document.getElementById("pan").style.backgroundColor = "red";
    }
}
