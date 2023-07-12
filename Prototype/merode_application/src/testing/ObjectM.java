package testing;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 * Created by Arturo Mantinetti on 1-05-2017.
 * Edited by Sofia Alarcon.
 */
public class ObjectM {
    public static String METASTATE = "mxp:metastate";
    public static String TRANSITION = "mxp:metatransition";
    public String name;
    public boolean abstractModel;
    public int id;
    public ArrayList<Pair<String, String>> dependents;
    public ArrayList<String> children;
    
    //  This arraylist uses the Attribute class declared in the ObjectM class
    public ArrayList<Attribute> attributes = null;
    
    public ArrayList<Fsm> fsms = null;

    // Methods
    public ObjectM(String name, boolean abstractModel, int id) {
        this.name = name;
        this.abstractModel = abstractModel;
        this.id = id;
        attributes = null;
    }

    public ObjectM(Element object) {
        name = object.getAttribute("name");
        
        String absModel = object.getAttribute("abstract");
        abstractModel = Boolean.valueOf(absModel);
        
        String sid = object.getAttribute("id");
        id = Integer.parseInt(sid);
        
        attributes = null;
        System.out.println("\tNew Object name:" + name + " | abstract:" + abstractModel + " | id:" + id);
    }

    public ObjectM(Element object, ArrayList<Pair<String, String>> dependents, ArrayList<String> children) {
        name = object.getAttribute("name");
        
        String absModel = object.getAttribute("abstract");
        abstractModel = Boolean.valueOf(absModel);
        
        String sid = object.getAttribute("id");
        id = Integer.parseInt(sid);
        
        attributes = null;

        this.dependents = dependents;
        this.children = children;

        System.out.println("\tNew Object name:" + name + " | abstract:" + abstractModel + " | id:" + id);
    }

    public ArrayList<Pair<String, String>> getDependents(){
        return this.dependents;
    }

    public ArrayList<String> getChildren(){
        return this.children;
    }

