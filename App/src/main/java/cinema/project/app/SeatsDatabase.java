package cinema.project.app;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.Objects;

@Entity
@Table(name = "seats", schema = "public", catalog = "CinemaSystem")
public class SeatsDatabase {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "seatnumber")
    private String seatNumber;
    @Basic
    @Column(name = "screenroomid")
    private int screenRoomId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getScreenRoomId() {
        return screenRoomId;
    }

    public void setScreenRoomId(int screenRoomId) {
        this.screenRoomId = screenRoomId;
    }

    public static boolean addSeat(String seatNumber, String screenName){
        EntityTransaction transaction = DatabaseSingleton.getTransaction();
        boolean seatAdded;
        try {
            transaction.begin();
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "INSERT INTO seats(seatnumber,screenroomid) " +
                            "VALUES (?1, ?2)");
            queryMovies.setParameter(1, seatNumber);
            queryMovies.setParameter(2, ScreenroomDatabase.getScreenRoomIdByName(screenName));
            queryMovies.executeUpdate();
            transaction.commit();
            seatAdded = true;
        } catch (Exception e) {
            System.out.println("Error in <addSeat>!");
            e.printStackTrace();
            seatAdded = false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return seatAdded;
    }

    public static int getSeatId(String seatNumber, String screenName){
        try{
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery("SELECT seats.id FROM seats " +
                    "JOIN screenroom ON screenroom.id = seats.screenroomid WHERE seatnumber = ?1 AND screenroomid = ?2");
            queryMovies.setParameter(1, seatNumber);
            queryMovies.setParameter(2, ScreenroomDatabase.getScreenRoomIdByName(screenName));
            Object id = queryMovies.getSingleResult();
            return (int) id;
        }
        catch (Exception e){
            System.out.println("Error in <getSeatId>!");
            e.printStackTrace();
        }

        return -1;
    }


    public static LinkedList<SeatsDatabase> getMovieAvailableSeats(int movieId, int showtimeId){
        try{
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "SELECT seats.seatNumber, seats.id, title, time, screenRoom.name FROM movies " +
                            "JOIN showtimes ON movies.id = showtimes.movieId " +
                            "JOIN screenRoom ON screenRoom.id = showtimes.screenRoomId " +
                            "JOIN seats ON screenRoom.id = seats.screenRoomId " +
                            "WHERE movies.id = ?1 AND seats.id NOT IN (" +
                            "SELECT reservations.seatId " +
                            "FROM reservations WHERE showtimes.id = reservations.showtimeId) " +
                            "AND showtimes.id = ?2\n" +
                            "ORDER BY title,time,seatNumber");

            queryMovies.setParameter(1, movieId);
            queryMovies.setParameter(2, showtimeId);
            return new LinkedList<SeatsDatabase>(queryMovies.getResultList());
        }
        catch (Exception e){
            System.out.println("Error in <getAvailableSeats With Showtime!>");
            e.printStackTrace();
        }

        return new LinkedList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatsDatabase that = (SeatsDatabase) o;
        return id == that.id && screenRoomId == that.screenRoomId && Objects.equals(seatNumber, that.seatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seatNumber, screenRoomId);
    }
}
