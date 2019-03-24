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

console.log("gen stats")
    export default {
    name: 'GeneralStats',
    data () {
        return {
        studentsInCoop: [],
        number: '',
        studentsInFirst: [],
        studentsInSecond: [],
        studentsInThird: [],
        errorStat: '',
        response: [],
        seen1: false,
        seen2: false,
        seen3: false,
        seen4: false
        }
    },
    created: function () {
        // Initializing admins from backend
        AXIOS.get(`/stats/studentsInCoop/?semDate=2019-03-01`)
        .then(response => {
        console.log(response.data)
        this.studentsInCoop = response.data
        this.number = this.studentsInCoop;
        })

        AXIOS.get(`/stats/studentsInFirst//?semDate=2019-03-01`)
        .then(response => {
        console.log(response.data)
        this.studentsInFirst = response.data
        })

        AXIOS.get(`/stats/studentsInSecond/?semDate=2020-01-01`)
        .then(response => {
        console.log(response.data)
        this.studentsInFirst = response.data
        })

        AXIOS.get(`/stats/studentsInThird/?semDate=2021-02-01`)
        .then(response => {
        console.log(response.data)
        this.studentsInFirst = response.data
        })

        .catch(e => {
        this.errorStat = e;
        });
    }
}