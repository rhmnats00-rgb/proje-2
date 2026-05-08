package ISO;


	import java.util.*;

	public class SWSystemData {
	    public static HashMap<String, ArrayList<SWSystem>> getAllSystems() {
	        HashMap<String, ArrayList<SWSystem>> map = new HashMap<>();
	        ArrayList<SWSystem> webSystems = new ArrayList<>();

	        // ShopSphere Sistemi Oluşturma
	        SWSystem shop = new SWSystem("ShopSphere", "Web", "3.2.1");
	        
	        // Örnek Boyut: Functional Suitability [cite: 112]
	        QualityDimension fs = new QualityDimension("Functional Suitability", "QC.FS", 25);
	        fs.addMetric(new Criterion("Completeness", 50, "higher", 0, 100, "%"));
	        fs.addMetric(new Criterion("Correctness", 50, "higher", 0, 100, "%"));
	        
	        shop.dimensions.add(fs);
	        // (Diğer boyutları da benzer şekilde ekleyebilirsin)

	        webSystems.add(shop);
	        map.put("Web", webSystems);
	        return map;
	    }
	}

