package cinema.project.app;
import jakarta.persistence.*;
import java.util.LinkedList;
import java.util.Objects;

@Entity
@Table(name = "showtimes", schema = "public", catalog = "CinemaSystem")
public class ShowtimesDatabase {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "time")
    private String time;
    @Basic
    @Column(name = "movieid")
    private int movieId;
    @Basic
    @Column(name = "screenroomid")
    private int screenRoomId;

    private String movieTitle;
    private String screenRoomName;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getScreenRoomName() {
        return screenRoomName;
    }

    public void setScreenRoomName(String screenRoomName) {
        this.screenRoomName = screenRoomName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getScreenRoomId() {
        return screenRoomId;
    }

    public void setScreenRoomId(int screenRoomId) {
        this.screenRoomId = screenRoomId;
    }

    public static LinkedList<Object> getMovieShowtimes(int id){
        Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                "SELECT showtimes.id,movies.title,screenroomid, screenroom.name, " +
                        "showtimes.time, movies.pgrating,movies.spokenlanguage,movies.runtime, movies.posterurl, movies.releasedate  " +
                        "FROM movies JOIN showtimes ON movies.id = showtimes.movieId JOIN screenroom on showtimes.screenroomid = screenroom.id " +
                        "WHERE movies.id = ?1 ORDER BY showtimes.id");
        queryMovies.setParameter(1, id);
        return new LinkedList<>(queryMovies.getResultList());

    }

    public static boolean addShowtime(ShowtimesDatabase showtime){
        EntityTransaction transaction = DatabaseSingleton.getTransaction();
        boolean showtimeAdded;
        if(!evaluateShowtimeFields(showtime)){
            return false;
        }
        try {
            transaction.begin();
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "INSERT INTO showtimes(time, movieid, screenroomid) " +
                            "VALUES (?1, ?2 ,?3)");
            queryMovies.setParameter(1, showtime.getTime());
            queryMovies.setParameter(2, MoviesDatabase.getIdByTitle(showtime.getMovieTitle()));
            queryMovies.setParameter(3, ScreenroomDatabase.getScreenRoomIdByName(showtime.getScreenRoomName()));
            queryMovies.executeUpdate();
            transaction.commit();
            showtimeAdded = true;
        } catch (Exception e) {
            System.out.println("Error in <add Showtime>!");
            e.printStackTrace();
            showtimeAdded = false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return showtimeAdded;
    }

    public static boolean deleteShowtime(ShowtimesDatabase showtime) {
        EntityTransaction transaction = DatabaseSingleton.getTransaction();
        boolean showtimeDeleted;
        if(!evaluateShowtimeFields(showtime)){
            return false;
        }
        try {
            transaction.begin();
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "DELETE FROM showtimes WHERE time=?1 AND movieid=?2 AND screenroomid=?3 RETURNING *");
            queryMovies.setParameter(1, showtime.getTime());
            queryMovies.setParameter(2, MoviesDatabase.getIdByTitle(showtime.getMovieTitle()));
            queryMovies.setParameter(3, ScreenroomDatabase.getScreenRoomIdByName(showtime.getScreenRoomName()));
            queryMovies.getSingleResult();
            showtimeDeleted = true;
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Error in <screeningDeleted>!");
            e.printStackTrace();
            showtimeDeleted = false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return showtimeDeleted;
    }

    public static int getShowtimeId(String showtime, String movieTitle, String screenName){
        try{
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery("SELECT showtimes.id FROM showtimes " +
                    "JOIN screenroom ON screenroom.id = showtimes.screenroomid JOIN movies ON showtimes.movieid = movies.id" +
                    " WHERE movieid = ?1 AND screenroomid = ?2 AND showtimes.time = ?3");
            queryMovies.setParameter(1, MoviesDatabase.getIdByTitle(movieTitle));
            queryMovies.setParameter(2, ScreenroomDatabase.getScreenRoomIdByName(screenName));
            queryMovies.setParameter(3, showtime);
            Object id = queryMovies.getSingleResult();
            return (int) id;
        }
        catch (Exception e){
            System.out.println("Error in <getScreeningId>!");
            e.printStackTrace();
        }

        return -1;
    }


    public static boolean evaluateShowtimeFields(ShowtimesDatabase showtime){
//        System.out.println(showtime.getTime());
//        System.out.println(showtime.getMovieTitle());
//        System.out.println(showtime.getScreenRoomName());
        if(showtime.getMovieTitle().isEmpty() || showtime.getTime().isEmpty() || showtime.getScreenRoomName().isEmpty()){
            return false;
        }
        else return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowtimesDatabase that = (ShowtimesDatabase) o;
        return id == that.id && movieId == that.movieId && screenRoomId == that.screenRoomId && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, movieId, screenRoomId);
    }
}
