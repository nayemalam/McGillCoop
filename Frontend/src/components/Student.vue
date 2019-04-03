<template>
  <div id="Student" class="container">
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

    <br/><br/> 
    <b-row>
    <b-col>
      <button v-if="studTable" style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" v-on:click="studTable = !studTable, termTable = !termTable, displayTerms()">
        Display all Coop Terms
      </button> 
      <button v-if="termTable" style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" v-on:click="studTable = !studTable, termTable = !termTable, displayStudents()">
        Display all Students
      </button> </b-col>
    <b-col></b-col>
    <b-col><input v-if="studTable" type="text" v-model="search" class="form-control" placeholder="Search by student ID"/>
    <input v-if="termTable" type="text" v-model="searchDate" class="form-control" placeholder="Search by Date"/></b-col>
      <br/><br/> 
    </b-row>
    
    <div v-if="termTable">
      <h1 align="center">Coop Terms</h1>
       <table class="table table-hover">
        <tr>
          <th scope="col">Start Date</th>
          <th scope="col">End Date</th>
          <th scope="col">Employer</th>
          <th scope="col">Student ID</th>
        </tr>

          <tr>
          <tr v-for="term in filterByDate(searchDate)" >
          <td> {{term.startDate}} </td>
          <td> {{term.endDate}} </td>
          <td>
          <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="viewEmployer(term.termId)">
           View Employer
           </button>
           </td>
            <td>
           <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="studentName(term.termId)">
           View Students 
           </button>
            </td>
        </tr>
      </table>
       







    </div>
    
    
    <div v-if="studTable">
    <h1 align="center">Coop Students</h1>
       <br/><br/>
      <table class="table table-hover">
        <thead>
        <tr>
          <th scope="col">Student Name</th>
          <th scope="col">Student ID</th>
          <th scope="col">Email Address</th>
          <th scope="col">Access Terms</th>
        </tr>
        </thead>
        <tbody>
          <tr v-for="student in filterById(search)">
          <td> {{student.lastName+ ', '+student.firstName}} </td>
          <td> {{student.studentId}} </td>
          <td> {{student.emailAddress}} </td>  
           <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="setStudId(student.userId, student.lastName, student.firstName)">
           View Coop Terms
           </button>
        </tr>
        </tbody>
      </table>
     
      <div v-if="seen">
      <h5 style="margin-top: 60px; text-align: left; padding-left: 25px;">Coop Terms of <u>{{studFirstName + ' '+ studLastName}}</u> </h5>
      <table class="table table-hover">
        <tr>
          <th scope="col">Start Date</th>
          <th scope="col">End Date</th>
           <th scope="col">Employer</th>
          <th scope="col">View Documents</th>
        </tr>
         <tr v-for="term in coopTerms" >
          <td> {{term.startDate}} </td>
          <td> {{term.endDate}} </td>
          <td>
          <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="viewEmployer(term.termId)">
           View Employer
           </button>
           </td>
            <td>
           <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="viewDoc(term.termId)">
           View Documents 
           </button>
            </td>
        </tr>
         </table>
      </div>

      <div v-if="seen2">
      <h5 style="margin-top: 60px; text-align: left; padding-left: 25px;">Documents of <u>{{studFirstName + ' '+ studLastName}}</u>  </h5>
      <table class="table table-hover">
        <tr>
          <th scope="col">Document Name</th>
          <th scope="col">Due Date</th>
          <th scope="col">Due Time</th>
          <th scope="col">Submission Date</th>
          <th scope="col">Submission Time</th>
          </tr>

          <tr v-for="document in documents" >
          <td> {{document.docName}} </td>
          <td> {{document.dueDate}} </td>
          <td> {{document.dueTime}} </td>
          <td> {{document.subDate}} </td>
          <td> {{document.subTime}} </td>
          <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="download(document.docName, document.docName)">
           Download 
          </button>
        </tr>
         </table>
        </div>

      <div v-if="seen3">
      <h5 style="margin-top: 60px; text-align: left; padding-left: 25px;">Employer Credentials</h5>
      <table class="table table-hover">
        <tr>
          <th scope="col">Company Name</th>
          <th scope="col">Location</th>
          <th scope="col">Employer Name</th>
          <th scope="col">Employer Email Address</th>
          </tr>

          <tr v-if="employer" >
          <td> {{employer.companyName}} </td>
          <td> {{employer.location}} </td>
          <td> {{employer.firstName+' '+ employer.lastName}} </td>
          <td> {{employer.emailAddress}} </td>
        </tr>
         </table>
      
      </div>
      <br/> <br/> 
      <br/> <br/> 
      <br/> <br/> 
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