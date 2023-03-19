package cinema.project.app;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tickets", schema = "public", catalog = "CinemaSystem")
public class TicketsDatabase {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "reservationid")
    private int reservationId;
    @Basic
    @Column(name = "price")
    private double price;
    private String movieTitle;
    private String seatNumber;
    private String time;
    private String screenName;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public int getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }

    private int roomFloor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketsDatabase that = (TicketsDatabase) o;
        return id == that.id && reservationId == that.reservationId && Objects.equals(price, that.price);
    }

    public static boolean addTicket(int reservationId, double ticketPrice) {
        EntityTransaction transaction = DatabaseSingleton.getTransaction();
        boolean ticketAdded;
        try {
            transaction.begin();
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "INSERT INTO tickets(reservationid, price) " +
                            "VALUES (?1, ?2)");
            queryMovies.setParameter(1, reservationId);
            queryMovies.setParameter(2, ticketPrice);
            queryMovies.executeUpdate();
            transaction.commit();
            ticketAdded = true;
        } catch (Exception e) {
            System.out.println("Error in <addTicket>!");
            e.printStackTrace();
            ticketAdded = false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return ticketAdded;
    }

    public static boolean deleteTicket(int id) {
        EntityTransaction transaction = DatabaseSingleton.getTransaction();
        boolean ticketDeleted;
        try {
            transaction.begin();
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "DELETE FROM tickets WHERE reservationid = ?1 RETURNING *");
            queryMovies.setParameter(1, id);
            queryMovies.getSingleResult();
            transaction.commit();
            ticketDeleted = true;
        } catch (Exception e) {
            System.out.println("Error in <ticketDeleted>!");
            e.printStackTrace();
            ticketDeleted = false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return ticketDeleted;
    }

    public static Object printTicket(int seatId, int showtimeId){
        try{
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "SELECT movies.title AS movieTitle, seats.seatnumber, showtimes.time, screenroom.name AS screenName, screenroom.roomfloor,reservations.id" +
                            " FROM showtimes JOIN movies ON showtimes.movieId = movies.id" +
                            " JOIN reservations ON reservations.showtimeId = showtimes.id " +
                            "JOIN seats ON seats.id = reservations.seatid JOIN screenroom ON showtimes.screenroomid = screenroom.id " +
                            " WHERE seats.id = ?1 AND showtimes.id = ?2");
            queryMovies.setParameter(1, seatId);
            queryMovies.setParameter(2, showtimeId);
            return queryMovies.getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reservationId, price);
    }
}
