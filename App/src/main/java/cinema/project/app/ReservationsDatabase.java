package cinema.project.app;
import jakarta.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "reservations", schema = "public", catalog = "CinemaSystem")
public class ReservationsDatabase {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "seatid")
    private int seatId;
    @Basic
    @Column(name = "showtimeid")
    private int showtimeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }


    public static boolean addReservation(int seatId, int showtimeId) {
        EntityTransaction transaction = DatabaseSingleton.getTransaction();
        boolean reservationAdded;
        if(seatId < 0 || showtimeId < 0)
            return false;
        try {
            transaction.begin();
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "INSERT INTO reservations(seatid, showtimeid) " +
                            "VALUES (?1, ?2)");
            queryMovies.setParameter(1,seatId);
            queryMovies.setParameter(2,showtimeId);
            queryMovies.executeUpdate();
            transaction.commit();
            reservationAdded = true;
        } catch (Exception e) {
            System.out.println("Error in <addReservation>!");
            e.printStackTrace();
            reservationAdded = false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return reservationAdded;
    }

    public static boolean deleteReservation(int seatId, int showtimeId) {
        EntityTransaction transaction = DatabaseSingleton.getTransaction();
        if(seatId < 0 || showtimeId < 0)
            return false;
        boolean reservationDeleted;
        try {
            transaction.begin();
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "DELETE FROM reservations WHERE seatId = ?1 AND showtimeid=?2 RETURNING *");
            queryMovies.setParameter(1, seatId);
            queryMovies.setParameter(2, showtimeId);
            queryMovies.getSingleResult();
            transaction.commit();
            reservationDeleted = true;
        } catch (Exception e) {
            System.out.println("Error in <deleteReservation>!");
            e.printStackTrace();
            reservationDeleted = false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return reservationDeleted;
    }

    public static int getReservationId(int seatId, int showtimeId){
        if(seatId < 0 || showtimeId < 0)
            return -1;
        try{
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery("SELECT id FROM reservations " +
                    "WHERE seatid = ?1 AND showtimeid = ?2");
            queryMovies.setParameter(1, seatId);
            queryMovies.setParameter(2, showtimeId);
            Object id = queryMovies.getSingleResult();
            if(id instanceof Integer)
                return (int) id;
            else{
                return -1;
            }
        }
        catch (Exception e){
            System.out.println("Error in <getReservationId>!");
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationsDatabase that = (ReservationsDatabase) o;
        return id == that.id && seatId == that.seatId && showtimeId == that.showtimeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seatId, showtimeId);
    }
}
