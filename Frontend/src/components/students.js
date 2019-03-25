import axios from 'axios'

var config = require('../../config')

//Uncomment below for LOCAL test

  // var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  // var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

// //Uncomment below for LIVE server test
    var frontendUrl = 'http://' + config.build.host
    var backendUrl = 'http://' + config.build.backendHost 

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
          employer: {
          coopTerm:[],
          emailAddress: '',
          userName: '',
          companyName:'',
          location:'',
          userId:'',
          firstName: '', //firstName
          lastName: '', //lastName
        },
        errorEmployer: '',
        response: [],

        //CoopTerm 
        coopTerms: [],
        newcoopTerm: {
            startDate:'',
            endDate:'',
            documents:[],
            termId:'',
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
        seen2:'',
        seen3:'',
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

    setStudId: function(id, last, first){
        console.log("im in !!!")
        this.seen = true;
        this.seen2 = false;
        this.seen3 = false;
        this.studLastName = last;
        this.studFirstName = first;
        this.studId = id;     
        AXIOS.get('/viewStudentTerms'+'?userId='+id)
        .then(response => {
        // JSON responses are automatically parsed.
        this.coopTerms = response.data
       })
        },  
  

    viewDoc: function(id){
      console.log("im in get Term !!!")
      this.termId= id;
      this.seen2 =  true;
      this.seen3 = false;
      var b = this.studId;
      AXIOS.get('/viewStudentFiles' + '/?userId='+b+'&termId='+id)
      .then(response => {
      // JSON responses are automatically parsed.
      this.documents = response.data
     })
  
    },

    viewEmployer: function(termId){
      this.seen3 = true;
      this.seen2 = false;
      
      AXIOS.get('/employers/term/'+termId)
      .then(response => {
        // JSON responses are automatically parsed.
        this.employer = response.data
        this.errorEmployer = ''
      })
      .catch(e => {
        var errorMsg = e.message
        console.log(errorMsg)
        this.errorEmployer = errorMsg
      });

    },

    getDocument: function(currentDocument){
        this.documents = currentDocument;
        console.log("AYEEE")
    },

    download: function(filename, text) {
      var element = document.createElement('a');
      element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
      element.setAttribute('download', filename);
  
      element.style.display = 'none';
      document.body.appendChild(element);
  
      element.click();
  
      document.body.removeChild(element);
  }
  
  },
  }

