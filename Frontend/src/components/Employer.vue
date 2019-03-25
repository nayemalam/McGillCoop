
<template>

  <div id="Employers" class="container">
  
    <br/><br/> 
    <h1 align="center">Coop Employer</h1>
    
      <table class="table table-hover">
        <tr>
          <th scope="col">Employer Name</th>
          <th scope="col">Email Address</th>
          <th scope="col">Company Name</th>
          <th scope="col">Location</th>

        </tr>
          <tr v-for="employer in employers" >
          <td> {{employer.lastName+ ', '+employer.firstName}} </td>
          <td> {{employer.emailAddress}} </td>
          <td> {{employer.companyName}} </td>  
          <td> {{employer.location}} </td> 
           <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="setEmpId(employer.userId, employer.lastName, employer.firstName)">
           View Coop Terms
           </button>
        </tr>
      </table>

      <br/><br/>
      <br/><br/>
      <br/><br/>

      <div v-if="seen">
      <h1 align="center">Coop Terms of {{empFirstName + ' '+ empLastName}} </h1>
      <table class="table table-hover">
        <tr>
          <th scope="col">Start Date</th>
          <th scope="col">End Date</th>
           <th scope="col">Student</th>
          <th scope="col">View Documents</th>
        </tr>
         <tr v-for="term in coopTerms" >
          <td> {{term.startDate}} </td>
          <td> {{term.endDate}} </td>
          <td>
          <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="viewStudent(term.termId)">
           View Student
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
      <h1 align="center">Documents of {{empFirstName + ' '+ empLastName}}  </h1>
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
      <h1 align="center">Student </h1>
      <table class="table table-hover">
        <tr>
          <th scope="col">Student Name</th>
          <th scope="col">Student ID</th>
          <th scope="col">Email Address</th>
        </tr>

          <tr v-if="student" >
          <td> {{student.lastName+ ', '+student.firstName}} </td>
          <td> {{student.studentId}} </td>
          <td> {{student.emailAddress}} </td>
        </tr>
         </table>
      </div>
      <br/> <br/> 
      <br/> <br/> 
      <br/> <br/>
  </div>
 
 </template>


<script src="./employers.js">
</script>

<style>
  #eventregistration {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>