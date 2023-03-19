<template>

    <ul class="navigationList">
        <li><RouterLink active-class="activeNavi" to="/">Browse Movies</RouterLink></li>
        <li><RouterLink active-class="activeNavi" to="/addMovie/">Add Movie</RouterLink></li>
        <li><RouterLink active-class="activeNavi" to="/updateMovie/">Update Movie</RouterLink></li>
        <li><RouterLink active-class="activeNavi" to="/deleteMovie/">Delete Movie</RouterLink></li>
        <li><RouterLink active-class="activeNavi" to="/addShowtime/">Add Showtime</RouterLink></li>
        <li><RouterLink active-class="activeNavi" to="/deleteShowtime/">Delete Showtime</RouterLink></li>
    </ul>
    



</template>



<style scoped>

ul {
  font-family: 'Courier New', Courier, monospace;
  list-style-type: none;
  margin: 0;
  padding: 0;
  overflow: hidden;
  background-color: #333;
}

li {
  float: left;
  border-right: 1px solid white;
  border-left: 1px solid white;
}

li a {
  display: block;
  color: white;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

li a:hover {
  background-color: rgb(249, 95, 0);
}
li a:active {
  background-color: rgb(255, 0, 0);
}

</style>



