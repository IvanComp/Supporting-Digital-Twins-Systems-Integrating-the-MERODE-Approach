package helpers;

/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author Nick Scheynen
 */
 
import dao.Suppliedproduct;
import dao.SuppliedproductImpl;
import dao.Purchaseorder;
import dao.PurchaseorderImpl;
import dao.Poline;
import dao.PolineImpl;
import dao.Product;
import dao.ProductImpl;
import dao.Supplier;
import dao.SupplierImpl;
import java.util.*;

public abstract class ResponseFactory {

	
	// Suppliedproduct Responses
	public static Collection makeAllSuppliedproduct(Collection data) {
		Collection result = new ArrayList<HashMap>();
		for( Iterator<SuppliedproductImpl> i = data.iterator(); i.hasNext(); ) {
			HashMap item = makeSuppliedproduct(i.next());
			result.add(item);
		}
		return result;
	}
	
	public static HashMap makeSuppliedproduct(Suppliedproduct suppliedproduct) {
		LinkedHashMap result = new LinkedHashMap();
			
		// Fill in attributes
		result.put("id", suppliedproduct.getId());
		result.put("price", suppliedproduct.getPrice());
		result.put("state", suppliedproduct.getState().getName());

		// Add dependents
		HashMap dependents = new HashMap();
		dependents.put("poline", makeSuppliedproductDepPoline(suppliedproduct.getPoline()));
		// Add dependents to result
		result.put("dependents", dependents);

		// Add masters
		HashMap masters = new HashMap();
		masters.put("product", makeSuppliedproductMasProduct(suppliedproduct.getProduct()));
		masters.put("supplier", makeSuppliedproductMasSupplier(suppliedproduct.getSupplier()));
		// Add masters to result
		result.put("masters", masters);

		// Add events
		result.put("events", makeSuppliedproductEvents(suppliedproduct));
		
		return result;
	}

	// Suppliedproduct Helpers
	private static Collection makeSuppliedproductDepPoline(Collection polines) {
		ArrayList result = new ArrayList();
		for(Iterator<PolineImpl> i = polines.iterator(); i.hasNext();) {
			PolineImpl poline = i.next();
			HashMap item = new HashMap<String, String>();
			item.put("url", "/poline/"+poline.getId());
			result.add(item);
		}
		return result;
	}

	private static HashMap makeSuppliedproductMasProduct(Product product) {
		HashMap item = new HashMap<String, String>();
		if(product != null) {
			item.put("url", "/product/" + product.getId()); 
		}
		return item;
	}
	private static HashMap makeSuppliedproductMasSupplier(Supplier supplier) {
		HashMap item = new HashMap<String, String>();
		if(supplier != null) {
			item.put("url", "/supplier/" + supplier.getId()); 
		}
		return item;
	}

	private static Collection makeSuppliedproductEvents(Suppliedproduct suppliedproduct) {
		ArrayList events = new ArrayList();
		switch (suppliedproduct.getState().getName()) {
			case "allocated":
				break;
			case "exists":
				break;
			case "ended":
				break;
		}
		return events;
	}	
	
	// Purchaseorder Responses
	public static Collection makeAllPurchaseorder(Collection data) {
		Collection result = new ArrayList<HashMap>();
		for( Iterator<PurchaseorderImpl> i = data.iterator(); i.hasNext(); ) {
			HashMap item = makePurchaseorder(i.next());
			result.add(item);
		}
		return result;
	}
	
	public static HashMap makePurchaseorder(Purchaseorder purchaseorder) {
		LinkedHashMap result = new LinkedHashMap();
			
		// Fill in attributes
		result.put("id", purchaseorder.getId());
		result.put("name", purchaseorder.getName());
		result.put("state", purchaseorder.getState().getName());

		// Add dependents
		HashMap dependents = new HashMap();
		dependents.put("poline", makePurchaseorderDepPoline(purchaseorder.getPoline()));
		// Add dependents to result
		result.put("dependents", dependents);

		// Add masters
		HashMap masters = new HashMap();
		masters.put("supplier", makePurchaseorderMasSupplier(purchaseorder.getSupplier()));
		// Add masters to result
		result.put("masters", masters);

		// Add events
		result.put("events", makePurchaseorderEvents(purchaseorder));
		
		return result;
	}

	// Purchaseorder Helpers
	private static Collection makePurchaseorderDepPoline(Collection polines) {
		ArrayList result = new ArrayList();
		for(Iterator<PolineImpl> i = polines.iterator(); i.hasNext();) {
			PolineImpl poline = i.next();
			HashMap item = new HashMap<String, String>();
			item.put("url", "/poline/"+poline.getId());
			result.add(item);
		}
		return result;
	}

	private static HashMap makePurchaseorderMasSupplier(Supplier supplier) {
		HashMap item = new HashMap<String, String>();
		if(supplier != null) {
			item.put("url", "/supplier/" + supplier.getId()); 
		}
		return item;
	}

	private static Collection makePurchaseorderEvents(Purchaseorder purchaseorder) {
		ArrayList events = new ArrayList();
		switch (purchaseorder.getState().getName()) {
			case "allocated":
				break;
			case "exists":
				break;
			case "ended":
				break;
		}
		return events;
	}	
	
	// Poline Responses
	public static Collection makeAllPoline(Collection data) {
		Collection result = new ArrayList<HashMap>();
		for( Iterator<PolineImpl> i = data.iterator(); i.hasNext(); ) {
			HashMap item = makePoline(i.next());
			result.add(item);
		}
		return result;
	}
	
