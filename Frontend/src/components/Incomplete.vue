<template>
<div id="Incomplete">
    <h1 style="padding-bottom: 50px;">Incomplete Coop Placements</h1>

  <b-row>

    <b-col>
        <table class="table table-hover">
        <thead class="thead">
            <tr>
                <th scope="col">Student Name</th>
                <th scope="col">Student ID</th>
                <th scope="col">Program</th>
                <!-- <th scope="col">Document Name</th>
                <th scope="col">Document Due Date</th>
                <th scope="col">Document Submission Date</th> -->
                <th scope="col">Status</th>
                <th scope="col">Email</th>
                <th>Incomplete Documents</th>
            </tr>
            <!-- Items -->
            <tr v-for="student in incompleteStudents"> 
                <td>{{student.firstName +' '+ student.lastName}}</td>
                <td>{{student.studentId}}</td>
                <td>{{student.program}}</td>
                <!-- <td v-for="document in documents">{{document.docName}}</td>
                <td v-for="document in documents">{{document.dueDate}}</td>
                <td v-for="document in documents">{{document.subDate}}</td> -->
                <td class="table-danger"><strong>Incomplete</strong></td>
                <!-- <td><a href="mailto:nayem.alam@mail.mcgill.ca"> Click </a></td> -->
                <td>
                  <a :href="`mailto:${student.emailAddress}?subject=McGill Coop: Incomplete Reminder!&body=Hello ${student.firstName + ' ' + student.lastName}, %0d%0a%0d%0a This is an automated reply letting you know that you have some files that have been submitted late and/or not submitted yet. Please submit them as soon as possible to complete your coop term properly. %0d%0a%0d%0a Sincerely, %0d%0a McGill Engineering Coop Department`">
                    <button type="submit" class="btn btn-outline-danger">
                      Send Reminder 
                    </button>
                  </a>
                </td>
                <!-- <td><a :href="`mailto:${student.emailAddress}?subject=McGill Coop: Incomplete Reminder!&body=This is an automated reply letting you know that you have some files that have been submitted late and/or not submitted yet. Please submit them as soon as possible to complete your coop properly.`">{{student.emailAddress}}</a></td> -->
                <td><button class="btn btn-md btn-info" @click="setStudId(student.userId, student.lastName, student.firstName)">View Terms</button></td>
            </tr>
        </thead>
        </table>

      <div v-if="seen">
      <h5 style="margin-top: 50px; text-align: left; padding-left: 25px;">Coop Terms of: <u>{{firstName + ' '+ lastName}}</u> </h5>
      <table class="table table-hover">
        <tr>
          <th scope="col">Coop Term</th>
          <th scope="col">Start Date</th>
          <th scope="col">End Date</th>
          <th scope="col">View Documents</th>
        </tr>
         <tr v-for="term in coopTerms" >
           <td>{{term.termId}}</td>
          <td> {{term.startDate}} </td>
          <td> {{term.endDate}} </td>
            <td>
           <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="viewDocument(term.termId)">
           View Documents 
           </button>
            </td>
        </tr>
      </table>
      </div>

      <div v-if="seen2">
      <h5 style="margin-top: 50px; text-align: left; padding-left: 25px;">Documents of: <u>{{firstName + ' '+ lastName}}</u>  </h5>
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
        </tr>
         </table>
      </div>
        

        <!-- <table v-if="seen" class="table" style="margin-top: 150px;">
          <tr>
            <th>coopTerm</th>
            <th>Document Name</th>
            <th>Doc Due Date</th>
            <th>Doc Due Time</th>
            <th>Doc Submission Date</th>
            <th>Doc Submission Time</th>
          </tr>
          <tr v-for="term in coopTerms">
            <td>{{term.termId}}</td>
            <td v-for="doc in term.document">{{doc.docName}}</td>
            <td v-for="doc in term.document">{{doc.dueDate}}</td>
            <td v-for="doc in term.document">{{doc.dueTime}}</td>
            <td v-for="doc in term.document">{{doc.subDate}}</td>
            <td v-for="doc in term.document">{{doc.subTime}}</td>
            <td></td>

          </tr>


        </table> -->
    </b-col>
    

  </b-row>

  <p>
      <span v-if="errorPlacement" style="color:red">Error: {{errorPlacement}} </span>
  </p>

</div>
</template>

<script src="./incompletePlace.js">
</script>

<style>
  #eventregistration {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>