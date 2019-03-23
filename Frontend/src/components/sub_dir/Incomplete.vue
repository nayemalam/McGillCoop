<template>
<div id="Incomplete">
    <h1 style="padding-bottom: 50px;">Incomplete Coop Placements</h1>

  <b-row>
    <b-col></b-col>

    <b-col>
        <h3>Coop Term:  </h3>
        <table class="table table-hover">
        <thead class="thead">
            <tr>
                <th scope="col">Student Name</th>
                <th scope="col">Student ID</th>
                <th scope="col">Program</th>
                <th scope="col">Document Name</th>
                <th scope="col">Document Due Date</th>
                <th scope="col">Document Submission Date</th>
                <th scope="col">Status</th>
                <th scope="col">Email Student</th>
            </tr>
            <!-- Items -->
            <tr v-for="student in incompleteStudents"> 
                <td>{{student.firstName +' '+ student.lastName}}</td>
                <td>{{student.studentId}}</td>
                <td>{{student.program}}</td>
                <td>{{student.docName}}</td>
                <td>{{student.dueDate}}</td>
                <td>{{student.subDate}}}</td>
                <td class="table-danger"><strong>{{student.status}}</strong></td>
                <!-- <td><a href="mailto:nayem.alam@mail.mcgill.ca"> Click </a></td> -->
                <td><a :href="`mailto:${student.emailAddress}`">{{student.emailAddress}}</a></td>
            </tr>
        </thead>
        </table>
    </b-col>

    <b-col></b-col>

 
  </b-row>

  <p>
      <span v-if="errorPlacement" style="color:red">Error: {{errorPlacement}} </span>
  </p>

</div>
</template>

<script src="./incomplete.js">
</script>

<style>
  #eventregistration {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>