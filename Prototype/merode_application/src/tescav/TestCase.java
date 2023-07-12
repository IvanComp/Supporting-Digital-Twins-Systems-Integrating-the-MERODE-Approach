package tescav;

import java.util.ArrayList;
import javafx.util.Pair;

public class TestCase {

	private ArrayList<Element> elements = new ArrayList<Element>();
	private boolean covered = true;
	private int objectTypeId; // this is useful for us for clarity purposes, and later the gui needs this too
	private String objectName;

	public TestCase(int objectTypeId, String objectTypeName){
		this.setObjectTypeId(objectTypeId);
		this.setObjectTypeName(objectTypeName);
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectName = objectTypeName;
	}	
	public void setObjectTypeId(int objTypeId) {
		this.objectTypeId = objTypeId;
	}	

	public int getObjectTypeId() {
		return this.objectTypeId;
	}	
	public String getObjectTypeName() {
		return this.objectName;
	}	
	public void addElement(Element element){
		elements.add(element);
	}
	
	public int getElementsSize(){
		return elements.size();
	}
	
	public int getMethodsAmount(int k){
		return elements.get(k).getMethodsAmount();
	}
	
	public int getMethodId(int k, int l){
		return elements.get(k).getMethodId(l);
	}
	
	public boolean isElementCovered(int k){
		return elements.get(k).isElementCovered();
	}
	
	public void coverElement(int k){
		elements.get(k).coverElement();
	}
	
	public void print(){
		System.out.println("\n\t\tTest Case");
		System.out.println("\t\tCovered:" + covered);
		System.out.println("\t\tObject Name: " + objectName);
		for(int i=0; i < getElementsSize(); i++ ){
			elements.get(i).print();
		}
	}
	
	public void setFinalCoverageState(){
		for(int i=0; i < getElementsSize(); i++){
			
			boolean elementCoverage = elements.get(i).isElementCovered();
			
			if(!elementCoverage){
				covered = false;
				break;
			}
		}
	}
	
	public boolean isTestCaseCovered(){
		return covered;
	}

	public Element getElement(int k){
		return elements.get(k);
	}
	
	public String getElementType(int i){
		return elements.get(i).getType();
	}

	public int getStateId(int i, int j){
		return elements.get(i).getStateId(j);
	}

	public ArrayList<Integer> getStateIds(int i){
		return elements.get(i).getStateIds();
	}

	public Pair<String, String> getDependent(int i){
		return elements.get(i).getDependent();
	}

	public String getAttribute(int i){
		return elements.get(i).getAttribute();
	}

	public String getElementName(int i){
		return elements.get(i).getElementName();
	}

	public String getObjectName(){
		return this.objectName;
	}

	public String getTestingType(int i){
		return elements.get(i).getTestingType();
	}
}
