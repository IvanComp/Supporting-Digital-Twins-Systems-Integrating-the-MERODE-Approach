package testing;

import java.util.*;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;
import java.lang.reflect.Array; 
import javafx.util.Pair;

/**
 * Created by Arturo Mantinetti on 1-05-2017.
 * Edited by Sofia Alarcon
 */
// Objetos -> Atributos
public class Model {
    public ArrayList<ObjectM> modelObjects = null;
    public ArrayList<Method>  modelMethods = null;

    public Model() {}

    public void insertObject(ObjectM objectM){ // used
        if (modelObjects==null)
            modelObjects = new ArrayList<ObjectM>();
        modelObjects.add(objectM);
    }
    
    public void insertMethod(Method method){ // used
    	if(modelMethods == null){
    		modelMethods = new ArrayList<Method>();
    	}
    	modelMethods.add(method);
    }

    // Returns attributes of a given object
    public ArrayList<String> getObjectAttributes(int i){
		ArrayList<String> attributeNames = new ArrayList<String>();
    	for (int j = 0; j < modelObjects.get(i).getAttributesSize(); j++){
			String attributeName = modelObjects.get(i).getAttributeName(j);
			attributeNames.add(attributeName);
		}
		return attributeNames;
    }

	// Returns dependents of a given object
    public ArrayList<Pair<String, String>> getDependents(int i){
    	return modelObjects.get(i).getDependents();
    }

	// Returns children of a given object
    public ArrayList<String> getChildren(int i){
    	return modelObjects.get(i).getChildren();
    }

	// Returns parents of a given object
    public ArrayList<Integer> getParents(int i){		  
		ObjectM objM = null;
		for (ObjectM currObj : modelObjects) {
			if (currObj.getId() == i) {
				objM = currObj;
			}
		}
		ArrayList<Integer> parents = new ArrayList<Integer>();
		for (ObjectM currObj : modelObjects) {
			if (currObj.getChildren().contains(String.valueOf(objM.getId()))) {
				parents.add(currObj.getId());
			}
		}
    	return parents;
    }
	
    // Returns amount of classes in the model
    public int getSize(){ // used
    	return modelObjects.size();
    }
    
    // Return id of a given object
    public int getObjectId(int i){
    	return modelObjects.get(i).getId();
    }
    
	// Return id of a given object by the name of the object type
    public int getObjectId(String name){
		for (ObjectM object : modelObjects){
			if (object.getName().equals(name)){
				return object.getId();
			}
		}
    	return -1;
    }

    // Return name of a given object
    public String getObjectName(int i){ // used
    	return modelObjects.get(i).getName();
    }

	// Return name of a given object
    public String getObjectNameById(int id){ // used
		for (ObjectM object : modelObjects){
			if (object.getId() == id){
				return object.getName();
			}
		}
    	return null;
    }
    
    // Get amount of available FSMs for a given object
    public int getFsmAmount(int i){
    	return modelObjects.get(i).getFsmSize();
    }

    // Get name of a fsm, given an object and fsm
    public String getFsmName(int i, int j){
    	return modelObjects.get(i).getFsmName(j);
    }
    
    // Ask if a FSM is code generated, given an object and fsm
    public boolean isCodeGenerated(int i, int j){
    	return modelObjects.get(i).isCodeGenerated(j);
    }

    // Get list of state names of a generated fsm and given object
    public ArrayList<String> getStateNameList(String objectName){
    	
    	ArrayList<String> names = new ArrayList<String>();
    	
    	for(int i=0; i < getSize(); i++){
    		
    		
    		
    		if(getObjectName(i).equals(objectName)){
    			
    			
    			
    			for(int j=0; j<getFsmAmount(i); j++){
    				
    				
    				
    				if(isCodeGenerated(i,j)){
    					for(int k=0; k<getStateAmount(i,j); k++){
    						
    						names.add(getStateName(i,j,k));
    					}
    				}
    			}
    		}
    	}
    	return names;
    }
    
	
    // Get id of class name
    public int getClassId(String objectName){
    	for(int i=0; i < getSize(); i++){
    		if(getObjectName(i).equals(objectName)){
    			return getObjectId(i);
    		}
    	}
    	return -1;
    }

