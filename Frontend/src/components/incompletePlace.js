import axios from 'axios'
var config = require('../../config')

// Uncomment below for LOCAL test

  var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

// Uncomment below for LIVE server test
// var frontendUrl = 'https://' + config.build.host
// var backendUrl = 'https://' + config.build.backendHost 

var AXIOS = axios.create({
baseURL: backendUrl,
headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
name: 'Incomplete',
    data () {
        return {
        incompleteStudents: [], //notice the 's'
        documents: [],
        incompleteStudent: {
            firstName: '', 
            lastName: '',
            studentId: 123,
            program: '',
            docName: '',
            dueDate: '', //not sure what var
            subDate: '', //not sure what var
            status: '',
            emailAddress: ''
        },
        coopTerms: [],
        termId: 2,
        documents: [],
        document : {
            docName: '',
            dueDate: '',
            subDate: '',
            dueTime: '',
            subTime: ''
        },
        seen: false,
        errorPlacement: '',
        response: []
        }
    },
    created: function () {
        // Initializing admins from backend
        AXIOS.get(`/incomplete`)
        .then(response => {
        console.log(response.data)
        this.incompleteStudents = response.data
        })

        AXIOS.get(`/documents`)
        .then(response => {
        console.log(response.data)
        this.documents = response.data
        })

        AXIOS.get(`/coopterms`)
        .then(response => {
        console.log(response.data)
        this.coopTerms = response.data
        })

        .catch(e => {
        this.errorAdmin = e;
        });
    },
    methods: {
        addItem(){
            var my_object = {
                firstName:this.firstName,
                lastName:this.lastName,
                studentId:this.studentId,
                program:this.program,
                docName:this.docName,
                dueDate:this.dueDate,
                subDate:this.subDate,
                emailAddress:this.emailAddress
            };
            this.incompleteStudents.push(my_object)
            this.documents.push(my_object)
      
            this.firstName = '';
            this.lastName = '';
            this.studentId = 123;
            this.program = '';
            this.docName = '';
            this.dueDate = ''; 
            this.subDate = ''; 
            this.emailAddress = '';
         
          }       
    }
}
