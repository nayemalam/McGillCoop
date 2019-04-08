import axios from 'axios'
var config = require('../../config')

// Uncomment below for LOCAL test

  // var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  // var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

// // Uncomment below for LIVE server test
    var frontendUrl = 'https://' + config.build.host
    var backendUrl = 'https://' + config.build.backendHost 

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'Employers',
  data () {
    return {
      
        coopAdmins: [],
      newAdmin: {
        lastName: '', 
        firstName: '', 
        emailAddress: '',
        userName: '',
        userId: '',
      },
      errorAdmin: '',
      response: [],

      //Student Object 
       student: {
        lastName: '',
        firstName: '', 
        emailAddress: '',
        userName: '',
        program:'',
        studentId:'',
        userId:'',
        coopTerms: [],
        },
        errorStudent: '',
        response: [],

        ///Employer object 
        employers: [],
          newemployer: {
          coopTerm:[],
          emailAddress: '',
          userName: '',
          companyName:'',
          location:'',
          userId:'',
          firstName: '', 
          lastName: '', 
        },
        errorEmployer: '',
        response: [],

        //CoopTerm 
        coopTerms: [],
        newcoopTerm: {
            startDate:'',
            endDate:'',
            documents:[],
            semester:'',
            termId:'',
        },

        //Document 
        documents:[],
        newDocument:{
            dueDate:'',
            subDate:'',
            dueTime:'',
            subTime:'',
            docName:'',
            externalDocId:'',
        },
        seen:'',
        seen2:'',
        seen3:'',
        empId:'',
        termId:'',
        empLastName:'',
        empFirstName:'',
        studTermId:'',
    }
  },

  
  created: function () {
    // Initializing admins from backend
      AXIOS.get(`/employers`)
      .then(response => {
        console.log(response.data)
        this.employers = response.data
      })
      .catch(e => {
        this.errorEmployer = e;
      });
    },
    
  methods: {
    //function to displat the
    showTerms: function(id, last, first){

      //toggle button 
      if(this.empId == id){
        this.seen = !this.seen;
      }
      else{
        this.seen = true;
      }
        //hide the other tables 
        this.seen2 = false;
        this.seen3 = false;
        this.empLastName = last;
        this.empFirstName = first;

        //save the employer id 
        this.empId = id;     
        AXIOS.get('/viewStudentTerms'+'?userId='+id)
        .then(response => {
        // JSON responses are automatically parsed.
        this.coopTerms = response.data
       })
        },  
  
    //function to display the documents of a given coopterm 
    viewDoc: function(id){

      //toggle button 
      if(this.termId == id){
        this.seen2 = !this.seen2;
      }
      else{
        this.seen2 = true;
      }
      
      //hide the student table
      this.seen3 = false;
      //save the coopterm id
      this.termId= id;

      var b = this.empId;
      AXIOS.get('/viewEmployerFiles' + '/?userId='+b+'&termId='+id)
      .then(response => {
      // JSON responses are automatically parsed.
      this.documents = response.data
     })
  
    },

    //function to display a student assosiated to a coopterm
    viewStudent: function(termId){

      //toggle button 
      if(this.studTermId==termId){
        this.seen3 = !this.seen3;
      }
      else{
        this.seen3 = true;
      }

      //hide the other tables
      this.seen2 = false;

      //save the term id
      this.studTermId = termId;
      AXIOS.get('/students/term/'+termId)
      .then(response => {
        // JSON responses are automatically parsed.
        this.student = response.data
        this.errorStudent = ''
      })
      .catch(e => {
        var errorMsg = e.message
        console.log(errorMsg)
        this.errorStudent = errorMsg
      });

    },

    download: function(docPath) {
      window.open('https://ecse321-w2019-g01-backend.herokuapp.com/external/documents/'+docPath+'/download'); 
  }
  
  },
  }
console.log("I am in employers")