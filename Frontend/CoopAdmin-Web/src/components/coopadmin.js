import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function StudentDto (name, fName, emailAddress, userName, password, studentId, program) {
  this.emailAddress = emailAddress;
  this.fName = fName;
  this.name = name;
  this.password = password;
  this.program = program;
  this.studentId = studentId;
  this.userName  = userName;	
}

export default {
  name: 'coopadministrator',
  data () {
    return {
      students: [],
      newStudent: '',
      errorStudent: '',
      response: []
    }
  },
  created: function () {
    // Initializing students from backend
      AXIOS.get(`/students`)
      .then(response => {
        this.students = response.data
      })
      .catch(e => {
        this.errorStudent = e;
      });
  },
  methods: {
    createStudent: function (name, fName, emailAddress, userName, password, studentId, program) {
      AXIOS.post(`/students/philippon?fName=thomas&emailAddress=thom@mcgill.ca&userName=tp&password=1234&studentId=575754&program=ecse`)
      .then(response => {
        // JSON responses are automatically parsed.
        this.students.push(response.data)
        this.newStudent = ''
        this.errorStudent = ''
      })
      .catch(e => {
        var errorMsg = e.message
        console.log(errorMsg)
        this.errorStudent = errorMsg
      });
    }
  }
}