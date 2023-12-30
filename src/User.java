public class User {
    private String useName;
    private String password;

    public User(){

    }

    public User(String useName, String password) {
        this.useName = useName;
        this.password = password;
    }

    public String getUseName() {
        return useName;
    }

    public String getPassword() {
        return password;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
