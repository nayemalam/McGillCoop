<template>
  <div id="app">
<!-- Menu -->
  <b-navbar toggleable="lg" type="dark" variant="info" class="fixed-top">
        <b-navbar-brand href="/#/login"><img src="./assets/logo.png" width="30px">  McGill Coop</b-navbar-brand>

        <b-navbar-toggle target="nav_collapse" />

        <b-collapse is-nav id="nav_collapse">
          <b-navbar-nav>
      
            <b-nav-item><router-link to="/students" style="color:white; decoration: none;" >Students</router-link></b-nav-item>
            <b-nav-item><router-link to="/employer" style="color:white; decoration: none;" >Employers</router-link></b-nav-item>
            <b-nav-item><router-link to="/statistics" style="color:white; decoration: none;" >Statistics</router-link></b-nav-item>

          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item><router-link to="/coopadmin" style="color:white; decoration: none;" ><b-button size="md" class="my-2 my-sm-0">Sign up</b-button></router-link></b-nav-item>
            <b-nav-item><router-link to="/login" style="color:white; decoration: none;" ><b-button size="md" class="my-2 my-sm-0" variant="danger">Log in</b-button></router-link></b-nav-item>

            <!-- <b-nav-form>
              <b-form-input size="sm" class="mr-sm-2" type="text" placeholder="Search" />
              <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
            </b-nav-form> -->
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>


    <!-- <img src="./assets/logo.png"> -->
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 100px;
}
</style>