    // Get list of transition ids of a generated fsm and given object
    public ArrayList<String> getTransitionIdList(String objectName){
    	ArrayList<String> ids = new ArrayList<String>();
    	for(int i=0; i < getSize(); i++){
    		if(getObjectName(i).equals(objectName)){
    			for(int j=0; j<getFsmAmount(i); j++){
    				if(isCodeGenerated(i,j)){
    					for(int k=0; k<getTransitionNumber(i,j); k++){
    						int a = getTransitionId(i,j,k);
    						String b = String.valueOf(a);
    						ids.add(b);
    					}
    				}
    			}
    		}
    	}
    	return ids;
    }
    
    // Get initial state id for a given object and fsm
    public int getInitialStateId(int i, int j){
    	return modelObjects.get(i).getInitialStateId(j);
    }

	 // Get ending state ids for a given object and fsm
	 public ArrayList<Integer> getEndingStatesIds(int i, int j){
		ArrayList<Integer> endingStates = new ArrayList<Integer>();
		for (int k = 0; k < modelObjects.get(i).getFsm(j).getStateAmount(); k++){
			if (modelObjects.get(i).getFsm(j).getStateType(k).equals("FINAL_STATE")){
				endingStates.add(getStateId(i, j, k));
			}
		}
    	return endingStates;
    }
    
    // Get state id, given an object, fsm and state
    public int getStateId(int i, int j, int k){
    	return modelObjects.get(i).getStateId(j, k);
    }

	// Get state ids, given an object, fsm
    public ArrayList<Integer> getStateIds(int i, int j){
    	return modelObjects.get(i).getStateIds(j);
    }
    
    // Get the state name given object, fsm and state
    public String getStateName(int i, int j, int k){
    	return modelObjects.get(i).getStateName(j, k);
    }
       
    // Get state name, given an id
    public String getStateNameById(int id){
    	for(int obj = 0; obj < getSize(); obj ++){
    		for(int fsm = 0; fsm < getFsmAmount(obj); fsm++){
    			for(int sta=0; sta < getStateAmount(obj,fsm) ; sta++ ){
    				if(id == modelObjects.get(obj).getStateId(fsm, sta)){
    					return modelObjects.get(obj).getStateName(fsm, sta);
    				}
    			}
    		}
    	}
    	return null;
    }
    
    // Get amount of states of a given object and fsm
    public int getStateAmount(int i, int j){
    	return modelObjects.get(i).getStateAmount(j);
    }
    
    
    // Get amount of transitions for a given object and fsm
    public int getTransitionNumber(int i, int j){
    	return modelObjects.get(i).getTransitionAmount(j);
    }
    
    // Get transition id, given object, fsm and transition
    public int getTransitionId(int i, int j, int k){
    	return modelObjects.get(i).getTransitionId(j, k);
    }
    
    
    // Retorna la transicion a la que pertenece un metodo, pero que tambien se cumpla que viaje entre esos dos estados. Hago esto porque un metodo puede coincidir con mas de una transicion.
    public int getTransitionIdByMethodId(int id, String srcState, String trgState){
    	// Search the model for the method that has the id.
    	for(int obj = 0; obj < getSize(); obj ++){
    		for(int fsm = 0; fsm < getFsmAmount(obj); fsm++){
    			if(isCodeGenerated(obj,fsm)){
    				for(int tran = 0; tran < getTransitionNumber(obj,fsm); tran++){
    					
    					int srcStateId = getTransitionSourceStateId(obj,fsm,tran);
    					int trgStateId = getTransitionTargetStateId(obj,fsm,tran);

    					String srcStateName = getStateNameById(srcStateId);
    					String trgStateName = getStateNameById(trgStateId);
						
    					if(srcState.equals(srcStateName) && trgState.equals(trgStateName)){
    						
    						for(int met=0; met < getMethodAmount(obj,fsm,tran); met++){
								
    							if(id == getMethodId(obj,fsm,tran,met)){
    								return getTransitionId(obj,fsm,tran);
    							}
    						}	
    					}
    				}
    			}
    		}
    	}
    return -1;
    }

