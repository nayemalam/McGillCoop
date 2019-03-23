<template>
<div id="coopMain">
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
        caption="Welcome"
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


    <b-form @submit="onSubmit" @reset="onReset" v-if="show">
      <b-form-group
        id="exampleInputGroup1"
        label="Username"
        label-for="exampleInput1"
        description="Please enter your registered Username"
      >
        <b-form-input
          id="exampleInput1"
          type="email"
          v-model="form.email"
          required
          placeholder="Enter your email" />
      </b-form-group>

    </b-form>

    <b-form  @submit.prevent>
        <label for="textPassword">Password</label>
        <b-input type="password" id="textPassword" aria-describedby="passwordHelpBlock" placeholder="Enter your password"/>

        <br/>
        <br/>

        <b-button type="submit" variant="danger">Sign In</b-button>

        <br/>
        <br/>

         <p>
            By clicking Sign In, you agree that your use of the system is governed by 
            <br/>
            your institution's privacy policies and our Privacy Policy and Terms
         </p>
   </b-form>
</div>
</template>

<script>
  export default {
    data() {
      return {
        form: {
          email: '',
          password: ''
        },
        show: true
      }
    },
    methods: {
      onSubmit(evt) {
        evt.preventDefault()
        alert(JSON.stringify(this.form))
      },
      onReset(evt) {
        evt.preventDefault()
        /* Reset our form values */
        this.form.email = ''
        this.form.password = ''
        /* Trick to reset/clear native browser form validation state */
        this.show = false
        this.$nextTick(() => {
          this.show = true
        })
      }
    }
  }
</script>