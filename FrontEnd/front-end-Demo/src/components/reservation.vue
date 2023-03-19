<script>
import axios from 'axios';

  export default{
    data(){
      return {
        seats: [
          []
        ],
        init : false,
        movieId : this.$route.params.movieId,
        showtimeId: this.$route.params.showtimeId,
        screenRoomId: this.$route.params.screenRoomId,
        seatId : ''
      }
    },
    methods: {  
      getMovieAvailableSeats(){
        const serverUrl= 'http://localhost:8080/getAvailableSeats/';
        console.log()
        const myHeaders={
        "Content-Type": "application/json; charset=utf-8",
        };

        axios({
          url: serverUrl,
          method: 'GET',
          mode: 'no-cors',
          headers: myHeaders,
          params: {movieId: this.$route.params.movieId, showtimeId:this.$route.params.showtimeId}
        })
          .then(response => {
            this.seats = response.data
            console.log(response)
          }
            )
          .catch(error => {
            console.log(error)
            this.seats = null
            alert("No available seats for this slot! All seats are reserved! Please check another showtime!");
          })

      },

      addReservationToDatabase(){
        const serverUrl= "http://localhost:8080/addReservation/";
        const myHeaders={
            "Content-Type": "application/json; charset=utf-8",
            "Access-Control-Allow-Origin" : "*",
            "Access-Control-Allow-Methods" : "OPTIONS, POST, GET",
            "Access-Control-Allow-Headers" : "Content-Type"
        };
        axios({
          url: serverUrl,
          data: {
            seatId : this.seatId,
            showtimeId: this.showtimeId,
          },
          method: 'POST',
          mode: 'no-cors',
          headers: myHeaders,
          
        })
          .then(response => {
            this.showtimes = response.data
            console.log(response)
          }
            )
          .catch(error => console.log(error))

      },
      addReservation(seatId){
        this.seatId = seatId
        this.addReservationToDatabase()
        setTimeout(300)
      }
    }
  }
</script>


<template>
    <div class="reservation">
      <h1>
        <div v-if="this.init === false">
            {{ getMovieAvailableSeats() }}
            {{this.init = true}}
        </div>
        
      </h1>
      <h2 v-if="this.init===true && this.seats!==null">
        Reserve Seats for: {{ seats[0][2] }} 
        at {{seats[0][3]}}
        <br><br>
        <br><br>
        Click on an available seat to reserve it!
        <br><br>
        
        <div class="cinemaSeatsContainer" v-if="this.seats!==null">
          <RouterLink @click.native="addReservation(seat[1])" :to="`/playingNow/${movieId}/${showtimeId}/${screenRoomId}/${seat[1]}/`"
           class="cinemaSeats" v-for="seat in seats">
          {{ seat[0] }}
          </RouterLink>
        </div>
      </h2>
      
      <h2 v-if="this.seats===null">
        NO AVAILABLE SEATS FOR THIS SHOWING!
        <br><br>
        Please check another slot!
      </h2>
    </div>
        
  
  </template>
