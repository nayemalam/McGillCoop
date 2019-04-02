<template>
  <div id="GeneralStats">
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
      
    <h2 style="padding-bottom: 50px;">General Statistics</h2>

    <b-row>
        <b-col></b-col>

        <b-col>
         <button style="background-color: #17a2b8; border-color: #17a2b8;" class="btn btn-lg btn-primary" v-on:click="seen1 = !seen1">Number of Students</button> 
        <h5 v-if="seen1" style="padding-top: 50px;">The number of students registered in the current term: <br/> <span v-if="number" class="amount">{{number}}</span></h5>
        </b-col>

        <b-col></b-col>


        
    </b-row>
    <b-row style="padding-top: 100px;">
        <b-col><button style="background-color: #17a2b8; border-color: #17a2b8;" class="btn btn-lg btn-primary" v-on:click="seen2 = !seen2">List of Students in 1st Term</button> 
        <h5 v-if="seen2" style="padding-top: 50px;">A list of student(s) currently doing their <u>first coop</u> term:</h5>
        <table v-if="seen2" style="margin-top: 50px;" class="table table-hover">
            <tr>
                <th>Name</th>
                <th>Student ID</th>
                <th>Program</th>
            </tr>

            <tr v-for="student in studentsInFirst">
                <td>{{student.firstName + ' ' + student.lastName}}</td>
                <td>{{student.studentId}}</td>
                <td>{{student.program}}</td>
            </tr>
        </table>
        </b-col> 

        <b-col><button style="background-color: #17a2b8; border-color: #17a2b8;" class="btn btn-lg btn-primary" v-on:click="seen3 = !seen3">List of Students in 2nd Term</button> 
        <h5 v-if="seen3" style="padding-top: 50px;">A list of student(s) currently doing their <u>second</u> coop term:</h5>
        <table v-if="seen3" style="margin-top: 50px;" class="table table-hover">
            <tr>
                <th>Name</th>
                <th>Student ID</th>
                <th>Program</th>
            </tr>

            <tr v-for="student in studentsInSecond">
                <td>{{student.firstName + ' ' + student.lastName}}</td>
                <td>{{student.studentId}}</td>
                <td>{{student.program}}</td>
            </tr>
        </table>
        </b-col> 

        <b-col><button style="background-color: #17a2b8; border-color: #17a2b8;" class="btn btn-lg btn-primary" v-on:click="seen4 = !seen4">List of Students in 3rd Term</button> 
        <h5 v-if="seen4" style="padding-top: 50px;">A list of student(s) currently doing their <u>third</u> coop term:</h5>
        <table v-if="seen4" style="margin-top: 50px;" class="table table-hover">
            <tr>
                <th>Name</th>
                <th>Student ID</th>
                <th>Program</th>
            </tr>

            <tr v-for="student in studentsInThird">
                <td>{{student.firstName + ' ' + student.lastName}}</td>
                <td>{{student.studentId}}</td>
                <td>{{student.program}}</td>
            </tr>
        </table>
        </b-col> 
    </b-row>
    

    <p>
      <span v-if="errorStat" style="color:red">Error: {{errorStat}} </span>
  </p>
  </div>
</template>

<script src="./genstats.js">
</script>

<style>
  #eventregistration {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
  .amount {
      font-size: 200px;
  }
</style>