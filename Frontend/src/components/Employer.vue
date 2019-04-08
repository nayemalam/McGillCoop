<template>

    <div id="Employers" class="container">
        <!-- Menu -->
        <b-navbar toggleable="lg" type="dark" variant="info" class="fixed-top">
            <b-navbar-brand href="/#/"><img src="../assets/coop-logo.png" width="50px"> McGill Coop</b-navbar-brand>

            <b-navbar-toggle target="nav_collapse" />

            <b-collapse is-nav id="nav_collapse">
                <b-navbar-nav>

                    <b-nav-item>
                        <router-link to="/students" style="color:white; decoration: none;">Students</router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <router-link to="/employer" style="color:white; decoration: none;">Employers</router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <router-link to="/statistics" style="color:white; decoration: none;">Statistics</router-link>
                    </b-nav-item>

                </b-navbar-nav>

                <!-- Right aligned nav items -->
                <b-navbar-nav class="ml-auto">
                    <b-nav-item>
                        <router-link to="/coopadmin" style="color:white; decoration: none;">
                            <b-button size="md" class="my-2 my-sm-0"><img src="https://img.pngio.com/white-people-png-svg-royalty-free-white-person-png-1000_758.png" width="21" /> Sign up</b-button>
                        </router-link>
                    </b-nav-item>
                    <b-nav-item>
                        <router-link to="/" style="color:white; decoration: none;">
                            <b-button size="md" class="my-2 my-sm-0" variant="danger" style="color: white;"><img src="https://res.cloudinary.com/nayemalam/image/upload/v1553558327/ECSE321/login.png" width="21" /> Log in</b-button>
                        </router-link>
                    </b-nav-item>

                    <!-- <b-nav-form>
              <b-form-input size="sm" class="mr-sm-2" type="text" placeholder="Search" />
              <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
            </b-nav-form> -->
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>

        <br/>
        <br/>
        <h1 align="center">Coop Employer</h1>
        <br/>
        <br/>
        <table class="table table-hover">
            <tr>
                <th scope="col">Employer Name</th>
                <th scope="col">Email Address</th>
                <th scope="col">Company Name</th>
                <th scope="col">Location</th>
                <th scope="col">Access Terms</th>

            </tr>
            <tr v-for="employer in employers">
                <td> {{employer.lastName+ ', '+employer.firstName}} </td>
                <td> {{employer.emailAddress}} </td>
                <td> {{employer.companyName}} </td>
                <td> {{employer.location}} </td>
                <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="showTerms(employer.userId, employer.lastName, employer.firstName)">
                    View Coop Terms
                </button>
            </tr>
        </table>

        <div v-if="seen">
            <h5 style="margin-top: 60px; text-align: left; padding-left: 25px;">Coop Terms of <u>{{empFirstName + ' '+ empLastName}}</u> </h5>
            <table class="table table-hover">
                <tr>
                    <th scope="col">Academic Semester</th>
                    <th scope="col">Start Date</th>
                    <th scope="col">End Date</th>
                    <th scope="col">Student</th>
                    <th scope="col">View Documents</th>
                </tr>
                <tr v-for="term in coopTerms">
                    <td>{{term.semester}}</td>
                    <td> {{term.startDate}} </td>
                    <td> {{term.endDate}} </td>
                    <td>
                        <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="viewStudent(term.termId)">
                            View Student
                        </button>
                    </td>
                    <td>
                        <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="viewDoc(term.termId)">
                            View Documents
                        </button>
                    </td>
                </tr>
            </table>
        </div>

        <div v-if="seen2">
            <h5 style="margin-top: 60px; text-align: left; padding-left: 25px;">Documents of <u>{{empFirstName + ' '+ empLastName}}</u>  </h5>
            <table class="table table-hover">
                <tr>
                    <th scope="col">Document Name</th>
                    <th scope="col">Due Date</th>
                    <th scope="col">Due Time</th>
                    <th scope="col">Submission Date</th>
                    <th scope="col">Submission Time</th>
                </tr>

                <tr v-for="document in documents">
                    <td> {{document.docName}} </td>
                    <td> {{document.dueDate}} </td>
                    <td> {{document.dueTime}} </td>
                    <td> {{document.subDate}} </td>
                    <td> {{document.subTime}} </td>
                    <button style="background-color: #17a2b8; border-color: #17a2b8;" type="submit" class="btn btn-primary" @click="download(document.externalDocId)">
                        Download
                    </button>
                </tr>
            </table>
        </div>

        <div v-if="seen3">
            <h5 style="margin-top: 60px; text-align: left; padding-left: 25px;">Student Credentials </h5>
            <table class="table table-hover">
                <tr>
                    <th scope="col">Student Name</th>
                    <th scope="col">Student ID</th>
                    <th scope="col">Email Address</th>
                </tr>

                <tr v-if="student">
                    <td> {{student.lastName+ ', '+student.firstName}} </td>
                    <td> {{student.studentId}} </td>
                    <td> {{student.emailAddress}} </td>
                </tr>
            </table>
        </div>

    </div>

</template>

<script src="./employers.js">
</script>

<style>
    #eventregistration {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        color: #2c3e50;
        background: #f2ece8;
    }
</style>