document.getElementById("User-name").addEventListener("invalid", myFunction);
    function myFunction() {
        alert("Name should contain 2 and more letters. e.g. John");
    }


document.getElementById("User-surname").addEventListener("invalid", myFunction);
    function myFunction() {
        alert("Surname should contain 2 and more letters. e.g. Lebowski");
    }


document.getElementById("User-mobile").addEventListener("invalid", myFunction);
    function myFunction() {
        alert("Mobile should be like: e.g. +37529XXXXXXX");
    }


document.getElementById("User-email").addEventListener("invalid", myFunction);
    function myFunction() {
        alert("Email should be like: e.g. something@gmail.com");
    }

document.getElementById("pwd").addEventListener("invalid", myFunction);
    function myFunction() {
        alert("Password shouldn't have symbols! Only numbers and letters");
    }

document.getElementById("pwd2").addEventListener("invalid", myFunction);
    function myFunction() {
        alert("Password shouldn't have symbols! Only numbers and letters");
    }
