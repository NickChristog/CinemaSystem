package cinema.project.app;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "screenroom", schema = "public", catalog = "CinemaSystem")
public class ScreenroomDatabase {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "roomfloor")
    private int roomFloor;
    @Basic
    @Column(name = "capacity")
    private int capacity;
    @Basic
    @Column(name = "imax")
    private boolean imax;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isImax() {
        return imax;
    }

    public void setImax(boolean imax) {
        this.imax = imax;
    }

    public static int getScreenRoomIdByName(String screenName){
        if(screenName.isEmpty())
            return -1;
        try{
            Query queryMovies = DatabaseSingleton.getEntityManager().createNativeQuery("SELECT id FROM screenroom " +
                    "WHERE name = ?1");
            queryMovies.setParameter(1, screenName);
            Object id = queryMovies.getSingleResult();
            return (int) id;
        }
        catch (Exception e){
            System.out.println("Error in <getScreenRoomIdByName>!");
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScreenroomDatabase that = (ScreenroomDatabase) o;
        return id == that.id && roomFloor == that.roomFloor && capacity == that.capacity && imax == that.imax && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, roomFloor, capacity, imax);
    }
}
