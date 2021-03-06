<#import "/parts/headerTxForm.ftl" as htx>
<@htx.headerTxForm/>

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
    <a href="/blockchain/users/${user.id}/blocks">Block Tree</a>
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
            <#if senderPublicKeyError??>
                <div class="alert alert-danger" role="alert">
                    ${senderPublicKeyError}
                </div>
            </#if>
            <input type="text" value=${wallet.id} id="sender" name="senderPublicKey" required />
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>


            <label for="receiver"><i class="fa fa-address-book-o"></i> Receiver Wallet ID</label>
            <#if receiverPublicKeyError??>
                <div class="alert alert-danger" role="alert">
                    ${receiverPublicKeyError}
                </div>
            </#if>
            <input type="text" id="receiver" name="receiverPublicKey" placeholder="${wallet.id}" required />
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>


            <label for="value"><i class="fa fa-bitcoin"></i> Value</label>
            <#if valueError??>
                <div class="alert alert-danger" role="alert">
                    ${valueError}
                </div>
            </#if>
            <input type="text" id="value" name="value" placeholder="${actualBalance}" pattern="^[-+]?[0-9]*\.?[0-9]+(?:[eE][-+]?[0-9]+)?$" required />
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>


            <label for="myCheck"><i class="fa fa-thumbs-o-up"></i>
            Comission for miner: <input type="checkbox" id="myCheck" onclick="myFunction()"><small class="form-text text-muted">It will help to confirm your transaction faster</small></label>
            <input type="hidden" id="comission" name="comission" placeholder="${actualBalance}" pattern="^[-+]?[0-9]*\.?[0-9]+(?:[eE][-+]?[0-9]+)?$" />
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>


            <label for="senderPK"><i class="fa fa-key"></i> Signature</label>
            <input type="password" id="senderPK" name="privateKey" placeholder="Insert your private wallet key to sign transaction" required />
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
          </div>
        </div>
        <label>
          <input type="checkbox" required /> Agree with wallet policy <i class="fa fa-shield"></i>
          <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </label>
        <input type="submit" value="Create transaction" class="btn">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
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