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



// export default {
//   name: 'students',
//   data () {
//     return {
//       students: [],
//       newStudent: {
//         lastName: '', //lastName
//         firstName: '', //firstName
//         emailAddress: '',
//         userName: '',
//         program:'',
//         coopTerm: [],
//       },
//       errorStudent: '',
//       response: [],

//     }


//   },
//   // name: 'coopTerms',
//   // data () {
//   //   return {
//   //     coopTerms: [],
//   //     newCoopTerm: {
//   //       termId:'',
//   //       startDate: '',
//   //       endDate:'',
//   //       document: [],
//   //     },
//   //     errorCoopTerm: '',
//   //     response: [],
//   //   }
//   // },
//   created: function () {
//   },
//   methods: {
//     getStudents: function () {
//       AXIOS.get('/students')
//       .then(response => {
//         // JSON responses are automatically parsed.
//         this.students = response.data
//         this.errorStudent = ''
//       })
//       .catch(e => {
//         var errorMsg = e.message
//         console.log(errorMsg)
//         this.errorStudent = errorMsg
//       });
//       console.log("im in here!!!")
//     },
//     getStudentTerm: function(){
      
//       console.log("HEllOO")
      

  

//     }
//   }
// }
console.log("test student")
