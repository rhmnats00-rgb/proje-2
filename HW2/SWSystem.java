package ISO;

	import java.util.ArrayList;

	public class SWSystem {
	    String name, category, version;
	    ArrayList<QualityDimension> dimensions = new ArrayList<>();

	    public SWSystem(String name, String category, String version) {
	        this.name = name; this.category = category; this.version = version;
	    }

	    public void printReport() {
	        System.out.println("=== SOFTWARE QUALITY REPORT: " + name + " v" + version + " ===");
	        QualityDimension weakest = dimensions.get(0);
	        
	        for (QualityDimension qd : dimensions) {
	            System.out.println("\n--- " + qd.name + " [" + qd.isoCode + "] ---");
	            for (Criterion c : qd.criteria) {
	                System.out.println(c.name + ": " + c.value + c.unit + " -> Score: " + c.calculateScore());
	            }
	            System.out.println(">> Dimension Score: " + qd.getScore() + " [" + qd.getLabel() + "]");
	            if (qd.getScore() < weakest.getScore()) weakest = qd;
	        }
	        
	        System.out.println("\nWEAKEST AREA: " + weakest.name + " (Gap: " + (5.0 - weakest.getScore()) + ")");
	    }
	}

