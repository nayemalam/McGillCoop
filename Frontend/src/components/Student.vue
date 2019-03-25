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
          <td> {{student.lastName+ ', '+student.firstName}} </td>
          <td> {{student.firstName}} </td>
          <td> {{student.emailAddress}} </td>  
           <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="setStudId(student.userId, student.lastName, student.firstName)">
           View Coop Terms
           </button>
        </tr>
      </table>

      <br/><br/>
      <br/><br/>
      <br/><br/>

      <div v-if="seen">
      <h1 align="center">Coop Terms of {{studFirstName + ' '+ studLastName}} </h1>
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

      <br/> <br/> 
      <br/> <br/> 
      <br/> <br/> 

      <div v-if="seen2">
      <h1 align="center">Documents of {{studFirstName + ' '+ studLastName}}  </h1>
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
      <h1 align="center">Employer </h1>
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