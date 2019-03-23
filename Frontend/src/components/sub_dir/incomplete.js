import axios from 'axios'
var config = require('../../../config')

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

function PersonDto (name) {
    this.name = name
    this.events = []
  }
  
  function EventDto (name, date, start, end) {
    this.name = name
    this.eventDate = date
    this.startTime = start
    this.endTime = end
  }

export default {
name: 'Incomplete',
    data () {
        return {
        incompleteStudents: [], //notice the 's'
        incompleteStudent: {
            termId: 3,
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
        coopTerm: {
            termId: 2,
        },
        documents: [],
        document : {
            docName: '',
            dueDate: '',
            subDate: '',
            dueTime: '',
            subTime: ''
        },
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
        this.coopTerms = response.date
        this.documents = response.date
        })
        .catch(e => {
        this.errorAdmin = e;
        });
    },
    methods: {
       
    }
}
