package testing;

import org.w3c.dom.Element;

/**
 *  Author: Sofia Alarcon 
 */
public class Method {

	public int id;
	public String name;
	public String provenance;
	public String type;
	public int ownerobjectid;
	public int ownereventid;
	
	
	public Method(Element object) {
		
		String sid = object.getAttribute("id");
        id = Integer.parseInt(sid);
		
		name = object.getAttribute("name");
		
		provenance = object.getAttribute("provenance");
		
		type = object.getAttribute("type");
		
		String sid2 = object.getAttribute("ownerobjectid");
		ownerobjectid = Integer.parseInt(sid2);
        
        String sid3 = object.getAttribute("ownereventid");
        ownereventid = Integer.parseInt(sid3);
		
		//System.out.println("Adding new metamethod: " + id + "," + name + "," + provenance + "," + type + "," + ownerobjectid + "," + ownereventid);
	}
	
	// Get id
	public int getId(){
		return id;
	}

	// Get name
	public String getName(){
		return name;
	}
	
	// Check if a method is of type "OWNED"
	public boolean isOwned(){
		if("OWNED".equals(provenance)){
			return true;
		}
		else{
			return false;
		}
	}

}
