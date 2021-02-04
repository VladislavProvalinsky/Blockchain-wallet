<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<jsp:include page = "header.jsp"/>

<body class="w3-light-grey">

<div class="w3-content" style="max-width:1400px">
<!-- Header -->
<header class="w3-container w3-center w3-padding-32">
  <h1><b>BLOCKCHAIN WALLET</b></h1>
  <p>Welcome in the most security wallet using <span class="w3-tag">Blockchain technology!</span></p>
</header>
<div class="container w-50 p-3 ">
  <h2 class="text-center">Please Sign In</h2>
  <hr>
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
    <hr>
    <div>
    <button type="submit" class="btn btn-success">Sign in</button>
    <button onclick="window.location.href='/blockchain/registration'" type="button" class="btn btn-primary">Don't have an account yet</button>
    </div>
  </form>
</div>

</body>
</html>