import java.io.Serializable;

public class RegisterPOJO implements Serializable {
    int id;
    String createdAt;

    @Override
    public String toString() {
        return "RegisterPOJO{" +
                "id=" + id +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
