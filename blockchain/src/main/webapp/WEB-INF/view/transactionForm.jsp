<!DOCTYPE html>
<html>
  <title>New Transaction</title>
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
    <script src="/blockchain/static/js/checkbox.js"></script>

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
    <a href="/blockchain/users/${user.id}">Home</a>
    <a href="/blockchain/users/${user.id}/transactions">Transactions</a>
    <a href="#">Block Tree</a>
    <a href="http://localhost:8080/blockchain/logout">Logout</a>
  </div>

<h2>New Transaction</h2>

<div class="row">
  <div class="col-50">
    <div class="container">
      <form action="/blockchain/users/${user.id}/new_transaction" modelAttribute="transaction" method="post">
        <div class="row">
          <div class="col-50">
            <br>
            <label for="sender"><i class="fa fa-address-book"></i> Sender Wallet ID</label>
            <input type="text" value=${wallet.id} id="sender" name="senderPublicKey" required>
            <small class="form-text text-muted">${senderPublicKeyError}</small>

            <label for="receiver"><i class="fa fa-address-book-o"></i> Receiver Wallet ID</label>
            <input type="text" id="receiver" name="receiverPublicKey" placeholder="${wallet.id}" required>
            <small class="form-text text-muted">${receiverPublicKeyError}</small>

            <label for="value"><i class="fa fa-bitcoin"></i> Value</label>
            <input type="text" id="value" name="value" placeholder="${actualBalance}" pattern="^[-+]?[0-9]*\.?[0-9]+(?:[eE][-+]?[0-9]+)?$" required>
            <small class="form-text text-muted">${valueError}</small>

            <label for="myCheck"><i class="fa fa-thumbs-o-up"></i>
            Comission for miner: <input type="checkbox" id="myCheck" onclick="myFunction()"><small class="form-text text-muted">It will help to confirm your transaction faster</small></label>
            <input type="hidden" id="comission" name="comission" placeholder="${actualBalance}" pattern="^[-+]?[0-9]*\.?[0-9]+(?:[eE][-+]?[0-9]+)?$">

            <label for="senderPK"><i class="fa fa-key"></i> Signature</label>
            <input type="password" id="senderPK" name="privateKey" placeholder="Insert your private wallet key to sign transaction" required>
          </div>
        </div>
        <label>
          <input type="checkbox" required> Agree with wallet policy <i class="fa fa-shield"></i>
        </label>
        <input type="submit" value="Create transaction" class="btn">
      </form>
    </div>
  </div>
  <div class="col-25">

        <div class="container">
        <br>
          <iframe frameborder="no" scrolling="no" width="100%" height="130"
          src="https://yandex.ru/time/widget/?geoid=157&lang=en&layout=vert&type=digital&face=serif"></iframe>
          <br>
          <iframe src="https://ru.widgets.investing.com/top-cryptocurrencies?theme=darkTheme&hideTitle=true&roundedCorners=true"
          width="100%" height="450" frameborder="0" allowtransparency="true" marginwidth="0" marginheight="0"></iframe><div class="poweredBy"
          style="font-family: Arial, Helvetica, sans-serif;"></div>

        </div>
      </div>

</div>

</body>
</html>