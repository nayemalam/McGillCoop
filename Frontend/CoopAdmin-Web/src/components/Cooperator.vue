<template>
  <div id="coopadministrator">
    <h2>Students</h2>
    <table>
      <tr v-for="student in students" >
        <td>{{ student.name }}</td>
        <td>{{ student.fname }}</td>
        <td>{{ student.emailAddress }}</td>
        <td>{{ student.userName }}</td>
        <td>{{ student.password }}</td>
        <td>{{ student.studentId }}</td>
        <td>{{ student.program }}</td>
      </tr>
      <tr>
        <td>
            <input type="text" v-model="newStudent" placeholder="Student Name">
        </td>
        <td>
            <button @click="createStudent(newStudent)">Create Student</button>
        </td>
      </tr>
    </table>
    <p>
      <span style="color:red">Error: Message text comes here</span>
    </p>
  </div>
</template>

<script src="./coopadmin.js">
</script>

<style>
  #eventregistration {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>