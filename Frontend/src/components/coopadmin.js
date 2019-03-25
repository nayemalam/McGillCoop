import axios from 'axios'
var config = require('../../config')

//Uncomment below for LOCAL test

  // var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  // var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

// //Uncomment below for LIVE server test
    var frontendUrl = 'https://' + config.build.host
    var backendUrl = 'https://' + config.build.backendHost 

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'coopAdmins',
  data () {
    return {
      coopAdmins: [],
      newAdmin: {
        lastName: '', //lastName
        firstName: '', //firstName
        emailAddress: '',
        userName: '',
        password: '' //maybe we should hide this one
      },
      errorAdmin: '',
      response: [],

      //Student Object 
      students: [],
      newStudent: {
        lastName: '', //lastName
        firstName: '', //firstName
        emailAddress: '',
        userName: '',
        program:'',
        coopTerm: [],
        },
        errorStudent: '',
        response: [],

        //Employer object 
        employers: [],
        newEmployer: {
          lastName: '', //lastName
          firstName: '', //firstName
          emailAddress: '',
          userName: '',
          location:'',
          companyName:'',
        },
        errorEmployer: '',
        response: [],
      
    }
  },

  
  created: function () {
    // Initializing admins from backend
      AXIOS.get(`/coopAdmins`)
      .then(response => {
        console.log(response.data)
        this.coopAdmins = response.data
      })
      .catch(e => {
        this.errorAdmin = e;
      });
    },
 
    
  methods: {
    createAdmin: function (admin) {
      AXIOS.post('coopAdmins/'+admin.lastName+'?fName='+admin.firstName+'&emailAddress='+admin.emailAddress+'&userName='+admin.userName+'&password='+ admin.password, {},{})
      // coopAdmins/Alam?fName=Nayem&emailAddress=Nayem.Alam@mcgill.ca&userName=NA_USER&password=1234
      .then(response => {
        // JSON responses are automatically parsed.
        this.coopAdmins.push(response.data)
        this.newAdmin = ''
        this.errorAdmin = ''
      })
      .catch(e => {
        var errorMsg = e.message
        console.log(errorMsg)
        this.errorAdmin = errorMsg
      });
    },
    getStudents: function () {
      AXIOS.get('/students')
      .then(response => {
        // JSON responses are automatically parsed.
        this.students = response.data
        this.errorStudent = ''
      })
      .catch(e => {
        var errorMsg = e.message
        console.log(errorMsg)
        this.errorStudent = errorMsg
      });
      console.log("im in here!!!")
    },
    getStudentTerm: function(){
      
      console.log("HEllOO")
    
    },
    getEmployers: function () {
      AXIOS.get('/employers')
      .then(response => {
        // JSON responses are automatically parsed.
        this.employers = response.data
        this.errorEmployer = ''
      })
      .catch(e => {
        var errorMsg = e.message
        console.log(errorMsg)
        this.errorEmployer = errorMsg
      });
      console.log("im in here!!!")
    }
  }
}