	public static HashMap makePoline(Poline poline) {
		LinkedHashMap result = new LinkedHashMap();
			
		// Fill in attributes
		result.put("id", poline.getId());
		result.put("name", poline.getName());
		result.put("state", poline.getState().getName());


		// Add masters
		HashMap masters = new HashMap();
		masters.put("suppliedproduct", makePolineMasSuppliedproduct(poline.getSuppliedproduct()));
		masters.put("purchaseorder", makePolineMasPurchaseorder(poline.getPurchaseorder()));
		// Add masters to result
		result.put("masters", masters);

		// Add events
		result.put("events", makePolineEvents(poline));
		
		return result;
	}

	// Poline Helpers

	private static HashMap makePolineMasSuppliedproduct(Suppliedproduct suppliedproduct) {
		HashMap item = new HashMap<String, String>();
		if(suppliedproduct != null) {
			item.put("url", "/suppliedproduct/" + suppliedproduct.getId()); 
		}
		return item;
	}
	private static HashMap makePolineMasPurchaseorder(Purchaseorder purchaseorder) {
		HashMap item = new HashMap<String, String>();
		if(purchaseorder != null) {
			item.put("url", "/purchaseorder/" + purchaseorder.getId()); 
		}
		return item;
	}

	private static Collection makePolineEvents(Poline poline) {
		ArrayList events = new ArrayList();
		switch (poline.getState().getName()) {
			case "allocated":
				break;
			case "exists":
				break;
			case "ended":
				break;
		}
		return events;
	}	
	
	// Product Responses
	public static Collection makeAllProduct(Collection data) {
		Collection result = new ArrayList<HashMap>();
		for( Iterator<ProductImpl> i = data.iterator(); i.hasNext(); ) {
			HashMap item = makeProduct(i.next());
			result.add(item);
		}
		return result;
	}
	
	public static HashMap makeProduct(Product product) {
		LinkedHashMap result = new LinkedHashMap();
			
		// Fill in attributes
		result.put("id", product.getId());
		result.put("name", product.getName());
		result.put("state", product.getState().getName());

		// Add dependents
		HashMap dependents = new HashMap();
		dependents.put("suppliedproduct", makeProductDepSuppliedproduct(product.getSuppliedproduct()));
		// Add dependents to result
		result.put("dependents", dependents);


		// Add events
		result.put("events", makeProductEvents(product));
		
		return result;
	}

	// Product Helpers
	private static Collection makeProductDepSuppliedproduct(Collection suppliedproducts) {
		ArrayList result = new ArrayList();
		for(Iterator<SuppliedproductImpl> i = suppliedproducts.iterator(); i.hasNext();) {
			SuppliedproductImpl suppliedproduct = i.next();
			HashMap item = new HashMap<String, String>();
			item.put("url", "/suppliedproduct/"+suppliedproduct.getId());
			result.add(item);
		}
		return result;
	}


	private static Collection makeProductEvents(Product product) {
		ArrayList events = new ArrayList();
		switch (product.getState().getName()) {
			case "allocated":
				break;
			case "exists":
				break;
			case "ended":
				break;
		}
		return events;
	}	
	
	// Supplier Responses
	public static Collection makeAllSupplier(Collection data) {
		Collection result = new ArrayList<HashMap>();
		for( Iterator<SupplierImpl> i = data.iterator(); i.hasNext(); ) {
			HashMap item = makeSupplier(i.next());
			result.add(item);
		}
		return result;
	}
	
	public static HashMap makeSupplier(Supplier supplier) {
		LinkedHashMap result = new LinkedHashMap();
			
		// Fill in attributes
		result.put("id", supplier.getId());
		result.put("name", supplier.getName());
		result.put("state", supplier.getState().getName());

		// Add dependents
		HashMap dependents = new HashMap();
		dependents.put("suppliedproduct", makeSupplierDepSuppliedproduct(supplier.getSuppliedproduct()));
		dependents.put("purchaseorder", makeSupplierDepPurchaseorder(supplier.getPurchaseorder()));
		// Add dependents to result
		result.put("dependents", dependents);


		// Add events
		result.put("events", makeSupplierEvents(supplier));
		
		return result;
	}

	// Supplier Helpers
	private static Collection makeSupplierDepSuppliedproduct(Collection suppliedproducts) {
		ArrayList result = new ArrayList();
		for(Iterator<SuppliedproductImpl> i = suppliedproducts.iterator(); i.hasNext();) {
			SuppliedproductImpl suppliedproduct = i.next();
			HashMap item = new HashMap<String, String>();
			item.put("url", "/suppliedproduct/"+suppliedproduct.getId());
			result.add(item);
		}
		return result;
	}
	private static Collection makeSupplierDepPurchaseorder(Collection purchaseorders) {
		ArrayList result = new ArrayList();
		for(Iterator<PurchaseorderImpl> i = purchaseorders.iterator(); i.hasNext();) {
			PurchaseorderImpl purchaseorder = i.next();
			HashMap item = new HashMap<String, String>();
			item.put("url", "/purchaseorder/"+purchaseorder.getId());
			result.add(item);
		}
		return result;
	}


	private static Collection makeSupplierEvents(Supplier supplier) {
		ArrayList events = new ArrayList();
		switch (supplier.getState().getName()) {
			case "allocated":
				break;
			case "exists":
				break;
			case "ended":
				break;
		}
		return events;
	}	
	
	public static HashMap makeSuccess(String event) {
		HashMap result = new HashMap<String, String>();
		result.put("message", "Successfully executed "+event+" event.");
		return result;
	}

	public static HashMap makeFail(String msg) {
		HashMap result = new HashMap<String, String>();
		result.put("error", msg);
		return result;
	}

}