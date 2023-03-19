package cinema.project.app;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class BackEndControllerTest {

    @Test
    void getMoviesTest() {
        LinkedList<MoviesDatabase> allMovies = MoviesDatabase.getAllMovies();
        assert !allMovies.isEmpty();
    }

    @Test
    void getMovieShowtimesTest() {
        int testId = 1;
        LinkedList<Object> movieShowtimes = ShowtimesDatabase.getMovieShowtimes(testId);
        assert !movieShowtimes.isEmpty();

        testId = -1;
        movieShowtimes = ShowtimesDatabase.getMovieShowtimes(testId);
        assert movieShowtimes.isEmpty();
    }

    @Test
    void addMovieTest() {
        MoviesDatabase movie = new MoviesDatabase();
        movie.setTitle("TestMovie");
        movie.setPgRating(13);
        movie.setRuntime("3h");
        movie.setReleaseDate("2/12/23");
        movie.setSpokenLanguage("Spanish");
        movie.setPosterUrl("TestPosterLink");
        // MAKE SURE THERE IS NO DUPLICATE
        MoviesDatabase.deleteMovie(movie.getTitle());
        //THIS SHOULD PASS
        assert  MoviesDatabase.addMovie(movie);

        //THIS SHOULD FAIL
        movie.setTitle("");
        assert  !MoviesDatabase.addMovie(movie);

    }

    @Test
    void updateMovieTest() {
        MoviesDatabase movie = new MoviesDatabase();
        movie.setTitle("TestMovie");
        movie.setPgRating(13);
        movie.setReleaseDate("2/12/23");
        movie.setSpokenLanguage("Spanish");
//        MoviesDatabase.addMovie(movie);
        movie.setPosterUrl("UpdatedPosterLink");
        movie.setRuntime("updatedRuntime");
        assert  MoviesDatabase.updateMovie(movie);

        movie.setTitle("");
        assert !MoviesDatabase.updateMovie(movie);

    }
    @Test
    void deleteMovieTest() {
        MoviesDatabase movie = new MoviesDatabase();
        movie.setTitle("movieToDelete");
        movie.setPgRating(13);
        movie.setReleaseDate("2/12/23");
        movie.setSpokenLanguage("Spanish");
        MoviesDatabase.addMovie(movie);
        assert MoviesDatabase.deleteMovie("movieToDelete");
        assert !MoviesDatabase.deleteMovie("THIS SHOULD FAIL");
    }

    @Test
    void addShowtimeTest() {
        ShowtimesDatabase showtime = new ShowtimesDatabase();
        showtime.setTime("17:00 2/1/2100");
        showtime.setMovieTitle("TestMovie");
        showtime.setScreenRoomName("Alpha");
        // MAKE SURE THERE IS NO DUPLICATE
        ShowtimesDatabase.deleteShowtime(showtime);
        assert ShowtimesDatabase.addShowtime(showtime);

        showtime.setScreenRoomName("NO ROOM NAMED LIKE THIS");
        assert !ShowtimesDatabase.addShowtime(showtime);

        showtime.setMovieTitle("NO MOVIE NAMED LIKE THIS");
        assert !ShowtimesDatabase.addShowtime(showtime);

        showtime.setMovieTitle("");
        showtime.setScreenRoomName("");
        assert !ShowtimesDatabase.addShowtime(showtime);
    }

    @Test
    void deleteShowtimeTest() {
        ShowtimesDatabase showtime = new ShowtimesDatabase();
        showtime.setTime("24:00 1/1/2100");
        showtime.setMovieTitle("TestMovie");
        showtime.setScreenRoomName("Alpha");
        ShowtimesDatabase.addShowtime(showtime);
        assert ShowtimesDatabase.deleteShowtime(showtime);

        showtime.setScreenRoomName("");
        assert !ShowtimesDatabase.deleteShowtime(showtime);

        showtime.setMovieTitle("THIS MOVIE DOES NOT EXIST");
        assert !ShowtimesDatabase.deleteShowtime(showtime);

        showtime.setTime("THIS DATE DOES NOT EXIST");
        assert !ShowtimesDatabase.deleteShowtime(showtime);

    }

    @Test
    void getAvailableSeatsTest() {
        LinkedList<SeatsDatabase> availableSeats = SeatsDatabase.getMovieAvailableSeats(1,1);
        assert !availableSeats.isEmpty();

        availableSeats = SeatsDatabase.getMovieAvailableSeats(-1,-1);
        assert availableSeats.isEmpty();
        availableSeats = SeatsDatabase.getMovieAvailableSeats(1,-1);
        assert availableSeats.isEmpty();
        availableSeats = SeatsDatabase.getMovieAvailableSeats(-1,1);
        assert availableSeats.isEmpty();

    }

    @Test
    void addReservation() {
        ReservationsDatabase reservation = new ReservationsDatabase();
        // SCREEN ROOM "DELTA"
        reservation.setShowtimeId(1);
        reservation.setSeatId(20);
        // DELETE PRE-EXISTING DUPLICATE RESERVATION
        ReservationsDatabase.deleteReservation(reservation.getSeatId(), reservation.getShowtimeId());
        assert ReservationsDatabase.addReservation(reservation.getSeatId(), reservation.getShowtimeId());

        // DUPLICATE RESERVATION IS NOT ALLOWED
        reservation.setShowtimeId(1);
        reservation.setSeatId(20);
        assert !ReservationsDatabase.addReservation(reservation.getSeatId(), reservation.getShowtimeId());

        // ILLEGAL ARGUMENTS ARE NOT ALLOWED
        reservation.setShowtimeId(-1);
        reservation.setSeatId(20);
        assert !ReservationsDatabase.addReservation(reservation.getSeatId(), reservation.getShowtimeId());

        reservation.setShowtimeId(1);
        reservation.setSeatId(-20);
        assert !ReservationsDatabase.addReservation(reservation.getSeatId(), reservation.getShowtimeId());

        // NOT EXISTING
        reservation.setShowtimeId(1000);
        reservation.setSeatId(1000);
        assert !ReservationsDatabase.addReservation(reservation.getSeatId(), reservation.getShowtimeId());


    }

    @Test
    void getReservationId() {
        ReservationsDatabase reservation = new ReservationsDatabase();
        reservation.setShowtimeId(1);
        reservation.setSeatId(23);
        int id = ReservationsDatabase.getReservationId(reservation.getSeatId(), reservation.getShowtimeId());
        assertEquals(3, id);

        reservation.setShowtimeId(1000);
        reservation.setSeatId(23);
        id = ReservationsDatabase.getReservationId(reservation.getSeatId(), reservation.getShowtimeId());
        assertEquals(-1, id);
    }

    @Test
    void deleteReservation() {
        ReservationsDatabase reservation = new ReservationsDatabase();
        // SCREEN ROOM "GAMMA" SEAT C2 --- id 19
        reservation.setShowtimeId(16);
        reservation.setSeatId(19);
        assert ReservationsDatabase.addReservation(reservation.getSeatId(), reservation.getShowtimeId());
        assert ReservationsDatabase.deleteReservation(reservation.getSeatId(), reservation.getShowtimeId());

        // ALREADY DELETED
        assert !ReservationsDatabase.deleteReservation(reservation.getSeatId(), reservation.getShowtimeId());
        // DELETE WITH ILLEGAL PARAMETERS
        assert !ReservationsDatabase.deleteReservation(-1, 1000);
    }

    @Test
    void printTicket() {
        // SCREEN ROOM "DELTA"
        ReservationsDatabase reservation = new ReservationsDatabase();
        reservation.setShowtimeId(1);
        reservation.setSeatId(20);
        Object response = TicketsDatabase.printTicket(reservation.getSeatId(), reservation.getShowtimeId());
        assertNotEquals(null, response);

        reservation = new ReservationsDatabase();
        reservation.setShowtimeId(1000);
        reservation.setSeatId(20);
        response = TicketsDatabase.printTicket(reservation.getSeatId(), reservation.getShowtimeId());
        assertNull(response);


    }

    @Test
    void addTicket() {
        TicketsDatabase ticket = new TicketsDatabase();
        ticket.setReservationId(3);
        TicketsDatabase.deleteTicket(ticket.getReservationId());
        assert TicketsDatabase.addTicket(ticket.getReservationId(), 15.0);

        // CANNOT ADD THE SAME TICKET
        assert !TicketsDatabase.addTicket(ticket.getReservationId(), 15.0);

        // CANNOT ADD RESERVATION THAT DOES NOT EXIST
        assert !TicketsDatabase.addTicket(5000, 15.0);


    }
}