    public void insertAttributes(NodeList attributesList){   
        for (int temp = 0; temp < attributesList.getLength(); temp++) {     
            Node nNode = attributesList.item(temp);        
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element attribute = (Element) nNode;
                Attribute atemp = new Attribute(attribute);
                if (attributes==null) {
                  
                    attributes = new ArrayList<Attribute>();
                   
                }
               
                attributes.add(atemp);
               
            }
        }
    }

    public void insertFSM(NodeList fsmList){
        for (int temp = 0; temp < fsmList.getLength(); temp++) {
            Node nNode = fsmList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element fsm = (Element) nNode;
                Fsm atemp = new Fsm(fsm);
                if (fsms==null)
                    fsms = new ArrayList<Fsm>();
                fsms.add(atemp);
            }
        }
    }

    // Return name of given attribute
    public String getAttributeName(int i){
        return attributes.get(i).name;
    }

     // Return amount of attributes of object
     public int getAttributesSize(){
    	return attributes.size();
    }

    // Return object id
    public int getId(){
    	return id;
    }
    
    // Return object name
    public String getName(){
    	return name;
    }

    // Return the FSM at the given index
    public Fsm getFsm(int i){
    	return this.fsms.get(i);
    }
    
    // Return a FSM name
    public String getFsmName(int i){
    	return fsms.get(i).getName();
    }
    
    // Return amount of FSMs available
    public int getFsmSize(){
    	return fsms.size();
    }
    
    // Ask if a given FSM is codegenerated
    public boolean isCodeGenerated(int i){
    	return fsms.get(i).isCodeGenerated();
    }
    
    // Get state amount, given an fsm
    public int getStateAmount(int i){
    	return fsms.get(i).getStateAmount();
    }
    
    // Get initial state ID of a given FSM
    public int getInitialStateId(int i){
    	return fsms.get(i).getInitialStateId();
    }
    
    // Get state id, given a fsm and state
    public int getStateId(int fsm, int state){
    	return fsms.get(fsm).getStateId(state);
    }
    
    // Get state id, given a fsm and state
    public ArrayList<Integer> getStateIds(int fsm){
    	return fsms.get(fsm).getStateIds();
    }

    // Get state name given a fsm and state
    public String getStateName(int i, int j){
    	return fsms.get(i).getStateName(j);
    }
    
    // Get amount of transitions for a given fsm
    public int getTransitionAmount(int i){
    	return fsms.get(i).getTransitionAmount();
    }
    
    // Get transition id, given fsm and transition
    public int getTransitionId(int i, int j){
    	return fsms.get(i).getTransitionId(j);
    }
    
    // Get transition name, given fsm and transition
    public String getTransitionName(int i, int j){
    	return fsms.get(i).getTransitionName(j);
    }
    
    // Get source state id of a given transition (given fsm and transition)
    public int getTransitionSourceStateId(int i, int j){
    	return fsms.get(i).getTransitionSourceStateId(j);
    }
    
    // Get target state id of a given transition (given fsm and transition)
    public int getTransitionTargetStateId(int i, int j){
    	return fsms.get(i).getTransitionTargetStateId(j);
    }
    
    // Get transition legacy name, given a fsm and transition
    public String getTransitionLegacyName(int i, int j){
    	return fsms.get(i).getTransitionLegacyName(j);
    }
    
    // Get amount of methods, given a fsm and transition
    public int getMethodAmount(int i, int j){
    	return fsms.get(i).getMethodAmount(j);
    }
    
    // Get method name, given a fsm, transition and method
    public String getMethodName(int i, int j, int k){
    	return fsms.get(i).getMethodName(j, k);
    }
    
    // Get method id, given a fsm, transition and method
    public int getMethodId(int i, int j, int k){
    	return fsms.get(i).getMethodId(j, k);
    }

     // Get method ids, given a fsm, transition
     public ArrayList<Integer> getMethodIds(int i, int j){
    	return fsms.get(i).getMethodIds(j);
    }
    
     class Attribute{
        int id;
        String name;
        String type;
        int type_id;

        public Attribute(int id, String name, String type, int type_id) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.type_id = type_id;
        }
        public Attribute(Element attribute) {

            String sid = attribute.getAttribute("id");

            id = Integer.parseInt(sid);

            name = attribute.getAttribute("name");

            type = attribute.getAttribute("type");

            //String tid = attribute.getAttribute("type-id");
            //type_id = Integer.parseInt(tid);

            System.out.println("\t\tNew Attribute id:"+id+" | name:" + name + " | Type:" + type);// + " | Type_id:" + type_id);
        }
    }

    public class Fsm {
        int id;
        String name;
        String type;
        boolean codegeneration;
        ArrayList<State> states = null;
        ArrayList<Transition> transitions = null;
        
        // Constructor
        public Fsm(Element fms) {
            String sid = fms.getAttribute("id");
            id = Integer.parseInt(sid);
            name = fms.getAttribute("name");
            type = fms.getAttribute("type");
            String scod = fms.getAttribute("codegeneration");
            codegeneration = Boolean.valueOf(scod);
            System.out.println("\t\tNew FSM id:"+id+" | name:" + name + " | Type:" + type + " | codegeneration:" + codegeneration);
            NodeList stateList = fms.getElementsByTagName(METASTATE);
            for (int temp = 0; temp < stateList.getLength(); temp++) {
                Node nNode = stateList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element state = (Element) nNode;
                    State atemp = new State(state);
                    if (states==null)
                        states = new ArrayList<State>();
                    states.add(atemp);
                }
            }
            NodeList transitionList = fms.getElementsByTagName(TRANSITION);
            for (int temp = 0; temp < transitionList.getLength(); temp++) {
                Node nNode = transitionList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element state = (Element) nNode;
                    Transition atemp = new Transition(state);
                    if (transitions==null)
                        transitions = new ArrayList<Transition>();
                    transitions.add(atemp);
                }
            }
        }
        
        public String getName(){
        	return name;
        }
        
        public boolean getCodeGeneration(){
        	return codegeneration;
        }
        
        public ArrayList<Transition> getTransitions(){
        	return transitions;
        }

        public Transition getTransition(int i){
        	return transitions.get(i);
        }
        
        
        public int getTransitionId(int i){
        	return transitions.get(i).getId();
        }
        
        public String getTransitionName(int i){
        	return transitions.get(i).getName();
        }
        
        public String getTransitionLegacyName(int i){
        	return transitions.get(i).getLegacyName();
        }
        
        // Get amount of states
        public int getStateAmount(){
        	return states.size();
        }
        
        // Get source state id of a given transition
        public int getTransitionSourceStateId(int i){
        	return transitions.get(i).getSourceStateId();
        }
        
        // Get target state id of a given transition
        public int getTransitionTargetStateId(int i){
        	return transitions.get(i).getTargetStateId();
        }
        
        // Get the ID of a given state
        public int getStateId(int i){
        	return states.get(i).getId();
        }

        // Get the ID of a given state
        public ArrayList<Integer> getStateIds(){
            ArrayList<Integer> stateIds = new ArrayList<Integer>();
            for (State s : states) {
                stateIds.add(s.getId());
            }
        	return stateIds;
        }
        
        // Get the type of a given state
        public String getStateType(int i){
        	return states.get(i).getType();
        }
        
        // Get name of a given state
        public String getStateName(int i){
        	return states.get(i).getName();
        }
        
       
        // Return the initial state ID
        public int getInitialStateId(){
        	int id = -1;
        	for(int i=0; i<states.size(); i++){
        		if("INITIAL_STATE".equals(getStateType(i))){		
        			id = getStateId(i);
        			break;
        		}
        	}
        	return id;
        }

        // Ask if FSM was generated
        public boolean isCodeGenerated(){
        	return codegeneration;
        }
    
        // Get amount of transitions
        public int getTransitionAmount(){
        	return transitions.size();
        }
    
        // Get amount of methods of a transition
        public int getMethodAmount(int i){
        	return transitions.get(i).getMethodAmount();
        }
        
        // Get name of a method, given a transition and method
        public String getMethodName(int i, int j){
        	return transitions.get(i).getMethodName(j);
        }
        
        // Get id of a method, given a transition and method
        public int getMethodId(int i, int j){
        	return transitions.get(i).getMethodId(j);
        }

         // Get method ids, given a transition
         public ArrayList<Integer> getMethodIds(int i){
        	return transitions.get(i).getMethodIds();
        }
    }
}
