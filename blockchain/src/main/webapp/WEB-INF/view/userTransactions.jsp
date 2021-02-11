<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
  <title>Transactions</title>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <style>body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}</style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <link rel="stylesheet" href="/blockchain/static/css/userCurtainMenu.css">
    <link rel="stylesheet" href="/blockchain/static/css/personPhoto.css">
    <link rel="stylesheet" href="/blockchain/static/css/transactionForm.css">
    <script src="/blockchain/static/js/userCurtainMenu.js"></script>

</head>

<body class="w3-light-grey">

<div class="w3-row">
    <div class="w3-col w3-left w3-container" style="width:100px">
        <div id="main">
        <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776;</span>
        </div>
    </div>
    <div class="w3-col w3-right w3-container" style="width:100px">
    </div>
    <div class="w3-rest w3-container">
        <!-- Header -->
        <header class="w3-container w3-center w3-padding-16">
            <h1><b>BLOCKCHAIN WALLET</b></h1>
            <p>Welcome in the most security wallet using <span class="w3-tag">Blockchain technology!</span></p>
            <hr>
        </header>
    </div>
</div>


<div class="w3-content" style="max-width:1400px">

  <div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
        <div class="photo">
            <img class="personPhoto" alt="user" src="https://w7.pngwing.com/pngs/86/421/png-transparent-computer-icons-user-profile-set-of-abstract-icon-miscellaneous-monochrome-computer-wallpaper-thumbnail.png">
        </div>
        <br>
        <div style="color: white; text-align: center;">
            <h4>${user.name} ${user.surname}</h4>
        </div>
        <hr color="grey">
    <a href="#">Wallets</a>
    <a href="#">Transactions</a>
    <a href="#">Block Tree</a>
    <a href="#">Contact</a>
    <a href="http://localhost:8080/blockchain/logout">Logout</a>
  </div>

<div class="row">
<div class="col-50">
    <div class="w3-container">
      <h2>Your Transactions</h2>
      <table class="w3-table-all w3-hoverable">
        <tr>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Points</th>
        </tr>
        <tr>
          <td>Jill</td>
          <td>Smith</td>
          <td>50</td>
        </tr>
        <tr>
          <td>Eve</td>
          <td>Jackson</td>
          <td>94</td>
        </tr>
        <tr>
          <td>Adam</td>
          <td>Johnson</td>
          <td>67</td>
        </tr>
      </table>
    </div>
    </div>
</div>


</body>
</html>