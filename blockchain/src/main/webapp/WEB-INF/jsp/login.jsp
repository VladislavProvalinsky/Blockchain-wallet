<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<jsp:include page = "header-login.jsp"/>

<div class="container w-50 p-3 ">
  <h2>Sign up</h2>
  <br>
  <form action = "/blockchain/login" method="post" class="needs-validation" novalidate>
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" class="form-control" id="email" placeholder="Enter email" name="username" required>
      <div class="valid-feedback">Valid.</div>
      <div class="invalid-feedback">Please fill out this field.</div>
    </div>
    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="password" required>
      <div class="valid-feedback">Valid.</div>
      <div class="invalid-feedback">Please fill out this field.</div>
    </div>
    <button class="btn bth-lg btn-primary" type="submit" >Sign in</button>
    <br>
    <br>
    <button onclick="window.location.href='/blockchain/registration'" type="button" class="btn btn-primary">Don't have an account yet</button>
  </form>
  </br>
</div>

<script>
// Disable form submissions if there are invalid fields
(function() {
  'use strict';
  window.addEventListener('load', function() {
    // Get the forms we want to add validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();
</script>

<jsp:include page = "footer.jsp"/>