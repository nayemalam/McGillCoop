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
// export default {
//   name: 'employers',
//   data () {
//     return {
//       employers: [],
//       newEmployer: {
//         lastName: '', //lastName
//         firstName: '', //firstName
//         emailAddress: '',
//         userName: '',
//         location:'',
//         companyName:'',
//       },
//       errorEmployer: '',
//       response: [],
//     }
//   },
//   created: function () {
//   },
//   methods: {
//     getEmployers: function () {
//       AXIOS.get('/employers')
//       .then(response => {
//         // JSON responses are automatically parsed.
//         this.employers = response.data
//         this.errorEmployer = ''
//       })
//       .catch(e => {
//         var errorMsg = e.message
//         console.log(errorMsg)
//         this.errorEmployer = errorMsg
//       });
//       console.log("im in here!!!")
//     }
//   }
// }
console.log("test employer")