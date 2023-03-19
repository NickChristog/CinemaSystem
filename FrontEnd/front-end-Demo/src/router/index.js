import {createRouter, createWebHistory} from 'vue-router'
import homePage from '../components/homePage.vue'
import addShowtime from '../components/addShowtime.vue'
import deleteShowtime from '../components/deleteShowtime.vue'
import movieShowtimes from '../components/movieShowtimes.vue'
import reservation from '../components/reservation.vue'
import addMovie from '../components/addMovie.vue'
import updateMovie from '../components/updateMovie.vue'
import deleteMovie from '../components/deleteMovie.vue'
import buyTicket from '../components/buyTicket.vue'


export default createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: 'homePage',
      component: homePage
    },
    {
      path: "/addMovie/",
      name: 'addMovie',
      component: addMovie
    },
    {
      path: "/updateMovie/",
      name: 'updateMovie',
      component: updateMovie
    },
    {
      path: "/deleteMovie/",
      name: 'deleteMovie',
      component: deleteMovie
    },
    {
      path: "/addShowtime/",
      name: 'addShowtime',
      component: addShowtime
    },
    {
      path: "/deleteShowtime/",
      name: 'deleteShowtime',
      component: deleteShowtime
    },
    {
      path: "/playingNow/:movieId/",
      name: 'movieShowtimes',
      component: movieShowtimes,
      props: true
    },
    {
      path: "/playingNow/:movieId/:showtimeId/:screenRoomId/",
      name: 'reservation',
      component: reservation,
      props: true
    },
    {
      path: "/playingNow/:movieId/:showtimeId/:screenRoomId/:seatId",
      name: 'buyTicket',
      component: buyTicket,
      props: true
    },
  ]
})
