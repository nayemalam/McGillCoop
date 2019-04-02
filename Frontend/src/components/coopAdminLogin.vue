<template>
  <div id="coopAdmins" style="margin-top: -70px;">

      <b-carousel
      id="carousel1"
      style="text-shadow: 1px 1px 2px #333;"
      controls
      indicators
      background="#ababab"
      :interval="4000"
      img-width="1024"
      img-height="480"
      
    >
      <!-- Text slides with image -->
      <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=52">
      
        <h1 style="font-size: 60px; margin-top:-18rem;">Welcome to CO-OP-ERATOR</h1>
      </b-carousel-slide>

      <!-- Slides with custom text -->
      <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=54">
        <h1 style="font-size: 100px; margin-top:-18rem;">Team 21</h1>
      </b-carousel-slide>

      <!-- Slides with image only -->
      <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=58" />
    </b-carousel>

    <br/> 
    <div class="row">
    <div style="margin: 0 auto;">
    <h2> If you are a Coop Admin <br/> Sign in here</h2>
    <br/>
     <!--<div class="row">
      <div class="col-xs-6">
        <table>
          <tr>
            <td> -->
              <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCxpZOFUPrdBJMZuFkN9EngeJ_R21Nt3QAdd8GzFgn52vCosXy" alt="login-icon" width="100px;"/>
              <form class="form-group"> <br/>
                <div>
                  <div class="control"> 
                    <h6 style="text-align: left;">Email Address</h6> 
                    <input type="email" class="form-control" autocomplete="email" required v-model="newAdmin.emailAddress" placeholder="Enter your email address"> <br/><br/>
                    <h6 style="text-align: left;">Password</h6>
                    <input type="password" class="form-control" autocomplete="password" required v-model="newAdmin.password" placeholder="Enter your password"> <br/>           
                  
                    <br/>
                   
                      
                      <button style="border-radius: 50px; padding-left: 30px; padding-right: 30px; font-size: 20px;" type="button" class="btn btn-info" @click="CheckLogin(newAdmin)">
                        Login
                      </button>
                  </div>
                    
                    <br/>
                    <p>
                    By clicking Sign In, you agree that your use of the system is governed by 
                    <br/>
                    your institution's privacy policies and our Privacy Policy and Terms
                    </p>
                    <h6 style="padding-top: 25px;">Don't have an account? <router-link to="/coopadmin"><b-button style="margin-left: 25px;">Sign me up!</b-button></router-link></h6>
                </div>
              </form>  
    </div>
    </div>
            <!-- </td>
          </tr> 
        </table>
      </div>
  </div>-->


    <p>
      <span v-if="errorAdmin" style="color:red">Error: {{errorAdmin}} </span>
    </p>
  </div>
</template>

<script src="./login.js">
</script>
