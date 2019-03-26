
<template>
  <div id="coopAdmins" class="container">
    <h2 style="padding-bottom: 30px;">Administrators Portal</h2>
      <div class="row">
        <div style="margin: 0 auto;">
          <table>
            <tr>
              <td>
                <form class="form-group"><h4>Sign Up ... it's easy as 1, 2, 3!</h4> <br/>
                  <div class="field">
                    <div class="control">
                      <div class="form-row">
                        <div class="col">
                          <input type="text" class="form-control form-inline" v-model="newAdmin.firstName" placeholder="First Name"> <br/><br/>
                        </div>
                        <div class="col">
                          <input type="text" class="form-control form-inline" v-model="newAdmin.lastName" placeholder="Last Name"> <br/><br/>
                        </div>
                      </div>
                      
                      
                      <input type="email" class="form-control" autocomplete="email" required v-model="newAdmin.emailAddress" placeholder="Email Address"> <br/><br/>
                      <input type="text" class="form-control" v-model="newAdmin.userName" placeholder="User name"> <br/><br/>
                      <input id="inputPassword5" class="form-control" aria-describedby="passwordHelpBlock" type="password" autocomplete="password" required v-model="newAdmin.password" placeholder="Password"> <br/>           
                      <small id="passwordHelpBlock" class="form-text text-muted">
                      Your password must be 4-20 characters long, contain letters and numbers, and must not contain spaces, special characters, or emojis.
                      </small>
                    </div>
                    <br/>
                    
                      <button type="submit" class="btn btn-info" @click="createAdmin(newAdmin)">
                        Create Administrator
                      </button>
                    
                  </div>
                </form>  
              </td>
            </tr>
          </table>
        </div>
       
        <!--
        <div class="col-sm-4">
          <br/><br/>
          <h4>Previewed Input</h4>
          <table>
            <tr v-for="admin in coopAdmins" >
              <td> Email: {{ admin.emailAddress }},</td>
              <td> UserName: {{ admin.userName }},</td>
              <td> FirstName: {{ admin.firstName }},</td> 
              <td> LastName: {{ admin.lastName }}</td>
              <td>{{ admin.password }}</td>
            </tr>
          </table>
        </div>
        -->
      </div>
      <p>
      
        <span v-if="errorAdmin" style="color:red">Error: {{errorAdmin}} </span>
      </p>
      </div>
</template>


<script src="./coopadmin.js">
</script>


<style>
  #eventregistration {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: #f2ece8;
  }
</style>