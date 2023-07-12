package tescav;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;
import java.io.FileWriter;   
import javafx.util.Pair;

import javax.print.attribute.HashAttributeSet;

import testing.Model;

import dao.MerodeLogger;


/**
 * 
 * @author Sofia Alarcon
 *
 */

public class MainTescav {
	private final String FILENAME = "eventLog.csv";
	private final String FILENAME2 = "session.tmp";
    private Model model; 
	private Coverage coverage = new Coverage();
	
	public MainTescav(Model modelRcv){
		model = modelRcv;
	}

	public Coverage getCoverage() {
		return this.coverage;
	}
	
	public void classAttribute(){
		Criterion criterion = new Criterion(Consts.CA, Consts.EDG);
		// For each object
		for (int i = 0; i < model.getSize(); i++) {	
			int objectTypeId = model.getObjectId(i);
			String objectTypeName = model.getObjectName(i);
			ArrayList<String> attributes = model.getObjectAttributes(i);
			for (String attribute : attributes){
				String type = Consts.ATTRIBUTE;
				TestCase testCase = new TestCase(objectTypeId, objectTypeName);
				Element element = new Element(type);
				element.setAttribute(attribute);
				element.setElementName(objectTypeName);
				testCase.addElement(element);
				criterion.addTestCase(testCase);
			}
		}
		coverage.addCriterion(criterion);
	}

	public void allTransitions(){
		Criterion criterion = new Criterion(Consts.AT, Consts.FSM);
		
		// For each object
		for (int i = 0; i < model.getSize(); i++) {	
			
			int objecTypeId = model.getObjectId(i);
			String objectTypeName = model.getObjectName(i);
			// For each FSM
			for (int j = 0; j < model.getFsmAmount(i); j++) {
	
				// Consider only the FSM that got generated
				if(model.isCodeGenerated(i, j)){	

					// For each transition
					for (int k = 0; k < model.getTransitionNumber(i,j); k++) {
						TestCase testCase = new TestCase(objecTypeId, objectTypeName);
						String type = Consts.TRANSITION;
						Element element = new Element(type);
						int srcStateID = model.getTransitionSourceStateId(i, j, k);
						int trgStateID = model.getTransitionTargetStateId(i, j, k);
						element.addStateId(srcStateID);
						element.addStateId(trgStateID);	
						element.addMethodIds(model.getMethodIds(i,j,k));

						testCase.addElement(element);
						criterion.addTestCase(testCase);

					}
				}
			}
		}
		coverage.addCriterion(criterion);
	}

	


	public void allLoopFreePaths(){
		Criterion criterion = new Criterion(Consts.ALFP, Consts.FSM);
		// For each object
		for (int i = 0; i < model.getSize(); i++) {	
			
			int objecTypeId = model.getObjectId(i);
			String objectTypeName = model.getObjectName(i);
			// For each FSM
			for (int j = 0; j < model.getFsmAmount(i); j++) {
	
				// Consider only the FSM that got generated
				if(model.isCodeGenerated(i, j)){

					ArrayList<ArrayList<Integer>> paths = model.getAllLoopFreePaths(i, j);
					//For each found path
					for (int m = 0; m < paths.size(); m++){

						ArrayList<Integer> path = paths.get(m);
						String type = Consts.TRANSITION;
						TestCase testCase = new TestCase(objecTypeId, objectTypeName);	

						//For each node in the path
						for (int n = 0; n < path.size() - 1; n++){					
							int srcID = path.get(n);
							int trgID = path.get(n+1);
							Element element = new Element(type);
							element.addMethodIds(model.getMethodIdsByTransition(srcID, trgID));
							element.addStateId(srcID);
							element.addStateId(trgID);
							testCase.addElement(element);
						}
						criterion.addTestCase(testCase);
					}
				}
			}
		}
		coverage.addCriterion(criterion);
	}

