<#import "/parts/headerUserTx.ftl" as utx>
<@utx.headerUserTx/>

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
    <a href="/blockchain/users/${user.id}/transactionForm">Create Transaction</a>
    <a href="#">Block Tree</a>
    <a href="http://localhost:8080/blockchain/logout">Logout</a>
  </div>



<div class="w3-row">
    <div class="w3-rest w3-container">
        <h2>Your Transactions Info</h2>
        <button type="button" class="btn btn-success" onclick="window.location.href='/blockchain/users/${user.id}/transactions'"><i class="fa fa-refresh"></i> Refresh</button>
    <div class="w3-col w3-right" style="width:300px">
    <form class="example" action="/blockchain/users/${user.id}/search" style="margin:auto;max-width:300px">
        <input type="text" placeholder="Search.." name="searchParam" pattern="[\w\S]*" required>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit"><i class="fa fa-search"></i></button>
    </form>


    </div>
   </div>
</div>

<div class="w3-row">
<div class="w3-col-50">
    <div class="w3-container">
      <div style="overflow-x:auto;">
      <table class="w3-table-all w3-hoverable">
        <tr>
          <th>&#8470;</th>
          <th>Value</th>
          <th>Comission</th>
          <th>Date of creation</th>
          <th>Current status</th>
        </tr>
        <#list transactionList as transaction>
        <#assign count = 0>
        <tr>
          <td>${count + 1}</td>
          <td>${transaction.value} <i class="fa fa-btc"></i></td>
          <td>${transaction.comission} <i class="fa fa-btc"></i></td>
          <td>${transaction.date}</td>
          <td>${transaction.status}</td>
        </tr>
        <#else> No Transactions
        </#list>
      </table>
      </div>
    </div>
</div>
</div>


</body>
</html>