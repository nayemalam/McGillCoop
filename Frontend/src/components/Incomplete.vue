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
                <th scope="col">Email Student</th>
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
                <td><a :href="`mailto:${student.emailAddress}?subject=McGill Coop: Incomplete Reminder!&body=This is an automated reply letting you know that you have some files that have been submitted late and/or not submitted yet. Please submit them as soon as possible to complete your coop properly.`">{{student.emailAddress}}</a></td>
                <td><button class="btn btn-lg btn-primary" v-on:click="seen = !seen">View Docs</button></td>
            </tr>
        </thead>
        </table>

        <table v-if="seen" class="table" style="margin-top: 150px;">
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


        </table>
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