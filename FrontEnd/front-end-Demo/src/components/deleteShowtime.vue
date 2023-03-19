<script>
  import axios, {HttpStatusCode} from 'axios'
  import { useRoute } from 'vue-router';
  import { ref } from 'vue';
  export default{
    data(){
      return {
        showtimes: [[]],
        init : false,
        time : '',
        movieTitle : '',
        screenRoomName : '' 
      }
    },
    methods: {
  
      deleteShowtime(){
        const serverUrl= "http://localhost:8080/deleteShowtime/";
        const myHeaders={
            "Content-Type": "application/json; charset=utf-8",
            "Access-Control-Allow-Origin" : "*",
            "Access-Control-Allow-Methods" : "OPTIONS, POST, GET",
            "Access-Control-Allow-Headers" : "Content-Type"
        };
        axios({
          url: serverUrl,
          data: {
            time: this.time,
            movieTitle: this.movieTitle,
            screenRoomName: this.screenRoomName
          },
          method: 'POST',
          mode: 'no-cors',
          headers: myHeaders,
          
        })
          .then(response => {
            this.showtimes = response.data
            if(response.status===HttpStatusCode.Ok){
              alert("The showtime was deleted from the database!")
            }
            console.log(response)
          }
            )
          .catch(error => {
            console.log(error)
            alert("The showtime was NOT deleted from the database!(Not Found!)")
          })

      }
    }
  }
</script>


<template>
    <div class="deleteShowtime">
      <h1>
        Delete a showtime from the database:
      </h1>
        <input class="textInput" v-model="this.time" type="text" placeholder="Date&Time"/>        
        <input class="textInput" v-model="this.movieTitle" type="text" placeholder="insert movie title"/>        
        <input class="textInput" v-model="this.screenRoomName" type="text" placeholder="insert room name"/>
    </div>
    <br>
    <button class="button" @click="deleteShowtime">Delete Showtime</button> 
        
  </template>
