<template>
<div id="Statistics">

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