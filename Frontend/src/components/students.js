import axios from 'axios'

var config = require('../../config')

//Uncomment below for LOCAL test

  // var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  // var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

// //Uncomment below for LIVE server test
    var frontendUrl = 'https://' + config.build.host
    var backendUrl = 'https://' + config.build.backendHost 

//Our RESTful endpoints
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
       
        //Student Object after filtering 
      filterStudents: [],
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

        //Single student
        termStudent: {
          lastName: '', //lastName
          firstName: '', //firstName
          emailAddress: '',
          userName: '',
          program:'',
          studentId:'',
          userId:'',
          coopTerms: [],
          },

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
            companyName:'',
            semester:'',
            studentId:'',
        },
      
        //Document 
        documents:[],
        newDocument:{
            dueDate:'',
            subDate:'',
            dueTime:'',
            subTime:'',
            docName:'',
            externalDocId:''
        },

        //Other variables
        studTable: true,
        termTable: false,
        studTable2:false,
        seen:'',
        seen2:'',
        seen3:'',
        seenImage: false,
        studId:'',
        termId:'',
        studLastName:'',
        studFirstName:'',
        search: '',
        searchDate:'',
        emplName:'',
        image:'',
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

      AXIOS.get('/coopterms')
      .then(response => {
      // JSON responses are automatically parsed.
      this.coopTerms = response.data
     })

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
      
    },

    setStudId: function(id, last, first){
        
        this.seen = true;
        this.seen2 = false;
        this.seen3 = false;
        this.studLastName = last;
        this.studFirstName = first;
        this.studId = id; 

        console.log(id)    
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
    
    studentName: function(termId){
      AXIOS.get('/students/term/'+termId)
      .then(response => {
        // JSON responses are automatically parsed.
        this.termStudent = response.data
      })
    },

    download: function(docPath) {

      window.open('https://ecse321-w2019-g01-backend.herokuapp.com/external/documents/'+docPath+'/download');  
  },

   filterById: function(search){
     var n = search.length; 
   
     if(search!= ""){
        return this.students.filter(student => student.studentId.toString().substring(0,n) == search)  
     }
     else{
       return this.students
     }
  },

  filterByDate: function(searchDate){
  
    var n = searchDate.length; 
    if(this.coopTerms.filter(coopTerm => coopTerm.semester.toString().substring(0,n) == searchDate)){
      return this.coopTerms.filter(coopTerm => coopTerm.semester.toString().substring(0,n).toLowerCase() == searchDate.toLowerCase())
    }
  
    else{
      return this.coopTerms
    }
 },

  displayStudents: function(){
   this.seen = false;
   this.seen2 = false;
   this.seen3 = false;
   this.studTable2 = false;

  },
  }
}

