package tescav;

import java.util.ArrayList;
import javafx.util.Pair;

public class Element {
	
	private ArrayList<Integer> methodIds = new ArrayList<Integer>(); // metodos que gatillan una transicion
	private ArrayList<Integer> stateIds = new ArrayList<Integer>();
	private String attribute;
	private String type;
	private boolean covered = false;
	private Pair<String, String> dependent;
	private String testingType;

	private int objectId;
	private String elementName;

	public Element(String type) {
		this.type = type;
	}

	public int getObjectId() {
		return this.objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getElementName() {
		return this.elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void addStateIds(ArrayList<Integer> stateIds) {
		this.stateIds.addAll(stateIds);
	}

	public void addStateId(int stateId) {
		this.stateIds.add(stateId);
	}

	public ArrayList<Integer> getStateIds(){
		return this.stateIds;
	}

	public int getStateId(int l){
		
		if(stateIds.size() == 0){
			return -1;
		}
		else{
			return stateIds.get(l);
		}
	}

	public Pair<String, String> getDependent(){
		return this.dependent;
	}

	public void setDependent(Pair<String, String> dependent){
		this.dependent = dependent;
	}

	public void addMethodId(int id){
		methodIds.add(id);
	}

	public void addMethodIds(ArrayList<Integer> ids){
		methodIds.addAll(ids);
	}
	
	public String getType(){
		return type;
	}

	public String getAttribute(){
		return attribute;
	}

	
	public int getMethodsAmount(){
		return methodIds.size();
	}
	
	public int getMethodId(int l){
		
		if(methodIds.size() == 0){
			return -1;
		}
		else{
			return methodIds.get(l);
		}
	}
	
	public void coverElement(){
		covered = true;
	}

	public boolean isElementCovered(){
		return covered;
	}
	
	public void setTestingType(String testingType) {
		this.testingType = testingType;
	}

	public String getTestingType(){
		return this.testingType;
	}
	
	public void print(){
		
		System.out.println("\n\t\t\tElement");
		System.out.println("\t\t\tMethod Ids: " + methodIds.toString());
		System.out.println("\t\t\tType: " + type);
		System.out.println("\t\t\tVisited: " + covered + " <<<<<<<<<<<<");
	}

}
