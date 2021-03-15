<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<jsp:include page = "header-login.jsp"/>

<div class="container">

<h3>Registration Form:</h3>
<br>

<form action="/blockchain/registration/new_user" modelAttribute="user" method="post">
  <div class="form-group">
      <label for="User-name">User name</label>
      <input type="text" value="${user.name}" path="name" class="form-control" name = "name" id="User-name" placeholder="User name" pattern="[A-Za-z]{2,30}" required>
  </div>
  <div class="form-group">
      <label for="User-surname">User surname</label>
      <input type="text" value="${user.surname}" path="surname" class="form-control" name = "surname" id="User-surname" placeholder="User surname" pattern="[A-Za-z]{2,30}" required>
  </div>
  <div class="form-group">
      <label for="User-mobile">Mobile phone</label>
      <input type="text" value="${user.mobile}" path="mobile" class="form-control" name = "mobile" id="User-mobile" placeholder="Mobile phone" pattern="\+375\d{9}" required>
  </div>
  <div class="form-group">
    <label for="User-email">Email address</label>
    <input type="email" value="${user.username}" path="email" class="form-control" name = "username" id="User-email" aria-describedby="emailHelp" placeholder="Email address" pattern=".+@.+\..+" required>
    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
    ${emailError}
  </div>
  <div class="form-group">
     <label for="pwd">Password:</label>
     <input type="password" path="password" class="form-control" id="pwd" placeholder="Enter password" name="password" pattern="[\d*A-Za-z]*" required>
  </div>
  <div class="form-group">
     <label for="pwd">Confirm password:</label>
     <input type="password" path="password" class="form-control" id="pwd" placeholder="Confirm your password" name="passwordConfirm" pattern="[\d*A-Za-z]*" required>
     ${passwordError}
  </div>
      <div class="form-group form-check">
        <label class="form-check-label">
          <input class="form-check-input" type="checkbox" name="remember" required> I agree on private policy
          <div class="valid-feedback">Valid.</div>
          <div class="invalid-feedback">Check this checkbox to continue.</div>
        </label>
      </div>
  <div>
     <csrf disabled="true"/>
  </div>
  <button type="submit" class="btn btn-success">Submit</button>
  <br><br>
  <button onclick="window.location.href='/blockchain'" type="button" class="btn btn-primary">Back</button>
</form>
</div>

<script>
    document.getElementById("User-name").addEventListener("invalid", myFunction);
        function myFunction() {
            alert("Name should contain 2 and more letters. e.g. John");
        }
</script>
<script>
    document.getElementById("User-surname").addEventListener("invalid", myFunction);
        function myFunction() {
            alert("Surname should contain 2 and more letters. e.g. Lebowski");
        }
</script>
<script>
    document.getElementById("User-mobile").addEventListener("invalid", myFunction);
        function myFunction() {
            alert("Mobile should be like: e.g. +37529XXXXXXX");
        }
</script>
<script>
    document.getElementById("User-email").addEventListener("invalid", myFunction);
        function myFunction() {
            alert("Email should be like: e.g. something@gmail.com");
        }
</script>
<script>
    document.getElementById("pwd").addEventListener("invalid", myFunction);
        function myFunction() {
            alert("Password shouldn't have symbols! Only numbers and letters");
        }
</script>

<jsp:include page = "footer.jsp"/>