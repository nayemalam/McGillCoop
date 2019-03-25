import axios from 'axios'
var config = require('../../config')

//Uncomment below for LOCAL test

  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

// //Uncomment below for LIVE server test
//     var frontendUrl = 'https://' + config.build.host
//     var backendUrl = 'https://' + config.build.backendHost 

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'Student',
  data () {
    return {
      coopAdmins: [],
      newAdmin: {
        lastName: '', //lastName
        firstName: '', //firstName
        emailAddress: '',
        userName: '',
        password: '', //maybe we should hide this one
        userId: '',
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
        studentId:'',
        userId:'',
        coopTerms: [],
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
          userId:'',
          coopTerms:[]
        },
        errorEmployer: '',
        response: [],

        //CoopTerm 
        coopTerms: [],
        newcoopTerm: {
            startDate:'',
            endDate:'',
            documents:[],
        },

        //Document 
        documents:[],
        newDocument:{
            dueDate:'',
            subDate:'',
            dueTime:'',
            subTime:'',
            docName:''
        },
        seen:'',
        studId:'',
        termId:'',
        studLastName:'',
        studFirstName:'',
    }
  },

  
  created: function () {
    // Initializing admins from backend
      AXIOS.get(`/students`)
      .then(response => {
        console.log(response.data)
        this.students = response.data
      })
      .catch(e => {
        this.errorStudent = e;
      });
    },
    
  methods: {
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
    },

    setStudId: function(id, first, last){
        console.log("im in !!!")
        this.seen = true;
        this.studId = id;     
        AXIOS.get('/viewStudentTerms'+'?userId='+id)
        .then(response => {
        // JSON responses are automatically parsed.
        this.coopTerms = response.data
       })
        }  
    },

    getTerm: function(){
      console.log("im in get Term !!!")
  
    },

    getDocument: function(currentDocument){
        this.documents = currentDocument;
        console.log("AYEEE")
    }
  }

