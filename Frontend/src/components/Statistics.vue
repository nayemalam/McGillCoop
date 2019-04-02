<template>
<div id="Statistics">
     <!-- Menu -->
  <b-navbar toggleable="lg" type="dark" variant="info" class="fixed-top">
        <b-navbar-brand href="/#/"><img src="../assets/coop-logo.png" width="50px">  McGill Coop</b-navbar-brand>

        <b-navbar-toggle target="nav_collapse" />

        <b-collapse is-nav id="nav_collapse">
          <b-navbar-nav>
      
            <b-nav-item><router-link to="/students" style="color:white; decoration: none;" >Students</router-link></b-nav-item>
            <b-nav-item><router-link to="/employer" style="color:white; decoration: none;" >Employers</router-link></b-nav-item>
            <b-nav-item><router-link to="/statistics" style="color:white; decoration: none;" >Statistics</router-link></b-nav-item>

          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item><router-link to="/coopadmin" style="color:white; decoration: none;" ><b-button size="md" class="my-2 my-sm-0"><img src="https://img.pngio.com/white-people-png-svg-royalty-free-white-person-png-1000_758.png" width="21"/>  Sign up</b-button></router-link></b-nav-item>
            <b-nav-item><router-link to="/" style="color:white; decoration: none;" ><b-button size="md" class="my-2 my-sm-0" variant="danger" style="color: white;"><img src="https://res.cloudinary.com/nayemalam/image/upload/v1553558327/ECSE321/login.png" width="21"/>  Log in</b-button></router-link></b-nav-item>

            <!-- <b-nav-form>
              <b-form-input size="sm" class="mr-sm-2" type="text" placeholder="Search" />
              <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
            </b-nav-form> -->
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>

  <div class="row">
    <div class="col-lg-6">
      <router-link to="/statistics/general" style="color:white; decoration: none;" >
        <b-button variant="info"> General Statistics </b-button>
        </router-link>
    </div>

    <div class="col-lg-6">
      <router-link to="/statistics/incomplete" style="color:white; decoration: none;" >
        <b-button variant="info"> Incomplete Placements </b-button>
        </router-link>
    </div>
  </div>

</div>
</template>

<script>
</script>

<style>
  #eventregistration {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>