	public ArrayList<Integer> getMethodIdsByTransition(int srcID, int trgID){
		ArrayList<Integer> methodIDs = new ArrayList<Integer>();
    	for(int obj = 0; obj < getSize(); obj ++){
    		for(int fsm = 0; fsm < getFsmAmount(obj); fsm++){
    			if(isCodeGenerated(obj,fsm)){
    				for(int tran = 0; tran < getTransitionNumber(obj,fsm); tran++){
						if (getTransitionSourceStateId(obj,fsm,tran) == srcID && getTransitionTargetStateId(obj,fsm,tran) == trgID){
							for(int met=0; met < getMethodAmount(obj,fsm,tran); met++){
								int methodID = modelObjects.get(obj).getFsm(fsm).getTransition(tran).getMethodId(met);
								methodIDs.add(methodID);
							}
						}
					}
				}
			}
		}
		return methodIDs;
	} 
	
    
    // Get transition name, given object, fsm and transition
    public String getTransitionName(int i, int j, int k){
    	return modelObjects.get(i).getTransitionName(j, k);
    }
    
    public int getTransitionSourceStateId(int obj, int fsm, int tran){
    	return modelObjects.get(obj).getTransitionSourceStateId(fsm, tran);
    }
    
    public int getTransitionTargetStateId(int obj, int fsm, int tran){
    	return modelObjects.get(obj).getTransitionTargetStateId(fsm, tran);
    }
    
    // Get transition legacy name, given object, fsm and transition
    public String getTransitionLegacyName(int obj, int fsm, int tran){
    	return modelObjects.get(obj).getTransitionLegacyName(fsm, tran);
    }
    
    // Get method amount, given an object, fsm and transition
    public int getMethodAmount(int obj, int fsm, int tran){
    	return modelObjects.get(obj).getMethodAmount(fsm, tran);
    }
    
    // Get method name, given an object, fsm, transition and method
    public String getMethodName(int i, int j, int k, int l){
    	return modelObjects.get(i).getMethodName(j, k, l);
    }

    // Get method id, given an object, fsm, transition and method
    public int getMethodId(int i, int j, int k, int l){
    	return modelObjects.get(i).getMethodId(j, k, l);
    }

	 // Get method id, given an object, fsm, transition
	public ArrayList<Integer> getMethodIds(int i, int j, int k){
    	return modelObjects.get(i).getMethodIds(j, k);
    }
    
    // Return the name of a method given its id
    public String getMethodNameById(int id){
		for (int i = 0; i < getMetaMethodAmount(); i++){
			if (getMetaMethodId(i) == id){
				return getMetaMethodName(i);
			}
		}
    return null;
    }

	// Return all methods ids of a given object
    public ArrayList<Integer> getAllMethodIdsByObjectId(int objId){
    	ArrayList<Integer> objectIDs = new ArrayList<Integer>();
    	for(int obj = 0; obj < getSize(); obj ++){
			if (objId == getObjectId(obj)){
				for(int fsm = 0; fsm < getFsmAmount(obj); fsm++){
					for(int tran = 0; tran < getTransitionNumber(obj,fsm); tran++){
						for(int met=0; met < getMethodAmount(obj,fsm,tran); met++){
							objectIDs.add(getMethodId(obj,fsm,tran,met));
						}
					}
				}
			}
		}
	    return objectIDs;
    }


	// Return the names of a method given its id
    public ArrayList<String> getMethodNamesById(int id){
    	ArrayList<String> methodNames = new ArrayList<String>();
    	// Search the model for the method that has the id.
    	for(int obj = 0; obj < getSize(); obj ++){
    		for(int fsm = 0; fsm < getFsmAmount(obj); fsm++){
    			for(int tran = 0; tran < getTransitionNumber(obj,fsm); tran++){
    				for(int met=0; met < getMethodAmount(obj,fsm,tran); met++){
    					if(id == getMethodId(obj,fsm,tran,met)){
							methodNames.add(getMethodName(obj,fsm,tran,met).toLowerCase());
    						//return getMethodName(obj,fsm,tran,met);
    					}
    				}
    			}
    		}
    	}
    return methodNames;
    }

	// Return all object names given a method id
    public ArrayList<String> getObjectNamesByMethodId(String strId){
    	
    	// Express the string id as an integer.
    	int id = Integer.parseInt(strId);
    	ArrayList<String> objectNames = new ArrayList<String>();
    	// Search the model for the methods that have the id.
    	for(int obj = 0; obj < getSize(); obj ++){
    		for(int fsm = 0; fsm < getFsmAmount(obj); fsm++){
    			for(int tran = 0; tran < getTransitionNumber(obj,fsm); tran++){
    				for(int met=0; met < getMethodAmount(obj,fsm,tran); met++){
    					if(id == getMethodId(obj,fsm,tran,met)){
    						objectNames.add(getObjectName(obj).toLowerCase());
    					}
    				}
    			}
    		}
    	}
    return objectNames;
    }

