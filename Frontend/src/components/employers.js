import axios from 'axios'
var config = require('../../config')

// Uncomment below for LOCAL test

  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

// // Uncomment below for LIVE server test
//     var frontendUrl = 'https://' + config.build.host
//     var backendUrl = 'https://' + config.build.backendHost 

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
      //students: [],
       student: {
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

        ///Employer object 
        employers: [],
          newemployer: {
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
        empId:'',
        termId:'',
        empLastName:'',
        empFirstName:'',
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
    setEmpId: function(id, last, first){
        console.log("im in !!!")
        this.seen = true;
        this.seen2 = false;
        this.seen3 = false;
        this.empLastName = last;
        this.empFirstName = first;
        this.empId = id;     
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
      var b = this.empId;
      AXIOS.get('/viewEmployerFiles' + '/?userId='+b+'&termId='+id)
      .then(response => {
      // JSON responses are automatically parsed.
      this.documents = response.data
     })
  
    },

    viewStudent: function(termId){
      this.seen3 = true;
      this.seen2 = false;
      
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
console.log("I am in employers")