	public void allOneLoopPaths(){
		
		Criterion criterion = new Criterion(Consts.AOLP, Consts.FSM);

		// For each object
		for (int i = 0; i < model.getSize(); i++) {	
			
			int objecTypeId = model.getObjectId(i);
			String objectTypeName = model.getObjectName(i);
			// For each FSM
			for (int j = 0; j < model.getFsmAmount(i); j++) {

				// Consider only the FSM that got generated
				if(model.isCodeGenerated(i, j)){
									
					ArrayList<ArrayList<Integer>> allPaths = model.getAllOneLoopPaths(i, j);

					//For each found path
					for (int m = 0; m < allPaths.size(); m++){

						ArrayList<Integer> path = allPaths.get(m);
						String type = Consts.TRANSITION;
						TestCase testCase = new TestCase(objecTypeId, objectTypeName);	

						//For each node in the path
						for (int n = 0; n < path.size() - 1; n++){					
							int srcID = path.get(n);
							int trgID = path.get(n+1);

							Element element = new Element(type);
							element.addMethodIds(model.getMethodIdsByTransition(srcID, trgID));
							element.addStateId(srcID);
							element.addStateId(trgID);
							testCase.addElement(element);
						}
						criterion.addTestCase(testCase);			
					}
				}
			}
		}
		
		coverage.addCriterion(criterion);
	}

	public void allStates(){
		Criterion criterion = new Criterion(Consts.AS, Consts.FSM);
		
		// For each object
		for(int i=0; i < model.getSize(); i++){
			
			int objecTypeId = model.getObjectId(i);
			String objectTypeName = model.getObjectName(i);
			// For each fsm
			for(int j=0; j < model.getFsmAmount(i); j++){
				// Consider only generated fsms
				if(model.isCodeGenerated(i, j)){
					for(int k=0; k < model.getStateAmount(i,j); k++){						
						TestCase testCase = new TestCase(objecTypeId, objectTypeName);

						String type = Consts.STATE;
						String elementName = model.getStateName(i,j,k);
						
						Element element = new Element(type);
						int stateId = model.getStateId(i,j,k);
						String stateName = model.getStateName(i, j, k);
						element.addStateId(stateId);
						element.setElementName(stateName);

						testCase.addElement(element);
						criterion.addTestCase(testCase);
					}
				}
			}
		}
		coverage.addCriterion(criterion);
	}
	
	public void associationsEndMultiplicity(){
		Criterion criterion = new Criterion(Consts.AEM, Consts.EDG);
		
		// For each object
		for(int i=0; i < model.getSize(); i++){
							
			String type = Consts.CLASS;
			int objecTypeId = model.getObjectId(i);
			
			ArrayList<Pair<String, String>> dependents = model.getDependents(i); 
			String objectTypeName = model.getObjectName(i);
			for(Pair<String, String> dependent : dependents){
				TestCase testCase = new TestCase(objecTypeId, objectTypeName);				
				Element element = new Element(type);
				element.setElementName(dependent.getKey());
				element.setDependent(dependent);
				testCase.addElement(element);
				criterion.addTestCase(testCase);
			}

		}
		coverage.addCriterion(criterion);
	}
	
	/*
	 * Generalization criterion:
	 * Check for each class that all child classes get instantiated.
	 */
	public void generalization(){
		Criterion criterion = new Criterion(Consts.GEN, Consts.EDG);
		// For each object
		for (int i = 0; i < model.getSize(); i++) {	
			int objecTypeId = model.getObjectId(i);
			String objectTypeName = model.getObjectName(i);

			ArrayList<String> children = model.getChildren(i);
			for (String childId : children){
				int id = Integer.parseInt(childId);
				String objectName = model.getObjectNameById(id);
				TestCase testCase = new TestCase(objecTypeId, objectTypeName);

				String type = Consts.CLASS;
				Element element = new Element(type);
				Pair<String, String> dependent = new Pair<String, String>(objectName, "CHILD");
				//element.setObjectId(id);
				element.setElementName(objectName);
				element.setDependent(dependent);

				testCase.addElement(element);
				criterion.addTestCase(testCase);

			}
		}
		coverage.addCriterion(criterion);
	}

