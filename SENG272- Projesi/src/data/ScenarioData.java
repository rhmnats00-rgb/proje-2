package data;
 
import model.Dimension;
import model.Metric;
import model.Scenario;
 
import java.util.ArrayList;
import java.util.List;
 
/**
 * Tüm hard-coded senaryo verilerinin merkezi deposu.
 * Dosya okuma yoktur; veriler doğrudan Java sınıfı içinde tanımlıdır.
 */
public class ScenarioData {
 
    private static final List<Scenario> ALL = new ArrayList<>();
 
    static {
        buildEducationScenarios();
        buildHealthScenarios();
    }
 
    // ─────────────────────────────────────────────────────────────────────────
    // EDUCATION modu — 2 senaryo
    // ─────────────────────────────────────────────────────────────────────────
    private static void buildEducationScenarios() {
 
        // ── Scenario C — Team Alpha ───────────────────────────────────────────
        Scenario c = new Scenario("Scenario C - Team Alpha", "Education", "Product");
 
        Dimension u1 = new Dimension("Usability", 25);
        u1.addMetric(new Metric("SUS score",       50, true,  0,   100, "points"));
        u1.addMetric(new Metric("Onboarding time", 50, false, 0,   60,  "min"));
        u1.getMetrics().get(0).setValue(89);
        u1.getMetrics().get(1).setValue(5);
 
        Dimension p1 = new Dimension("Performance Efficiency", 20);
        p1.addMetric(new Metric("Video start time", 50, false, 0,  15,  "sec"));
        p1.addMetric(new Metric("Concurrent exams", 50, true,  0,  600, "users"));
        p1.getMetrics().get(0).setValue(3);
        p1.getMetrics().get(1).setValue(450);
 
        Dimension a1 = new Dimension("Accessibility", 20);
        a1.addMetric(new Metric("WCAG compliance",     50, true, 0, 100, "%"));
        a1.addMetric(new Metric("Screen reader score", 50, true, 0, 100, "%"));
        a1.getMetrics().get(0).setValue(78);
        a1.getMetrics().get(1).setValue(82);
 
        Dimension r1 = new Dimension("Reliability", 20);
        r1.addMetric(new Metric("Uptime", 50, true,  95, 100, "%"));
        r1.addMetric(new Metric("MTTR",   50, false,  0, 120, "min"));
        r1.getMetrics().get(0).setValue(99.2);
        r1.getMetrics().get(1).setValue(15);
 
        Dimension f1 = new Dimension("Functional Suitability", 15);
        f1.addMetric(new Metric("Feature completion",    50, true, 0, 100, "%"));
        f1.addMetric(new Metric("Assignment submit rate", 50, true, 0, 100, "%"));
        f1.getMetrics().get(0).setValue(91);
        f1.getMetrics().get(1).setValue(87);
 
        c.addDimension(u1); c.addDimension(p1); c.addDimension(a1);
        c.addDimension(r1); c.addDimension(f1);
 
        // ── Scenario D — Team Beta ────────────────────────────────────────────
        Scenario d = new Scenario("Scenario D - Team Beta", "Education", "Product");
 
        Dimension u2 = new Dimension("Usability", 30);
        u2.addMetric(new Metric("SUS score",       50, true,  0, 100, "points"));
        u2.addMetric(new Metric("Onboarding time", 50, false, 0,  60, "min"));
        u2.getMetrics().get(0).setValue(72);
        u2.getMetrics().get(1).setValue(18);
 
        Dimension p2 = new Dimension("Performance Efficiency", 25);
        p2.addMetric(new Metric("Page load time",    50, false, 0,   10, "sec"));
        p2.addMetric(new Metric("API response time", 50, false, 0,  500, "ms"));
        p2.getMetrics().get(0).setValue(4);
        p2.getMetrics().get(1).setValue(220);
 
        Dimension r2 = new Dimension("Reliability", 25);
        r2.addMetric(new Metric("Uptime",     50, true,  95, 100, "%"));
        r2.addMetric(new Metric("Error rate", 50, false,  0,  10, "%"));
        r2.getMetrics().get(0).setValue(97.5);
        r2.getMetrics().get(1).setValue(2.1);
 
        Dimension m2 = new Dimension("Maintainability", 20);
        m2.addMetric(new Metric("Code coverage",   50, true,   0, 100, "%"));
        m2.addMetric(new Metric("Tech debt ratio", 50, false,  0,  50, "%"));
        m2.getMetrics().get(0).setValue(68);
        m2.getMetrics().get(1).setValue(12);
 
        d.addDimension(u2); d.addDimension(p2);
        d.addDimension(r2); d.addDimension(m2);
 
        ALL.add(c);
        ALL.add(d);
    }
 
