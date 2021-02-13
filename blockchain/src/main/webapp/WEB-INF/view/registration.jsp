<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<jsp:include page = "header.jsp"/>

<body class="w3-light-grey">
<script src="/blockchain/static/js/validateRegistration.js"></script>

<div class="w3-content" style="max-width:1400px">
<!-- Header -->
<header class="w3-container w3-center w3-padding-16">
  <h1><b>BLOCKCHAIN WALLET</b></h1>
  <p>Welcome in the most security wallet using <span class="w3-tag">Blockchain technology!</span></p>
</header>
<div class="container w-50 p-3 ">
  <h2 class="text-center">Registration Form</h2>
<hr>

<form action="/blockchain/registration/new_user" modelAttribute="user" method="post">
  <div class="form-group">
      <label for="User-name"><i class="fa fa-address-card-o"></i> User name</label>
      <input type="text" value="${user.name}" path="name" class="form-control" name = "name" id="User-name" placeholder="User name" pattern="[A-Za-z]{2,30}" required>
  </div>
  <div class="form-group">
      <label for="User-surname"><i class="fa fa-vcard"></i> User surname</label>
      <input type="text" value="${user.surname}" path="surname" class="form-control" name = "surname" id="User-surname" placeholder="User surname" pattern="[A-Za-z]{2,30}" required>
  </div>
  <div class="form-group">
      <label for="User-mobile"><i class="fa fa-mobile-phone"></i> Mobile phone</label>
      <input type="text" value="${user.mobile}" path="mobile" class="form-control" name = "mobile" id="User-mobile" placeholder="Mobile phone" pattern="\+375\d{9}" required>
  </div>
  <div class="form-group">
    <label for="User-email"><i class="fa fa-envelope-open-o"></i> Email address</label>
    <input type="email" value="${user.username}" path="email" class="form-control" name = "username" id="User-email" aria-describedby="emailHelp" placeholder="Email address" pattern=".+@.+\..+" required>
    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
    ${emailError}
  </div>
  <div class="form-group">
     <label for="pwd"><i class="fa fa-lock"></i> Password:</label>
     <input type="password" path="password" class="form-control" id="pwd" placeholder="Enter password" name="password" pattern="[\d*A-Za-z]*" required>
  </div>
  <div class="form-group">
     <label for="pwd"><i class="fa fa-key"></i> Confirm password:</label>
     <input type="password" path="password" class="form-control" id="pwd2" placeholder="Confirm your password" name="passwordConfirm" pattern="[\d*A-Za-z]*" required>
     ${passwordError}
  </div>
      <div class="form-group form-check">
        <label class="form-check-label">
          <input class="form-check-input" type="checkbox" name="remember" required><i class="fa fa-shield"></i> I agree on private policy
          <div class="valid-feedback">Valid.</div>
          <div class="invalid-feedback">Check this checkbox to continue.</div>
        </label>
      </div>
  <div>
     <csrf disabled="true"/>
  </div>
  <div>
  <button type="submit" class="btn btn-success">Submit</button>
  <button onclick="window.location.href='/blockchain'" type="button" class="btn btn-primary">Back</button>
  </div>
</form>
<hr>
</div>

</body>
</html>