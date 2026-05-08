package model;
 
/**
 * Step 1'de toplanan kullanıcı oturumu bilgilerini tutar.
 */
public class UserProfile {
 
    private String username;
    private String school;
    private String sessionName;
 
    public UserProfile() {}
 
    public UserProfile(String username, String school, String sessionName) {
        this.username    = username;
        this.school      = school;
        this.sessionName = sessionName;
    }
 
    // ── Getter / Setter ───────────────────────────────────────────────────────
    public String getUsername()                    { return username; }
    public void   setUsername(String username)     { this.username = username; }
 
    public String getSchool()                      { return school; }
    public void   setSchool(String school)         { this.school = school; }
 
    public String getSessionName()                 { return sessionName; }
    public void   setSessionName(String s)         { this.sessionName = s; }
}