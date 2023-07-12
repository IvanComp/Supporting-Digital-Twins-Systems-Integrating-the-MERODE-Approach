package testing;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * Created by Arturo Mantinetti.
 */
class Transition{
    public static String TRANSITION_METHOD = "mxp:metatransitionmethod";
    public int id;
    public String name;
    public String legacy_name;
    public int sourcestateid;
    public int targetstateid;
    public ArrayList<TransitionMethod> transitionMethods = null;
    
    Transition(Element transition){
        String sid = transition.getAttribute("id");
        id = Integer.parseInt(sid);
        String ssourcestateid = transition.getAttribute("sourcestateid");
        sourcestateid = Integer.parseInt(ssourcestateid);
        String stargetstateid = transition.getAttribute("targetstateid");
        targetstateid = Integer.parseInt(stargetstateid);
        name = transition.getAttribute("name");
        legacy_name = transition.getAttribute("legacy_name");
        System.out.println("\t\t\t New Transition id:"+id+" |name:" + name + " |Legacy Name:" + legacy_name +
                " |sourcestateid:"+ sourcestateid + " |targetstateid:" + targetstateid);

        NodeList methodList = transition.getElementsByTagName(TRANSITION_METHOD);
        for (int temp = 0; temp < methodList.getLength(); temp++) {
            Node nNode = methodList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element transitionMethod = (Element) nNode;
                TransitionMethod atemp = new TransitionMethod(transitionMethod);
                if (transitionMethods==null)
                    transitionMethods = new ArrayList<TransitionMethod>();
                transitionMethods.add(atemp);
            }
        }

    }

    public int getId(){
    	return id;
    }
    
    public String getName(){
    	return name;
    }
    
    public String getLegacyName(){
    	return legacy_name;
    }
    
    public int getSourceStateId(){
    	return sourcestateid;
    }
    
    public int getTargetStateId(){
    	return targetstateid;
    }
        
    // Get amount of methods
    public int getMethodAmount(){
    	return transitionMethods.size();
    }
    
    // Get name of a given method
    public String getMethodName(int i){
    	return transitionMethods.get(i).getMethodName();
    }
    
    // Get id of a given method
    public int getMethodId(int i){
    	return transitionMethods.get(i).getId();
    }

    // Get all method ids
    public ArrayList<Integer> getMethodIds(){
        ArrayList<Integer> methodIds = new ArrayList<Integer>();
        for (TransitionMethod tMethod : transitionMethods) {
            methodIds.add(tMethod.getId());
        }
    	return methodIds;
    }
}