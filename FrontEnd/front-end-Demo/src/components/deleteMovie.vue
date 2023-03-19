<script>
  import axios, {HttpStatusCode} from 'axios'
  import { useRoute } from 'vue-router';
  import { ref } from 'vue';
  export default{
    data(){
      return {
        movie: [],
        init : false,
        title : "",
      }
    },
    methods: {
  
      deleteMovie(){
        const serverUrl= "http://localhost:8080/deleteMovie/";
        const myHeaders={
            "Content-Type": "application/json; charset=utf-8",
            "Access-Control-Allow-Origin" : "*",
            "Access-Control-Allow-Methods" : "OPTIONS, POST, GET",
            "Access-Control-Allow-Headers" : "Content-Type"
        };
        axios({
          url: serverUrl,
          data: {
            title: this.title,
          },
          method: 'POST',
          mode: 'no-cors',
          headers: myHeaders,
          
        })
          .then(response => {
            if(response.status===HttpStatusCode.Ok){
              alert("The movie was deleted from the database!")
            }
            console.log(response)
          }
            )
          .catch(error => {
            console.log(error)
            alert("The movie was NOT found in the database!")
          })

      }
    }
  }
</script>


<template>
    <div class="deleteMovie">
      <h1>
        Delete a movie from the database!
      </h1>
        <input class="textInput" v-model="this.title" type="text" placeholder="Movie Title"/>              
    </div>
    <br>
    <button class="button" @click="deleteMovie">Delete from Database</button> 
        
  </template>
