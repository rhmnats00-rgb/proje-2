package ISO;

	import java.util.ArrayList;

	public class QualityDimension {
	    String name, isoCode;
	    double weight;
	    ArrayList<Criterion> criteria = new ArrayList<>();

	    public QualityDimension(String name, String isoCode, double weight) {
	        this.name = name; this.isoCode = isoCode; this.weight = weight;
	    }

	    public void addMetric(Criterion c) { criteria.add(c); }

	    public double getScore() {
	        double total = 0, weights = 0;
	        for (Criterion c : criteria) {
	            total += c.calculateScore() * c.weight;
	            weights += c.weight;
	        }
	        return total / weights;
	    }

	    public String getLabel() {
	        double s = getScore(); // PDF Tablo: 4.5+ Excellent, 3.5+ Good vb. [cite: 58]
	        if (s >= 4.5) return "Excellent Quality";
	        if (s >= 3.5) return "Good Quality";
	        if (s >= 2.5) return "Needs Improvement";
	        return "Poor Quality";
	    }
	}