    // Return object name given a method id
    public String getObjectNameByMethodId(String strId){
    	
    	// Express the string id as an integer.
    	int id = Integer.parseInt(strId);
    	
    	// Search the model for the method that has the id.
    	for(int obj = 0; obj < getSize(); obj ++){
    		for(int fsm = 0; fsm < getFsmAmount(obj); fsm++){
    			for(int tran = 0; tran < getTransitionNumber(obj,fsm); tran++){
    				for(int met=0; met < getMethodAmount(obj,fsm,tran); met++){
    					if(id == getMethodId(obj,fsm,tran,met)){
    						return getObjectName(obj);
    					}
    				}
    			}
    		}
    	}
    return null;
    }
    

    // Check if a method belongs to an object
    public boolean methodBelongsToClass(String method, String object){    	
    	String o = getObjectNameByMethodId(method);
    	if(o.equals(object)){
    		return true;
    	}
    	else{
    		return false;
    	}
    	
    }
    // Return all source state name given method id
    public ArrayList<String> getAllSourceStateNameByMethodId(int id){

    	ArrayList<String> sourceStateNames = new ArrayList<String>();
    	// Search the model for the method that has the id.
    	for(int obj = 0; obj < getSize(); obj ++){
    		
    		for(int fsm = 0; fsm < getFsmAmount(obj); fsm++){
    			
    			if(isCodeGenerated(obj,fsm)){
    			
    			
    				for(int tran = 0; tran < getTransitionNumber(obj,fsm); tran++){
    					for(int met=0; met < getMethodAmount(obj,fsm,tran); met++){
    						if(id == getMethodId(obj,fsm,tran,met)){
    						
    							
    							int sourceStateId =  getTransitionSourceStateId(obj, fsm, tran);
								sourceStateNames.add(getStateNameById(sourceStateId));
    							//return getStateNameById(sourceStateId);
    							
    						}
    					}
    				}
    			}
    		}
    	}
    return sourceStateNames;
    }
    // Return source state name given method id
    public String getSourceStateNameByMethodId(int id){

    	
    	// Search the model for the method that has the id.
    	for(int obj = 0; obj < getSize(); obj ++){
    		
    		for(int fsm = 0; fsm < getFsmAmount(obj); fsm++){
    			
    			if(isCodeGenerated(obj,fsm)){
    			
    			
    				for(int tran = 0; tran < getTransitionNumber(obj,fsm); tran++){
    					for(int met=0; met < getMethodAmount(obj,fsm,tran); met++){
    						if(id == getMethodId(obj,fsm,tran,met)){
    						
    							
    							int sourceStateId =  getTransitionSourceStateId(obj, fsm, tran);
    						
    							return getStateNameById(sourceStateId);
    							
    						}
    					}
    				}
    			}
    		}
    	}
    return null;
    }

    // Return target state name given method id
    public String getTargetStateNameByMethodId(String strId){
    	// Express the string id as an integer.
    	int id = Integer.parseInt(strId);
    	
    	// Search the model for the method that has the id.
    	for(int obj = 0; obj < getSize(); obj ++){
    		for(int fsm = 0; fsm < getFsmAmount(obj); fsm++){
    			if(isCodeGenerated(obj,fsm)){
    				for(int tran = 0; tran < getTransitionNumber(obj,fsm); tran++){
    					for(int met=0; met < getMethodAmount(obj,fsm,tran); met++){
    						if(id == getMethodId(obj,fsm,tran,met)){
    						
    							int targetStateId =  getTransitionTargetStateId(obj, fsm, tran);
    						
    							return getStateNameById(targetStateId);
    						
    						}
    					}
    				}
    			}
    		}
    	}
    return null;
    }

	// Return target state name given method id
    public ArrayList<String> getAllTargetStateNameByMethodId(String strId){
    	// Express the string id as an integer.
    	int id = Integer.parseInt(strId);
		ArrayList<String> allTargetStateNames = new ArrayList<String>();
    	// Search the model for the method that has the id.
    	for(int obj = 0; obj < getSize(); obj ++){
    		for(int fsm = 0; fsm < getFsmAmount(obj); fsm++){
    			if(isCodeGenerated(obj,fsm)){
    				for(int tran = 0; tran < getTransitionNumber(obj,fsm); tran++){
    					for(int met=0; met < getMethodAmount(obj,fsm,tran); met++){
    						if(id == getMethodId(obj,fsm,tran,met)){
    						
    							int targetStateId =  getTransitionTargetStateId(obj, fsm, tran);
								allTargetStateNames.add(getStateNameById(targetStateId));
    							//return getStateNameById(targetStateId);
    						
    						}
    					}
    				}
    			}
    		}
    	}
    return allTargetStateNames;
    }

