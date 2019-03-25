import axios from 'axios'
var config = require('../../config')

// Uncomment below for LOCAL test

  // var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
  // var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

// Uncomment below for LIVE server test
    var frontendUrl = 'https://' + config.build.host
    var backendUrl = 'https://' + config.build.backendHost 

var AXIOS = axios.create({
  baseURL: backendUrl,
  headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
  name: 'coopAdmins',
  data () {
    return {
      coopAdmins: [],
      newAdmin: {
        emailAddress: '',
        password: '' 
      },
      errorAdmin: '',
      response: [],
    }
  },
  created: function () {
    // Initializing admins from backend
      AXIOS.get(`/coopAdmins`)
      .then(response => {
        console.log(response.data)
        this.coopAdmins = response.data
      })
      .catch(e => {
        this.errorAdmin = e;
      });
  },
  methods: {
    CheckLogin: function (admin) {
      AXIOS.get('/login'+'?email='+admin.emailAddress+'&password='+ admin.password)
      // /login?email=Nayem.Alam@mcgill.ca&password=1234
    
      .then(response => {
        // JSON responses are automatically parsed.
        
        if( response.data == 1) {
          //this.coopAdmins.push(response.data)
          console.log('login successfully!')
         //location.replace('http://127.0.0.1:8087/?#/');
         location.replace('http://127.0.0.1:8087/?#/students');
        } 
        //this.newAdmin = ''
        //this.errorAdmin = ''
      })
      .catch(e => {
        var errorMsg = e.message
        console.log('login unsuccesful'+errorMsg)
        this.errorAdmin = errorMsg
      });
    }
  }
}