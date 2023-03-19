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
      }
    },
    methods: {
  
      updateMovie(){
        const serverUrl= "http://localhost:8080/updateMovie/";
        const myHeaders={
            "Content-Type": "application/json; charset=utf-8",
            "Access-Control-Allow-Origin" : "*",
            "Access-Control-Allow-Methods" : "OPTIONS, POST, GET, PUT",
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
          },
          method: 'PUT',
          mode: 'no-cors',
          headers: myHeaders,
          
        })
          .then(response => {
            this.movie = response.data
            if(response.status===HttpStatusCode.Created){
              alert("The movie was updated in the database!")
            }
            
            console.log(response)
          }
            )
          .catch(error => {
            console.log(error.response)
            alert("ERROR: The movie was NOT updated in the database!")
        })
        if(this.runtime === ''){
            this.runtime = NULL
        }
        if(this.posterUrl === ''){
            this.posterUrl = NULL
        }
      }
    }
  }
</script>


<template>
    <div class="updateMovie">
        <h1>
            Update a movie in the database!
            <br>
            Please provide the film`s title!
            <br>
            <input class="textInput" v-model="this.title" type="text" placeholder="Movie Title"/>
            <br><br><br>
            Fields that you can update:
            <br>
            <input class="textInput" v-model="this.runtime" type="text" placeholder="runtime"/>
            <input class="textInput" v-model="this.posterUrl" type="text" placeholder="Poster URL"/>
        </h1>
    </div>
    <br>
    <button class="button" @click="updateMovie">Update Movie</button> 
        
  </template>

