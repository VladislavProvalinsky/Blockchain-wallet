<!DOCTYPE html>
<html>
  <title>Wallet Home</title>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <style>body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}</style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script type="text/javascript" src="https://files.coinmarketcap.com/static/widget/coinPriceBlock.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="https://use.fontawesome.com/releases/v5.11.0/css/all.css">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="https://mdbootstrap.com/api/snippets/static/download/MDB-Pro_4.8.1/css/mdb.min.css">
    <link rel="stylesheet" type="text/css" href="https://mdbootstrap.com/wp-content/themes/mdbootstrap4/css/mdb-plugins-gathered.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.13.0/umd/popper.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src="https://mdbootstrap.com/api/snippets/static/download/MDB-Pro_4.8.1/js/mdb.min.js"></script>
    <script type="text/javascript" src="https://mdbootstrap.com/wp-content/themes/mdbootstrap4/js/plugins/mdb-plugins-gathered.min.js"></script>


    <link rel="stylesheet" href="/blockchain/static/css/userCurtainMenu.css">
    <link rel="stylesheet" href="/blockchain/static/css/personPhoto.css">
    <link rel="stylesheet" href="/blockchain/static/css/imageUploadForm.css">
    <script src="/blockchain/static/js/userCurtainMenu.js"></script>
    <script src="/blockchain/static/js/imageUploadForm.js"></script>
    <script src="/blockchain/static/js/blockCarousel.js"></script>


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
           <#if user.filename??>
                <img class="personPhoto" alt="userLogo" src="/blockchain/static/img/${user.filename}">
           <#else>
                <img class="personPhoto" alt="userLogo" src="https://w7.pngwing.com/pngs/86/421/png-transparent-computer-icons-user-profile-set-of-abstract-icon-miscellaneous-monochrome-computer-wallpaper-thumbnail.png">
           </#if>
        </div>
        <br>
        <div style="color: white; text-align: center;">
            <h4>${user.name} ${user.surname}</h4>
        </div>
        <hr color="grey">
    <a href="/blockchain/users/${user.id}">Home</a>
    <a href="/blockchain/users/${user.id}/transactions">Transactions</a>
    <a href="/blockchain/users/${user.id}/transactionForm">Create Transaction</a>
    <a href="http://localhost:8080/blockchain/logout">Logout</a>
  </div>

<div id="carousel-example-multi" class="carousel slide carousel-multi-item v-2" data-ride="carousel">

  <!--Controls-->
  <div class="controls-top">
    <a class="btn-floating" href="#carousel-example-multi" data-slide="prev"><i
        class="fas fa-chevron-left"></i></a>
    <a class="btn-floating" href="#carousel-example-multi" data-slide="next"><i
        class="fas fa-chevron-right"></i></a>
  </div>
  <!--/.Controls-->

  <!-- Indicators -->
  <ol class="carousel-indicators">
  <#if blockTree?has_content>
  <#list blockTree as block>
    <li data-target="#carousel-example-multi" data-slide-to="${block.id}" class="active"></li>
  </#list>
  <#else>
    <li data-target="#carousel-example-multi" data-slide-to="0" class="active"></li>
  </#if>
  </ol>
  <!--/.Indicators-->

  <div class="carousel-inner" role="listbox">
    <#if blockTree?has_content && (blockTree?size > 3) >
      <#list blockTree as block>
        <#if block.id <= 3>
        <div class="carousel-item active">
          <div class="col-12 col-md-4">
            <div class="card mb-2">
              <#if block.id == 1>
              <img class="card-img-top" src="https://i.ytimg.com/vi/WAIOcPA0EBk/maxresdefault.jpg"
                alt="Card image cap">
              <#else>
              <img class="card-img-top" src="https://www.tbstat.com/wp/uploads/2018/09/20200325_OG-The-Block-Home.jpg"
                alt="Card image cap">
              </#if>
              <div class="card-body">
                <h4 class="card-title font-weight-bold">Block N ${block.id}</h4>
                <p class="card-text">Hash: ${block.hash}<br>
                Nonce: ${block.nonce}<br>
                PreviousHash: ${block.previousHash}<br>
                <#setting date_format="dd-MM-yyyy HH:mm:ss">
                <#assign createdOn='${block.date}'>
                Date of creation: ${createdOn?datetime("yyyy-MM-dd'T'HH:mm:ss")?date}<br></p>
              </div>
            </div>
          </div>
        </div>
        <#else>
        <div class="carousel-item">
          <div class="col-12 col-md-4">
            <div class="card mb-2">
              <img class="card-img-top" src="https://www.tbstat.com/wp/uploads/2018/09/20200325_OG-The-Block-Home.jpg"
                alt="Card image cap">
              <div class="card-body">
                <h4 class="card-title font-weight-bold">Block N ${block.id}</h4>
                <p class="card-text">Hash: ${block.hash}<br>
                Nonce: ${block.nonce}<br>
                PreviousHash: ${block.previousHash}<br>
                <#setting date_format="dd-MM-yyyy HH:mm:ss">
                <#assign createdOn='${block.date}'>
                Date of creation: ${createdOn?datetime("yyyy-MM-dd'T'HH:mm:ss")?date}<br></p>
              </div>
            </div>
          </div>
        </div>
        </#if>
        </#list>

    <#else>
    <div class="carousel-item active">
      <#if blockTree?has_content && (blockTree?size <=3) >
      <#list blockTree as block>
      <div class="col-12 col-md-4">
        <div class="card mb-2">
          <#if block.id == 1>
          <img class="card-img-top" src="https://i.ytimg.com/vi/WAIOcPA0EBk/maxresdefault.jpg"
            alt="Card image cap">
          <#else>
          <img class="card-img-top" src="https://www.tbstat.com/wp/uploads/2018/09/20200325_OG-The-Block-Home.jpg"
            alt="Card image cap">
          </#if>
          <div class="card-body">
            <h4 class="card-title font-weight-bold">Block N ${block.id}</h4>
            <p class="card-text">Hash: ${block.hash}<br>
            Nonce: ${block.nonce}<br>
            PreviousHash: ${block.previousHash}<br>
            <#setting date_format="dd-MM-yyyy HH:mm:ss">
            <#assign createdOn='${block.date}'>
            Date of creation: ${createdOn?datetime("yyyy-MM-dd'T'HH:mm:ss")?date}<br></p>
          </div>
        </div>
      </div>
      </#list>
      <#else>
      <div class="col-12 col-md-4">
        <div class="card mb-2">
          <img class="card-img-top" src="https://i.pinimg.com/originals/1e/88/f0/1e88f00bcff87cfae46b49162ab92557.gif"
            alt="Card image cap">
          <div class="card-body">
            <h4 class="card-title font-weight-bold">Block</h4>
            <p class="card-text">In database no blocks yet</p>
          </div>
        </div>
      </div>
      </#if>
    </div>
    </#if>


  </div>
</div>
</div>

</body>
</html>