package model;
public class User {
    private int id;
    private String username;
    private String password;
    private String photoPath;
    private String nationality;

    public User(){
        
    }

    public User(String username , String password,String photopath){
        this.username = username;
        this.password = password;
        this.photoPath = photopath;
        this.nationality = "Unknown";
    }
    public User(int id, String username, String password, String photoPath) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.photoPath = photoPath;
        this.nationality = "Unknown";
    }

    //user
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getNationality(){
        return nationality;
    }
   
    public void setNationality(String nationality){
        this.nationality = nationality;
    }

    //check valid register
   public String validateRegistration(String confirmPassword) {
        if (username == null || username.length() < 5) {
            return "Username must be at least 6 characters.";
        }

        if (password == null || password.length() < 6) {
            return "Password must be at least 6 characters.";
        }

        if (!password.matches(".*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/\\\\|~`].*")) {
            return "Password must contain at least one special character.";
        }

        if (password.contains(" ")) {
            return "Password must not contain spaces.";
        }

        if (!password.equals(confirmPassword)) {
            return "Passwords do not match.";
        }

        return null; 
    }
}