    // ─────────────────────────────────────────────────────────────────────────
    // HEALTH modu — 2 senaryo
    // ─────────────────────────────────────────────────────────────────────────
    private static void buildHealthScenarios() {
 
        // ── Scenario A — Hospital System ──────────────────────────────────────
        Scenario a = new Scenario("Scenario A - Hospital System", "Health", "Product");
 
        Dimension s1 = new Dimension("Security", 30);
        s1.addMetric(new Metric("Auth failure rate",     50, false, 0,  100, "%"));
        s1.addMetric(new Metric("Data encryption score", 50, true,  0,  100, "points"));
        s1.getMetrics().get(0).setValue(1.2);
        s1.getMetrics().get(1).setValue(95);
 
        Dimension p3 = new Dimension("Performance Efficiency", 25);
        p3.addMetric(new Metric("Record load time",  50, false, 0,   5, "sec"));
        p3.addMetric(new Metric("Concurrent users",  50, true,  0, 500, "users"));
        p3.getMetrics().get(0).setValue(1.5);
        p3.getMetrics().get(1).setValue(380);
 
        Dimension r3 = new Dimension("Reliability", 25);
        r3.addMetric(new Metric("Uptime", 50, true,  99, 100, "%"));
        r3.addMetric(new Metric("MTTR",   50, false,  0,  60, "min"));
        r3.getMetrics().get(0).setValue(99.7);
        r3.getMetrics().get(1).setValue(8);
 
        Dimension u3 = new Dimension("Usability", 20);
        u3.addMetric(new Metric("SUS score",     50, true,  0, 100, "points"));
        u3.addMetric(new Metric("Training time", 50, false, 0, 120, "min"));
        u3.getMetrics().get(0).setValue(80);
        u3.getMetrics().get(1).setValue(30);
 
        a.addDimension(s1); a.addDimension(p3);
        a.addDimension(r3); a.addDimension(u3);
 
        // ── Scenario B — Pharmacy System ─────────────────────────────────────
        Scenario b = new Scenario("Scenario B - Pharmacy System", "Health", "Process");
 
        Dimension sp = new Dimension("Sprint Efficiency", 35);
        sp.addMetric(new Metric("Velocity",         50, true, 0, 100, "pts/sprint"));
        sp.addMetric(new Metric("Sprint goal rate", 50, true, 0, 100, "%"));
        sp.getMetrics().get(0).setValue(78);
        sp.getMetrics().get(1).setValue(85);
 
        Dimension cq = new Dimension("Code Quality", 35);
        cq.addMetric(new Metric("Bug density",      50, false, 0,  20, "bugs/KLOC"));
        cq.addMetric(new Metric("Code review rate", 50, true,  0, 100, "%"));
        cq.getMetrics().get(0).setValue(3.5);
        cq.getMetrics().get(1).setValue(92);
 
        Dimension tc = new Dimension("Team Collaboration", 30);
        tc.addMetric(new Metric("Meeting attendance", 50, true, 0, 100, "%"));
        tc.addMetric(new Metric("Ticket resolution",  50, true, 0, 100, "%"));
        tc.getMetrics().get(0).setValue(95);
        tc.getMetrics().get(1).setValue(88);
 
        b.addDimension(sp); b.addDimension(cq); b.addDimension(tc);
 
        ALL.add(a);
        ALL.add(b);
    }
 
    // ── Sorgulama metodları ───────────────────────────────────────────────────
 
    /** Verilen mode'a göre tüm senaryoları döner. */
    public static List<Scenario> getByMode(String mode) {
        List<Scenario> result = new ArrayList<>();
        for (Scenario s : ALL) {
            if (s.getMode().equalsIgnoreCase(mode)) {
                result.add(s);
            }
        }
        return result;
    }
 
    /** Tüm senaryoları döner. */
    public static List<Scenario> getAll() {
        return ALL;
    }
}