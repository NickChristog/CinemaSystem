<script>
  import axios, { HttpStatusCode } from 'axios'
  import { useRoute } from 'vue-router';
  import { ref } from 'vue';
  export default{
    data(){
      return {
        movie: [],
        init : false,
        title : "",
        runtime : "",
        posterUrl: "",
        releaseDate: "",
        pgRating: "",
        spokenLanguage: "",
      }
    },
    methods: {
  
      addMovie(){
        const serverUrl= "http://localhost:8080/addMovie/";
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
            runtime: this.runtime,
            releaseDate: this.releaseDate,
            pgRating: this.pgRating,
            posterUrl: this.posterUrl,
            spokenLanguage: this.spokenLanguage
          },
          method: 'POST',
          mode: 'no-cors',
          headers: myHeaders,
          
        })
          .then(response => {
            this.movie = response.data
            if(response.status===HttpStatusCode.Created){
              alert("The movie was saved in the database!")
            }
            
            console.log(response)
          }
            )
          .catch(error => {
            console.log(error.response)
            alert("The movie was NOT saved in the database!")
          }
          
          )

      }
    }
  }
</script>


<template>
    <div class="addMovie">
      <h1>
        Add a movie in the database!
      </h1>
      
        <input class="textInput" v-model="this.title" type="text" placeholder="Movie Title"/>        
        <input class="textInput" v-model="this.runtime" type="text" placeholder="runtime"/>        
        <input class="textInput" v-model="this.releaseDate" type="text" placeholder="releaseDate"/>
        <input class="textInput" v-model="this.pgRating" type="text" placeholder="PG Rating"/>
        <input class="textInput" v-model="this.posterUrl" type="text" placeholder="Poster URL"/>
        <input class="textInput" v-model="this.spokenLanguage" type="text" placeholder="spokenLanguage"/>
    </div>
    <br>
    <button class="button" @click="addMovie">Add in database</button> 
        
  </template>

