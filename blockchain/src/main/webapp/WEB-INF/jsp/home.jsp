<jsp:include page = "header.jsp"/>

<div class="container">
<div id="demo" class="carousel slide" data-ride="carousel">

    <!-- Indicators -->
    <ul class="carousel-indicators">
      <li data-target="#demo" data-slide-to="0" class="active"></li>
      <li data-target="#demo" data-slide-to="1"></li>
      <li data-target="#demo" data-slide-to="2"></li>
    </ul>

    <!-- The slideshow -->
    <div class="carousel-inner">
      <div class="carousel-item active">
        <img src="https://hackernoon.com/hn-images/1*I3PvdUSaBNnw4qg_JQqirg.png" alt="Los Angeles" width="1100" height="500">
      </div>
      <div class="carousel-item">
        <img src="https://innovationatwork.ieee.org/wp-content/uploads/2018/06/bigstock-Blockchain-technology-futurist-225602305-1024x576.jpg" alt="Chicago" width="1100" height="500">
      </div>
      <div class="carousel-item">
        <img src="https://grebweb.com/wp-content/uploads/2018/03/CBCA.jpg" alt="New York" width="1100" height="500">
      </div>
    </div>

    <!-- Left and right controls -->
    <a class="carousel-control-prev" href="#demo" data-slide="prev">
      <span class="carousel-control-prev-icon"></span>
    </a>
    <a class="carousel-control-next" href="#demo" data-slide="next">
      <span class="carousel-control-next-icon"></span>
    </a>
    </div>

<div class="wrapper">
<br>
<button onclick="window.location.href='/blockchain/login'" type="button" class="btn btn-primary">Sign in</button>
<br><br>
<button onclick="window.location.href='/blockchain/registration'" type="button" class="btn btn-primary">Registrate</button>
</div>


<jsp:include page = "footer.jsp"/>