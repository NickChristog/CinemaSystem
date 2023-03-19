package cinema.project.app;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
public class BackEndController {
    @CrossOrigin(origins = "http://localhost:5173/")
    @GetMapping("/playingNow")
    public ResponseEntity<LinkedList<MoviesDatabase>> getMovies() {
            LinkedList<MoviesDatabase> allMovies = MoviesDatabase.getAllMovies();
            if(!allMovies.isEmpty())
                return new ResponseEntity<>(allMovies, HttpStatus.OK);
            else{
                return new ResponseEntity<>(allMovies, HttpStatus.NOT_FOUND);
            }
    }

    @GetMapping("/getMovieShowtimes/")
    public ResponseEntity<LinkedList<Object>> getMovieShowtimes(@RequestParam String id) {
            LinkedList<Object> movieShowtimes = ShowtimesDatabase.getMovieShowtimes(Integer.parseInt(id));
            if(!movieShowtimes.isEmpty()){
                return new ResponseEntity<>(movieShowtimes, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:5173/addMovie/")
    @PostMapping("/addMovie/")
    public ResponseEntity<LinkedList<Object>> addMovie(@RequestBody MoviesDatabase movie) {
            boolean added = MoviesDatabase.addMovie(movie);
            if(added)
                return new ResponseEntity<>(HttpStatus.CREATED);
            else{
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @CrossOrigin(origins = "http://localhost:5173/updateMovie/")
    @PutMapping("/updateMovie/")
    public ResponseEntity<LinkedList<Object>> updateMovie(@RequestBody MoviesDatabase movie) {
            boolean updated = MoviesDatabase.updateMovie(movie);
            if(updated)
                return new ResponseEntity<>(HttpStatus.CREATED);
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    @CrossOrigin(origins = "http://localhost:5173/deleteMovie/")
    @PostMapping("/deleteMovie/")
    public ResponseEntity<LinkedList<Object>> deleteMovie(@RequestBody MoviesDatabase movie) {
            boolean deleted = MoviesDatabase.deleteMovie(movie.getTitle());
            if(deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    @CrossOrigin(origins = "http://localhost:5173/addShowtime/")
    @PostMapping("/addShowtime/")
    public ResponseEntity<LinkedList<Object>> addShowtime(@RequestBody ShowtimesDatabase showtime) {
            boolean added = ShowtimesDatabase.addShowtime(showtime);
            if(added)
                return new ResponseEntity<>(HttpStatus.CREATED);
            else{
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
    }

    @CrossOrigin(origins = "http://localhost:5173/deleteShowtime/")
    @PostMapping("/deleteShowtime/")
    public ResponseEntity<LinkedList<Object>> deleteShowtime(@RequestBody ShowtimesDatabase showtime) {
            boolean deleted = ShowtimesDatabase.deleteShowtime(showtime);
            if(deleted)
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin("http://localhost:5173/playingNow")
    @GetMapping("/getAvailableSeats/")
    public ResponseEntity<LinkedList<SeatsDatabase>> getAvailableSeats(@RequestParam int movieId, int showtimeId) {
            LinkedList<SeatsDatabase> availableSeats = SeatsDatabase.getMovieAvailableSeats(movieId,showtimeId);
            if(!availableSeats.isEmpty())
                return new ResponseEntity<>(availableSeats, HttpStatus.OK);
            else{
                return new ResponseEntity<>(availableSeats, HttpStatus.NOT_FOUND);
            }
    }

    @CrossOrigin(origins = "http://localhost:5173/playingNow/")
    @PostMapping("/addReservation/")
    public ResponseEntity<Object> addReservation(@RequestBody ReservationsDatabase reservation) {
            boolean reservationAdded = ReservationsDatabase.addReservation(reservation.getSeatId(),
                    reservation.getShowtimeId());
            if(reservationAdded)
                return new ResponseEntity<>(HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/getReservationId/")
    public ResponseEntity<Object> getReservationId(@RequestParam int seatId, int showtimeId) {
        int id = ReservationsDatabase.getReservationId(seatId, showtimeId);
        if(id > 0){
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173/playingNow/")
    @PostMapping("/deleteReservation/")
    public ResponseEntity<HttpStatus> deleteReservation(@RequestBody ReservationsDatabase reservation) {
        boolean response = ReservationsDatabase.deleteReservation(reservation.getSeatId(),reservation.getShowtimeId());
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin("http://localhost:5173/playingNow/")
    @GetMapping("/printTicket/")
    public ResponseEntity<Object> printTicket(@RequestParam int seatId, int showtimeId) {
        Object response = TicketsDatabase.printTicket(seatId, showtimeId);
        if(response != null){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin("http://localhost:5173/playingNow/")
    @PostMapping("/addTicket/")
    public ResponseEntity<Object> addTicket(@RequestBody TicketsDatabase ticket) {
        boolean response = TicketsDatabase.addTicket(ticket.getReservationId(), 15.0);
        if(response){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}