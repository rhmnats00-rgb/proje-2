package model;

/**
 * Holds the entire application session state.
 * This is passed between all wizard panels (MVC model).
 */
public class AppState {

    // Step 1 - Profile
    private String username;
    private String school;
    private String sessionName;

    // Step 2 - Define
    private String qualityType;   // "Product" or "Process"
    private String mode;          // "Health" or "Education"
    private Scenario scenario;

    public String getUsername()    { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSchool()      { return school; }
    public void setSchool(String school) { this.school = school; }

    public String getSessionName() { return sessionName; }
    public void setSessionName(String sessionName) { this.sessionName = sessionName; }

    public String getQualityType() { return qualityType; }
    public void setQualityType(String qualityType) { this.qualityType = qualityType; }

    public String getMode()        { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public Scenario getScenario()  { return scenario; }
    public void setScenario(Scenario scenario) { this.scenario = scenario; }
}
