package tescav;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;
import javafx.util.Pair;

/**
 * 
 * @author Sofia Alarcon
 *
 */
public class Coverage {
	
	public ArrayList<Criterion> criteria  = new ArrayList<Criterion>();
	public boolean covered = true;
	
	public void addCriterion (Criterion criterion){
		criteria.add(criterion);
	}
	
	// Return amount of criteria available for test case checking
	public int getCriteriaSize(){
		return criteria.size();
	}

	public String getCriterionName(int i){
		return criteria.get(i).getName();
	}
	
	public String getObjectTypeName(int i, int j) {
		return criteria.get(i).getObjectTypeName(j);
	}	

	public String getCriterionType(int i){
		return criteria.get(i).getType();
	}

	// Return amount of test cases for a given criterion
	public int getTestCasesAmount(int i){
		return criteria.get(i).getTestCasesSize();
	}
	
	// Return amount of elements for a given criterion and test case
	public int getElementsAmount(int i, int j){
		return criteria.get(i).getElementsAmount(j);
	}
	
	// Return amount of methods for a given criterion, testcase and element
	public int getMethodsAmount(int i, int j, int k){
		return criteria.get(i).getMethodsAmount(j,k);
	}
	
	// Return a method id for a given criterion, testcase, element and method id
	public int getMethodId(int i, int j, int k, int l){
		return criteria.get(i).getMethodId(j,k,l);
	}

	// Check if an element is covered, given a criterion, test case and element
	public boolean isElementCovered(int i, int j, int k){
		return criteria.get(i).isElementCovered(j, k);
	}
	
	public void coverElement(int i, int j, int k){
		criteria.get(i).coverElement(j, k);
	}
	
	// Print all test cases and their status
	public void print(){
		
		System.out.println("\nTesCaV Coverage Results");
		System.out.println("Covered: " + covered);
				
		for (int i=0; i < getCriteriaSize(); i++){
			criteria.get(i).print();
		}
	}

	// Print all test cases and their status
	public String toString(){
		//e.g. {"Job": {"ALFP": 3, 10}}
		HashMap<String, HashMap<String, Pair<Integer, Integer>>> counts = new HashMap<String, HashMap<String, Pair<Integer, Integer>>>();
		for (int i=0; i < getCriteriaSize(); i++){
			for (int j=0; j < criteria.get(i).getTestCasesSize(); j++){
				String objectTypeName = getObjectTypeName(i, j);
				String critName = getCriterionName(i);
				int coveredCount = criteria.get(i).getCoverageCount(objectTypeName);
				int total =  criteria.get(i).getCoverageTotal(objectTypeName);
				// The object type already exists
				if (counts.keySet().contains(objectTypeName)) {
					Pair<Integer, Integer> count = new Pair<Integer, Integer>(coveredCount, total);
					counts.get(objectTypeName).put(critName, count);
				} else {
					Pair<Integer, Integer> count = new Pair<Integer, Integer>(coveredCount, total);
					HashMap<String, Pair<Integer, Integer>> critMap = new HashMap<String, Pair<Integer, Integer>>();
					critMap.put(critName, count);
					counts.put(objectTypeName, critMap);
				}
			}
		}
		String result = "\n+--------------------------+";
		result += "\n|   TesCav final report  |";
		for (String objectTypeName : counts.keySet()){
			result += "\n|-- " + objectTypeName + " --|\n";
			for (String critName : counts.get(objectTypeName).keySet()){
				Integer count = counts.get(objectTypeName).get(critName).getKey();
				Integer total = counts.get(objectTypeName).get(critName).getValue();
				result += critName + " " + String.valueOf(count) + "/" + String.valueOf(total) + "\n";
			}
		}
		return result;
	}
	
	// Check all the cases to set the final coverage state
	public void setFinalCoverageState(){
		
		for(int i=0; i < getCriteriaSize(); i++){
			criteria.get(i).setFinalCoverageState();
		}
		
		for(int i=0; i < getCriteriaSize(); i++){
			boolean criteriaCoverage = criteria.get(i).isCriterionCovered();
			
			if(!criteriaCoverage){
				covered = false;
				break;
			}
		}
		System.out.println("final coverage is: " + covered);
	}
	
	public boolean getFinalCoverageState(){
		return covered;
	}

	public boolean isTestCaseCovered(int i, int j){
		return criteria.get(i).isTestCaseCovered(j);
	}
	
	public Element getElement(int i, int j, int k){
		return criteria.get(i).getElement(j, k);
	}
	
	public TestCase getTestCase(int i, int j){
		return criteria.get(i).getTestCase(j);
	}

	public String getElementType(int i, int j, int k){
		return criteria.get(i).getElementType(j,k);
	}

	public int getStateId(int i, int j, int k, int l){
		return criteria.get(i).getStateId(j, k, l);
	}

	public ArrayList<Integer> getStateIds(int i, int j, int k){
		return criteria.get(i).getStateIds(j, k);
	}


	public Pair<String, String> getDependent(int i, int j, int k){
		return criteria.get(i).getDependent(j, k);
	}

	public String getAttribute(int i, int j, int k){
		return criteria.get(i).getAttribute(j, k);
	}

	public int getObjectTypeId(int i, int j) {
		return criteria.get(i).getObjectTypeId(j);
	}

	public String getElementName(int i, int j, int k){
		return criteria.get(i).getElementName(j, k);
	}

	public String getObjectName(int i, int j){
		return criteria.get(i).getObjectName(j);
	}

	public String getTestingType(int i, int j, int k){
		return criteria.get(i).getTestingType(j, k);

	}
}