	/**
	 * Get list of all loop-free-paths between a src and dst state given the list of all transitions.
	 */
	public ArrayList<ArrayList<Integer>> generateLoopFreePaths(ArrayList<ArrayList<Integer>> transitions, int src,  int dst){
        ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
        
        ArrayList<Integer> startTransition = transitions.get(0);

        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(src);
        
        Queue<ArrayList<Integer>> queue = new LinkedList();
        queue.add(path);
        while (!queue.isEmpty()){
            path = queue.poll();
            int last = path.get(path.size() - 1);
            if (last == dst){
                Set<Integer> set = new HashSet<Integer>(path);
                if (set.size() == path.size() && !paths.contains(path) && path.size() > 1){
                    paths.add(path);
                }
            }
            for (ArrayList<Integer> transition : transitions){
                
                if (transition.get(0) == last){
					if (!path.contains(transition.get(1))){
                        ArrayList<Integer> newPath = new ArrayList<Integer>(path);
                        newPath.add(transition.get(1));

                        queue.add(newPath);
                    }
                }
            }
        }
        return paths;
    }

	//Get all loop free paths for object type i, fsm j
	public ArrayList<ArrayList<Integer>> getAllLoopFreePaths(int i, int j){
		ArrayList<ArrayList<Integer>> transitions = new ArrayList<ArrayList<Integer>>();
		HashMap<ArrayList<Integer>, Integer> transitionIDs = new HashMap<ArrayList<Integer>, Integer>();
		int initialState = this.getInitialStateId(i, j);
		ArrayList<Integer> endStates = this.getEndingStatesIds(i, j);

		// For each transition
		for (int k = 0; k < this.getTransitionNumber(i,j); k++) {
			int srcStateID = this.getTransitionSourceStateId(i, j, k);
			int trgStateID = this.getTransitionTargetStateId(i, j, k);
			ArrayList<Integer> transition = new ArrayList<Integer>();
			transition.add(srcStateID);
			transition.add(trgStateID);
			transitions.add(transition);
		}
			
		ArrayList<ArrayList<Integer>> allPaths = new ArrayList<ArrayList<Integer>>();
			
		//Find all paths between each pair of start and end nodes
		for (int endState : endStates){
			ArrayList<ArrayList<Integer>> newPath = generateLoopFreePaths(transitions, initialState, endState);
			allPaths.addAll(newPath);
		}
		return allPaths;
	}
    
	public ArrayList<ArrayList<Integer>> getAllOneLoopPaths(int i, int j) {
		ArrayList<ArrayList<Integer>> transitions = new ArrayList<ArrayList<Integer>>();
		HashMap<ArrayList<Integer>, Integer> transitionIDs = new HashMap<ArrayList<Integer>, Integer>();
		int initialState = this.getInitialStateId(i, j);
		ArrayList<Integer> endStates = this.getEndingStatesIds(i, j);

		for (int k = 0; k < this.getTransitionNumber(i,j); k++) {						
			int srcStateID = this.getTransitionSourceStateId(i, j, k);
			int trgStateID = this.getTransitionTargetStateId(i, j, k);
			ArrayList<Integer> transition = new ArrayList<Integer>();
			transition.add(srcStateID);
			transition.add(trgStateID);
			transitions.add(transition);
		}
			
		ArrayList<ArrayList<Integer>> allPaths = new ArrayList<ArrayList<Integer>>();

		//Find all one loop paths between each pair of start and end nodes
		for (int endState : endStates){
			ArrayList<ArrayList<Integer>> newPath = generateAllOneLoopPaths(transitions, initialState, endState);
			allPaths.addAll(newPath);						
		}
		return allPaths;
	}
    

	
     /**
	  * Return whether the given path contains a double loop, this means that a node in the path is visited at least 3 times.
	  * @param path
	  * @return
	  */
	  public boolean hasDoubleLoop(ArrayList<Integer> path){

		HashMap<Integer, Integer> counts = new HashMap<Integer,Integer>();

		for (int node : path){
			if (counts.containsKey(node) && counts.get(node) == 2){
				return true;
			} else if(counts.containsKey(node)) {
				counts.put(node, counts.get(node) + 1);
			} else {
				counts.put(node, 1);
			}			
		}
		return false;
	}

