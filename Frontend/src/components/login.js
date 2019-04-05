import axios from 'axios'
// import router from '../router';
import router from '../router/index.js'
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
      signedAdmin: [],
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
    
      .then(response => {
        // JSON responses are automatically parsed.
        if( response.data == 1) {
      
          console.log('login successful!')

          //call the controller method for synchronizing our database with team 1
         // AXIOS.get('/students/update/')

          this.signedAdmin = response.data;
          // debugger
          const path = 'Student'
          router.push(path);
          // this.$router.push('/Hello')
         //location.replace(frontendUrl + '/#/Hello/');
        }  else {
          alert("The password you entered is incorrect.");
        }
        //this.newAdmin = ''
        //this.errorAdmin = ''

      })

      this.$emit("authenticated", true);
      this.$router.replace({ name: "secure" })
      .catch(e => {
        var errorMsg = e.message
        console.log('login unsuccesful \n' + errorMsg)
        alert("The email you entered is incorrect.");
        this.errorAdmin = errorMsg
      });
    }
  }
}