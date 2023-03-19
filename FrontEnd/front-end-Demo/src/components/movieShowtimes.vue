<script>
  import axios, { HttpStatusCode } from 'axios'
  import { useRoute } from 'vue-router';
  import { ref } from 'vue';


  const route = useRoute();
  export default{
    data(){
      return {
        showtimes: [[]],
        init : false
      }
    },
    methods: {
  
      getMovieShowtimes(){
        const serverUrl= 'http://localhost:8080/getMovieShowtimes/';
        const myHeaders={
        "Content-Type": "application/json; charset=utf-8",
        };

        axios({
          url: serverUrl,
          method: 'GET',
          mode: 'no-cors',
          headers: myHeaders,
          params: {id: this.$route.params.movieId}
        })
          .then(response => {
            this.showtimes = response.data
            if(response.status === HttpStatusCode.Ok){
              this.init = true;
            }
            console.log(response.status)
          }
            )
          .catch(error => console.log(error))

      }
    }
  }
</script>


<template>
    <div class="movieShowtimes">
        <div v-if="this.init === false">
            {{ getMovieShowtimes() }}
            <h3>
              THE MOVIE HAS NO SHOWTIMES YET<br>
              Coming soon!       
            </h3>
            <!-- {{this.init = true}} -->
        </div>

          <h1 v-if="this.init===true">
            Find the screening that works for you!<br>
            {{this.showtimes[0][1]}}<br>
              <p class="movieDetails">
                runtime:{{this.showtimes[0][7]}}
                PG{{ this.showtimes[0][5] }}
                langague:{{this.showtimes[0][6] }}<br>
                Release Date: {{ this.showtimes[0][9] }}
              </p>
              <img class="poster" :src="'../public/' + this.showtimes[0][8]">
              
                  
          <RouterLink :to="`/playingNow/${this.$route.params.movieId}/${showtime[0]}/${showtime[2]}`" v-for="showtime in showtimes" :key="showtime[0]">
            <p class="showtimesList">
              Screen: "{{showtime[3]}}"
              <!-- {{ this.$route.params.movieId }} -->
              Date: {{ showtime[4] }}
              
            </p>
          </RouterLink>
        </h1>
    </div>
        
  
  </template>


<style scoped>
body{
  background-color: grey;

}


h1{
  font-size: 30px;
  font-family: 'Courier New', Courier, monospace;
  font-weight: bold;
  color: black;
  text-align: center;
}

h2{
  font-size: 45px;
  font-family: 'Courier New', Courier, monospace;
  font-weight: bold;
  color: black;
  text-align: center;
}

h3{
  font-size: 50px;
  font-family: 'Courier New', Courier, monospace;
  font-weight: bold;
  color: black;
  text-align: center;
}

.movieDetails{
  font-size: 20px;
  font-family: 'Courier New', Courier, monospace;
  font-weight: bold;
  color: black;
  text-align: center;
}
.poster{
  height: 25%;
  width: 20%;
  align-items: right;
  border: 5px solid #292828;
}

/* a{
    
    color: black;
}

a:hover{
    
    color: rgb(255, 94, 0);
} */

</style>