package data;

import model.Dimension;
import model.Metric;
import model.Scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central repository for all hard-coded scenario data.
 * Organised as: mode -> list of scenarios.
 */
public class ScenarioRepository {

    // key: mode name ("Health", "Education"), value: list of scenarios
    private static final Map<String, List<Scenario>> scenarioMap = new HashMap<>();

    static {
        buildEducationScenarios();
        buildHealthScenarios();
    }

    // ---------------------------------------------------------------
    // EDUCATION MODE
    // ---------------------------------------------------------------
    private static void buildEducationScenarios() {
        List<Scenario> eduList = new ArrayList<>();

        // --- Scenario C: Team Alpha ---
        Scenario scenC = new Scenario("Scenario C — Team Alpha");

        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("SUS score",        50, "Higher",  0, 100, "points", 89));
        usability.addMetric(new Metric("Onboarding time",  50, "Lower",   0,  60, "min",     5));

        Dimension perfEff = new Dimension("Performance Efficiency", 20);
        perfEff.addMetric(new Metric("Video start time",   50, "Lower",   0,  15, "sec",     2));
        perfEff.addMetric(new Metric("Concurrent exams",   50, "Higher",  0, 600, "users", 450));

        Dimension access = new Dimension("Accessibility", 20);
        access.addMetric(new Metric("WCAG compliance",     50, "Higher",  0, 100, "%",      85));
        access.addMetric(new Metric("Screen reader score", 50, "Higher",  0, 100, "%",      78));

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime",          50, "Higher", 95, 100, "%",    99.5));
        reliability.addMetric(new Metric("MTTR",            50, "Lower",   0, 120, "min",   10));

        Dimension funcSuit = new Dimension("Functional Suitability", 15);
        funcSuit.addMetric(new Metric("Feature completion",    50, "Higher", 0, 100, "%", 92));
        funcSuit.addMetric(new Metric("Assignment submit rate", 50, "Higher", 0, 100, "%", 88));

        scenC.addDimension(usability);
        scenC.addDimension(perfEff);
        scenC.addDimension(access);
        scenC.addDimension(reliability);
        scenC.addDimension(funcSuit);

        // --- Scenario D: Team Beta ---
        Scenario scenD = new Scenario("Scenario D — Team Beta");

        Dimension usability2 = new Dimension("Usability", 25);
        usability2.addMetric(new Metric("SUS score",        50, "Higher",  0, 100, "points", 72));
        usability2.addMetric(new Metric("Onboarding time",  50, "Lower",   0,  60, "min",    20));

        Dimension perfEff2 = new Dimension("Performance Efficiency", 20);
        perfEff2.addMetric(new Metric("Video start time",   50, "Lower",   0,  15, "sec",     6));
        perfEff2.addMetric(new Metric("Concurrent exams",   50, "Higher",  0, 600, "users", 300));

        Dimension access2 = new Dimension("Accessibility", 20);
        access2.addMetric(new Metric("WCAG compliance",     50, "Higher",  0, 100, "%",      60));
        access2.addMetric(new Metric("Screen reader score", 50, "Higher",  0, 100, "%",      55));

        Dimension reliability2 = new Dimension("Reliability", 20);
        reliability2.addMetric(new Metric("Uptime",         50, "Higher", 95, 100, "%",    97.0));
        reliability2.addMetric(new Metric("MTTR",           50, "Lower",   0, 120, "min",   40));

        Dimension funcSuit2 = new Dimension("Functional Suitability", 15);
        funcSuit2.addMetric(new Metric("Feature completion",    50, "Higher", 0, 100, "%", 75));
        funcSuit2.addMetric(new Metric("Assignment submit rate", 50, "Higher", 0, 100, "%", 70));

        scenD.addDimension(usability2);
        scenD.addDimension(perfEff2);
        scenD.addDimension(access2);
        scenD.addDimension(reliability2);
        scenD.addDimension(funcSuit2);

        eduList.add(scenC);
        eduList.add(scenD);
        scenarioMap.put("Education", eduList);
    }

    // ---------------------------------------------------------------
    // HEALTH MODE
    // ---------------------------------------------------------------
    private static void buildHealthScenarios() {
        List<Scenario> healthList = new ArrayList<>();

        // --- Scenario A: Clinic System ---
        Scenario scenA = new Scenario("Scenario A — Clinic System");

        Dimension security = new Dimension("Security", 30);
        security.addMetric(new Metric("Auth failure rate",    50, "Lower",   0, 100, "%",     2));
        security.addMetric(new Metric("Data encryption rate", 50, "Higher",  0, 100, "%",    95));

        Dimension usabilityH = new Dimension("Usability", 25);
        usabilityH.addMetric(new Metric("SUS score",          50, "Higher",  0, 100, "points", 80));
        usabilityH.addMetric(new Metric("Task completion",    50, "Higher",  0, 100, "%",      90));

        Dimension reliabilityH = new Dimension("Reliability", 25);
        reliabilityH.addMetric(new Metric("Uptime",           50, "Higher", 95, 100, "%",   99.0));
        reliabilityH.addMetric(new Metric("Error rate",       50, "Lower",   0,  10, "%",    0.5));

        Dimension perfH = new Dimension("Performance", 20);
        perfH.addMetric(new Metric("Response time",           50, "Lower",   0,   5, "sec",   0.8));
        perfH.addMetric(new Metric("Throughput",              50, "Higher",  0, 500, "req/s", 350));

        scenA.addDimension(security);
        scenA.addDimension(usabilityH);
        scenA.addDimension(reliabilityH);
        scenA.addDimension(perfH);

        // --- Scenario B: Hospital Portal ---
        Scenario scenB = new Scenario("Scenario B — Hospital Portal");

        Dimension security2 = new Dimension("Security", 30);
        security2.addMetric(new Metric("Auth failure rate",    50, "Lower",   0, 100, "%",    8));
        security2.addMetric(new Metric("Data encryption rate", 50, "Higher",  0, 100, "%",   70));

        Dimension usability2 = new Dimension("Usability", 25);
        usability2.addMetric(new Metric("SUS score",           50, "Higher",  0, 100, "points", 65));
        usability2.addMetric(new Metric("Task completion",     50, "Higher",  0, 100, "%",      75));

        Dimension reliability2 = new Dimension("Reliability", 25);
        reliability2.addMetric(new Metric("Uptime",            50, "Higher", 95, 100, "%",   96.5));
        reliability2.addMetric(new Metric("Error rate",        50, "Lower",   0,  10, "%",    3.0));

        Dimension perf2 = new Dimension("Performance", 20);
        perf2.addMetric(new Metric("Response time",            50, "Lower",   0,   5, "sec",  2.5));
        perf2.addMetric(new Metric("Throughput",               50, "Higher",  0, 500, "req/s",200));

        scenB.addDimension(security2);
        scenB.addDimension(usability2);
        scenB.addDimension(reliability2);
        scenB.addDimension(perf2);

        healthList.add(scenA);
        healthList.add(scenB);
        scenarioMap.put("Health", healthList);
    }

    // ---------------------------------------------------------------
    // Public API
    // ---------------------------------------------------------------

    /** Returns all scenarios for the given mode, or an empty list. */
    public static List<Scenario> getScenarios(String mode) {
        return scenarioMap.getOrDefault(mode, new ArrayList<>());
    }

    /** Returns all available mode names. */
    public static List<String> getModes() {
        return new ArrayList<>(scenarioMap.keySet());
    }
}
