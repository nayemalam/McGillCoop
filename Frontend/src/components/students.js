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
        lastName: '',
        firstName: '', 
        emailAddress: '',
        userName: '',
        userId: '',
      },
      errorAdmin: '',
      response: [],

      //Student Object 
      students: [],
      newStudent: {
        lastName: '',
        firstName: '', 
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

        //Single student
        termStudent: {
          lastName: '', 
          firstName: '', 
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
        seen: false, //coopterms table 
        seen2: false, //documents table
        seen3:false,     //employer table
        seenImage: false,
        studId:'',
        termId:'',
        studLastName:'',
        studFirstName:'',
        search: '',
        academicSemester:'',
        emplName:'',
        image:'',
        termStudentId:'',
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

    //function to display the list of coopterms of a given student 
    displayTerms: function(id, last, first){
        
      //hide the employer and documents tables
      if(this.studId ==id){
        this.seen = !this.seen;
      }
      else{
        this.seen = true;
      }
        this.seen2 = false;
        this.seen3 = false;
        this.studLastName = last;
        this.studFirstName = first;
        this.studId = id; //save the student id to display his docs/employer later on if needed

        //call the RESTful endpoint
        console.log(id)    
        AXIOS.get('/viewStudentTerms'+'?userId='+id)
        .then(response => {
        // JSON responses are automatically parsed.
        this.coopTerms = response.data
       })
      },  
  

    //Function to display the list of documents of a given coopterm 
    viewDoc: function(currentTermId){

      //toggle button
      if(this.termId == currentTermId){
        this.seen2 =  !this.seen2;
      }
      else{
        this.seen2 = true;
      }

      //hide the employer table 
      this.seen3 = false;
      this.termId= currentTermId;
      var b = this.studId;

      //call the RESTful endpoint to get the list of documents of a given term 
      AXIOS.get('/viewStudentFiles' + '/?userId='+b+'&termId='+currentTermId)
      .then(response => {
      // JSON responses are automatically parsed.
      this.documents = response.data
     })
  
    },

    //function to display the employer associated to a coopterm
    viewEmployer: function(termId){

      //toggle button
      if(this.termId ==termId){
        this.seen3 = !this.seen3;
      }
      else{
        this.seen3 = true;
      }

      //hide the documents table
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
    
    //fonction do display the student associated to a given coopterm 
    displayTermStudent: function(termId){

      //toggle button
      if(this.termStudentId== termId){
        this.studTable2 = !this.studTable2;
      }
      else{
        this.studTable2 = true;
      }
      //save the id to compare next time for toggle button
      this.termStudentId = termId;

      AXIOS.get('/students/term/'+termId)
      .then(response => {
        // JSON responses are automatically parsed.
        this.termStudent = response.data
      })
    },

    //Function for calling team 1's endoint providing the documents
    download: function(docPath) {
      window.open('https://ecse321-w2019-g01-backend.herokuapp.com/external/documents/'+docPath+'/download');  
  },

  //Function for the student id search bar 
   filterById: function(search){
     var n = search.length; 
   
     if(search!= ""){
        return this.students.filter(student => student.studentId.toString().substring(0,n) == search)  
     }
     else{
       return this.students
     }
  },

  //function for the academic semester search bar
  filterByDate: function(academicSemester){
  
    var n = academicSemester.length; 
    if(this.coopTerms.filter(coopTerm => coopTerm.semester.toString().substring(0,n) == academicSemester)){
      return this.coopTerms.filter(coopTerm => coopTerm.semester.toString().substring(0,n).toLowerCase() == academicSemester.toLowerCase())
    }
  
    else{
      return this.coopTerms
    }
 },

 //function called to display the list of all coopterms, need to update them first
 displayCoopTerms: function(){

  //call the RESTful endpoint to get all coopterms 
  AXIOS.get('/coopterms')
      .then(response => {
      // JSON responses are automatically parsed.
      this.coopTerms = response.data
     })
 },

 //function to hide all tables when opening the view student page
  displayStudents: function(){
   this.seen = false;
   this.seen2 = false;
   this.seen3 = false;
   this.studTable2 = false;

  },
  }
}

