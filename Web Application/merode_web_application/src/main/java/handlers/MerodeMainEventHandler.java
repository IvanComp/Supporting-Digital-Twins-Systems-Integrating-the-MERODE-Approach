/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members - adapted by Nick Scheynen
 */
package handlers;

import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import dao.MerodeException;
import dao.MerodeLogger;

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

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class MerodeMainEventHandler {

	// --------------- Merode Logger -------------------------

	public java.lang.String flushLog() {
		return MerodeLogger.flush();
	}

	// ---------------- business methods ----------------------

	public String mecrsuppliedproduct(
		java.lang.String productId, 
		java.lang.String supplierId, 
		java.lang.String Price)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrsuppliedproduct");
		Session session = null;
		crResult handled = new crResult(true, "");
		try {
			session = getSession();
			handled = handleMecrsuppliedproduct(session, 
				productId,  
				supplierId,  
			     Price
			    );
			    
			noMultiplePropagationDetected = handled.getNoMultiplePropagationDetected();
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrsuppliedproduct: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrsuppliedproduct: "
							+ he.getMessage());
				}
			}
		}
		return handled.getID();
	}


	public void meendsuppliedproduct(java.lang.String suppliedproductId) throws Exception {
		MerodeLogger.logln("--> Executing event meendsuppliedproduct");
		Session session = null;
		try {
			session = getSession();
			handleMeendsuppliedproduct(session, suppliedproductId);
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meendsuppliedproduct: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meendsuppliedproduct: "
							+ he.getMessage());
				}
			}
		}
	}

	

	public String mecrpurchaseorder(
		java.lang.String supplierId, 
		java.lang.String Name)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrpurchaseorder");
		Session session = null;
		crResult handled = new crResult(true, "");
		try {
			session = getSession();
			handled = handleMecrpurchaseorder(session, 
				supplierId,  
			     Name
			    );
			    
			noMultiplePropagationDetected = handled.getNoMultiplePropagationDetected();
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrpurchaseorder: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrpurchaseorder: "
							+ he.getMessage());
				}
			}
		}
		return handled.getID();
	}


	public void meendpurchaseorder(java.lang.String purchaseorderId) throws Exception {
		MerodeLogger.logln("--> Executing event meendpurchaseorder");
		Session session = null;
		try {
			session = getSession();
			handleMeendpurchaseorder(session, purchaseorderId);
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meendpurchaseorder: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meendpurchaseorder: "
							+ he.getMessage());
				}
			}
		}
	}

	

	public String mecrpoline(
		java.lang.String suppliedproductId, 
		java.lang.String purchaseorderId, 
		java.lang.String Name)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrpoline");
		Session session = null;
		crResult handled = new crResult(true, "");
		try {
			session = getSession();
			handled = handleMecrpoline(session, 
				suppliedproductId,  
				purchaseorderId,  
			     Name
			    );
			    
			noMultiplePropagationDetected = handled.getNoMultiplePropagationDetected();
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrpoline: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrpoline: "
							+ he.getMessage());
				}
			}
		}
		return handled.getID();
	}


	public void meendpoline(java.lang.String polineId) throws Exception {
		MerodeLogger.logln("--> Executing event meendpoline");
		Session session = null;
		try {
			session = getSession();
			handleMeendpoline(session, polineId);
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meendpoline: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meendpoline: "
							+ he.getMessage());
				}
			}
		}
	}

	

	public String mecrproduct(
		java.lang.String Name)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrproduct");
		Session session = null;
		crResult handled = new crResult(true, "");
		try {
			session = getSession();
			handled = handleMecrproduct(session, 
			     Name
			    );
			    
			noMultiplePropagationDetected = handled.getNoMultiplePropagationDetected();
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrproduct: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrproduct: "
							+ he.getMessage());
				}
			}
		}
		return handled.getID();
	}


	public void meendproduct(java.lang.String productId) throws Exception {
		MerodeLogger.logln("--> Executing event meendproduct");
		Session session = null;
		try {
			session = getSession();
			handleMeendproduct(session, productId);
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meendproduct: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meendproduct: "
							+ he.getMessage());
				}
			}
		}
	}

	

	public String mecrsupplier(
		java.lang.String Name)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrsupplier");
		Session session = null;
		crResult handled = new crResult(true, "");
		try {
			session = getSession();
			handled = handleMecrsupplier(session, 
			     Name
			    );
			    
			noMultiplePropagationDetected = handled.getNoMultiplePropagationDetected();
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrsupplier: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrsupplier: "
							+ he.getMessage());
				}
			}
		}
		return handled.getID();
	}


	public void meendsupplier(java.lang.String supplierId) throws Exception {
		MerodeLogger.logln("--> Executing event meendsupplier");
		Session session = null;
		try {
			session = getSession();
			handleMeendsupplier(session, supplierId);
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meendsupplier: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meendsupplier: "
							+ he.getMessage());
				}
			}
		}
	}

	

	// ---------------- search methods -------------------------

    //search for all instances
	public java.util.Collection getAllSuppliedproduct() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = SuppliedproductFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllSuppliedproduct : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllSuppliedproduct : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchSuppliedproductByPrice(java.lang.String Price)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = SuppliedproductFactory.findByPrice(session, Price);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchSuppliedproductByPrice: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchSuppliedproductByPrice: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Suppliedproduct searchSuppliedproductById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Suppliedproduct result = SuppliedproductFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchSuppliedproductById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchSuppliedproductById: " + he.getMessage());
				}
			}
		}

	}

    //search for all instances
	public java.util.Collection getAllPurchaseorder() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = PurchaseorderFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllPurchaseorder : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllPurchaseorder : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchPurchaseorderByName(java.lang.String Name)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = PurchaseorderFactory.findByName(session, Name);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchPurchaseorderByName: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchPurchaseorderByName: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Purchaseorder searchPurchaseorderById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Purchaseorder result = PurchaseorderFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchPurchaseorderById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchPurchaseorderById: " + he.getMessage());
				}
			}
		}

	}

    //search for all instances
	public java.util.Collection getAllPoline() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = PolineFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllPoline : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllPoline : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchPolineByName(java.lang.String Name)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = PolineFactory.findByName(session, Name);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchPolineByName: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchPolineByName: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Poline searchPolineById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Poline result = PolineFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchPolineById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchPolineById: " + he.getMessage());
				}
			}
		}

	}

    //search for all instances
	public java.util.Collection getAllProduct() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = ProductFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllProduct : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllProduct : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchProductByName(java.lang.String Name)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = ProductFactory.findByName(session, Name);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchProductByName: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchProductByName: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Product searchProductById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Product result = ProductFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchProductById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchProductById: " + he.getMessage());
				}
			}
		}

	}

    //search for all instances
	public java.util.Collection getAllSupplier() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = SupplierFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllSupplier : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllSupplier : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchSupplierByName(java.lang.String Name)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = SupplierFactory.findByName(session, Name);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchSupplierByName: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchSupplierByName: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Supplier searchSupplierById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Supplier result = SupplierFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchSupplierById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchSupplierById: " + he.getMessage());
				}
			}
		}

	}
	
	// ---------------- handler methods ------------------------



	protected crResult handleMecrsuppliedproduct(org.hibernate.Session sess,
		java.lang.String productId, 
		java.lang.String supplierId, 
		java.lang.String Price)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		
		Product product = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 product = ProductFactory.findByPrimaryKey(sess, productId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}

		Supplier supplier = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 supplier = SupplierFactory.findByPrimaryKey(sess, supplierId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}


		// create object
		MerodeLogger.logln("Creating Suppliedproduct object");
		Suppliedproduct suppliedproduct = SuppliedproductFactory.create(sess);

		// save object in database
		sess.save(suppliedproduct);
		// check user and state preconditions
		suppliedproduct.check_mecrsuppliedproduct();
		suppliedproduct.getState().check_mecrsuppliedproduct();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProductImpl".equals(product.getClass().getName())) {
			product.getState().check_mecrsuppliedproduct();	
			product.check_mecrsuppliedproduct();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.SupplierImpl".equals(supplier.getClass().getName())) {
			supplier.getState().check_mecrsuppliedproduct();	
			supplier.check_mecrsuppliedproduct();		
		} 
		else {
		}
		
		// register connections


		product.attachSuppliedproduct(suppliedproduct);
		suppliedproduct.setProduct(product);
		supplier.attachSuppliedproduct(suppliedproduct);
		suppliedproduct.setSupplier(supplier);
		// execute creating method
		suppliedproduct.mecrsuppliedproduct( Price);
		suppliedproduct.getState().mecrsuppliedproduct(sess, suppliedproduct);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProductImpl".equals(product.getClass().getName())) {
			product.getState().mecrsuppliedproduct(sess, product);	
			product.mecrsuppliedproduct();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.SupplierImpl".equals(supplier.getClass().getName())) {
			supplier.getState().mecrsuppliedproduct(sess, supplier);	
			supplier.mecrsuppliedproduct();
		} 
		else {
		}
		
		crResult result = new crResult(noMultiplePropagationDetected, suppliedproduct.getId());
		return result;
	}

	protected void handleMeendsuppliedproduct(org.hibernate.Session sess,
			java.lang.String suppliedproductId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Suppliedproduct suppliedproduct = SuppliedproductFactory.findByPrimaryKey(sess, suppliedproductId);
		Product product = suppliedproduct.getProduct();
		Supplier supplier = suppliedproduct.getSupplier();

		// check user and state preconditions
		suppliedproduct.check_meendsuppliedproduct();
		suppliedproduct.getState().check_meendsuppliedproduct();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProductImpl".equals(product.getClass().getName())) {
			product.getState().check_meendsuppliedproduct();	
			product.check_meendsuppliedproduct();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.SupplierImpl".equals(supplier.getClass().getName())) {
			supplier.getState().check_meendsuppliedproduct();	
			supplier.check_meendsuppliedproduct();		
		} 
		else {
		}

		// execute end object
		suppliedproduct.meendsuppliedproduct();
		suppliedproduct.getState().meendsuppliedproduct(sess, suppliedproduct);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProductImpl".equals(product.getClass().getName())) {
			product.getState().meendsuppliedproduct(sess, product);	
			product.meendsuppliedproduct();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.SupplierImpl".equals(supplier.getClass().getName())) {
			supplier.getState().meendsuppliedproduct(sess, supplier);	
			supplier.meendsuppliedproduct();
		} 
		else {
		}

	}










	protected crResult handleMecrpurchaseorder(org.hibernate.Session sess,
		java.lang.String supplierId, 
		java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		
		Supplier supplier = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 supplier = SupplierFactory.findByPrimaryKey(sess, supplierId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}


		// create object
		MerodeLogger.logln("Creating Purchaseorder object");
		Purchaseorder purchaseorder = PurchaseorderFactory.create(sess);

		// save object in database
		sess.save(purchaseorder);
		// check user and state preconditions
		purchaseorder.check_mecrpurchaseorder();
		purchaseorder.getState().check_mecrpurchaseorder();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.SupplierImpl".equals(supplier.getClass().getName())) {
			supplier.getState().check_mecrpurchaseorder();	
			supplier.check_mecrpurchaseorder();		
		} 
		else {
		}
		
		// register connections


		supplier.attachPurchaseorder(purchaseorder);
		purchaseorder.setSupplier(supplier);
		// execute creating method
		purchaseorder.mecrpurchaseorder( Name);
		purchaseorder.getState().mecrpurchaseorder(sess, purchaseorder);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.SupplierImpl".equals(supplier.getClass().getName())) {
			supplier.getState().mecrpurchaseorder(sess, supplier);	
			supplier.mecrpurchaseorder();
		} 
		else {
		}
		
		crResult result = new crResult(noMultiplePropagationDetected, purchaseorder.getId());
		return result;
	}

	protected void handleMeendpurchaseorder(org.hibernate.Session sess,
			java.lang.String purchaseorderId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Purchaseorder purchaseorder = PurchaseorderFactory.findByPrimaryKey(sess, purchaseorderId);
		Supplier supplier = purchaseorder.getSupplier();

		// check user and state preconditions
		purchaseorder.check_meendpurchaseorder();
		purchaseorder.getState().check_meendpurchaseorder();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.SupplierImpl".equals(supplier.getClass().getName())) {
			supplier.getState().check_meendpurchaseorder();	
			supplier.check_meendpurchaseorder();		
		} 
		else {
		}

		// execute end object
		purchaseorder.meendpurchaseorder();
		purchaseorder.getState().meendpurchaseorder(sess, purchaseorder);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.SupplierImpl".equals(supplier.getClass().getName())) {
			supplier.getState().meendpurchaseorder(sess, supplier);	
			supplier.meendpurchaseorder();
		} 
		else {
		}

	}










	protected crResult handleMecrpoline(org.hibernate.Session sess,
		java.lang.String suppliedproductId, 
		java.lang.String purchaseorderId, 
		java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		
		Suppliedproduct suppliedproduct = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 suppliedproduct = SuppliedproductFactory.findByPrimaryKey(sess, suppliedproductId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}

		Purchaseorder purchaseorder = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 purchaseorder = PurchaseorderFactory.findByPrimaryKey(sess, purchaseorderId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}


		// create object
		MerodeLogger.logln("Creating Poline object");
		Poline poline = PolineFactory.create(sess);

		// save object in database
		sess.save(poline);
		// check user and state preconditions
		poline.check_mecrpoline();
		poline.getState().check_mecrpoline();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.SuppliedproductImpl".equals(suppliedproduct.getClass().getName())) {
			suppliedproduct.getState().check_mecrpoline();	
			suppliedproduct.check_mecrpoline();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PurchaseorderImpl".equals(purchaseorder.getClass().getName())) {
			purchaseorder.getState().check_mecrpoline();	
			purchaseorder.check_mecrpoline();		
		} 
		else {
		}
		purchaseorder.getSupplier().getState().check_mecrpoline();
		purchaseorder.getSupplier().check_mecrpoline();
		suppliedproduct.getProduct().getState().check_mecrpoline();
		suppliedproduct.getProduct().check_mecrpoline();
		suppliedproduct.getSupplier().getState().check_mecrpoline();
		suppliedproduct.getSupplier().check_mecrpoline();
		
		// register connections


		suppliedproduct.attachPoline(poline);
		poline.setSuppliedproduct(suppliedproduct);
		purchaseorder.attachPoline(poline);
		poline.setPurchaseorder(purchaseorder);
		// execute creating method
		poline.mecrpoline( Name);
		poline.getState().mecrpoline(sess, poline);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.SuppliedproductImpl".equals(suppliedproduct.getClass().getName())) {
			suppliedproduct.getState().mecrpoline(sess, suppliedproduct);	
			suppliedproduct.mecrpoline();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PurchaseorderImpl".equals(purchaseorder.getClass().getName())) {
			purchaseorder.getState().mecrpoline(sess, purchaseorder);	
			purchaseorder.mecrpoline();
		} 
		else {
		}
		purchaseorder.getSupplier().getState().mecrpoline(sess, purchaseorder.getSupplier());
		purchaseorder.getSupplier().mecrpoline();
		suppliedproduct.getProduct().getState().mecrpoline(sess, suppliedproduct.getProduct());
		suppliedproduct.getProduct().mecrpoline();
		suppliedproduct.getSupplier().getState().mecrpoline(sess, suppliedproduct.getSupplier());
		suppliedproduct.getSupplier().mecrpoline();
		
		crResult result = new crResult(noMultiplePropagationDetected, poline.getId());
		return result;
	}

	protected void handleMeendpoline(org.hibernate.Session sess,
			java.lang.String polineId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Poline poline = PolineFactory.findByPrimaryKey(sess, polineId);
		Suppliedproduct suppliedproduct = poline.getSuppliedproduct();
		Purchaseorder purchaseorder = poline.getPurchaseorder();

		// check user and state preconditions
		poline.check_meendpoline();
		poline.getState().check_meendpoline();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.SuppliedproductImpl".equals(suppliedproduct.getClass().getName())) {
			suppliedproduct.getState().check_meendpoline();	
			suppliedproduct.check_meendpoline();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PurchaseorderImpl".equals(purchaseorder.getClass().getName())) {
			purchaseorder.getState().check_meendpoline();	
			purchaseorder.check_meendpoline();		
		} 
		else {
		}
		purchaseorder.getSupplier().getState().check_meendpoline();
		purchaseorder.getSupplier().check_meendpoline();
		suppliedproduct.getProduct().getState().check_meendpoline();
		suppliedproduct.getProduct().check_meendpoline();
		suppliedproduct.getSupplier().getState().check_meendpoline();
		suppliedproduct.getSupplier().check_meendpoline();

		// execute end object
		poline.meendpoline();
		poline.getState().meendpoline(sess, poline);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.SuppliedproductImpl".equals(suppliedproduct.getClass().getName())) {
			suppliedproduct.getState().meendpoline(sess, suppliedproduct);	
			suppliedproduct.meendpoline();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PurchaseorderImpl".equals(purchaseorder.getClass().getName())) {
			purchaseorder.getState().meendpoline(sess, purchaseorder);	
			purchaseorder.meendpoline();
		} 
		else {
		}
		purchaseorder.getSupplier().getState().meendpoline(sess, purchaseorder.getSupplier());
		purchaseorder.getSupplier().meendpoline();
		suppliedproduct.getProduct().getState().meendpoline(sess, suppliedproduct.getProduct());
		suppliedproduct.getProduct().meendpoline();
		suppliedproduct.getSupplier().getState().meendpoline(sess, suppliedproduct.getSupplier());
		suppliedproduct.getSupplier().meendpoline();

	}










	protected crResult handleMecrproduct(org.hibernate.Session sess,
		java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		

		// create object
		MerodeLogger.logln("Creating Product object");
		Product product = ProductFactory.create(sess);

		// save object in database
		sess.save(product);
		// check user and state preconditions
		product.check_mecrproduct();
		product.getState().check_mecrproduct();
		
		// register connections


		// execute creating method
		product.mecrproduct( Name);
		product.getState().mecrproduct(sess, product);

		// propagation and state modifications
		
		crResult result = new crResult(noMultiplePropagationDetected, product.getId());
		return result;
	}

	protected void handleMeendproduct(org.hibernate.Session sess,
			java.lang.String productId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Product product = ProductFactory.findByPrimaryKey(sess, productId);

		// check user and state preconditions
		product.check_meendproduct();
		product.getState().check_meendproduct();

		// execute end object
		product.meendproduct();
		product.getState().meendproduct(sess, product);

		// propagation and state modifications

	}










	protected crResult handleMecrsupplier(org.hibernate.Session sess,
		java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		

		// create object
		MerodeLogger.logln("Creating Supplier object");
		Supplier supplier = SupplierFactory.create(sess);

		// save object in database
		sess.save(supplier);
		// check user and state preconditions
		supplier.check_mecrsupplier();
		supplier.getState().check_mecrsupplier();
		
		// register connections


		// execute creating method
		supplier.mecrsupplier( Name);
		supplier.getState().mecrsupplier(sess, supplier);

		// propagation and state modifications
		
		crResult result = new crResult(noMultiplePropagationDetected, supplier.getId());
		return result;
	}

	protected void handleMeendsupplier(org.hibernate.Session sess,
			java.lang.String supplierId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Supplier supplier = SupplierFactory.findByPrimaryKey(sess, supplierId);

		// check user and state preconditions
		supplier.check_meendsupplier();
		supplier.getState().check_meendsupplier();

		// execute end object
		supplier.meendsupplier();
		supplier.getState().meendsupplier(sess, supplier);

		// propagation and state modifications

	}










	// ---------------- update and delete methods ------------------
	
	
	public void deleteSuppliedproduct(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Suppliedproduct result = SuppliedproductFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deleteSuppliedproduct: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deleteSuppliedproduct: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updateSuppliedproduct(java.lang.String id, 
				java.lang.String Price) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Suppliedproduct result = SuppliedproductFactory.findByPrimaryKey(session, id);
			result.setPrice(Price);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updateSuppliedproduct: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updateSuppliedproduct: " + he.getMessage());
				}
			}
		}
	}
	
	public void deletePurchaseorder(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Purchaseorder result = PurchaseorderFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deletePurchaseorder: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deletePurchaseorder: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updatePurchaseorder(java.lang.String id, 
				java.lang.String Name) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Purchaseorder result = PurchaseorderFactory.findByPrimaryKey(session, id);
			result.setName(Name);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updatePurchaseorder: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updatePurchaseorder: " + he.getMessage());
				}
			}
		}
	}
	
	public void deletePoline(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Poline result = PolineFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deletePoline: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deletePoline: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updatePoline(java.lang.String id, 
				java.lang.String Name) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Poline result = PolineFactory.findByPrimaryKey(session, id);
			result.setName(Name);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updatePoline: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updatePoline: " + he.getMessage());
				}
			}
		}
	}
	
	public void deleteProduct(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Product result = ProductFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deleteProduct: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deleteProduct: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updateProduct(java.lang.String id, 
				java.lang.String Name) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Product result = ProductFactory.findByPrimaryKey(session, id);
			result.setName(Name);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updateProduct: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updateProduct: " + he.getMessage());
				}
			}
		}
	}
	
	public void deleteSupplier(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Supplier result = SupplierFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deleteSupplier: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deleteSupplier: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updateSupplier(java.lang.String id, 
				java.lang.String Name) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Supplier result = SupplierFactory.findByPrimaryKey(session, id);
			result.setName(Name);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updateSupplier: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updateSupplier: " + he.getMessage());
				}
			}
		}
	}
	// ------------------------------------------------------------
	
	// ---------------- Hibernate helpers -------------------------

	private SessionFactory _sessionFactory = null;

	private SessionFactory getSessionFactory() throws HibernateException {
		if (_sessionFactory == null) {
			_sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		}
		return _sessionFactory;
	}

	private Session getSession() throws HibernateException {
		return getSessionFactory().openSession();
	}

	// ------------------------------------------------------------

}

final class crResult {
	private final boolean noMultiplePropagationDetected;
    private final String id;

    public crResult(boolean nmpd, String id) {
        this.noMultiplePropagationDetected = nmpd;
        this.id = id;
    }

    public boolean getNoMultiplePropagationDetected() {
        return noMultiplePropagationDetected;
    }

    public String getID() {
        return id;
    }
}
