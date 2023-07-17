/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author Nick Scheynen
 */
 
import handlers.MerodeMainEventHandler;
import dao.Suppliedproduct;
import dao.SuppliedproductFactory;
import dao.Purchaseorder;
import dao.PurchaseorderFactory;
import dao.Poline;
import dao.PolineFactory;
import dao.Product;
import dao.ProductFactory;
import dao.Supplier;
import dao.SupplierFactory;
import helpers.ResponseFactory;

import com.google.gson.Gson;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import static spark.Spark.*;

public class MerodeApplicationService {

	public static void main(String[] args) {
	
		// Make EventHandler
		MerodeMainEventHandler eh = new MerodeMainEventHandler();
		
		get("/", (request, response) -> {					
			HashMap result = new HashMap<String, ArrayList>();	
			
			// Resources
			ArrayList resources = new ArrayList<HashMap>();
			HashMap suppliedproduct = new HashMap<String, String>();
			suppliedproduct.put("name", "Suppliedproduct");
			suppliedproduct.put("href", "/suppliedproduct");
			resources.add(suppliedproduct);
			HashMap purchaseorder = new HashMap<String, String>();
			purchaseorder.put("name", "Purchaseorder");
			purchaseorder.put("href", "/purchaseorder");
			resources.add(purchaseorder);
			HashMap poline = new HashMap<String, String>();
			poline.put("name", "Poline");
			poline.put("href", "/poline");
			resources.add(poline);
			HashMap product = new HashMap<String, String>();
			product.put("name", "Product");
			product.put("href", "/product");
			resources.add(product);
			HashMap supplier = new HashMap<String, String>();
			supplier.put("name", "Supplier");
			supplier.put("href", "/supplier");
			resources.add(supplier);

			// Format Data
			result.put("resources", resources);
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
			response.type("application/json");
			return responseContent;
		});

		get("suppliedproduct", (request, response) -> {
			// Get data
			Collection resultSet = eh.getAllSuppliedproduct();
			
			// Make Response
			resultSet = ResponseFactory.makeAllSuppliedproduct(resultSet);
			
			// Format data
			Gson gson = new Gson();
			String responseContent = gson.toJson(resultSet);
			
			// Make Response
			response.type("application/json");
			return responseContent;
		});
		
		

		post("suppliedproduct", (request, response) -> {
			HashMap result = new HashMap<String, ArrayList>();
						
			// Resources
			ArrayList resources = new ArrayList<HashMap>();
			HashMap mecrsuppliedproduct = new HashMap<String, String>();
			mecrsuppliedproduct.put("method", "POST");
			mecrsuppliedproduct.put("href", "/suppliedproduct/mecrsuppliedproduct");
			resources.add(mecrsuppliedproduct);

			// Format Data
			result.put("create_events", resources);
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
			
			response.status(300);
			response.type("application/json");
			return responseContent;
			
		
		});
			
		post("suppliedproduct/mecrsuppliedproduct", (request, response) -> {
			// Result
			HashMap result = null;
			
			// Get data from request in HashMap
			Gson gson1 = new Gson();
			Map<String, String> parmap = new HashMap<String, String>();
			parmap = (Map<String, String>) gson1.fromJson(request.body(), parmap.getClass());
			
			// Call EventHandler with request parameters
			String suppliedproductId = "";
			try {
				suppliedproductId = eh.mecrsuppliedproduct(
					parmap.get("productId"),
					parmap.get("supplierId"),
					parmap.get("price")
				);
				response.status(201);
				result = ResponseFactory.makeSuccess("mecrsuppliedproduct");
				HashMap suppliedproductMap = new HashMap<String, String>();
				suppliedproductMap.put("href", "/suppliedproduct/"+suppliedproductId);
				result.put("suppliedproduct", suppliedproductMap);
			} catch(Exception exc) {
				response.status(403);
				result = ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Format Data
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
		
			response.type("application/json");
			return responseContent;
		});		

		get("suppliedproduct/:id", (request, response) -> {
			// Get Suppliedproduct ID
			String suppliedproductId = request.params("id");
			
			// Search Suppliedproduct
			Suppliedproduct suppliedproduct = null; 
			try {
				suppliedproduct = eh.searchSuppliedproductById(suppliedproductId);
			} catch(Exception exc) {
				return ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Make Response
			HashMap resultSet = ResponseFactory.makeSuppliedproduct(suppliedproduct);
			
			// Format data
			Gson gson = new Gson();
			String responseContent = gson.toJson(resultSet);
			
			// Make Response
			response.type("application/json");
			return responseContent;
		});
		
		
		delete("suppliedproduct/:id/meendsuppliedproduct", (request, response) -> {
			// Result
			HashMap result = null;
			
			// Get Suppliedproduct ID
			String suppliedproductId = request.params("id");
			
			// Execute event
			try {
				eh.meendsuppliedproduct(suppliedproductId);
				result = ResponseFactory.makeSuccess("meendsuppliedproduct");
			} catch(Exception exc) {
				response.status(403);
				result = ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Format Data
			Gson gson = new Gson();
			String responseContent = gson.toJson(result);
			
			response.type("application/json");
			return responseContent;
		});

		delete("suppliedproduct/:id", (request, response) -> {
			HashMap result = new HashMap<String, ArrayList>();
		
			// Get Suppliedproduct ID
			String suppliedproductId = request.params("id");
			
			// Get data from request in HashMap
			Gson gson1 = new Gson();
			Map<String, String> parmap = new HashMap<String, String>();
			parmap = (Map<String, String>) gson1.fromJson(request.body(), parmap.getClass());
						
			ArrayList resources = new ArrayList<HashMap>();
			HashMap meendsuppliedproduct = new HashMap<String, String>();
			meendsuppliedproduct.put("method", "DELETE");
			meendsuppliedproduct.put("href", "/suppliedproduct/"+suppliedproductId+"/meendsuppliedproduct");
			resources.add(meendsuppliedproduct);

			// Format Data
			result.put("end_events", resources);
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
			
			response.status(300);
			response.type("application/json");
			return responseContent;
		});

   
	
		
		get("purchaseorder", (request, response) -> {
			// Get data
			Collection resultSet = eh.getAllPurchaseorder();
			
			// Make Response
			resultSet = ResponseFactory.makeAllPurchaseorder(resultSet);
			
			// Format data
			Gson gson = new Gson();
			String responseContent = gson.toJson(resultSet);
			
			// Make Response
			response.type("application/json");
			return responseContent;
		});
		
		

		post("purchaseorder", (request, response) -> {
			HashMap result = new HashMap<String, ArrayList>();
						
			// Resources
			ArrayList resources = new ArrayList<HashMap>();
			HashMap mecrpurchaseorder = new HashMap<String, String>();
			mecrpurchaseorder.put("method", "POST");
			mecrpurchaseorder.put("href", "/purchaseorder/mecrpurchaseorder");
			resources.add(mecrpurchaseorder);

			// Format Data
			result.put("create_events", resources);
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
			
			response.status(300);
			response.type("application/json");
			return responseContent;
			
		
		});
			
		post("purchaseorder/mecrpurchaseorder", (request, response) -> {
			// Result
			HashMap result = null;
			
			// Get data from request in HashMap
			Gson gson1 = new Gson();
			Map<String, String> parmap = new HashMap<String, String>();
			parmap = (Map<String, String>) gson1.fromJson(request.body(), parmap.getClass());
			
			// Call EventHandler with request parameters
			String purchaseorderId = "";
			try {
				purchaseorderId = eh.mecrpurchaseorder(
					parmap.get("supplierId"),
					parmap.get("name")
				);
				response.status(201);
				result = ResponseFactory.makeSuccess("mecrpurchaseorder");
				HashMap purchaseorderMap = new HashMap<String, String>();
				purchaseorderMap.put("href", "/purchaseorder/"+purchaseorderId);
				result.put("purchaseorder", purchaseorderMap);
			} catch(Exception exc) {
				response.status(403);
				result = ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Format Data
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
		
			response.type("application/json");
			return responseContent;
		});		

		get("purchaseorder/:id", (request, response) -> {
			// Get Purchaseorder ID
			String purchaseorderId = request.params("id");
			
			// Search Purchaseorder
			Purchaseorder purchaseorder = null; 
			try {
				purchaseorder = eh.searchPurchaseorderById(purchaseorderId);
			} catch(Exception exc) {
				return ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Make Response
			HashMap resultSet = ResponseFactory.makePurchaseorder(purchaseorder);
			
			// Format data
			Gson gson = new Gson();
			String responseContent = gson.toJson(resultSet);
			
			// Make Response
			response.type("application/json");
			return responseContent;
		});
		
		
		delete("purchaseorder/:id/meendpurchaseorder", (request, response) -> {
			// Result
			HashMap result = null;
			
			// Get Purchaseorder ID
			String purchaseorderId = request.params("id");
			
			// Execute event
			try {
				eh.meendpurchaseorder(purchaseorderId);
				result = ResponseFactory.makeSuccess("meendpurchaseorder");
			} catch(Exception exc) {
				response.status(403);
				result = ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Format Data
			Gson gson = new Gson();
			String responseContent = gson.toJson(result);
			
			response.type("application/json");
			return responseContent;
		});

		delete("purchaseorder/:id", (request, response) -> {
			HashMap result = new HashMap<String, ArrayList>();
		
			// Get Purchaseorder ID
			String purchaseorderId = request.params("id");
			
			// Get data from request in HashMap
			Gson gson1 = new Gson();
			Map<String, String> parmap = new HashMap<String, String>();
			parmap = (Map<String, String>) gson1.fromJson(request.body(), parmap.getClass());
						
			ArrayList resources = new ArrayList<HashMap>();
			HashMap meendpurchaseorder = new HashMap<String, String>();
			meendpurchaseorder.put("method", "DELETE");
			meendpurchaseorder.put("href", "/purchaseorder/"+purchaseorderId+"/meendpurchaseorder");
			resources.add(meendpurchaseorder);

			// Format Data
			result.put("end_events", resources);
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
			
			response.status(300);
			response.type("application/json");
			return responseContent;
		});

   
	
		
		get("poline", (request, response) -> {
			// Get data
			Collection resultSet = eh.getAllPoline();
			
			// Make Response
			resultSet = ResponseFactory.makeAllPoline(resultSet);
			
			// Format data
			Gson gson = new Gson();
			String responseContent = gson.toJson(resultSet);
			
			// Make Response
			response.type("application/json");
			return responseContent;
		});
		
		

		post("poline", (request, response) -> {
			HashMap result = new HashMap<String, ArrayList>();
						
			// Resources
			ArrayList resources = new ArrayList<HashMap>();
			HashMap mecrpoline = new HashMap<String, String>();
			mecrpoline.put("method", "POST");
			mecrpoline.put("href", "/poline/mecrpoline");
			resources.add(mecrpoline);

			// Format Data
			result.put("create_events", resources);
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
			
			response.status(300);
			response.type("application/json");
			return responseContent;
			
		
		});
			
		post("poline/mecrpoline", (request, response) -> {
			// Result
			HashMap result = null;
			
			// Get data from request in HashMap
			Gson gson1 = new Gson();
			Map<String, String> parmap = new HashMap<String, String>();
			parmap = (Map<String, String>) gson1.fromJson(request.body(), parmap.getClass());
			
			// Call EventHandler with request parameters
			String polineId = "";
			try {
				polineId = eh.mecrpoline(
					parmap.get("suppliedproductId"),
					parmap.get("purchaseorderId"),
					parmap.get("name")
				);
				response.status(201);
				result = ResponseFactory.makeSuccess("mecrpoline");
				HashMap polineMap = new HashMap<String, String>();
				polineMap.put("href", "/poline/"+polineId);
				result.put("poline", polineMap);
			} catch(Exception exc) {
				response.status(403);
				result = ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Format Data
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
		
			response.type("application/json");
			return responseContent;
		});		

		get("poline/:id", (request, response) -> {
			// Get Poline ID
			String polineId = request.params("id");
			
			// Search Poline
			Poline poline = null; 
			try {
				poline = eh.searchPolineById(polineId);
			} catch(Exception exc) {
				return ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Make Response
			HashMap resultSet = ResponseFactory.makePoline(poline);
			
			// Format data
			Gson gson = new Gson();
			String responseContent = gson.toJson(resultSet);
			
			// Make Response
			response.type("application/json");
			return responseContent;
		});
		
		
		delete("poline/:id/meendpoline", (request, response) -> {
			// Result
			HashMap result = null;
			
			// Get Poline ID
			String polineId = request.params("id");
			
			// Execute event
			try {
				eh.meendpoline(polineId);
				result = ResponseFactory.makeSuccess("meendpoline");
			} catch(Exception exc) {
				response.status(403);
				result = ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Format Data
			Gson gson = new Gson();
			String responseContent = gson.toJson(result);
			
			response.type("application/json");
			return responseContent;
		});

		delete("poline/:id", (request, response) -> {
			HashMap result = new HashMap<String, ArrayList>();
		
			// Get Poline ID
			String polineId = request.params("id");
			
			// Get data from request in HashMap
			Gson gson1 = new Gson();
			Map<String, String> parmap = new HashMap<String, String>();
			parmap = (Map<String, String>) gson1.fromJson(request.body(), parmap.getClass());
						
			ArrayList resources = new ArrayList<HashMap>();
			HashMap meendpoline = new HashMap<String, String>();
			meendpoline.put("method", "DELETE");
			meendpoline.put("href", "/poline/"+polineId+"/meendpoline");
			resources.add(meendpoline);

			// Format Data
			result.put("end_events", resources);
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
			
			response.status(300);
			response.type("application/json");
			return responseContent;
		});

   
	
		
		get("product", (request, response) -> {
			// Get data
			Collection resultSet = eh.getAllProduct();
			
			// Make Response
			resultSet = ResponseFactory.makeAllProduct(resultSet);
			
			// Format data
			Gson gson = new Gson();
			String responseContent = gson.toJson(resultSet);
			
			// Make Response
			response.type("application/json");
			return responseContent;
		});
		
		

		post("product", (request, response) -> {
			HashMap result = new HashMap<String, ArrayList>();
						
			// Resources
			ArrayList resources = new ArrayList<HashMap>();
			HashMap mecrproduct = new HashMap<String, String>();
			mecrproduct.put("method", "POST");
			mecrproduct.put("href", "/product/mecrproduct");
			resources.add(mecrproduct);

			// Format Data
			result.put("create_events", resources);
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
			
			response.status(300);
			response.type("application/json");
			return responseContent;
			
		
		});
			
		post("product/mecrproduct", (request, response) -> {
			// Result
			HashMap result = null;
			
			// Get data from request in HashMap
			Gson gson1 = new Gson();
			Map<String, String> parmap = new HashMap<String, String>();
			parmap = (Map<String, String>) gson1.fromJson(request.body(), parmap.getClass());
			
			// Call EventHandler with request parameters
			String productId = "";
			try {
				productId = eh.mecrproduct(
					parmap.get("name")
				);
				response.status(201);
				result = ResponseFactory.makeSuccess("mecrproduct");
				HashMap productMap = new HashMap<String, String>();
				productMap.put("href", "/product/"+productId);
				result.put("product", productMap);
			} catch(Exception exc) {
				response.status(403);
				result = ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Format Data
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
		
			response.type("application/json");
			return responseContent;
		});		

		get("product/:id", (request, response) -> {
			// Get Product ID
			String productId = request.params("id");
			
			// Search Product
			Product product = null; 
			try {
				product = eh.searchProductById(productId);
			} catch(Exception exc) {
				return ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Make Response
			HashMap resultSet = ResponseFactory.makeProduct(product);
			
			// Format data
			Gson gson = new Gson();
			String responseContent = gson.toJson(resultSet);
			
			// Make Response
			response.type("application/json");
			return responseContent;
		});
		
		
		delete("product/:id/meendproduct", (request, response) -> {
			// Result
			HashMap result = null;
			
			// Get Product ID
			String productId = request.params("id");
			
			// Execute event
			try {
				eh.meendproduct(productId);
				result = ResponseFactory.makeSuccess("meendproduct");
			} catch(Exception exc) {
				response.status(403);
				result = ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Format Data
			Gson gson = new Gson();
			String responseContent = gson.toJson(result);
			
			response.type("application/json");
			return responseContent;
		});

		delete("product/:id", (request, response) -> {
			HashMap result = new HashMap<String, ArrayList>();
		
			// Get Product ID
			String productId = request.params("id");
			
			// Get data from request in HashMap
			Gson gson1 = new Gson();
			Map<String, String> parmap = new HashMap<String, String>();
			parmap = (Map<String, String>) gson1.fromJson(request.body(), parmap.getClass());
						
			ArrayList resources = new ArrayList<HashMap>();
			HashMap meendproduct = new HashMap<String, String>();
			meendproduct.put("method", "DELETE");
			meendproduct.put("href", "/product/"+productId+"/meendproduct");
			resources.add(meendproduct);

			// Format Data
			result.put("end_events", resources);
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
			
			response.status(300);
			response.type("application/json");
			return responseContent;
		});

   
	
		
		get("supplier", (request, response) -> {
			// Get data
			Collection resultSet = eh.getAllSupplier();
			
			// Make Response
			resultSet = ResponseFactory.makeAllSupplier(resultSet);
			
			// Format data
			Gson gson = new Gson();
			String responseContent = gson.toJson(resultSet);
			
			// Make Response
			response.type("application/json");
			return responseContent;
		});
		
		

		post("supplier", (request, response) -> {
			HashMap result = new HashMap<String, ArrayList>();
						
			// Resources
			ArrayList resources = new ArrayList<HashMap>();
			HashMap mecrsupplier = new HashMap<String, String>();
			mecrsupplier.put("method", "POST");
			mecrsupplier.put("href", "/supplier/mecrsupplier");
			resources.add(mecrsupplier);

			// Format Data
			result.put("create_events", resources);
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
			
			response.status(300);
			response.type("application/json");
			return responseContent;
			
		
		});
			
		post("supplier/mecrsupplier", (request, response) -> {
			// Result
			HashMap result = null;
			
			// Get data from request in HashMap
			Gson gson1 = new Gson();
			Map<String, String> parmap = new HashMap<String, String>();
			parmap = (Map<String, String>) gson1.fromJson(request.body(), parmap.getClass());
			
			// Call EventHandler with request parameters
			String supplierId = "";
			try {
				supplierId = eh.mecrsupplier(
					parmap.get("name")
				);
				response.status(201);
				result = ResponseFactory.makeSuccess("mecrsupplier");
				HashMap supplierMap = new HashMap<String, String>();
				supplierMap.put("href", "/supplier/"+supplierId);
				result.put("supplier", supplierMap);
			} catch(Exception exc) {
				response.status(403);
				result = ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Format Data
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
		
			response.type("application/json");
			return responseContent;
		});		

		get("supplier/:id", (request, response) -> {
			// Get Supplier ID
			String supplierId = request.params("id");
			
			// Search Supplier
			Supplier supplier = null; 
			try {
				supplier = eh.searchSupplierById(supplierId);
			} catch(Exception exc) {
				return ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Make Response
			HashMap resultSet = ResponseFactory.makeSupplier(supplier);
			
			// Format data
			Gson gson = new Gson();
			String responseContent = gson.toJson(resultSet);
			
			// Make Response
			response.type("application/json");
			return responseContent;
		});
		
		
		delete("supplier/:id/meendsupplier", (request, response) -> {
			// Result
			HashMap result = null;
			
			// Get Supplier ID
			String supplierId = request.params("id");
			
			// Execute event
			try {
				eh.meendsupplier(supplierId);
				result = ResponseFactory.makeSuccess("meendsupplier");
			} catch(Exception exc) {
				response.status(403);
				result = ResponseFactory.makeFail(exc.getMessage());
			}
			
			// Format Data
			Gson gson = new Gson();
			String responseContent = gson.toJson(result);
			
			response.type("application/json");
			return responseContent;
		});

		delete("supplier/:id", (request, response) -> {
			HashMap result = new HashMap<String, ArrayList>();
		
			// Get Supplier ID
			String supplierId = request.params("id");
			
			// Get data from request in HashMap
			Gson gson1 = new Gson();
			Map<String, String> parmap = new HashMap<String, String>();
			parmap = (Map<String, String>) gson1.fromJson(request.body(), parmap.getClass());
						
			ArrayList resources = new ArrayList<HashMap>();
			HashMap meendsupplier = new HashMap<String, String>();
			meendsupplier.put("method", "DELETE");
			meendsupplier.put("href", "/supplier/"+supplierId+"/meendsupplier");
			resources.add(meendsupplier);

			// Format Data
			result.put("end_events", resources);
			Gson gson2 = new Gson();
			String responseContent = gson2.toJson(result);
			
			response.status(300);
			response.type("application/json");
			return responseContent;
		});

   
	
		
	
	}
}