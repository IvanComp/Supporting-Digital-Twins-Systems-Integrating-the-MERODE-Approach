package testing;

import org.w3c.dom.Element;

/**
 * Created by Arturo Mantinetti.
 */
class TransitionMethod {
    int methodid;
    String methodname;
    int safeid;

    TransitionMethod(Element transitionMethod){
        String sid = transitionMethod.getAttribute("methodid");
        methodid = Integer.parseInt(sid);
        String ssafeid = transitionMethod.getAttribute("safeid");
        safeid = Integer.parseInt(ssafeid);
        methodname = transitionMethod.getAttribute("methodname");
        System.out.println("\t\t\t\t New Transition Method methodid:"+methodid+" |methodname:" + methodname + " |safeid:" + safeid);
    }
    
    public int getId(){
    	return methodid;
    }
    
    public String getMethodName(){
    	return methodname;
    }
}
