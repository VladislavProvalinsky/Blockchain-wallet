<#import "/parts/headerUser.ftl" as hu>
<@hu.headerUser/>

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
        <#assign name="${user.name}">
        <#assign surname="${user.surname}">
        <#assign res=name+surname>
        <#if (res?length >= 21)>
            <h4>${user.name}<br>${user.surname}</h4>
        <#else>
            <h4>${user.name} ${user.surname}</h4>
        </#if>
        </div>
        <hr color="grey">
    <a href="/blockchain/users/${user.id}">Home</a>
    <a href="/blockchain/users/${user.id}/wallets">Wallets</a>
    <a href="/blockchain/users/${user.id}/transactions">Transactions</a>
    <a href="/blockchain/users/${user.id}/transactionForm">Create Transaction</a>
    <a href="/blockchain/users/${user.id}/blocks">Block Tree</a>
    <a onclick="document.getElementById('id01').style.display='block'"
    onmouseover="this.style.color='#f1f1f1';" onmouseout="this.style.color='#818181';" style="cursor: pointer; color:#818181">Upload Image</a>
    <a href="http://localhost:8080/blockchain/logout">Logout</a>

    <div id="id01" class="modal">
        <form class="modal-content animate" method="post" action="/blockchain/users/${user.id}/upload" enctype="multipart/form-data">
                <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
                <br><br>
                <input style="margin:5px" type="file" name="file">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="w3-button w3-teal" style="width:20%; margin:5px" type="submit">Add</button>
        </form>
    </div>
</div>

<div class="w3-row">
  <div class="w3-col s4">
    <div class="w3-card w3-margin w3-margin-top">
        <img src="https://www.creativefabrica.com/wp-content/uploads/2019/05/Wallet-icon-by-nurfajrialdi95-580x387.jpg" style="width:100%">
          <div class="w3-container w3-white">
            <h4><b>YOUR WALLET</b></h4>
            <hr>
            <h4><u><b>BALANCE:</b></u><cite> ${actualBalance} </cite><i class="fa fa-bitcoin"></i></h4></p>
            <p class="text-sm-left">Your personal wallet!<br>Make transactions, mine bitcoins, enjoy the life!</p>
                <div class="w3-col m8 s12">
                    <p><button class="btn w3-button w3-padding-large w3-white w3-border" data-clipboard-text="${wallet.id}"><b>COPY PUBLIC KEY</b></button></p>
                    <script src="https://cdn.jsdelivr.net/npm/clipboard@2.0.6/dist/clipboard.min.js"></script>
                    <script src="/blockchain/static/js/copy.js"></script>

                    <iframe name="hiddenFrame" width="0" height="0" border="0" style="display: none;"></iframe>
                    <u><b>MINER ENGINE</b></u>
                    <button formtarget="_blank" type="submit" class="btn btn-success" onclick="window.location.href='http://localhost:7777/miner/start/${user.wallet.id}';">Launch</button>
                    <button formtarget="_blank" type="submit" class="btn btn-danger" onclick="window.location.href='http://localhost:7777/miner/stop/${user.wallet.id}';">Stop</button>
                    <p></p>
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
            <hr>
            <p class="text-sm-left">Transaction is a transfer of Bitcoin value that is broadcast to the network and collected into blocks.
            Generate new transactions, send money and support blockchain!</p>
                <div class="w3-col m8 s12">
                    <!-- Button to open the modal -->
                    <p><button type="button" onclick="window.location='http://localhost:8080/blockchain/users/${user.id}/transactionForm';"
                    class="w3-button w3-padding-large w3-white w3-border" style="width:auto;"><b>CREATE TRANSACTION</b></button></p>
                    <p><button type="button" onclick="window.location.href='/blockchain/users/${user.id}/transactions'"
                    class="w3-button w3-padding-large w3-white w3-border"><b>SEE TRANSACTIONS</b></button></p>
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
            <hr>
            <p class="text-sm-left">A full copy of a block chain contains every transaction ever executed.
            Every block contains a hash of the previous block.
            This has the effect of creating a chain of blocks from the genesis block to the current block.
            With this information, you can find out how much value belonged to each address at any point in history.
            Look at the fully-system Blockchain tree!</p>
                <div class="w3-col m8 s12">
                    <p><button type="button" onclick="window.location.href='/blockchain/users/${user.id}/blocks';"
                    class="w3-button w3-padding-large w3-white w3-border"><b>SEE BLOCK TREE</b></button></p>
                </div>
          </div>
        </div>
  </div>

</div>

</body>
</html>