	/*
	 * All transition pairs criterion:
	 * Check for each pair of incoming and outgoing transitions whether they have been executed.
	 */
	public void allTransitionPairs(){

		Criterion criterion = new Criterion(Consts.TP, Consts.FSM);
		// For each object
		for (int i = 0; i < model.getSize(); i++) {	
			int objecTypeId = model.getObjectId(i);
			String objectTypeName = model.getObjectName(i);
			// For each FSM
			for (int j = 0; j < model.getFsmAmount(i); j++) {
				
				// Consider only the FSM that got generated
				if(model.isCodeGenerated(i, j)){
					
					ArrayList<Pair<Integer, Integer>> transitions = new ArrayList<Pair<Integer, Integer>>();
					HashMap<Pair<Integer, Integer>, Integer> transitionIDs = new HashMap<Pair<Integer, Integer>, Integer>();
					Set<Integer> states = new HashSet<Integer>();

					// For each transition
					for (int k = 0; k < model.getTransitionNumber(i,j); k++) {
						
						int srcStateID = model.getTransitionSourceStateId(i, j, k);
						int trgStateID = model.getTransitionTargetStateId(i, j, k);

						Pair<Integer, Integer> transition = new Pair<Integer, Integer>(srcStateID, trgStateID);
						transitions.add(transition);
						transitionIDs.put(transition, k);
						states.add(srcStateID);
						states.add(trgStateID);

					}

					for (int state : states) {
						
						ArrayList<Pair<Integer, Integer>> incomingTransitions = new ArrayList<Pair<Integer, Integer>>();
						ArrayList<Pair<Integer, Integer>> outgoingTransitions = new ArrayList<Pair<Integer, Integer>>();

						//Find all incoming and outgoing transitions for this state
						for (Pair<Integer, Integer> transition : transitions){
							
							//Do not consider loops
							if (transition.getKey() != transition.getValue()){
								if (transition.getKey() == state){
									outgoingTransitions.add(transition);
								} else if (transition.getValue() == state){
									incomingTransitions.add(transition);
								}
							}
						}
						for (Pair<Integer, Integer> incomingTransition : incomingTransitions){
							for (Pair<Integer, Integer> outgoingTransition : outgoingTransitions){
								int incomingTransitionIndex = -1;
								for (int s = 0; s < model.getStateAmount(i, j); s++){
									if (model.getStateId(i, j, s) == incomingTransition.getKey()){
										incomingTransitionIndex = s;
									}
								}

								int outgoingTransitionIndex = -1;
								for (int s = 0; s < model.getStateAmount(i, j); s++){
									if (model.getStateId(i, j, s) == outgoingTransition.getValue()){
										outgoingTransitionIndex = s;
									}
								}

								int stateIndex = -1;
								for (int s = 0; s < model.getStateAmount(i, j); s++){
									if (model.getStateId(i, j, s) == state){
										stateIndex = s;
									}
								}

								if (incomingTransitionIndex != -1 && stateIndex != -1 && outgoingTransitionIndex != -1 &&
									incomingTransitionIndex != stateIndex &&  stateIndex != outgoingTransitionIndex && incomingTransitionIndex != outgoingTransitionIndex){
									TestCase testCase = new TestCase(objecTypeId, objectTypeName);

									String type = Consts.TRANSITION;
									Element element1 = new Element(type);
									Element element2 = new Element(type);

									ArrayList<Integer> incomingMethodIDs = model.getMethodIdsByTransition(incomingTransition.getKey(), state);
									ArrayList<Integer> outgoingMethodIDs = model.getMethodIdsByTransition(state, outgoingTransition.getValue());
									element1.addMethodIds(incomingMethodIDs);
									element2.addMethodIds(outgoingMethodIDs);

									int incomingStateId = model.getStateId(i, j, incomingTransitionIndex);
									int stateNameId = model.getStateId(i, j, stateIndex);
									int outgoingStateId = model.getStateId(i, j, outgoingTransitionIndex);

									element1.addStateId(incomingStateId);
									element1.addStateId(stateNameId);
									element2.addStateId(stateNameId);
									element2.addStateId(outgoingStateId);

									testCase.addElement(element1);
									testCase.addElement(element2);
									criterion.addTestCase(testCase);

								}

							}
						}

					}
				}
			}
		}
		coverage.addCriterion(criterion);
	}

