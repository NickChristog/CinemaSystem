<script>
import axios from 'axios'
  export default{

          data(){
            return {
              movies: [],
              init: false
            }
          },
          methods: {
            
            getMovies(){
              this.movies = null;
              const serverUrl= 'http://localhost:8080/playingNow';

              const myHeaders={
              "Content-Type": "application/json; charset=utf-8",
              };

              axios({
                url: serverUrl,
                method: 'GET',
                mode: 'no-cors',
                headers: myHeaders,
              })
                .then(response => {
                  this.movies = response.data
                  console.log(response)
                }
                  )
                .catch(error => console.log(error))

            }
          }
      }
    
</script>

<template>
  <div class="home">
    <h1>
        Movies Playing now:
    </h1>

    <div v-if="this.init === false">
        {{ getMovies() }}
        {{this.init = true}}
    </div>

    <RouterLink :to="`/playingNow/${movie[0]}`" v-for="movie in this.movies" :key="movie[0]">
      <p class="playingNow">
        {{movie[1]}}
      </p>
    </RouterLink>

  </div>
</template>

