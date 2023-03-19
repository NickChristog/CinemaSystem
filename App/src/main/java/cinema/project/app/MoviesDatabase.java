package cinema.project.app;
import jakarta.persistence.*;
import java.util.LinkedList;
import java.util.Objects;

@Entity
@Table(name = "movies", schema = "public", catalog = "CinemaSystem")
public class MoviesDatabase {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "runtime")
    private String runtime;
    @Basic
    @Column(name = "pgrating")
    private Integer pgRating;
    @Basic
    @Column(name = "spokenlanguage")
    private String spokenLanguage;

    @Basic
    @Column(name="posterUrl")
    private String posterUrl;

    @Basic
    @Column(name="releaseDate")
    private String releaseDate;

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Integer getPgRating() {
        return pgRating;
    }

    public void setPgRating(Integer pgRating) {
        this.pgRating = pgRating;
    }

    public String getSpokenLanguage() {
        return spokenLanguage;
    }

    public void setSpokenLanguage(String spokenLanguage) {
        this.spokenLanguage = spokenLanguage;
    }

    public static boolean addMovie(MoviesDatabase movie) {
        EntityTransaction transaction = DatabaseSingleton.getTransaction();
        // filter out empty movie titles
        boolean movieAdded = movie.getTitle().isEmpty();
        if(movieAdded) {
            return false;
        }
        try {
            transaction.begin();
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "INSERT INTO movies(title, runtime, pgrating, spokenlanguage, posterurl, releasedate) " +
                            "VALUES (?1,?2 ,?3, ?4, ?5, ?6)");
            queryMovies.setParameter(1, movie.getTitle());
            queryMovies.setParameter(2, movie.getRuntime());
            queryMovies.setParameter(3, movie.getPgRating());
            queryMovies.setParameter(4, movie.getSpokenLanguage());
            queryMovies.setParameter(5, movie.getPosterUrl());
            queryMovies.setParameter(6, movie.getReleaseDate());
            queryMovies.executeUpdate();
            transaction.commit();
            movieAdded = true;
        } catch (Exception e) {
            System.out.println("Error in <addMovie>!");
            e.printStackTrace();
            movieAdded = false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return movieAdded;
    }

    public static boolean updateMovie(MoviesDatabase movie) {
        EntityTransaction transaction = DatabaseSingleton.getTransaction();
        boolean movieUpdated;
        if(movie.getTitle().isEmpty()){
            return false;
        }
//        System.out.println(movie);
        try {
            transaction.begin();
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "UPDATE movies SET runtime = CASE WHEN ?1 IS NOT NULL THEN ?1 END " +
                            "WHERE title= ?2");
            queryMovies.setParameter(1, movie.getRuntime());
            queryMovies.setParameter(2, movie.getTitle());
            queryMovies.executeUpdate();

            queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "UPDATE movies SET posterurl = CASE WHEN ?1 IS NOT NULL THEN ?1 END " +
                            "WHERE title= ?2 RETURNING *");
            queryMovies.setParameter(1, movie.getPosterUrl());
            queryMovies.setParameter(2, movie.getTitle());
            queryMovies.getResultList();

            transaction.commit();
            movieUpdated = true;
        } catch (Exception e) {
            System.out.println("Error in <updateMovie>!");
            e.printStackTrace();
            movieUpdated = false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return movieUpdated;
    }

    public static boolean deleteMovie(String title) {
        EntityTransaction transaction = DatabaseSingleton.getTransaction();
        boolean movieDeleted;
        try {
            transaction.begin();
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery(
                    "DELETE FROM movies WHERE title = ?1 RETURNING *");
            queryMovies.setParameter(1, title);
            queryMovies.getSingleResult();
            transaction.commit();
            movieDeleted = true;
        } catch (Exception e) {
            System.out.println("Error in <deleteMovie>!");
            e.printStackTrace();
            movieDeleted = false;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return movieDeleted;
    }

    public static int getIdByTitle(String title){
        if(title.isEmpty())
            return -1;
        try{
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery("SELECT id FROM movies " +
                    "WHERE title = ?1");
            queryMovies.setParameter(1, title);
            Object id = queryMovies.getSingleResult();
            return (int) id;
        }
        catch (Exception e){
            System.out.println("Error in <getIdByTitle>!");
            e.printStackTrace();
        }

        return -1;
    }

    public static LinkedList<MoviesDatabase> getAllMovies(){
        try{
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery("SELECT * FROM movies");
            return new LinkedList<>(queryMovies.getResultList());
        }catch (Exception e){
            System.out.println("Error in <getAllMovies>!");
            e.printStackTrace();
            return new LinkedList<>();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoviesDatabase that = (MoviesDatabase) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(runtime, that.runtime)
                && Objects.equals(pgRating, that.pgRating) && Objects.equals(spokenLanguage, that.spokenLanguage);
    }


    @Override
    public String toString() {
        return "MoviesDatabase{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", runtime='" + runtime + '\'' +
                ", pgRating=" + pgRating +
                ", spokenLanguage='" + spokenLanguage + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, runtime, pgRating, spokenLanguage);
    }
}