	public void allLoops(){
		
		Criterion criterion = new Criterion(Consts.AL, Consts.FSM);

		// For each object
		for (int i = 0; i < model.getSize(); i++) {	
			
			int objecTypeId = model.getObjectId(i);
			String objectTypeName = model.getObjectName(i);
			// For each FSM
			for (int j = 0; j < model.getFsmAmount(i); j++) {
	
				// Consider only the FSM that got generated
				if(model.isCodeGenerated(i, j)){

					ArrayList<ArrayList<Integer>> paths = model.getAllLoops(i, j);

					//For each found path
					for (int m = 0; m < paths.size(); m++){

						ArrayList<Integer> path = paths.get(m);
						TestCase testCase = new TestCase(objecTypeId, objectTypeName);	
						String type = Consts.TRANSITION;
						
						//For each node in the path
						for (int n = 0; n < path.size() - 1; n++){					
							int srcID = path.get(n);
							int trgID = path.get(n+1);
							Element element = new Element(type);
							element.addStateId(srcID);
							element.addStateId(trgID);
							element.addMethodIds(model.getMethodIdsByTransition(srcID, trgID));
							testCase.addElement(element);

						}	
						criterion.addTestCase(testCase);
					}


				}
			}
		}
		
		coverage.addCriterion(criterion);
	}

		
	/*
	 * All methods criterion:
	 * Check for each class that all allowed and disallowed transition methods have been tested.
	 */
	public void allMethods(){
		Criterion criterion = new Criterion(Consts.AM, Consts.TABLE);
		// For each object
		for (int i = 0; i < model.getSize(); i++) {	
			int objectTypeId = model.getObjectId(i);
			String objectTypeName = model.getObjectName(i);
			String type = Consts.METHOD;

			// For each FSM
			for (int j = 0; j < model.getFsmAmount(i); j++) {

				// Consider only the FSM that got generated
				if(model.isCodeGenerated(i, j)){

					for (int k = 0; k < model.getStateAmount(i,j); k++) {

						ArrayList<String> visitedMethods = new ArrayList<String>();

						int currentSrcStateId = model.getStateId(i, j, k);
						ArrayList<Integer> allowedMethodIDs = new ArrayList<Integer>();
						ArrayList<Integer> allMethodIDs = model.getAllMethodIdsByObjectId(model.getObjectId(i));

						// For each transition
						for (int l = 0; l < model.getTransitionNumber(i,j); l++) {
							int srcStateID = model.getTransitionSourceStateId(i, j, l);
							int trgStateID = model.getTransitionTargetStateId(i, j, l);
							if (srcStateID == currentSrcStateId && !model.getEndingStatesIds(i, j).contains(currentSrcStateId)){
								allowedMethodIDs.addAll(model.getMethodIdsByTransition(srcStateID, trgStateID));
							}
						}
						//Only if state is not an ending state
						if (!model.getEndingStatesIds(i, j).contains(currentSrcStateId)){
							Set<Integer> allMethodIDsSet = new HashSet<Integer>(allMethodIDs);
							Set<Integer> allowedMethodIDsSet = new HashSet<Integer>(allowedMethodIDs);

							for (int methodId : allowedMethodIDs){
								String methodName = model.getMethodNameById(methodId);

								TestCase testCase = new TestCase(objectTypeId, objectTypeName);

								Element element = new Element(type);
								element.addStateId(currentSrcStateId);
								element.addMethodId(methodId);
								element.setElementName(methodName);
								element.setObjectId(objectTypeId);
								element.setTestingType(Consts.POSITIVE);

								testCase.addElement(element);
								criterion.addTestCase(testCase);
							}
	
							allMethodIDsSet.removeAll(allowedMethodIDsSet);
	
							//The remaining method IDs are the disallowed methods
							for (int methodId : allMethodIDsSet){
								String methodName = model.getMethodNameById(methodId);
								TestCase testCase = new TestCase(objectTypeId, objectTypeName);

								Element element = new Element(type);
								element.addStateId(currentSrcStateId);
								element.addMethodId(methodId);
								element.setElementName(methodName);
								element.setObjectId(objectTypeId);
								element.setTestingType(Consts.NEGATIVE);

								testCase.addElement(element);
								criterion.addTestCase(testCase);
	
							}
						} 					
						
					}
				}			

			}
			
		}
		coverage.addCriterion(criterion);
	}

	private boolean equalPaths(ArrayList<Integer> path1, ArrayList<Integer> path2) {
		if (path1.size() != path2.size()) {
			return false;
		}
		for (int i = 0; i < path1.size(); i++) {
			if (path1.get(i) != path2.get(i)){
				return false;
			}
		}
		return true;
	}