	/**
	 * Check whether the given path has exactly one loop. This is checked by converting the list of nodes to a set and checking that only one node was double in the list.
	 * @param path
	 * @return
	 */
	public boolean hasExactlyOneLoop(ArrayList<Integer> path){
		HashSet<Integer> nodesSet = new HashSet<Integer>(path);;
		return nodesSet.size() + 1 == path.size();
	}

	/**
	 * Get a list of all one-loop-paths between the src and dst state given the transitions of the FSM
	 * @param transitions
	 * @param src
	 * @param dst
	 * @return
	 */
	public ArrayList<ArrayList<Integer>> generateAllOneLoopPaths(ArrayList<ArrayList<Integer>> transitions, int src,  int dst){
        ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
        
        ArrayList<Integer> startTransition = transitions.get(0);

        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(src);
        
        Queue<ArrayList<Integer>> queue = new LinkedList();
        queue.add(path);
        while (!queue.isEmpty()){
            path = queue.poll();
            int last = path.get(path.size() - 1);
            if (last == dst){
				if(!paths.contains(path) && path.size() > 1){
                    paths.add(path);
                }
            }
            for (ArrayList<Integer> transition : transitions){
                
                if (transition.get(0) == last){

					if(!this.hasDoubleLoop(path)){
                        ArrayList<Integer> newPath = new ArrayList<Integer>(path);
                        newPath.add(transition.get(1));

                        queue.add(newPath);
                    }
                }
            }
        }
		ArrayList<ArrayList<Integer>> finalPaths = new ArrayList<ArrayList<Integer>>();

		for (ArrayList<Integer> potentialPath : paths){
			if (this.hasExactlyOneLoop(potentialPath)){
				finalPaths.add(potentialPath);

			}
		}
        return finalPaths;
    }

	public ArrayList<ArrayList<Integer>> getAllLoops(int i, int j){
		ArrayList<Integer> states = this.getStateIds(i, j);
		ArrayList<ArrayList<Integer>> transitions = new ArrayList<ArrayList<Integer>>();

		// For each transition
		for (int k = 0; k < this.getTransitionNumber(i,j); k++) {
						
			int srcStateID = this.getTransitionSourceStateId(i, j, k);
			int trgStateID = this.getTransitionTargetStateId(i, j, k);

			ArrayList<Integer> transition = new ArrayList<Integer>();
			transition.add(srcStateID);
			transition.add(trgStateID);
			transitions.add(transition);
		}

		ArrayList<ArrayList<Integer>> allPaths = new ArrayList<ArrayList<Integer>>();
		 
		for (int state : states){
			ArrayList<ArrayList<Integer>> newPaths = this.generateAllOneLoopPaths(transitions, state, state);

			for (ArrayList<Integer> path1 : newPaths){
				boolean alreadyThere = false;
				for (ArrayList<Integer> path2 : allPaths){
					HashSet<Integer> set1 = new HashSet<Integer>(path1);
					HashSet<Integer> set2 = new HashSet<Integer>(path2);
					if(set1.equals(set2)){
						alreadyThere = true;
					}
				}
				if(!alreadyThere){
					allPaths.add(path1);						
				}
			}
		}
		return allPaths;
	}
    
    public int getMethodId(int eventID) {
		for (Method method : modelMethods) {
			if (method.ownereventid == eventID) {
				return method.id;
			}
		}
		return 0;
	}

    
    // <mxp:metamethod> functions:
    
    // Get id of a meta method
    public int getMetaMethodId(int i){
    	return modelMethods.get(i).getId();
    }

	// Get amount of meta methods
    public int getMetaMethodAmount(){
    	return modelMethods.size();
    }

	 // Get name a meta method
	 public String getMetaMethodName(int i){
    	return modelMethods.get(i).getName();
    }
    
    // Check if a method is of provenance OWNED, given its id
    public boolean isMethodOwned(int id){
    	
    	boolean isOwned = false;
    	
    	for(int i=0; i < modelMethods.size(); i++){
    		
    		if(getMetaMethodId(i) == id){
    			if(modelMethods.get(i).isOwned()){
    				isOwned = true; 
    			}
    		}	
    	}
    	return isOwned;
    }    

}
