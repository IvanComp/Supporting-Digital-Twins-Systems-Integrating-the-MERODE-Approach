/** 
 * This class was automatically generated  
 * using a Merode XML model and Apache Velocity
 * 
 * Merode Code Generator 2.0
 * @author MERODE Team-members
 */
package handlers;

import java.util.Collection;
import java.time.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import dao.MerodeException;
import dao.MerodeLogger;

import dao.Registereddevice;
import dao.RegistereddeviceFactory;
import dao.Procedure;
import dao.ProcedureFactory;
import dao.Deviceresult;
import dao.DeviceresultFactory;
import dao.Featureofinterest;
import dao.FeatureofinterestFactory;
import dao.Platform;
import dao.PlatformFactory;
import dao.Property;
import dao.PropertyFactory;
import dao.Deviceusage;
import dao.DeviceusageFactory;
import dao.Platformdeployment;
import dao.PlatformdeploymentFactory;
import dao.Device;
import dao.DeviceFactory;

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

	public boolean mecrregistereddevice(
		java.lang.String platformId, 
		java.lang.String deviceId, 
		java.lang.String Name)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrregistereddevice");
		Session session = null;
		try {
			session = getSession();
			noMultiplePropagationDetected = handleMecrregistereddevice(session, 
				platformId,  
				deviceId,  
			     Name
			    );
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrregistereddevice: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrregistereddevice: "
							+ he.getMessage());
				}
			}
		}
		return noMultiplePropagationDetected;
	}


	public void meendregistereddevice(java.lang.String registereddeviceId) throws Exception {
		MerodeLogger.logln("--> Executing event meendregistereddevice");
		Session session = null;
		try {
			session = getSession();
			handleMeendregistereddevice(session, registereddeviceId);
			MerodeLogger.logln("Ended Registereddevice with ID " + registereddeviceId); 
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meendregistereddevice: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meendregistereddevice: "
							+ he.getMessage());
				}
			}
		}
	}

	

	public boolean mecrprocedure(
		java.lang.String deviceId, 
		java.lang.String Name)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrprocedure");
		Session session = null;
		try {
			session = getSession();
			noMultiplePropagationDetected = handleMecrprocedure(session, 
				deviceId,  
			     Name
			    );
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrprocedure: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrprocedure: "
							+ he.getMessage());
				}
			}
		}
		return noMultiplePropagationDetected;
	}


	public void meendprocedure(java.lang.String procedureId) throws Exception {
		MerodeLogger.logln("--> Executing event meendprocedure");
		Session session = null;
		try {
			session = getSession();
			handleMeendprocedure(session, procedureId);
			MerodeLogger.logln("Ended Procedure with ID " + procedureId); 
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meendprocedure: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meendprocedure: "
							+ he.getMessage());
				}
			}
		}
	}

	

	public boolean mecrdeviceresult(
		java.lang.String deviceusageId, 
		 java.lang.String Time,
		java.lang.String Value)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrdeviceresult");
		Session session = null;
		try {
			session = getSession();
			noMultiplePropagationDetected = handleMecrdeviceresult(session, 
				deviceusageId,  
			     Time, Value
			    );
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrdeviceresult: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrdeviceresult: "
							+ he.getMessage());
				}
			}
		}
		return noMultiplePropagationDetected;
	}


	public void meenddeviceresult(java.lang.String deviceresultId) throws Exception {
		MerodeLogger.logln("--> Executing event meenddeviceresult");
		Session session = null;
		try {
			session = getSession();
			handleMeenddeviceresult(session, deviceresultId);
			MerodeLogger.logln("Ended Deviceresult with ID " + deviceresultId); 
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meenddeviceresult: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meenddeviceresult: "
							+ he.getMessage());
				}
			}
		}
	}

	

	public boolean mecrfeatureofinterest(
		java.lang.String Name)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrfeatureofinterest");
		Session session = null;
		try {
			session = getSession();
			noMultiplePropagationDetected = handleMecrfeatureofinterest(session, 
			     Name
			    );
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrfeatureofinterest: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrfeatureofinterest: "
							+ he.getMessage());
				}
			}
		}
		return noMultiplePropagationDetected;
	}


	public void meendfeatureofinterest(java.lang.String featureofinterestId) throws Exception {
		MerodeLogger.logln("--> Executing event meendfeatureofinterest");
		Session session = null;
		try {
			session = getSession();
			handleMeendfeatureofinterest(session, featureofinterestId);
			MerodeLogger.logln("Ended Featureofinterest with ID " + featureofinterestId); 
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meendfeatureofinterest: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meendfeatureofinterest: "
							+ he.getMessage());
				}
			}
		}
	}

	

	public boolean mecrplatform(
		java.lang.String Name)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrplatform");
		Session session = null;
		try {
			session = getSession();
			noMultiplePropagationDetected = handleMecrplatform(session, 
			     Name
			    );
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrplatform: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrplatform: "
							+ he.getMessage());
				}
			}
		}
		return noMultiplePropagationDetected;
	}


	public void meendplatform(java.lang.String platformId) throws Exception {
		MerodeLogger.logln("--> Executing event meendplatform");
		Session session = null;
		try {
			session = getSession();
			handleMeendplatform(session, platformId);
			MerodeLogger.logln("Ended Platform with ID " + platformId); 
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meendplatform: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meendplatform: "
							+ he.getMessage());
				}
			}
		}
	}

	

	public boolean mecrproperty(
		java.lang.String featureofinterestId, 
		java.lang.String Name)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrproperty");
		Session session = null;
		try {
			session = getSession();
			noMultiplePropagationDetected = handleMecrproperty(session, 
				featureofinterestId,  
			     Name
			    );
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrproperty: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrproperty: "
							+ he.getMessage());
				}
			}
		}
		return noMultiplePropagationDetected;
	}


	public void meendproperty(java.lang.String propertyId) throws Exception {
		MerodeLogger.logln("--> Executing event meendproperty");
		Session session = null;
		try {
			session = getSession();
			handleMeendproperty(session, propertyId);
			MerodeLogger.logln("Ended Property with ID " + propertyId); 
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meendproperty: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meendproperty: "
							+ he.getMessage());
				}
			}
		}
	}

	

	public boolean mecrdeviceusage(
		java.lang.String platformdeploymentId, 
		java.lang.String registereddeviceId, 
		java.lang.String procedureId, 
		java.lang.String propertyId, 
		java.lang.String Name)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrdeviceusage");
		Session session = null;
		try {
			session = getSession();
			noMultiplePropagationDetected = handleMecrdeviceusage(session, 
				platformdeploymentId,  
				registereddeviceId,  
				procedureId,  
				propertyId,  
			     Name
			    );
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrdeviceusage: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrdeviceusage: "
							+ he.getMessage());
				}
			}
		}
		return noMultiplePropagationDetected;
	}


	public void meenddeviceusage(java.lang.String deviceusageId) throws Exception {
		MerodeLogger.logln("--> Executing event meenddeviceusage");
		Session session = null;
		try {
			session = getSession();
			handleMeenddeviceusage(session, deviceusageId);
			MerodeLogger.logln("Ended Deviceusage with ID " + deviceusageId); 
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meenddeviceusage: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meenddeviceusage: "
							+ he.getMessage());
				}
			}
		}
	}
	public void deviceundeployment(java.lang.String deviceusageId) throws Exception {
		MerodeLogger.logln("--> Executing event deviceundeployment");
		Session session = null;
		try {
			session = getSession();
			handleDeviceundeployment(session, deviceusageId);
			MerodeLogger.logln("Ended Deviceusage with ID " + deviceusageId); 
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.deviceundeployment: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.deviceundeployment: "
							+ he.getMessage());
				}
			}
		}
	}







	public void devicedeployment(
		java.lang.String deviceusageId, java.lang.String Name)

			throws Exception {
		MerodeLogger.logln("--> Executing event devicedeployment");
		Session session = null;
		try {
			session = getSession();
			handleDevicedeployment(session, deviceusageId,
			     Name );
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.devicedeployment: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.devicedeployment: "
							+ he.getMessage());
				}
			}
		}
	}

	public void mesetready(
		java.lang.String deviceusageId, java.lang.String Name)

			throws Exception {
		MerodeLogger.logln("--> Executing event mesetready");
		Session session = null;
		try {
			session = getSession();
			handleMesetready(session, deviceusageId,
			     Name );
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mesetready: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mesetready: "
							+ he.getMessage());
				}
			}
		}
	}
	

	public boolean mecrplatformdeployment(
		java.lang.String platformId, 
		java.lang.String featureofinterestId, 
		java.lang.String Name)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrplatformdeployment");
		Session session = null;
		try {
			session = getSession();
			noMultiplePropagationDetected = handleMecrplatformdeployment(session, 
				platformId,  
				featureofinterestId,  
			     Name
			    );
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrplatformdeployment: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrplatformdeployment: "
							+ he.getMessage());
				}
			}
		}
		return noMultiplePropagationDetected;
	}


	public void meendplatformdeployment(java.lang.String platformdeploymentId) throws Exception {
		MerodeLogger.logln("--> Executing event meendplatformdeployment");
		Session session = null;
		try {
			session = getSession();
			handleMeendplatformdeployment(session, platformdeploymentId);
			MerodeLogger.logln("Ended Platformdeployment with ID " + platformdeploymentId); 
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meendplatformdeployment: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meendplatformdeployment: "
							+ he.getMessage());
				}
			}
		}
	}

	

	public boolean mecrdevice(
		java.lang.String Name)
			throws Exception {
		
		boolean noMultiplePropagationDetected = true;
		MerodeLogger.logln("--> Executing event mecrdevice");
		Session session = null;
		try {
			session = getSession();
			noMultiplePropagationDetected = handleMecrdevice(session, 
			     Name
			    );
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		} catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.mecrdevice: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.mecrdevice: "
							+ he.getMessage());
				}
			}
		}
		return noMultiplePropagationDetected;
	}


	public void meenddevice(java.lang.String deviceId) throws Exception {
		MerodeLogger.logln("--> Executing event meenddevice");
		Session session = null;
		try {
			session = getSession();
			handleMeenddevice(session, deviceId);
			MerodeLogger.logln("Ended Device with ID " + deviceId); 
			//persisting to the database
			session.beginTransaction().commit();
		} catch (MerodeException e) {
			// _ctx.setRollbackOnly();
			session.connection().rollback();
			throw e;
		}

		catch (Throwable th) {
			session.connection().rollback();
			throw new Exception("MerodeMainEventHandlerBean.meenddevice: "
					+ th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("MerodeMainEventHandlerBean.meenddevice: "
							+ he.getMessage());
				}
			}
		}
	}

	

	// ---------------- search methods -------------------------

    //search for all instances
	public java.util.Collection getAllRegistereddevice() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = RegistereddeviceFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllRegistereddevice : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllRegistereddevice : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchRegistereddeviceByName(java.lang.String Name)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = RegistereddeviceFactory.findByName(session, Name);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchRegistereddeviceByName: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchRegistereddeviceByName: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Registereddevice searchRegistereddeviceById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Registereddevice result = RegistereddeviceFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchRegistereddeviceById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchRegistereddeviceById: " + he.getMessage());
				}
			}
		}

	}

    //search for all instances
	public java.util.Collection getAllProcedure() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = ProcedureFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllProcedure : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllProcedure : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchProcedureByName(java.lang.String Name)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = ProcedureFactory.findByName(session, Name);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchProcedureByName: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchProcedureByName: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Procedure searchProcedureById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Procedure result = ProcedureFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchProcedureById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchProcedureById: " + he.getMessage());
				}
			}
		}

	}

    //search for all instances
	public java.util.Collection getAllDeviceresult() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = DeviceresultFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllDeviceresult : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllDeviceresult : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchDeviceresultByTime(java.lang.String Time)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = DeviceresultFactory.findByTime(session, Time);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchDeviceresultByTime: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchDeviceresultByTime: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchDeviceresultByValue(java.lang.String Value)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = DeviceresultFactory.findByValue(session, Value);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchDeviceresultByValue: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchDeviceresultByValue: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Deviceresult searchDeviceresultById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Deviceresult result = DeviceresultFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchDeviceresultById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchDeviceresultById: " + he.getMessage());
				}
			}
		}

	}

    //search for all instances
	public java.util.Collection getAllFeatureofinterest() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = FeatureofinterestFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllFeatureofinterest : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllFeatureofinterest : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchFeatureofinterestByName(java.lang.String Name)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = FeatureofinterestFactory.findByName(session, Name);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchFeatureofinterestByName: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchFeatureofinterestByName: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Featureofinterest searchFeatureofinterestById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Featureofinterest result = FeatureofinterestFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchFeatureofinterestById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchFeatureofinterestById: " + he.getMessage());
				}
			}
		}

	}

    //search for all instances
	public java.util.Collection getAllPlatform() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = PlatformFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllPlatform : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllPlatform : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchPlatformByName(java.lang.String Name)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = PlatformFactory.findByName(session, Name);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchPlatformByName: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchPlatformByName: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Platform searchPlatformById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Platform result = PlatformFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchPlatformById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchPlatformById: " + he.getMessage());
				}
			}
		}

	}

    //search for all instances
	public java.util.Collection getAllProperty() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = PropertyFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllProperty : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllProperty : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchPropertyByName(java.lang.String Name)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = PropertyFactory.findByName(session, Name);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchPropertyByName: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchPropertyByName: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Property searchPropertyById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Property result = PropertyFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchPropertyById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchPropertyById: " + he.getMessage());
				}
			}
		}

	}

    //search for all instances
	public java.util.Collection getAllDeviceusage() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = DeviceusageFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllDeviceusage : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllDeviceusage : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchDeviceusageByName(java.lang.String Name)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = DeviceusageFactory.findByName(session, Name);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchDeviceusageByName: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchDeviceusageByName: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Deviceusage searchDeviceusageById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Deviceusage result = DeviceusageFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchDeviceusageById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchDeviceusageById: " + he.getMessage());
				}
			}
		}

	}

    //search for all instances
	public java.util.Collection getAllPlatformdeployment() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = PlatformdeploymentFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllPlatformdeployment : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllPlatformdeployment : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchPlatformdeploymentByName(java.lang.String Name)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = PlatformdeploymentFactory.findByName(session, Name);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchPlatformdeploymentByName: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchPlatformdeploymentByName: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Platformdeployment searchPlatformdeploymentById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Platformdeployment result = PlatformdeploymentFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchPlatformdeploymentById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchPlatformdeploymentById: " + he.getMessage());
				}
			}
		}

	}

    //search for all instances
	public java.util.Collection getAllDevice() throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = DeviceFactory.getAllObjects(session);
			return result;
		} catch (Throwable th) {
			throw new Exception("getAllDevice : " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("getAllDevice : " + he.getMessage());
				}
			}
		}

	}

	//searching by given attribute
	public java.util.Collection searchDeviceByName(java.lang.String Name)
			throws Exception {
		Session session = null;
		try {
			session = getSession();
			Collection result = DeviceFactory.findByName(session, Name);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchDeviceByName: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchDeviceByName: "
							+ he.getMessage());
				}
			}
		}

	}

	//searching by PK
	public Device searchDeviceById(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Device result = DeviceFactory.findByPrimaryKey(session, id);
			return result;
		} catch (Throwable th) {
			throw new Exception("searchDeviceById: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("searchDeviceById: " + he.getMessage());
				}
			}
		}

	}
	
	// ---------------- handler methods ------------------------



	protected boolean handleMecrregistereddevice(org.hibernate.Session sess,
		java.lang.String platformId, 
		java.lang.String deviceId, 
		java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		
		Platform platform = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 platform = PlatformFactory.findByPrimaryKey(sess, platformId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}

		Device device = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 device = DeviceFactory.findByPrimaryKey(sess, deviceId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}


		// create object
		MerodeLogger.logln("Creating Registereddevice object");

		Registereddevice registereddevice = RegistereddeviceFactory.create(sess);

		// save object in database
		sess.save(registereddevice);
		// check user and state preconditions
		registereddevice.check_mecrregistereddevice();
		registereddevice.getState().check_mecrregistereddevice();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformImpl".equals(platform.getClass().getName())) {
			platform.getState().check_mecrregistereddevice();	
			platform.check_mecrregistereddevice();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.DeviceImpl".equals(device.getClass().getName())) {
			device.getState().check_mecrregistereddevice();	
			device.check_mecrregistereddevice();		
		} 
		else {
		}
		
		// register connections


		platform.attachRegistereddevice(registereddevice);
		registereddevice.setPlatform(platform);
		device.attachRegistereddevice(registereddevice);
		registereddevice.setDevice(device);
		// execute creating method
		registereddevice.mecrregistereddevice( Name);
		registereddevice.getState().mecrregistereddevice(sess, registereddevice);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformImpl".equals(platform.getClass().getName())) {
			platform.getState().mecrregistereddevice(sess, platform);	
			platform.mecrregistereddevice();
			MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object " + registereddevice.getId() + " of object type 1 related to " + platform.getId() + " with attributes java.lang.String Name");
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.DeviceImpl".equals(device.getClass().getName())) {
			device.getState().mecrregistereddevice(sess, device);	
			device.mecrregistereddevice();
			MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object " + registereddevice.getId() + " of object type 1 related to " + device.getId() + " with attributes java.lang.String Name");
		} 
		else {
		}

		return noMultiplePropagationDetected;
	}

	protected void handleMeendregistereddevice(org.hibernate.Session sess,
			java.lang.String registereddeviceId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Registereddevice registereddevice = RegistereddeviceFactory.findByPrimaryKey(sess, registereddeviceId);
		Platform platform = registereddevice.getPlatform();
		Device device = registereddevice.getDevice();

		// check user and state preconditions
		registereddevice.check_meendregistereddevice();
		registereddevice.getState().check_meendregistereddevice();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformImpl".equals(platform.getClass().getName())) {
			platform.getState().check_meendregistereddevice();	
			platform.check_meendregistereddevice();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.DeviceImpl".equals(device.getClass().getName())) {
			device.getState().check_meendregistereddevice();	
			device.check_meendregistereddevice();		
		} 
		else {
		}

		// execute end object
		registereddevice.meendregistereddevice();
		registereddevice.getState().meendregistereddevice(sess, registereddevice);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformImpl".equals(platform.getClass().getName())) {
			platform.getState().meendregistereddevice(sess, platform);	
			platform.meendregistereddevice();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.DeviceImpl".equals(device.getClass().getName())) {
			device.getState().meendregistereddevice(sess, device);	
			device.meendregistereddevice();
		} 
		else {
		}

	}










	protected boolean handleMecrprocedure(org.hibernate.Session sess,
		java.lang.String deviceId, 
		java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		
		Device device = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 device = DeviceFactory.findByPrimaryKey(sess, deviceId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}


		// create object
		MerodeLogger.logln("Creating Procedure object");

		Procedure procedure = ProcedureFactory.create(sess);

		// save object in database
		sess.save(procedure);
		// check user and state preconditions
		procedure.check_mecrprocedure();
		procedure.getState().check_mecrprocedure();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.DeviceImpl".equals(device.getClass().getName())) {
			device.getState().check_mecrprocedure();	
			device.check_mecrprocedure();		
		} 
		else {
		}
		
		// register connections


		device.attachProcedure(procedure);
		procedure.setDevice(device);
		// execute creating method
		procedure.mecrprocedure( Name);
		procedure.getState().mecrprocedure(sess, procedure);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.DeviceImpl".equals(device.getClass().getName())) {
			device.getState().mecrprocedure(sess, device);	
			device.mecrprocedure();
			MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object " + procedure.getId() + " of object type 49 related to " + device.getId() + " with attributes java.lang.String Name");
		} 
		else {
		}

		return noMultiplePropagationDetected;
	}

	protected void handleMeendprocedure(org.hibernate.Session sess,
			java.lang.String procedureId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Procedure procedure = ProcedureFactory.findByPrimaryKey(sess, procedureId);
		Device device = procedure.getDevice();

		// check user and state preconditions
		procedure.check_meendprocedure();
		procedure.getState().check_meendprocedure();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.DeviceImpl".equals(device.getClass().getName())) {
			device.getState().check_meendprocedure();	
			device.check_meendprocedure();		
		} 
		else {
		}

		// execute end object
		procedure.meendprocedure();
		procedure.getState().meendprocedure(sess, procedure);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.DeviceImpl".equals(device.getClass().getName())) {
			device.getState().meendprocedure(sess, device);	
			device.meendprocedure();
		} 
		else {
		}

	}










	protected boolean handleMecrdeviceresult(org.hibernate.Session sess,
		java.lang.String deviceusageId, 
		 java.lang.String Time,
		java.lang.String Value)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		
		Deviceusage deviceusage = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 deviceusage = DeviceusageFactory.findByPrimaryKey(sess, deviceusageId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}


		// create object
		MerodeLogger.logln("Creating Deviceresult object");

		Deviceresult deviceresult = DeviceresultFactory.create(sess);

		// save object in database
		sess.save(deviceresult);
		// check user and state preconditions
		deviceresult.check_mecrdeviceresult();
		deviceresult.getState().check_mecrdeviceresult();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.DeviceusageImpl".equals(deviceusage.getClass().getName())) {
			deviceusage.getState().check_mecrdeviceresult();	
			deviceusage.check_mecrdeviceresult();		
		} 
		else {
		}
		deviceusage.getProcedure().getState().check_mecrdeviceresult();
		deviceusage.getProcedure().check_mecrdeviceresult();
		deviceusage.getRegistereddevice().getPlatform().getState().check_mecrdeviceresult();
		deviceusage.getRegistereddevice().getPlatform().check_mecrdeviceresult();
		deviceusage.getPlatformdeployment().getPlatform().getState().check_mecrdeviceresult();
		deviceusage.getPlatformdeployment().getPlatform().check_mecrdeviceresult();
		deviceusage.getProperty().getFeatureofinterest().getState().check_mecrdeviceresult();
		deviceusage.getProperty().getFeatureofinterest().check_mecrdeviceresult();
		deviceusage.getPlatformdeployment().getState().check_mecrdeviceresult();
		deviceusage.getPlatformdeployment().check_mecrdeviceresult();
		deviceusage.getProperty().getState().check_mecrdeviceresult();
		deviceusage.getProperty().check_mecrdeviceresult();
		deviceusage.getProcedure().getDevice().getState().check_mecrdeviceresult();
		deviceusage.getProcedure().getDevice().check_mecrdeviceresult();
		deviceusage.getRegistereddevice().getState().check_mecrdeviceresult();
		deviceusage.getRegistereddevice().check_mecrdeviceresult();
		deviceusage.getRegistereddevice().getDevice().getState().check_mecrdeviceresult();
		deviceusage.getRegistereddevice().getDevice().check_mecrdeviceresult();
		deviceusage.getPlatformdeployment().getFeatureofinterest().getState().check_mecrdeviceresult();
		deviceusage.getPlatformdeployment().getFeatureofinterest().check_mecrdeviceresult();
		
		// register connections


		deviceusage.attachDeviceresult(deviceresult);
		deviceresult.setDeviceusage(deviceusage);
		// execute creating method
		deviceresult.mecrdeviceresult( Time, Value);
		deviceresult.getState().mecrdeviceresult(sess, deviceresult);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.DeviceusageImpl".equals(deviceusage.getClass().getName())) {
			deviceusage.getState().mecrdeviceresult(sess, deviceusage);	
			deviceusage.mecrdeviceresult();
			MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object " + deviceresult.getId() + " of object type 75 related to " + deviceusage.getId() + " with attributes java.lang.String Time java.lang.String Value");
		} 
		else {
		}
		deviceusage.getProcedure().getState().mecrdeviceresult(sess, deviceusage.getProcedure());
		deviceusage.getProcedure().mecrdeviceresult();
		deviceusage.getRegistereddevice().getPlatform().getState().mecrdeviceresult(sess, deviceusage.getRegistereddevice().getPlatform());
		deviceusage.getRegistereddevice().getPlatform().mecrdeviceresult();
		deviceusage.getPlatformdeployment().getPlatform().getState().mecrdeviceresult(sess, deviceusage.getPlatformdeployment().getPlatform());
		deviceusage.getPlatformdeployment().getPlatform().mecrdeviceresult();
		deviceusage.getProperty().getFeatureofinterest().getState().mecrdeviceresult(sess, deviceusage.getProperty().getFeatureofinterest());
		deviceusage.getProperty().getFeatureofinterest().mecrdeviceresult();
		deviceusage.getPlatformdeployment().getState().mecrdeviceresult(sess, deviceusage.getPlatformdeployment());
		deviceusage.getPlatformdeployment().mecrdeviceresult();
		deviceusage.getProperty().getState().mecrdeviceresult(sess, deviceusage.getProperty());
		deviceusage.getProperty().mecrdeviceresult();
		deviceusage.getProcedure().getDevice().getState().mecrdeviceresult(sess, deviceusage.getProcedure().getDevice());
		deviceusage.getProcedure().getDevice().mecrdeviceresult();
		deviceusage.getRegistereddevice().getState().mecrdeviceresult(sess, deviceusage.getRegistereddevice());
		deviceusage.getRegistereddevice().mecrdeviceresult();
		deviceusage.getRegistereddevice().getDevice().getState().mecrdeviceresult(sess, deviceusage.getRegistereddevice().getDevice());
		deviceusage.getRegistereddevice().getDevice().mecrdeviceresult();
		deviceusage.getPlatformdeployment().getFeatureofinterest().getState().mecrdeviceresult(sess, deviceusage.getPlatformdeployment().getFeatureofinterest());
		deviceusage.getPlatformdeployment().getFeatureofinterest().mecrdeviceresult();

		return noMultiplePropagationDetected;
	}

	protected void handleMeenddeviceresult(org.hibernate.Session sess,
			java.lang.String deviceresultId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Deviceresult deviceresult = DeviceresultFactory.findByPrimaryKey(sess, deviceresultId);
		Deviceusage deviceusage = deviceresult.getDeviceusage();

		// check user and state preconditions
		deviceresult.check_meenddeviceresult();
		deviceresult.getState().check_meenddeviceresult();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.DeviceusageImpl".equals(deviceusage.getClass().getName())) {
			deviceusage.getState().check_meenddeviceresult();	
			deviceusage.check_meenddeviceresult();		
		} 
		else {
		}
		deviceusage.getProcedure().getState().check_meenddeviceresult();
		deviceusage.getProcedure().check_meenddeviceresult();
		deviceusage.getRegistereddevice().getPlatform().getState().check_meenddeviceresult();
		deviceusage.getRegistereddevice().getPlatform().check_meenddeviceresult();
		deviceusage.getPlatformdeployment().getPlatform().getState().check_meenddeviceresult();
		deviceusage.getPlatformdeployment().getPlatform().check_meenddeviceresult();
		deviceusage.getProperty().getFeatureofinterest().getState().check_meenddeviceresult();
		deviceusage.getProperty().getFeatureofinterest().check_meenddeviceresult();
		deviceusage.getPlatformdeployment().getState().check_meenddeviceresult();
		deviceusage.getPlatformdeployment().check_meenddeviceresult();
		deviceusage.getProperty().getState().check_meenddeviceresult();
		deviceusage.getProperty().check_meenddeviceresult();
		deviceusage.getProcedure().getDevice().getState().check_meenddeviceresult();
		deviceusage.getProcedure().getDevice().check_meenddeviceresult();
		deviceusage.getRegistereddevice().getState().check_meenddeviceresult();
		deviceusage.getRegistereddevice().check_meenddeviceresult();
		deviceusage.getRegistereddevice().getDevice().getState().check_meenddeviceresult();
		deviceusage.getRegistereddevice().getDevice().check_meenddeviceresult();
		deviceusage.getPlatformdeployment().getFeatureofinterest().getState().check_meenddeviceresult();
		deviceusage.getPlatformdeployment().getFeatureofinterest().check_meenddeviceresult();

		// execute end object
		deviceresult.meenddeviceresult();
		deviceresult.getState().meenddeviceresult(sess, deviceresult);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.DeviceusageImpl".equals(deviceusage.getClass().getName())) {
			deviceusage.getState().meenddeviceresult(sess, deviceusage);	
			deviceusage.meenddeviceresult();
		} 
		else {
		}
		deviceusage.getProcedure().getState().meenddeviceresult(sess, deviceusage.getProcedure());
		deviceusage.getProcedure().meenddeviceresult();
		deviceusage.getRegistereddevice().getPlatform().getState().meenddeviceresult(sess, deviceusage.getRegistereddevice().getPlatform());
		deviceusage.getRegistereddevice().getPlatform().meenddeviceresult();
		deviceusage.getPlatformdeployment().getPlatform().getState().meenddeviceresult(sess, deviceusage.getPlatformdeployment().getPlatform());
		deviceusage.getPlatformdeployment().getPlatform().meenddeviceresult();
		deviceusage.getProperty().getFeatureofinterest().getState().meenddeviceresult(sess, deviceusage.getProperty().getFeatureofinterest());
		deviceusage.getProperty().getFeatureofinterest().meenddeviceresult();
		deviceusage.getPlatformdeployment().getState().meenddeviceresult(sess, deviceusage.getPlatformdeployment());
		deviceusage.getPlatformdeployment().meenddeviceresult();
		deviceusage.getProperty().getState().meenddeviceresult(sess, deviceusage.getProperty());
		deviceusage.getProperty().meenddeviceresult();
		deviceusage.getProcedure().getDevice().getState().meenddeviceresult(sess, deviceusage.getProcedure().getDevice());
		deviceusage.getProcedure().getDevice().meenddeviceresult();
		deviceusage.getRegistereddevice().getState().meenddeviceresult(sess, deviceusage.getRegistereddevice());
		deviceusage.getRegistereddevice().meenddeviceresult();
		deviceusage.getRegistereddevice().getDevice().getState().meenddeviceresult(sess, deviceusage.getRegistereddevice().getDevice());
		deviceusage.getRegistereddevice().getDevice().meenddeviceresult();
		deviceusage.getPlatformdeployment().getFeatureofinterest().getState().meenddeviceresult(sess, deviceusage.getPlatformdeployment().getFeatureofinterest());
		deviceusage.getPlatformdeployment().getFeatureofinterest().meenddeviceresult();

	}










	protected boolean handleMecrfeatureofinterest(org.hibernate.Session sess,
		java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		

		// create object
		MerodeLogger.logln("Creating Featureofinterest object");

		Featureofinterest featureofinterest = FeatureofinterestFactory.create(sess);

		MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object type 88 with attributes java.lang.String Name");
		// save object in database
		sess.save(featureofinterest);
		// check user and state preconditions
		featureofinterest.check_mecrfeatureofinterest();
		featureofinterest.getState().check_mecrfeatureofinterest();
		
		// register connections


		// execute creating method
		featureofinterest.mecrfeatureofinterest( Name);
		featureofinterest.getState().mecrfeatureofinterest(sess, featureofinterest);

		// propagation and state modifications

		return noMultiplePropagationDetected;
	}

	protected void handleMeendfeatureofinterest(org.hibernate.Session sess,
			java.lang.String featureofinterestId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Featureofinterest featureofinterest = FeatureofinterestFactory.findByPrimaryKey(sess, featureofinterestId);

		// check user and state preconditions
		featureofinterest.check_meendfeatureofinterest();
		featureofinterest.getState().check_meendfeatureofinterest();

		// execute end object
		featureofinterest.meendfeatureofinterest();
		featureofinterest.getState().meendfeatureofinterest(sess, featureofinterest);

		// propagation and state modifications

	}










	protected boolean handleMecrplatform(org.hibernate.Session sess,
		java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		

		// create object
		MerodeLogger.logln("Creating Platform object");

		Platform platform = PlatformFactory.create(sess);

		MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object type 136 with attributes java.lang.String Name");
		// save object in database
		sess.save(platform);
		// check user and state preconditions
		platform.check_mecrplatform();
		platform.getState().check_mecrplatform();
		
		// register connections


		// execute creating method
		platform.mecrplatform( Name);
		platform.getState().mecrplatform(sess, platform);

		// propagation and state modifications

		return noMultiplePropagationDetected;
	}

	protected void handleMeendplatform(org.hibernate.Session sess,
			java.lang.String platformId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Platform platform = PlatformFactory.findByPrimaryKey(sess, platformId);

		// check user and state preconditions
		platform.check_meendplatform();
		platform.getState().check_meendplatform();

		// execute end object
		platform.meendplatform();
		platform.getState().meendplatform(sess, platform);

		// propagation and state modifications

	}










	protected boolean handleMecrproperty(org.hibernate.Session sess,
		java.lang.String featureofinterestId, 
		java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		
		Featureofinterest featureofinterest = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 featureofinterest = FeatureofinterestFactory.findByPrimaryKey(sess, featureofinterestId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}


		// create object
		MerodeLogger.logln("Creating Property object");

		Property property = PropertyFactory.create(sess);

		// save object in database
		sess.save(property);
		// check user and state preconditions
		property.check_mecrproperty();
		property.getState().check_mecrproperty();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.FeatureofinterestImpl".equals(featureofinterest.getClass().getName())) {
			featureofinterest.getState().check_mecrproperty();	
			featureofinterest.check_mecrproperty();		
		} 
		else {
		}
		
		// register connections


		featureofinterest.attachProperty(property);
		property.setFeatureofinterest(featureofinterest);
		// execute creating method
		property.mecrproperty( Name);
		property.getState().mecrproperty(sess, property);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.FeatureofinterestImpl".equals(featureofinterest.getClass().getName())) {
			featureofinterest.getState().mecrproperty(sess, featureofinterest);	
			featureofinterest.mecrproperty();
			MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object " + property.getId() + " of object type 184 related to " + featureofinterest.getId() + " with attributes java.lang.String Name");
		} 
		else {
		}

		return noMultiplePropagationDetected;
	}

	protected void handleMeendproperty(org.hibernate.Session sess,
			java.lang.String propertyId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Property property = PropertyFactory.findByPrimaryKey(sess, propertyId);
		Featureofinterest featureofinterest = property.getFeatureofinterest();

		// check user and state preconditions
		property.check_meendproperty();
		property.getState().check_meendproperty();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.FeatureofinterestImpl".equals(featureofinterest.getClass().getName())) {
			featureofinterest.getState().check_meendproperty();	
			featureofinterest.check_meendproperty();		
		} 
		else {
		}

		// execute end object
		property.meendproperty();
		property.getState().meendproperty(sess, property);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.FeatureofinterestImpl".equals(featureofinterest.getClass().getName())) {
			featureofinterest.getState().meendproperty(sess, featureofinterest);	
			featureofinterest.meendproperty();
		} 
		else {
		}

	}










	protected boolean handleMecrdeviceusage(org.hibernate.Session sess,
		java.lang.String platformdeploymentId, 
		java.lang.String registereddeviceId, 
		java.lang.String procedureId, 
		java.lang.String propertyId, 
		java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		
		Platformdeployment platformdeployment = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 platformdeployment = PlatformdeploymentFactory.findByPrimaryKey(sess, platformdeploymentId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}

		Registereddevice registereddevice = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 registereddevice = RegistereddeviceFactory.findByPrimaryKey(sess, registereddeviceId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}

		Procedure procedure = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 procedure = ProcedureFactory.findByPrimaryKey(sess, procedureId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}

		Property property = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 property = PropertyFactory.findByPrimaryKey(sess, propertyId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}


		// -------- multiple propagation check (1) --------- 	 
		   
 	       if (!(property.getFeatureofinterest().getId()
					.equals(platformdeployment.getFeatureofinterest().getId())) && "EQUAL_TO" == "EQUAL_TO"){
				
        		MerodeLogger.logln(" check for multipropagation constraint...failed");
        		ImageIcon icon = new ImageIcon(this.getClass().getResource(
						"teacher.png"));
		
				JOptionPane.showOptionDialog(
						null,
						"<html><b>WARNING: Constraint violation</b><br><br>"
						+ "Make sure that the following two paths lead to the same <b><u> FeatureOfInterest</u></b>:<br><br> "
						+ "<font color='green'><b>"
						+ "FIRST PATH via </b>Property: Property_DeviceUsage<br> Property --> FeatureOfInterest"
						+ "</font><br><br><font color='blue'><b>"
						+ "SECOND PATH via </b>PlatformDeployment: PlatformDeployment_DeviceUsage<br> PlatformDeployment --> FeatureOfInterest"
						+ "</font><br></html>",
						"Multipropagation Constraint Violation",
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.WARNING_MESSAGE, 
						icon,
						null, 
						null
				);
				MerodeLogger.logln("Constraint violation different master objects: " + property.getFeatureofinterest() + " " + platformdeployment.getFeatureofinterest());
				return noMultiplePropagationDetected = false;

 			}
			else if ((property.getFeatureofinterest().getId()
					.equals(platformdeployment.getFeatureofinterest().getId())) && "EQUAL_TO" == "NOT_EQUAL_TO"){
			
        		MerodeLogger.logln(" check for multipropagation constraint...failed");
        		ImageIcon icon = new ImageIcon(this.getClass().getResource(
						"teacher.png"));

				JOptionPane.showOptionDialog(
						null,
						"<html><b>WARNING: Constraint violation</b><br><br>"
						+ "Make sure that the following two paths lead to a different <b><u> FeatureOfInterest</u></b>:<br><br> "
						+ "<font color='green'><b>"
						+ "FIRST PATH via </b>Property: Property_DeviceUsage<br> Property --> FeatureOfInterest"
						+ "</font><br><br><font color='blue'><b>"
						+ "SECOND PATH via </b>PlatformDeployment: PlatformDeployment_DeviceUsage<br> PlatformDeployment --> FeatureOfInterest"
						+ "</font><br></html>",
						"Multipropagation Constraint Violation",
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.WARNING_MESSAGE, 
						icon,
						null, 
						null
				);
			
				return noMultiplePropagationDetected = false;

 			}
 		
		// ----------------------------------------------
		// -------- multiple propagation check (2) --------- 	 
		   
 	       if (!(registereddevice.getPlatform().getId()
					.equals(platformdeployment.getPlatform().getId())) && "EQUAL_TO" == "EQUAL_TO"){
				
        		MerodeLogger.logln(" check for multipropagation constraint...failed");
        		ImageIcon icon = new ImageIcon(this.getClass().getResource(
						"teacher.png"));
		
				JOptionPane.showOptionDialog(
						null,
						"<html><b>WARNING: Constraint violation</b><br><br>"
						+ "Make sure that the following two paths lead to the same <b><u> Platform</u></b>:<br><br> "
						+ "<font color='green'><b>"
						+ "FIRST PATH via </b>RegisteredDevice: RegisteredDevice_DeviceLocation<br> RegisteredDevice --> Platform"
						+ "</font><br><br><font color='blue'><b>"
						+ "SECOND PATH via </b>PlatformDeployment: PlatformDeployment_DeviceUsage<br> PlatformDeployment --> Platform"
						+ "</font><br></html>",
						"Multipropagation Constraint Violation",
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.WARNING_MESSAGE, 
						icon,
						null, 
						null
				);
				MerodeLogger.logln("Constraint violation different master objects: " + registereddevice.getPlatform() + " " + platformdeployment.getPlatform());
				return noMultiplePropagationDetected = false;

 			}
			else if ((registereddevice.getPlatform().getId()
					.equals(platformdeployment.getPlatform().getId())) && "EQUAL_TO" == "NOT_EQUAL_TO"){
			
        		MerodeLogger.logln(" check for multipropagation constraint...failed");
        		ImageIcon icon = new ImageIcon(this.getClass().getResource(
						"teacher.png"));

				JOptionPane.showOptionDialog(
						null,
						"<html><b>WARNING: Constraint violation</b><br><br>"
						+ "Make sure that the following two paths lead to a different <b><u> Platform</u></b>:<br><br> "
						+ "<font color='green'><b>"
						+ "FIRST PATH via </b>RegisteredDevice: RegisteredDevice_DeviceLocation<br> RegisteredDevice --> Platform"
						+ "</font><br><br><font color='blue'><b>"
						+ "SECOND PATH via </b>PlatformDeployment: PlatformDeployment_DeviceUsage<br> PlatformDeployment --> Platform"
						+ "</font><br></html>",
						"Multipropagation Constraint Violation",
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.WARNING_MESSAGE, 
						icon,
						null, 
						null
				);
			
				return noMultiplePropagationDetected = false;

 			}
 		
		// ----------------------------------------------
		// -------- multiple propagation check (3) --------- 	 
		   
 	       if (!(registereddevice.getDevice().getId()
					.equals(procedure.getDevice().getId())) && "EQUAL_TO" == "EQUAL_TO"){
				
        		MerodeLogger.logln(" check for multipropagation constraint...failed");
        		ImageIcon icon = new ImageIcon(this.getClass().getResource(
						"teacher.png"));
		
				JOptionPane.showOptionDialog(
						null,
						"<html><b>WARNING: Constraint violation</b><br><br>"
						+ "Make sure that the following two paths lead to the same <b><u> Device</u></b>:<br><br> "
						+ "<font color='green'><b>"
						+ "FIRST PATH via </b>RegisteredDevice: RegisteredDevice_DeviceLocation<br> RegisteredDevice --> Device"
						+ "</font><br><br><font color='blue'><b>"
						+ "SECOND PATH via </b>Procedure: Procedure_DeviceUsage<br> Procedure --> Device"
						+ "</font><br></html>",
						"Multipropagation Constraint Violation",
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.WARNING_MESSAGE, 
						icon,
						null, 
						null
				);
				MerodeLogger.logln("Constraint violation different master objects: " + registereddevice.getDevice() + " " + procedure.getDevice());
				return noMultiplePropagationDetected = false;

 			}
			else if ((registereddevice.getDevice().getId()
					.equals(procedure.getDevice().getId())) && "EQUAL_TO" == "NOT_EQUAL_TO"){
			
        		MerodeLogger.logln(" check for multipropagation constraint...failed");
        		ImageIcon icon = new ImageIcon(this.getClass().getResource(
						"teacher.png"));

				JOptionPane.showOptionDialog(
						null,
						"<html><b>WARNING: Constraint violation</b><br><br>"
						+ "Make sure that the following two paths lead to a different <b><u> Device</u></b>:<br><br> "
						+ "<font color='green'><b>"
						+ "FIRST PATH via </b>RegisteredDevice: RegisteredDevice_DeviceLocation<br> RegisteredDevice --> Device"
						+ "</font><br><br><font color='blue'><b>"
						+ "SECOND PATH via </b>Procedure: Procedure_DeviceUsage<br> Procedure --> Device"
						+ "</font><br></html>",
						"Multipropagation Constraint Violation",
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.WARNING_MESSAGE, 
						icon,
						null, 
						null
				);
			
				return noMultiplePropagationDetected = false;

 			}
 		
		// ----------------------------------------------
		// create object
		MerodeLogger.logln("Creating Deviceusage object");

		Deviceusage deviceusage = DeviceusageFactory.create(sess);

		// save object in database
		sess.save(deviceusage);
		// check user and state preconditions
		deviceusage.check_mecrdeviceusage();
		deviceusage.getState().check_mecrdeviceusage();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformdeploymentImpl".equals(platformdeployment.getClass().getName())) {
			platformdeployment.getState().check_mecrdeviceusage();	
			platformdeployment.check_mecrdeviceusage();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.RegistereddeviceImpl".equals(registereddevice.getClass().getName())) {
			registereddevice.getState().check_mecrdeviceusage();	
			registereddevice.check_mecrdeviceusage();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProcedureImpl".equals(procedure.getClass().getName())) {
			procedure.getState().check_mecrdeviceusage();	
			procedure.check_mecrdeviceusage();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PropertyImpl".equals(property.getClass().getName())) {
			property.getState().check_mecrdeviceusage();	
			property.check_mecrdeviceusage();		
		} 
		else {
		}
		platformdeployment.getFeatureofinterest().getState().check_mecrdeviceusage();
		platformdeployment.getFeatureofinterest().check_mecrdeviceusage();
		procedure.getDevice().getState().check_mecrdeviceusage();
		procedure.getDevice().check_mecrdeviceusage();
		registereddevice.getPlatform().getState().check_mecrdeviceusage();
		registereddevice.getPlatform().check_mecrdeviceusage();
		registereddevice.getDevice().getState().check_mecrdeviceusage();
		registereddevice.getDevice().check_mecrdeviceusage();
		platformdeployment.getPlatform().getState().check_mecrdeviceusage();
		platformdeployment.getPlatform().check_mecrdeviceusage();
		property.getFeatureofinterest().getState().check_mecrdeviceusage();
		property.getFeatureofinterest().check_mecrdeviceusage();
		
		// register connections


		platformdeployment.attachDeviceusage(deviceusage);
		deviceusage.setPlatformdeployment(platformdeployment);
		registereddevice.attachDeviceusage(deviceusage);
		deviceusage.setRegistereddevice(registereddevice);
		procedure.attachDeviceusage(deviceusage);
		deviceusage.setProcedure(procedure);
		property.attachDeviceusage(deviceusage);
		deviceusage.setProperty(property);
		// execute creating method
		deviceusage.mecrdeviceusage( Name);
		deviceusage.getState().mecrdeviceusage(sess, deviceusage);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformdeploymentImpl".equals(platformdeployment.getClass().getName())) {
			platformdeployment.getState().mecrdeviceusage(sess, platformdeployment);	
			platformdeployment.mecrdeviceusage();
			MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object " + deviceusage.getId() + " of object type 210 related to " + platformdeployment.getId() + " with attributes java.lang.String Name");
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.RegistereddeviceImpl".equals(registereddevice.getClass().getName())) {
			registereddevice.getState().mecrdeviceusage(sess, registereddevice);	
			registereddevice.mecrdeviceusage();
			MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object " + deviceusage.getId() + " of object type 210 related to " + registereddevice.getId() + " with attributes java.lang.String Name");
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProcedureImpl".equals(procedure.getClass().getName())) {
			procedure.getState().mecrdeviceusage(sess, procedure);	
			procedure.mecrdeviceusage();
			MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object " + deviceusage.getId() + " of object type 210 related to " + procedure.getId() + " with attributes java.lang.String Name");
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PropertyImpl".equals(property.getClass().getName())) {
			property.getState().mecrdeviceusage(sess, property);	
			property.mecrdeviceusage();
			MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object " + deviceusage.getId() + " of object type 210 related to " + property.getId() + " with attributes java.lang.String Name");
		} 
		else {
		}
		platformdeployment.getFeatureofinterest().getState().mecrdeviceusage(sess, platformdeployment.getFeatureofinterest());
		platformdeployment.getFeatureofinterest().mecrdeviceusage();
		procedure.getDevice().getState().mecrdeviceusage(sess, procedure.getDevice());
		procedure.getDevice().mecrdeviceusage();
		registereddevice.getPlatform().getState().mecrdeviceusage(sess, registereddevice.getPlatform());
		registereddevice.getPlatform().mecrdeviceusage();
		registereddevice.getDevice().getState().mecrdeviceusage(sess, registereddevice.getDevice());
		registereddevice.getDevice().mecrdeviceusage();
		platformdeployment.getPlatform().getState().mecrdeviceusage(sess, platformdeployment.getPlatform());
		platformdeployment.getPlatform().mecrdeviceusage();
		property.getFeatureofinterest().getState().mecrdeviceusage(sess, property.getFeatureofinterest());
		property.getFeatureofinterest().mecrdeviceusage();

		return noMultiplePropagationDetected;
	}

	protected void handleMeenddeviceusage(org.hibernate.Session sess,
			java.lang.String deviceusageId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Deviceusage deviceusage = DeviceusageFactory.findByPrimaryKey(sess, deviceusageId);
		Platformdeployment platformdeployment = deviceusage.getPlatformdeployment();
		Registereddevice registereddevice = deviceusage.getRegistereddevice();
		Procedure procedure = deviceusage.getProcedure();
		Property property = deviceusage.getProperty();

		// check user and state preconditions
		deviceusage.check_meenddeviceusage();
		deviceusage.getState().check_meenddeviceusage();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformdeploymentImpl".equals(platformdeployment.getClass().getName())) {
			platformdeployment.getState().check_meenddeviceusage();	
			platformdeployment.check_meenddeviceusage();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.RegistereddeviceImpl".equals(registereddevice.getClass().getName())) {
			registereddevice.getState().check_meenddeviceusage();	
			registereddevice.check_meenddeviceusage();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProcedureImpl".equals(procedure.getClass().getName())) {
			procedure.getState().check_meenddeviceusage();	
			procedure.check_meenddeviceusage();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PropertyImpl".equals(property.getClass().getName())) {
			property.getState().check_meenddeviceusage();	
			property.check_meenddeviceusage();		
		} 
		else {
		}
		platformdeployment.getFeatureofinterest().getState().check_meenddeviceusage();
		platformdeployment.getFeatureofinterest().check_meenddeviceusage();
		procedure.getDevice().getState().check_meenddeviceusage();
		procedure.getDevice().check_meenddeviceusage();
		registereddevice.getPlatform().getState().check_meenddeviceusage();
		registereddevice.getPlatform().check_meenddeviceusage();
		registereddevice.getDevice().getState().check_meenddeviceusage();
		registereddevice.getDevice().check_meenddeviceusage();
		platformdeployment.getPlatform().getState().check_meenddeviceusage();
		platformdeployment.getPlatform().check_meenddeviceusage();
		property.getFeatureofinterest().getState().check_meenddeviceusage();
		property.getFeatureofinterest().check_meenddeviceusage();

		// execute end object
		deviceusage.meenddeviceusage();
		deviceusage.getState().meenddeviceusage(sess, deviceusage);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformdeploymentImpl".equals(platformdeployment.getClass().getName())) {
			platformdeployment.getState().meenddeviceusage(sess, platformdeployment);	
			platformdeployment.meenddeviceusage();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.RegistereddeviceImpl".equals(registereddevice.getClass().getName())) {
			registereddevice.getState().meenddeviceusage(sess, registereddevice);	
			registereddevice.meenddeviceusage();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProcedureImpl".equals(procedure.getClass().getName())) {
			procedure.getState().meenddeviceusage(sess, procedure);	
			procedure.meenddeviceusage();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PropertyImpl".equals(property.getClass().getName())) {
			property.getState().meenddeviceusage(sess, property);	
			property.meenddeviceusage();
		} 
		else {
		}
		platformdeployment.getFeatureofinterest().getState().meenddeviceusage(sess, platformdeployment.getFeatureofinterest());
		platformdeployment.getFeatureofinterest().meenddeviceusage();
		procedure.getDevice().getState().meenddeviceusage(sess, procedure.getDevice());
		procedure.getDevice().meenddeviceusage();
		registereddevice.getPlatform().getState().meenddeviceusage(sess, registereddevice.getPlatform());
		registereddevice.getPlatform().meenddeviceusage();
		registereddevice.getDevice().getState().meenddeviceusage(sess, registereddevice.getDevice());
		registereddevice.getDevice().meenddeviceusage();
		platformdeployment.getPlatform().getState().meenddeviceusage(sess, platformdeployment.getPlatform());
		platformdeployment.getPlatform().meenddeviceusage();
		property.getFeatureofinterest().getState().meenddeviceusage(sess, property.getFeatureofinterest());
		property.getFeatureofinterest().meenddeviceusage();

	}
	protected void handleDeviceundeployment(org.hibernate.Session sess,
			java.lang.String deviceusageId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Deviceusage deviceusage = DeviceusageFactory.findByPrimaryKey(sess, deviceusageId);
		Platformdeployment platformdeployment = deviceusage.getPlatformdeployment();
		Registereddevice registereddevice = deviceusage.getRegistereddevice();
		Procedure procedure = deviceusage.getProcedure();
		Property property = deviceusage.getProperty();

		// check user and state preconditions
		deviceusage.check_deviceundeployment();
		deviceusage.getState().check_deviceundeployment();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformdeploymentImpl".equals(platformdeployment.getClass().getName())) {
			platformdeployment.getState().check_deviceundeployment();	
			platformdeployment.check_deviceundeployment();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.RegistereddeviceImpl".equals(registereddevice.getClass().getName())) {
			registereddevice.getState().check_deviceundeployment();	
			registereddevice.check_deviceundeployment();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProcedureImpl".equals(procedure.getClass().getName())) {
			procedure.getState().check_deviceundeployment();	
			procedure.check_deviceundeployment();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PropertyImpl".equals(property.getClass().getName())) {
			property.getState().check_deviceundeployment();	
			property.check_deviceundeployment();		
		} 
		else {
		}
		platformdeployment.getFeatureofinterest().getState().check_deviceundeployment();
		platformdeployment.getFeatureofinterest().check_deviceundeployment();
		procedure.getDevice().getState().check_deviceundeployment();
		procedure.getDevice().check_deviceundeployment();
		registereddevice.getPlatform().getState().check_deviceundeployment();
		registereddevice.getPlatform().check_deviceundeployment();
		registereddevice.getDevice().getState().check_deviceundeployment();
		registereddevice.getDevice().check_deviceundeployment();
		platformdeployment.getPlatform().getState().check_deviceundeployment();
		platformdeployment.getPlatform().check_deviceundeployment();
		property.getFeatureofinterest().getState().check_deviceundeployment();
		property.getFeatureofinterest().check_deviceundeployment();

		// execute end object
		deviceusage.deviceundeployment();
		deviceusage.getState().deviceundeployment(sess, deviceusage);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformdeploymentImpl".equals(platformdeployment.getClass().getName())) {
			platformdeployment.getState().deviceundeployment(sess, platformdeployment);	
			platformdeployment.deviceundeployment();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.RegistereddeviceImpl".equals(registereddevice.getClass().getName())) {
			registereddevice.getState().deviceundeployment(sess, registereddevice);	
			registereddevice.deviceundeployment();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProcedureImpl".equals(procedure.getClass().getName())) {
			procedure.getState().deviceundeployment(sess, procedure);	
			procedure.deviceundeployment();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PropertyImpl".equals(property.getClass().getName())) {
			property.getState().deviceundeployment(sess, property);	
			property.deviceundeployment();
		} 
		else {
		}
		platformdeployment.getFeatureofinterest().getState().deviceundeployment(sess, platformdeployment.getFeatureofinterest());
		platformdeployment.getFeatureofinterest().deviceundeployment();
		procedure.getDevice().getState().deviceundeployment(sess, procedure.getDevice());
		procedure.getDevice().deviceundeployment();
		registereddevice.getPlatform().getState().deviceundeployment(sess, registereddevice.getPlatform());
		registereddevice.getPlatform().deviceundeployment();
		registereddevice.getDevice().getState().deviceundeployment(sess, registereddevice.getDevice());
		registereddevice.getDevice().deviceundeployment();
		platformdeployment.getPlatform().getState().deviceundeployment(sess, platformdeployment.getPlatform());
		platformdeployment.getPlatform().deviceundeployment();
		property.getFeatureofinterest().getState().deviceundeployment(sess, property.getFeatureofinterest());
		property.getFeatureofinterest().deviceundeployment();

	}

	protected void handleDevicedeployment(org.hibernate.Session sess,
		java.lang.String deviceusageId, java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {

				Deviceusage deviceusage = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 deviceusage = DeviceusageFactory.findByPrimaryKey(sess, deviceusageId);
		} catch (Exception _excption){
			System.out.println ("not an instance");
		}

		Platformdeployment platformdeployment = deviceusage.getPlatformdeployment();
		Registereddevice registereddevice = deviceusage.getRegistereddevice();
		Procedure procedure = deviceusage.getProcedure();
		Property property = deviceusage.getProperty();
		// check user and state preconditions
		deviceusage.check_devicedeployment();
		deviceusage.getState().check_devicedeployment();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformdeploymentImpl".equals(platformdeployment.getClass().getName())) {
			platformdeployment.getState().check_devicedeployment();	
			platformdeployment.check_devicedeployment();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.RegistereddeviceImpl".equals(registereddevice.getClass().getName())) {
			registereddevice.getState().check_devicedeployment();	
			registereddevice.check_devicedeployment();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProcedureImpl".equals(procedure.getClass().getName())) {
			procedure.getState().check_devicedeployment();	
			procedure.check_devicedeployment();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PropertyImpl".equals(property.getClass().getName())) {
			property.getState().check_devicedeployment();	
			property.check_devicedeployment();		
		} 
		else {
		}
		platformdeployment.getFeatureofinterest().getState().check_devicedeployment();
		platformdeployment.getFeatureofinterest().check_devicedeployment();
		procedure.getDevice().getState().check_devicedeployment();
		procedure.getDevice().check_devicedeployment();
		registereddevice.getPlatform().getState().check_devicedeployment();
		registereddevice.getPlatform().check_devicedeployment();
		registereddevice.getDevice().getState().check_devicedeployment();
		registereddevice.getDevice().check_devicedeployment();
		platformdeployment.getPlatform().getState().check_devicedeployment();
		platformdeployment.getPlatform().check_devicedeployment();
		property.getFeatureofinterest().getState().check_devicedeployment();
		property.getFeatureofinterest().check_devicedeployment();


		// execute modifying method
		deviceusage.devicedeployment( Name);
		deviceusage.getState().devicedeployment(sess, deviceusage);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformdeploymentImpl".equals(platformdeployment.getClass().getName())) {
			platformdeployment.getState().devicedeployment(sess, platformdeployment);	
			platformdeployment.devicedeployment();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.RegistereddeviceImpl".equals(registereddevice.getClass().getName())) {
			registereddevice.getState().devicedeployment(sess, registereddevice);	
			registereddevice.devicedeployment();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProcedureImpl".equals(procedure.getClass().getName())) {
			procedure.getState().devicedeployment(sess, procedure);	
			procedure.devicedeployment();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PropertyImpl".equals(property.getClass().getName())) {
			property.getState().devicedeployment(sess, property);	
			property.devicedeployment();
		} 
		else {
		}
		platformdeployment.getFeatureofinterest().getState().devicedeployment(sess, platformdeployment.getFeatureofinterest());
		platformdeployment.getFeatureofinterest().devicedeployment();
		procedure.getDevice().getState().devicedeployment(sess, procedure.getDevice());
		procedure.getDevice().devicedeployment();
		registereddevice.getPlatform().getState().devicedeployment(sess, registereddevice.getPlatform());
		registereddevice.getPlatform().devicedeployment();
		registereddevice.getDevice().getState().devicedeployment(sess, registereddevice.getDevice());
		registereddevice.getDevice().devicedeployment();
		platformdeployment.getPlatform().getState().devicedeployment(sess, platformdeployment.getPlatform());
		platformdeployment.getPlatform().devicedeployment();
		property.getFeatureofinterest().getState().devicedeployment(sess, property.getFeatureofinterest());
		property.getFeatureofinterest().devicedeployment();

	}
	protected void handleMesetready(org.hibernate.Session sess,
		java.lang.String deviceusageId, java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {

				Deviceusage deviceusage = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 deviceusage = DeviceusageFactory.findByPrimaryKey(sess, deviceusageId);
		} catch (Exception _excption){
			System.out.println ("not an instance");
		}

		Platformdeployment platformdeployment = deviceusage.getPlatformdeployment();
		Registereddevice registereddevice = deviceusage.getRegistereddevice();
		Procedure procedure = deviceusage.getProcedure();
		Property property = deviceusage.getProperty();
		// check user and state preconditions
		deviceusage.check_mesetready();
		deviceusage.getState().check_mesetready();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformdeploymentImpl".equals(platformdeployment.getClass().getName())) {
			platformdeployment.getState().check_mesetready();	
			platformdeployment.check_mesetready();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.RegistereddeviceImpl".equals(registereddevice.getClass().getName())) {
			registereddevice.getState().check_mesetready();	
			registereddevice.check_mesetready();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProcedureImpl".equals(procedure.getClass().getName())) {
			procedure.getState().check_mesetready();	
			procedure.check_mesetready();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PropertyImpl".equals(property.getClass().getName())) {
			property.getState().check_mesetready();	
			property.check_mesetready();		
		} 
		else {
		}
		platformdeployment.getFeatureofinterest().getState().check_mesetready();
		platformdeployment.getFeatureofinterest().check_mesetready();
		procedure.getDevice().getState().check_mesetready();
		procedure.getDevice().check_mesetready();
		registereddevice.getPlatform().getState().check_mesetready();
		registereddevice.getPlatform().check_mesetready();
		registereddevice.getDevice().getState().check_mesetready();
		registereddevice.getDevice().check_mesetready();
		platformdeployment.getPlatform().getState().check_mesetready();
		platformdeployment.getPlatform().check_mesetready();
		property.getFeatureofinterest().getState().check_mesetready();
		property.getFeatureofinterest().check_mesetready();


		// execute modifying method
		deviceusage.mesetready( Name);
		deviceusage.getState().mesetready(sess, deviceusage);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformdeploymentImpl".equals(platformdeployment.getClass().getName())) {
			platformdeployment.getState().mesetready(sess, platformdeployment);	
			platformdeployment.mesetready();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.RegistereddeviceImpl".equals(registereddevice.getClass().getName())) {
			registereddevice.getState().mesetready(sess, registereddevice);	
			registereddevice.mesetready();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.ProcedureImpl".equals(procedure.getClass().getName())) {
			procedure.getState().mesetready(sess, procedure);	
			procedure.mesetready();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PropertyImpl".equals(property.getClass().getName())) {
			property.getState().mesetready(sess, property);	
			property.mesetready();
		} 
		else {
		}
		platformdeployment.getFeatureofinterest().getState().mesetready(sess, platformdeployment.getFeatureofinterest());
		platformdeployment.getFeatureofinterest().mesetready();
		procedure.getDevice().getState().mesetready(sess, procedure.getDevice());
		procedure.getDevice().mesetready();
		registereddevice.getPlatform().getState().mesetready(sess, registereddevice.getPlatform());
		registereddevice.getPlatform().mesetready();
		registereddevice.getDevice().getState().mesetready(sess, registereddevice.getDevice());
		registereddevice.getDevice().mesetready();
		platformdeployment.getPlatform().getState().mesetready(sess, platformdeployment.getPlatform());
		platformdeployment.getPlatform().mesetready();
		property.getFeatureofinterest().getState().mesetready(sess, property.getFeatureofinterest());
		property.getFeatureofinterest().mesetready();

	}









	protected boolean handleMecrplatformdeployment(org.hibernate.Session sess,
		java.lang.String platformId, 
		java.lang.String featureofinterestId, 
		java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		
		Platform platform = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 platform = PlatformFactory.findByPrimaryKey(sess, platformId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}

		Featureofinterest featureofinterest = null;
		// inheritance support: one of the instances is correct, either supertype or subtype
		try {
			 featureofinterest = FeatureofinterestFactory.findByPrimaryKey(sess, featureofinterestId);
		} catch (Exception _exception){
			System.out.println ("not an instance");
		}


		// create object
		MerodeLogger.logln("Creating Platformdeployment object");

		Platformdeployment platformdeployment = PlatformdeploymentFactory.create(sess);

		// save object in database
		sess.save(platformdeployment);
		// check user and state preconditions
		platformdeployment.check_mecrplatformdeployment();
		platformdeployment.getState().check_mecrplatformdeployment();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformImpl".equals(platform.getClass().getName())) {
			platform.getState().check_mecrplatformdeployment();	
			platform.check_mecrplatformdeployment();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.FeatureofinterestImpl".equals(featureofinterest.getClass().getName())) {
			featureofinterest.getState().check_mecrplatformdeployment();	
			featureofinterest.check_mecrplatformdeployment();		
		} 
		else {
		}
		
		// register connections


		platform.attachPlatformdeployment(platformdeployment);
		platformdeployment.setPlatform(platform);
		featureofinterest.attachPlatformdeployment(platformdeployment);
		platformdeployment.setFeatureofinterest(featureofinterest);
		// execute creating method
		platformdeployment.mecrplatformdeployment( Name);
		platformdeployment.getState().mecrplatformdeployment(sess, platformdeployment);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformImpl".equals(platform.getClass().getName())) {
			platform.getState().mecrplatformdeployment(sess, platform);	
			platform.mecrplatformdeployment();
			MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object " + platformdeployment.getId() + " of object type 270 related to " + platform.getId() + " with attributes java.lang.String Name");
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.FeatureofinterestImpl".equals(featureofinterest.getClass().getName())) {
			featureofinterest.getState().mecrplatformdeployment(sess, featureofinterest);	
			featureofinterest.mecrplatformdeployment();
			MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object " + platformdeployment.getId() + " of object type 270 related to " + featureofinterest.getId() + " with attributes java.lang.String Name");
		} 
		else {
		}

		return noMultiplePropagationDetected;
	}

	protected void handleMeendplatformdeployment(org.hibernate.Session sess,
			java.lang.String platformdeploymentId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Platformdeployment platformdeployment = PlatformdeploymentFactory.findByPrimaryKey(sess, platformdeploymentId);
		Platform platform = platformdeployment.getPlatform();
		Featureofinterest featureofinterest = platformdeployment.getFeatureofinterest();

		// check user and state preconditions
		platformdeployment.check_meendplatformdeployment();
		platformdeployment.getState().check_meendplatformdeployment();
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformImpl".equals(platform.getClass().getName())) {
			platform.getState().check_meendplatformdeployment();	
			platform.check_meendplatformdeployment();		
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.FeatureofinterestImpl".equals(featureofinterest.getClass().getName())) {
			featureofinterest.getState().check_meendplatformdeployment();	
			featureofinterest.check_meendplatformdeployment();		
		} 
		else {
		}

		// execute end object
		platformdeployment.meendplatformdeployment();
		platformdeployment.getState().meendplatformdeployment(sess, platformdeployment);

		// propagation and state modifications
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.PlatformImpl".equals(platform.getClass().getName())) {
			platform.getState().meendplatformdeployment(sess, platform);	
			platform.meendplatformdeployment();
		} 
		else {
		}
		// inheritance support: one of the instances is correct, either supertype or subtype
		if ("dao.FeatureofinterestImpl".equals(featureofinterest.getClass().getName())) {
			featureofinterest.getState().meendplatformdeployment(sess, featureofinterest);	
			featureofinterest.meendplatformdeployment();
		} 
		else {
		}

	}










	protected boolean handleMecrdevice(org.hibernate.Session sess,
		java.lang.String Name)
			throws dao.MerodeException, org.hibernate.HibernateException {
			
		boolean noMultiplePropagationDetected = true;
		

		// create object
		MerodeLogger.logln("Creating Device object");

		Device device = DeviceFactory.create(sess);

		MerodeLogger.logln("[" + Instant.now() + "]" + " Initializing object type 296 with attributes java.lang.String Name");
		// save object in database
		sess.save(device);
		// check user and state preconditions
		device.check_mecrdevice();
		device.getState().check_mecrdevice();
		
		// register connections


		// execute creating method
		device.mecrdevice( Name);
		device.getState().mecrdevice(sess, device);

		// propagation and state modifications

		return noMultiplePropagationDetected;
	}

	protected void handleMeenddevice(org.hibernate.Session sess,
			java.lang.String deviceId) throws dao.MerodeException,
				org.hibernate.HibernateException {
		Device device = DeviceFactory.findByPrimaryKey(sess, deviceId);

		// check user and state preconditions
		device.check_meenddevice();
		device.getState().check_meenddevice();

		// execute end object
		device.meenddevice();
		device.getState().meenddevice(sess, device);

		// propagation and state modifications

	}










	// ---------------- update and delete methods ------------------
	
	
	public void deleteRegistereddevice(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Registereddevice result = RegistereddeviceFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deleteRegistereddevice: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deleteRegistereddevice: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updateRegistereddevice(java.lang.String id, 
				java.lang.String Name) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Registereddevice result = RegistereddeviceFactory.findByPrimaryKey(session, id);
			result.setName(Name);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updateRegistereddevice: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updateRegistereddevice: " + he.getMessage());
				}
			}
		}
	}
	
	public void deleteProcedure(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Procedure result = ProcedureFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deleteProcedure: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deleteProcedure: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updateProcedure(java.lang.String id, 
				java.lang.String Name) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Procedure result = ProcedureFactory.findByPrimaryKey(session, id);
			result.setName(Name);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updateProcedure: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updateProcedure: " + he.getMessage());
				}
			}
		}
	}
	
	public void deleteDeviceresult(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Deviceresult result = DeviceresultFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deleteDeviceresult: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deleteDeviceresult: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updateDeviceresult(java.lang.String id, 
				 java.lang.String Time,
		java.lang.String Value) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Deviceresult result = DeviceresultFactory.findByPrimaryKey(session, id);
			result.setTime(Time);
			result.setValue(Value);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updateDeviceresult: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updateDeviceresult: " + he.getMessage());
				}
			}
		}
	}
	
	public void deleteFeatureofinterest(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Featureofinterest result = FeatureofinterestFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deleteFeatureofinterest: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deleteFeatureofinterest: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updateFeatureofinterest(java.lang.String id, 
				java.lang.String Name) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Featureofinterest result = FeatureofinterestFactory.findByPrimaryKey(session, id);
			result.setName(Name);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updateFeatureofinterest: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updateFeatureofinterest: " + he.getMessage());
				}
			}
		}
	}
	
	public void deletePlatform(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Platform result = PlatformFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deletePlatform: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deletePlatform: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updatePlatform(java.lang.String id, 
				java.lang.String Name) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Platform result = PlatformFactory.findByPrimaryKey(session, id);
			result.setName(Name);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updatePlatform: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updatePlatform: " + he.getMessage());
				}
			}
		}
	}
	
	public void deleteProperty(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Property result = PropertyFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deleteProperty: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deleteProperty: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updateProperty(java.lang.String id, 
				java.lang.String Name) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Property result = PropertyFactory.findByPrimaryKey(session, id);
			result.setName(Name);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updateProperty: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updateProperty: " + he.getMessage());
				}
			}
		}
	}
	
	public void deleteDeviceusage(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Deviceusage result = DeviceusageFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deleteDeviceusage: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deleteDeviceusage: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updateDeviceusage(java.lang.String id, 
				java.lang.String Name) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Deviceusage result = DeviceusageFactory.findByPrimaryKey(session, id);
			result.setName(Name);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updateDeviceusage: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updateDeviceusage: " + he.getMessage());
				}
			}
		}
	}
	
	public void deletePlatformdeployment(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Platformdeployment result = PlatformdeploymentFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deletePlatformdeployment: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deletePlatformdeployment: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updatePlatformdeployment(java.lang.String id, 
				java.lang.String Name) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Platformdeployment result = PlatformdeploymentFactory.findByPrimaryKey(session, id);
			result.setName(Name);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updatePlatformdeployment: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updatePlatformdeployment: " + he.getMessage());
				}
			}
		}
	}
	
	public void deleteDevice(java.lang.String id) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Device result = DeviceFactory.findByPrimaryKey(session, id);
			session.delete(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("deleteDevice: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("deleteDevice: " + he.getMessage());
				}
			}
		}
	}
	
	
	public void updateDevice(java.lang.String id, 
				java.lang.String Name) throws Exception {
		Session session = null;
		try {
			session = getSession();
			Device result = DeviceFactory.findByPrimaryKey(session, id);
			result.setName(Name);
			session.save(result);
			session.beginTransaction().commit();
		} catch (Throwable th) {
			throw new Exception("updateDevice: " + th.toString());
		} finally {
			if (session != null) {
				try {
					//session.flush();
					session.close();
				} catch (HibernateException he) {
					throw new Exception("updateDevice: " + he.getMessage());
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