	// Receives a student interaction and compares it to every test case
	public void checkCases(String crit, HashMap<String, ArrayList<Integer>> visitedPaths, HashMap<String, ArrayList<Integer>> associations, HashMap<Integer, ArrayList<String>> allAttributes,
	 HashMap<Integer, ArrayList<Pair<Integer, Integer>>> methods, HashMap<String, Integer> objectTypeIds, HashMap<Integer, ArrayList<Integer>> inheritance){
		for(int i=0; i < coverage.getCriteriaSize(); i++ ){	
			for(int j=0; j < coverage.getTestCasesAmount(i); j++){
				int objectTypeId = coverage.getObjectTypeId(i, j);
				for (int k=0; k < coverage.getElementsAmount(i, j); k++){
					if (coverage.getElementType(i, j, k).equals(Consts.ATTRIBUTE)){
						String attributeName = coverage.getAttribute(i,j,k);
						if (allAttributes.get(objectTypeId) != null && allAttributes.get(objectTypeId).contains(attributeName)){
							coverage.coverElement(i,j,k);
						}
					}
					if (coverage.getElementType(i, j, k).equals(Consts.TRANSITION)){
						int srcStateId = coverage.getStateIds(i,j,k).get(0);
						int trgStateId = coverage.getStateIds(i,j,k).get(1);
						for(String currObjectId : visitedPaths.keySet()){
							int currObjectTypeId = objectTypeIds.get(currObjectId);
							if (currObjectTypeId == objectTypeId) {
								for (int n = 0; n < visitedPaths.get(currObjectId).size() - 1; n++){
									if (visitedPaths.get(currObjectId).get(n) == srcStateId && visitedPaths.get(currObjectId).get(n+1) == trgStateId) {
										coverage.coverElement(i,j,k);
									}
								}
							}
						}
					}
					if (coverage.getElementType(i, j, k).equals(Consts.STATE)){
						int stateId = coverage.getStateIds(i,j,k).get(0);
						for(String currObjectId : visitedPaths.keySet()){
							int currObjectTypeId = objectTypeIds.get(currObjectId);
							if (currObjectTypeId == objectTypeId) {
								if (visitedPaths.get(currObjectId).contains(stateId)) {
									coverage.coverElement(i,j,k);
								}
							}
						}
					}
					if (coverage.getElementType(i, j, k).equals(Consts.CLASS)){
						int masterTypeId = coverage.getObjectTypeId(i, j);
						Pair<String, String> dependency = coverage.getDependent(i, j, k);
						int dependentTypeId = model.getObjectId(dependency.getKey());
						//For master-dependent
						for (String currMasterId : associations.keySet()){
							int currMasterTypeId = objectTypeIds.get(currMasterId);
							if (currMasterTypeId == masterTypeId && associations.get(currMasterId).contains(dependentTypeId)) {
								int count = 0;
								for (int currDepTypeId : associations.get(currMasterId)) {
									if (currDepTypeId == dependentTypeId) {
										count++;
									}
								}
								if (dependency.getValue().equals("OPTIONAL_N") ||
									dependency.getValue().equals("MANDATORY_N")) {
									if (count >= 2){
										coverage.coverElement(i,j,k);
									}
								}
								if (dependency.getValue().equals("OPTIONAL_1") ||
									dependency.getValue().equals("MANDATORY_1")) {
									if (count >= 1){
										coverage.coverElement(i,j,k);
									}
								}
							}
						}
						//For child-parent
						for (int parentId : inheritance.keySet()){
							if (parentId == masterTypeId && inheritance.get(parentId).contains(dependentTypeId)) {
								coverage.coverElement(i,j,k);
							}
						}
					}
					if (coverage.getElementType(i, j, k).equals(Consts.METHOD)){
						Integer methodId = coverage.getMethodId(i, j, k, 0);
						Integer stateId = coverage.getStateId(i, j, k, 0);
					
						if (methods.keySet().contains(objectTypeId)) {
							for (Pair<Integer, Integer> execution : methods.get(objectTypeId)){
								if (execution.getKey() == stateId && execution.getValue() == methodId){
									coverage.coverElement(i,j,k);
								}
							}
						}
					}
				}
			}
		}
	}

	
	public HashMap<String, Object> searchPatterns(String line){
		String[] partsArray = line.split(" ");
		ArrayList<String> parts = new ArrayList<String>(Arrays.asList(partsArray));
		HashMap<String, Object> result = new HashMap<String, Object>();

		//Add related to
		if(line.contains("Initializing") ){
			String objectTypeId = parts.get(parts.indexOf("type") + 1);
			ArrayList<String> attributesList = new ArrayList<String>();
			for (int i = parts.indexOf("attributes") + 2; i < parts.size(); i=i+2){
				String attribute = parts.get(i);
				attributesList.add(attribute.toLowerCase());
			}
			String attributes = String.join(",", attributesList);
			if (parts.indexOf("to") > 0) {
				String masterId = parts.get(parts.indexOf("to") + 1);
				result.put("masterId", masterId);
			}
			result.put("type", "CREATE");
			result.put("objectTypeId", Integer.parseInt(objectTypeId));
			result.put("attributes", attributes);

			return result;
		}		

		if(line.contains("Object") ){
			String objectId = parts.get(parts.indexOf("Object") + 1);
			String objectTypeId = parts.get(parts.indexOf("type") + 1);
			String srcStateId = parts.get(parts.indexOf("from") + 2);
			String trgStateId = parts.get(parts.indexOf("to") + 2);
			String methodId = parts.get(parts.indexOf("method") + 1);

			result.put("type", "MODIFY");
			result.put("objectId", objectId);
			result.put("objectTypeId", Integer.parseInt(objectTypeId));
			result.put("srcStateId", Integer.parseInt(srcStateId));
			result.put("trgStateId", Integer.parseInt(trgStateId));
			result.put("methodId", Integer.parseInt(methodId));

			return result;
		}		

		if(line.contains("Can't execute") && line.contains("in state")){
			String eventId = parts.get(parts.indexOf("event") + 1);
			String objectTypeId = parts.get(parts.indexOf("type") + 1);
			String srcStateId = parts.get(parts.indexOf("state") + 1);
			int methodID = model.getMethodId(Integer.parseInt(eventId));

			result.put("type", "VIOLATION");
			result.put("methodId", methodID);
			result.put("objectTypeId", Integer.parseInt(objectTypeId));
			result.put("srcStateId", Integer.parseInt(srcStateId));

			return result;
		}

		return null;
		
	}
	
	
	// Reads student's interactions
	public void getInteractions(){
		
		//Make sure everything we need from this session is in the session.tmp file.  
		SessionLogger.getInstance().flushBuffer();
		
		BufferedReader br = null;
		FileReader fr = null;

		try {

			String sCurrentLine;

			// Process historic log file		
			File file = new File(FILENAME);
			ArrayList<String> allLines = new ArrayList<String>();
			if(file.isFile()){
				
				fr = new FileReader(FILENAME);
				br = new BufferedReader(fr);

				System.out.println("\nReading historic log...");
				
				while ((sCurrentLine = br.readLine()) != null) {
					allLines.add(sCurrentLine);
				}
			} else{
				System.out.println("\nNo historic log found...");
			}
			// Process this session file
			file = new File(FILENAME2);
			
			if(file.isFile()){
				fr = new FileReader(FILENAME2);
				br = new BufferedReader(fr);

				System.out.println("Reading current session log...");
				
				while ((sCurrentLine = br.readLine()) != null) {	
					allLines.add(sCurrentLine);

				}
			}

			HashMap<String, ArrayList<Integer>> visitedPaths = new HashMap<String, ArrayList<Integer>>();
			HashMap<String, ArrayList<Integer>> associations = new HashMap<String, ArrayList<Integer>>();
			HashMap<Integer, ArrayList<Integer>> inheritance = new HashMap<Integer, ArrayList<Integer>>();
			HashMap<Integer, ArrayList<String>> allAttributes = new HashMap<Integer, ArrayList<String>>();
			HashMap<Integer, ArrayList<Pair<Integer, Integer>>> methods = new HashMap<Integer, ArrayList<Pair<Integer, Integer>>>();
			HashMap<String, Integer> objectTypeIds = new HashMap<String, Integer>();
			int i = 0;
			for (String line : allLines){
				i++;
				// Process each file line
				HashMap<String, Object> result = searchPatterns(line);

				if (result != null){
					if (result.get("type").equals("CREATE")){
						int objectTypeId = (Integer) result.get("objectTypeId");
						String attributes = (String) result.get("attributes");

						if (allAttributes.containsKey(objectTypeId)){
							allAttributes.get(objectTypeId).addAll(new ArrayList<String>(Arrays.asList(attributes.split(","))));
						} else {
							allAttributes.put(objectTypeId, new ArrayList<String>(Arrays.asList(attributes.split(","))));
						}
						if (result.containsKey("masterId")){
							String masterId = (String) result.get("masterId");
							if (associations.containsKey(masterId)) {
								associations.get(masterId).add(objectTypeId);
							} else {
								associations.put(masterId, new ArrayList<Integer>(Arrays.asList(objectTypeId)));
							}
						}
						
						ArrayList<Integer> parentIds = model.getParents(objectTypeId);
						for (Integer parentId : parentIds) {
							if (inheritance.containsKey(parentId)) {
								inheritance.get(parentId).add(objectTypeId);
							} else {
								inheritance.put(parentId, new ArrayList<Integer>(Arrays.asList(objectTypeId)));
							}
						}
					}
					if (result.get("type").equals("MODIFY")){
						int objectTypeId = (Integer) result.get("objectTypeId");
						String objectId = (String) result.get("objectId");
						int methodId = (Integer) result.get("methodId");
						int srcStateId = (Integer) result.get("srcStateId");
						int trgStateId = (Integer) result.get("trgStateId");
						objectTypeIds.put(objectId, objectTypeId);
						Pair<Integer, Integer> allowedMethod = new Pair<Integer, Integer>(srcStateId, methodId);
						if (methods.containsKey(objectTypeId)) {
							methods.get(objectTypeId).add(allowedMethod);
						} else {
							methods.put(objectTypeId, new ArrayList<Pair<Integer, Integer>>(Arrays.asList(allowedMethod)));
						}
						if (visitedPaths.containsKey(objectId)) {
							visitedPaths.get(objectId).add(trgStateId);
						} else {
							visitedPaths.put(objectId, new ArrayList<Integer>(Arrays.asList(srcStateId, trgStateId)));
						}
					}
					if (result.get("type").equals("VIOLATION")){
						int objectTypeId = (Integer) result.get("objectTypeId");
						int methodId = (Integer) result.get("methodId");
						int srcStateId = (Integer) result.get("srcStateId");

						Pair<Integer, Integer> disallowedMethod = new Pair<Integer, Integer>(srcStateId, methodId);
						if (methods.containsKey(objectTypeId)) {
							methods.get(objectTypeId).add(disallowedMethod);
						} else {
							methods.put(objectTypeId, new ArrayList<Pair<Integer, Integer>>(Arrays.asList(disallowedMethod)));
						}

						
					}
				}
					
			}

			checkCases(Consts.CA, visitedPaths, associations, allAttributes, methods, objectTypeIds, inheritance);
			checkCases(Consts.ALFP, visitedPaths, associations, allAttributes, methods, objectTypeIds, inheritance);
			checkCases(Consts.AOLP, visitedPaths, associations, allAttributes, methods, objectTypeIds, inheritance);
			checkCases(Consts.AT, visitedPaths, associations, allAttributes, methods, objectTypeIds, inheritance);
			checkCases(Consts.AS, visitedPaths, associations, allAttributes, methods, objectTypeIds, inheritance);
			checkCases(Consts.AEM, visitedPaths, associations, allAttributes, methods, objectTypeIds, inheritance);
			checkCases(Consts.GEN, visitedPaths, associations, allAttributes, methods, objectTypeIds, inheritance);
			checkCases(Consts.TP, visitedPaths, associations, allAttributes, methods, objectTypeIds, inheritance);
			checkCases(Consts.AL, visitedPaths, associations, allAttributes, methods, objectTypeIds, inheritance);
			checkCases(Consts.AM, visitedPaths, associations, allAttributes, methods, objectTypeIds, inheritance);

			// Once the files are read, set the final coverage status
			coverage.setFinalCoverageState();
			
		}
		catch (IOException e) {
			
			e.printStackTrace();
		} 
		finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} 
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}		
	
	
	
	
	public void main(){
		System.out.println("\n+--------------------------+");
		System.out.println("|   Running TesCaV Module  |");
		System.out.println("+--------------------------+");

		// Generate test cases for these criteria
		classAttribute();
		allTransitions();
		allStates();
		allLoopFreePaths();
		allOneLoopPaths();
		associationsEndMultiplicity();
		generalization();
		allTransitionPairs();
		allLoops();
		allMethods();

		// Check student's test case coverage
		getInteractions();
		
		// Display results on console
		coverage.print();
		
		// Show GUI
		new VisualizerUI(model, coverage);
		MerodeLogger.logln(coverage.toString());

		System.out.println("\n+--------------------------+");
		System.out.println("|       TesCaV done.       |");
		System.out.println("+--------------------------+\n");
	}

}
