package tescav;

import java.util.ArrayList;
import javafx.util.Pair;

/** 
 * 
 * @author Sofia Alarcon
 */
public class Criterion {
	private String name;
	private ArrayList<TestCase>  testCases = new ArrayList<TestCase>();
	private boolean covered = true;
	private String type;
	
	public Criterion(String name, String type){
		this.name = name;
		this.type = type;
	}
	
	// Returns this criterion name
	public String getName(){
		return this.name;
	}

	// Returns this criterion type
	public String getType(){
		return this.type;
	}

	public String getObjectTypeName(int j) {
		return testCases.get(j).getObjectTypeName();
	}	
	
	public void addTestCase(TestCase testCase){
		testCases.add(testCase);
	}
	
	public int getTestCasesSize(){
		return testCases.size();
	}
	
	public int getElementsAmount(int j){
		return testCases.get(j).getElementsSize();
	}
	
	public int getMethodsAmount(int j, int k){
		return testCases.get(j).getMethodsAmount(k);
	}
	
	public int getMethodId(int j, int k, int l){
		return testCases.get(j).getMethodId(k,l);
	}
	
	// Check if an element is covered, given a testcase and element
	public boolean isElementCovered(int j, int k){
		return testCases.get(j).isElementCovered(k);
	}
	
	public void coverElement(int j, int k){
		testCases.get(j).coverElement(k);
	}
	
	public void print(){
		System.out.println("\n\tCriterion");
		System.out.println("\tName: " + name);
		System.out.println("\tCoverage: " + covered);
		for(int i=0; i < getTestCasesSize(); i++ ){
			testCases.get(i).print();
		}
	}
	public int getCoverageCount(String objectTypeName){
		int count = 0;
		for(int i=0; i < getTestCasesSize(); i++){
			if (objectTypeName.equals(getTestCase(i).getObjectTypeName()) && isTestCaseCovered(i)) {
				count++;
			}
		}
		return count;
	}

	public int getCoverageTotal(String objectTypeName){
		int count = 0;
		for(int i=0; i < getTestCasesSize(); i++){
			if (objectTypeName.equals(getTestCase(i).getObjectTypeName())) {
				count++;
			}
		}
		return count;
	}
	public void setFinalCoverageState(){
		
		for(int i=0; i < getTestCasesSize(); i++){
			testCases.get(i).setFinalCoverageState();
		}
		
		for(int i=0; i < getTestCasesSize(); i++){
			boolean tCCoverage = testCases.get(i).isTestCaseCovered();
			
			if(!tCCoverage){
				covered = false;
				break;
			}
		}
	}

	public boolean isCriterionCovered(){
		return covered;
	}
	
	public boolean isTestCaseCovered(int j){
		return testCases.get(j).isTestCaseCovered();
	}
	
	public Element getElement(int j, int k){
		return testCases.get(j).getElement(k);
	}
	
	public TestCase getTestCase(int j){
		return testCases.get(j);
	}
	
	public String getElementType(int j, int k){
		return testCases.get(j).getElementType(k);
	}
	
	public int getStateId(int j, int k, int l){
		return testCases.get(j).getStateId(k, l);
	}

	public ArrayList<Integer> getStateIds(int j, int k){
		return testCases.get(j).getStateIds(k);
	}

	public Pair<String, String> getDependent(int j, int k){
		return testCases.get(j).getDependent(k);
	}

	public String getAttribute(int j, int k){
		return testCases.get(j).getAttribute(k);
	}

	public int getObjectTypeId(int j) {
		return testCases.get(j).getObjectTypeId();
	}

	public String getElementName(int j, int k){
		return testCases.get(j).getElementName(k);
	}

	public String getObjectName(int j){
		return testCases.get(j).getObjectName();
	}

	public String getTestingType(int j, int k){
		return testCases.get(j).getTestingType(k);
	}
}




