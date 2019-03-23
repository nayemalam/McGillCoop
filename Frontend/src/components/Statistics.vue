<template>
<div id="Statistics">

  <div class="row">
    <div class="col-lg-6">
      <router-link to="/students" style="color:white; decoration: none;" >
        <button class="jumbotron vertical-center"> Student Progress </button>
        </router-link>
    </div>

    <div class="col-lg-6">
      <router-link to="/statistics/incomplete" style="color:white; decoration: none;" >
        <button class="jumbotron vertical-center"> Incomplete Placements </button>
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