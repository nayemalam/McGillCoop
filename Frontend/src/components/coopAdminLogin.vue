<template>
  <div id="coopAdmins" class="container">

      <b-carousel
      id="carousel1"
      style="text-shadow: 1px 1px 2px #333;"
      controls
      indicators
      background="#ababab"
      :interval="4000"
      img-width="1024"
      img-height="480"
      v-model="slide"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <!-- Text slides with image -->
      <b-carousel-slide
        caption="Welcome to CO-OP-ERATOR"
        text=" "
        img-src="https://picsum.photos/1024/480/?image=52"
      />

      <!-- Slides with custom text -->
      <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=54">
        <h1>Team 21</h1>
      </b-carousel-slide>

      <!-- Slides with image only -->
      <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=58" />
    
    </b-carousel>

    <br/> 
    <h2>Sign In here</h2>
    <br/>
     <!--<div class="row">
      <div class="col-xs-6">
        <table>
          <tr>
            <td> -->
              <form> <br/>
                <div class="field">
                  <div class="control"> 
                    <h4>Username</h4> 
                    <input type="email" v-model="newAdmin.emailAddress" placeholder="Enter your username"> <br/><br/>
                    <h4>Password</h4>
                    <input type="password" v-model="newAdmin.password" placeholder="Enter your password"> <br/>           
                  </div>
                    <br/>
                    <div class="form">
                      <button type="submit" class="btn btn-primary" @click="CheckLogin(newAdmin)">
                        Sign In
                      </button>
                    </div>
                    <br/>
                    <p>
                    By clicking Sign In, you agree that your use of the system is governed by 
                    <br/>
                    your institution's privacy policies and our Privacy Policy and Terms
                    </p>
                </div>
              </form>  
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
