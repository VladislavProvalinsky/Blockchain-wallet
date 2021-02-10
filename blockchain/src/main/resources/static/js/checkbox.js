function myFunction() {
  // Get the checkbox
  var checkBox = document.getElementById("myCheck");
  // Get the output text
  var comission = document.getElementById("comission");

  // If the checkbox is checked, display the output text
  if (checkBox.checked == true){
    comission.type = "text";
  } else {
    comission.type = "hidden";
  }
}