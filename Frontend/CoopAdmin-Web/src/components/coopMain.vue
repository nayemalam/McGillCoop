<template>
<div id="coopMain">
  <b-navbar toggleable="lg" type="dark" variant="info" class="fixed-top">
    <b-navbar-brand href="#">McGill Coop</b-navbar-brand>

    <b-navbar-toggle target="nav_collapse" />

    <b-collapse is-nav id="nav_collapse">
      <b-navbar-nav>
        
        <b-nav-item><router-link to="/coopadmin" style="color:white; decoration: none;" >Coop Admin</router-link></b-nav-item>
        <b-nav-item><router-link to="/students" style="color:white; decoration: none;" >Students</router-link></b-nav-item>
        <b-nav-item><router-link to="/employers" style="color:white; decoration: none;" >Employers</router-link></b-nav-item>

      </b-navbar-nav>

      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-nav-form>
          <b-form-input size="sm" class="mr-sm-2" type="text" placeholder="Search" />
          <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
        </b-nav-form>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>

    <div>
        <h1 class="jumbotron vertical-center"> Click on the links above to test POST/GET methods </h1>
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