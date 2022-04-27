public class UserPOJO {
    int id;
    String email, first_name, last_name, avatar;

    @Override
    public String toString() {
        return "UserPOJO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
