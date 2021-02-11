<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page = "header-user.jsp"/>

<body class="w3-light-grey">

<div class="w3-content" style="max-width:1400px">
<!-- Header -->
<header class="w3-container w3-center w3-padding-16">
  <h1><b>BLOCKCHAIN WALLET</b></h1>
  <p>Welcome in the most security wallet using <span class="w3-tag">Blockchain technology!</span></p>
<hr>
</header>

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
  <div id="main">
  <span style="font-size:30px;cursor:pointer" onclick="openNav()">&#9776; HOME</span>
  </div>

<div class="w3-row">
  <div class="w3-col s4">
    <div class="w3-card w3-margin w3-margin-top">
        <img src="https://www.creativefabrica.com/wp-content/uploads/2019/05/Wallet-icon-by-nurfajrialdi95-580x387.jpg" style="width:100%">
          <div class="w3-container w3-white">
            <h4><b>YOUR WALLET</b></h4>
            <hr>
            <h4><u><b>BALANCE:</b></u><cite> ${actualBalance} </cite><i class="fa fa-bitcoin"></i></h4></p>
            <p class="text-sm-left">Your personal wallet! Make transactions, mine bitcoins, enjoy the life!</p>
                <div class="w3-col m8 s12">
                    <p><button type="button" onclick="window.location.href='https://github.com/VladislavProvalinsky/Blockchain-wallet';"
                    class="w3-button w3-padding-large w3-white w3-border"><b>SEE TRANSACTIONS</b></button></p>
                    <p><button class="btn w3-button w3-padding-large w3-white w3-border" data-clipboard-text="${wallet.id}"><b>COPY PUBLIC KEY</b></button></p>
                    <script src="https://cdn.jsdelivr.net/npm/clipboard@2.0.6/dist/clipboard.min.js"></script>
                    <script src="/blockchain/static/js/copy.js"></script>
                </div>
          </div>
    </div>
  </div>


  <div class="w3-col s4">
    <div class="w3-card w3-margin w3-margin-top">
        <img src="https://wellcoinpay.com/wp-content/uploads/2020/04/1-L-1170x650-1-1024x569.jpg" style="width:100%">
          <div class="w3-container w3-white">
            <br>
            <h4><b>TRANSACTION</b></h4>
            <p class="text-sm-left">Generate new transactions, send money and support blockchain!</p>
                <div class="w3-col m8 s12">
                    <!-- Button to open the modal -->
                    <p><button type="button" onclick="window.location='http://localhost:8080/blockchain/users/${user.id}/transactionForm';"
                    class="w3-button w3-padding-large w3-white w3-border" style="width:auto;"><b>CREATE TRANSACTION</b></button></p>
                </div>
          </div>
        </div>
    </div>


  <div class="w3-col s4">
    <div class="w3-card w3-margin w3-margin-top">
        <img src="https://fi.co/system/posts/learn-blockchain.png" style="width:100%">
          <div class="w3-container w3-white">
            <br>
            <h4><b>BLOCKS</b></h4>
            <p class="text-sm-left">Look at the fully-system Blockchain tree!</p>
                <div class="w3-col m8 s12">
                    <p><button type="button" onclick="window.location.href='https://github.com/VladislavProvalinsky/Blockchain-wallet';"
                    class="w3-button w3-padding-large w3-white w3-border"><b>SEE BLOCK TREE</b></button></p>
                </div>
          </div>
        </div>
  </div>

<div class="w3-row">
 <hr>
 <div class="w3-col l8 s12">
  <div class="w3-card-4 w3-margin w3-white">
  <img src="https://www.marydaytrader.com/wp-content/uploads/2018/09/swingtrading3.jpg" alt="Rates" width="100%" height="200">
    <div class="w3-container">
      <br>
      <h3><b>CRYPTOCURRENCY EXCHANGE RATES: live</b></h3>
    </div>

    <div id="coinmarketcap-widget-coin-price-block" coins="1,1027,825,52,6636,2010,1975" currency="USD" theme="light"
    transparent="true" show-symbol-logo="true">
    </div>
    <br>
  </div>
</div>
</div>

</div>

</body>
</html>