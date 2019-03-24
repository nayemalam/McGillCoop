<template>
  <div id="Student" class="container">
    <br/><br/> 
    <h1 align="center">Coop Students</h1>
      <table class="table table-hover">
        <tr>
          <th scope="col">Student Name</th>
          <th scope="col">Student ID</th>
          <th scope="col">Email Address</th>
          <th scope="col">Access Terms</th>
        </tr>
          <tr v-for="student in students" >
          <td> {{ student.lastName+ ', '+student.firstName }} </td>
          <td> {{ student.firstName}} </td>
          <td> {{ student.emailAddress}} </td>  
           <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="getTerm(student.userId)">
           View Coop Terms
           </button>
        </tr>
      </table>

      <br/><br/>
      <br/><br/>
      <br/><br/>
      <br/><br/>

      <div v-if="seen">
      <table class="table table-hover" id="my_table">
        <tr>
          <th scope="col">Start Date</th>
          <th scope="col">End Date</th>
          <th scope="col">View Documents</th>
        </tr>
        <!--
        <tr v-if="student.userId == studId" v-for="student in students">
          <td v-for="term in student.coopTerm">
          {{term.startDate}} 
          </td>
          <button  style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="getDocument(coopTerm.term)">
          View Documents
          </button> 
        </tr>
        -->
         <tr v-for="term in coopTerms">
          <td> {{term.startDate}} </td>
          <button  style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="getDocument(coopTerm.term)">
          View Documents
          </button> 
        </tr>

      </table>
      </div>

  </div>
 </template>

<script src="./students.js">
</script>

<style>
  #eventregistration {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>