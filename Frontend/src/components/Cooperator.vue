<template>
  <div id="coopAdmins" class="container">
    <h2>Administrators Portal</h2>
    <div class="row">
      <div class="col-lg-6">
        <table>
          <tr>
            <td>
              <form><h4>Add Administrator</h4> <br/>
                <div class="field">
                  <div class="control">
                    <input type="text" v-model="newAdmin.firstName" placeholder="First Name"> <br/><br/>
                    <input type="text" v-model="newAdmin.lastName" placeholder="Last Name"> <br/><br/>
                    <input type="text" v-model="newAdmin.emailAddress" placeholder="Email Address"> <br/><br/>
                    <input type="text" v-model="newAdmin.userName" placeholder="userName"> <br/><br/>
                    <input type="text" v-model="newAdmin.password" placeholder="Password"> <br/>           
                  </div>
                    <br/>
                    <div class="form">
                      <button type="submit" class="btn btn-primary" @click="createAdmin(newAdmin)">
                        Create Administrator
                      </button>
                    </div>
                </div>
              </form>  
            </td>
          </tr>
        </table>
      </div>
    <!-- PREVIEW INPUT -->
    <div class="col-lg-6">
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