<!DOCTYPE html>
<html>
  <title>Blockchain Wallet</title>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <style>body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}</style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <link rel="stylesheet" href="/blockchain/static/css/transactionForm.css">
    <script src="/blockchain/static/js/checkbox.js"></script>

</head>

<body class="w3-light-grey">


<div class="w3-content" style="max-width:1400px">
<!-- Header -->
<header class="w3-container w3-center w3-padding-16">
  <h1><b>BLOCKCHAIN WALLET</b></h1>
  <p>Welcome in the most security wallet using <span class="w3-tag">Blockchain technology!</span></p>
<hr>
</header>

<h2>New Transaction</h2>
<br>
<div class="row">
  <div class="col-50">
    <div class="container">
      <form action="/action_page.php">
        <div class="row">
          <div class="col-50">
            <br>
            <label for="sender"><i class="fa fa-address-book"></i> Sender Wallet ID</label>
            <input type="text" value=${wallet.id} id="sender" name="senderPublicKey" required>

            <label for="receiver"><i class="fa fa-address-book-o"></i> Receiver Wallet ID</label>
            <input type="text" id="receiver" name="receiverPublicKey" placeholder="${wallet.id}" required>

            <label for="value"><i class="fa fa-bitcoin"></i> Value</label>
            <input type="text" id="value" name="value" placeholder="${wallet.input}" pattern="\d*" required>

            <label for="myCheck"><i class="fa fa-thumbs-o-up"></i>
            Comission for miner: <input type="checkbox" id="myCheck" onclick="myFunction()"><small class="form-text text-muted">It will help to confirm your transaction faster</small></label>
            <input type="hidden" id="comission" name="comission" placeholder="${wallet.input}" pattern="\d*">

            <label for="senderPK"><i class="fa fa-key"></i> Signature</label>
            <input type="password" id="senderPK" name="sender" placeholder="Insert your private wallet key to sign transaction" required>
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