package model;
 
/**
 * Tüm adımlar arasında paylaşılan merkezi uygulama durumu.
 * MVC'deki Model katmanını temsil eder.
 */
public class AppState {
 
    private UserProfile profile;
    private String      qualityType;       // "Product" veya "Process"
    private String      mode;             // "Health" veya "Education"
    private Scenario    selectedScenario;
 
    public AppState() {
        this.profile = new UserProfile();
    }
 
    // ── Getter / Setter ───────────────────────────────────────────────────────
    public UserProfile getProfile()                         { return profile; }
    public void        setProfile(UserProfile p)            { this.profile = p; }
 
    public String      getQualityType()                     { return qualityType; }
    public void        setQualityType(String qualityType)   { this.qualityType = qualityType; }
 
    public String      getMode()                            { return mode; }
    public void        setMode(String mode)                 { this.mode = mode; }
 
    public Scenario    getSelectedScenario()                { return selectedScenario; }
    public void        setSelectedScenario(Scenario s)      { this.selectedScenario = s; }
}