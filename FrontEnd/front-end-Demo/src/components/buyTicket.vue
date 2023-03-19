<script>
import axios , { HttpStatusCode } from 'axios';
  export default{
    
    data(){
      return {
        reservationConfirmed : false,
        addTicketFlag : false,
        seatId: this.$route.params.seatId,
        reservationId: '',
        ticket: []
      }
    },
    methods: {  
      getTicket(){
        const serverUrl= 'http://localhost:8080/printTicket/';
        const myHeaders={
        "Content-Type": "application/json; charset=utf-8",
        };

        axios({
          url: serverUrl,
          method: 'GET',
          mode: 'no-cors',
          headers: myHeaders,
          params: {seatId: this.$route.params.seatId, showtimeId: this.$route.params.showtimeId}
        })
        .then(response => {
            this.ticket = response.data
            console.log(response.data)
            this.reservationConfirmed = true;
            this.addTicketFlag = true;
            this.reservationId = response.data[5]
          }
            )
          .catch(error => {
            console.log(error)
            alert("Something went wrong! Please check if the input is correct!")
          })

      },

      addTicket(){
        const serverUrl= 'http://localhost:8080/addTicket/';
        const myHeaders={
        "Content-Type": "application/json; charset=utf-8",
        };

        axios({
          url: serverUrl,
          method: 'POST',
          mode: 'no-cors',
          headers: myHeaders,
          data: {reservationId: this.reservationId}
        })
        .then(response => {
          }
            )
          .catch(error => {
            console.log(error)
            alert("Something went wrong! Please check if the input is correct!")
          })

      },
      deleteReservation(){
        const serverUrl= 'http://localhost:8080/deleteReservation/';
        const myHeaders={
          "Content-Type": "application/json; charset=utf-8",
        };

        axios({
          url: serverUrl,
          method: 'POST',
          data: {
            seatId: this.$route.params.seatId, 
            showtimeId: this.$route.params.showtimeId
          },
          mode: 'no-cors',
          headers: myHeaders,
          
        })
        .then(response => {
            this.ticket = ''
            // window.location.href = ('/playingNow/:movieId/:showtimeId/:screenRoomId/')
            window.location.href = ('http://localhost:5173/')

          }
            )
          .catch(error => {
            console.log(error)
            alert("Something went wrong! Please check if the input is correct!")
          })
          
      }

    }
  }
</script>


<template>
    <div class="buyTicket">
      <h1>
          PRINT THE TICKET:
      </h1>
        <div v-if="reservationConfirmed===false">
          <button @click="getTicket" class="button">Confirm Reservation</button>
          <br>
          <button @click="deleteReservation" class="button">Undo Reservation</button>
        </div>

        <div v-if="this.reservationConfirmed === true">
          <h1>
            Printing ticket: <br>
            {{ this.ticket[0] }}<br>

            seat {{ this.ticket[1] }}
            on {{ this.ticket[2] }}
            <br>
            screen room: "{{ this.ticket[3] }}"
            {{ this.ticket[4] }} floor
            
          </h1>          
        
          <div v-if="this.addTicketFlag === true">
              {{ this.addTicket() }}
              {{this.addTicketFlag = false}}
            </div>
        </div>
    </div>
        
  
  </template>


<style scoped>
body{
  background-color: grey;

}


h1{
  font-size: 50px;
  font-family:  Courier, monospace;
  font-weight: bold;
  color: black;
  text-align: center;
}

</style>