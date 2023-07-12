package tescav;



import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComboBox;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import testing.Model;
import validation.XMLProperties;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  

/* Helper classes to generate the GUI */
/**
 * 
 * @author Sofia Alarcon
 *
 */
public class VisualizerBean {
	// Attributes
	private Model model;
	private Coverage coverage;
	static String USER_DIRECTORY = "user.dir";
	static String FILE_SEPARATOR = "file.separator";
	static String IMAGES_URL = "/images";
	final static String GUI_TRANSITIONS = "mxp:guitransition";
	final static String CONTROL_POINTS = "mxp:edgecontrolpoint";
	
	// Methods
	public VisualizerBean(Model modelRcv, Coverage coverageRcv){
		model = modelRcv;
    	coverage = coverageRcv;
	}
	
	public String getSummaryText(){
		
		String message="<html><font size=+1><b>Test case coverage summary:</b><br><br>";	
		
		if(coverage.getFinalCoverageState()){
			message +="<b><font color='green'>Congratulations!</font></b> you "
					+ "have coverered all test cases.";
		}
		else{
			message += "There are test cases <font color='orange'><b>you "
					+ "haven't covered</b></font> yet.";
		}
		
		
		
		return message;	
	}
	
	public String getTitle(){
		String title = "<html><font size=+2>TesCaV: Test Case Visualizer</font></html>";
		return title;
	}
	
	public JComboBox<String> getComboBox(){

		String [] classes = new String[model.getSize()];

		for(int i=0; i < classes.length; i++){

			classes[i] = "<html><font size=+1>"+model.getObjectName(i);
		}
		
		JComboBox<String> classList = new JComboBox<String>(classes);	
		
		return classList;
	}

	
	public int getCriteriaAmount(){	
		return coverage.getCriteriaSize();
	}
	
	public String getCriterionName(int i){
		return coverage.getCriterionName(i);
	}
	
	// Returns coverage given an object and a criterion
	public int[] getCoverageForCriterion(String selectedClass, int j){
		 
		// Represents covered and total test cases
		int[]result = {0,0}; 

		// Recorre todos los casos de cierto criterio
		for(int i=0; i< coverage.getTestCasesAmount(j); i++){
					
			if(selectedClass.equals(coverage.getObjectName(j,i))){
				result[1]++;
				
				if(coverage.isTestCaseCovered(j,i)){
					result[0]++;
				}
			}
		}		
		return result;
	}	

	public ArrayList<TestCase> getUncoveredTestCases (String objectName, int i){
		
		ArrayList<TestCase> cases = new ArrayList<TestCase>();
		
		for(int j=0; j < coverage.getTestCasesAmount(i); j++){
			
			if(objectName.equals(coverage.getObjectName(i,j) ) &&  !coverage.isTestCaseCovered(i,j)){
				 cases.add(coverage.getTestCase(i,j));
			}
		}		
		return cases;
	}
	
	/* Get the image file name that needs to be painted acording to a given 
	 * criterion and object
	 */
	public String getFileName(int ix, String objectName){

		String criterion = coverage.getCriterionName(ix);
		String criterionType = coverage.getCriterionType(ix);

		String userDir = System.getProperty(USER_DIRECTORY);
		String fileSeparator = System.getProperty(FILE_SEPARATOR);
		String imgDir = (userDir + IMAGES_URL).replace(fileSeparator, "/");

		String[] imgFiles = new File(imgDir).list();
		
		String fileName = null;
		
		for(int i = 0; i < imgFiles.length; i++){
			
			if((criterionType.equals(Consts.EDG)) && 
					imgFiles[i].contains("EDG_")
				){
				fileName = imgFiles[i];	
			}
			if((criterionType.equals(Consts.FSM)) 	&&
					imgFiles[i].contains("FSM_") 	&&
					imgFiles[i].contains(objectName.toUpperCase())
				){ 
				fileName = imgFiles[i];
			}
		}
		return fileName;
	}

	// Extract X axis from an image file name
	public float getFileXAxis(String fileName){
		return Float.valueOf(fileName.substring(fileName.lastIndexOf("_") + 1,
				fileName.lastIndexOf("x")));
		
	}
	
	public float getFileYAxis(String fileName){
		return Float.valueOf(fileName.substring(fileName.lastIndexOf("x") + 1,
				fileName.lastIndexOf(".png")));
	}

	
	public ArrayList<String> getControlPoints(int methodId, String srcState, String trgState){
		
		ArrayList<String> controlPoints = new ArrayList<String>();
		
		int transitionId = model.getTransitionIdByMethodId(methodId,srcState, trgState);
		//tid == -1
		System.out.println("transitionId is: " + transitionId);
		

		String tId = String.valueOf(transitionId);
		Document doc = XMLProperties.buildXMLDocument();
		
		NodeList guiTransitions = doc.getElementsByTagName(GUI_TRANSITIONS);
		
		
		// I messed up and named one of my classes "Element" so I can't import the one from w3c because it will be confusing. I guess it needs refactoring, i'm sorry. 
		
		// Search the guitransitions
		for(int i=0; i < guiTransitions.getLength(); i++){
			org.w3c.dom.Element el = (org.w3c.dom.Element) guiTransitions.item(i);
			
			// If the transition id is found
			if(tId.equals(el.getAttributes().getNamedItem("refid").getNodeValue())){
				
				NodeList edgeControlPoints = el.getElementsByTagName(CONTROL_POINTS);
				
				int controlPointCount = edgeControlPoints.getLength();
				
				/* absence of control points is no longer explicit in .mxp 1.6,
				so this will prevent crashes */  
				if(controlPointCount == 0){
					controlPoints.add("0.00!0.00");
				}else{
					// For each control point
					for(int j=0; j < edgeControlPoints.getLength(); j++){
					
						org.w3c.dom.Element el2 = (org.w3c.dom.Element) edgeControlPoints.item(j);
					
						String strValue = el2.getAttributes().getNamedItem("value").getNodeValue();
					
						controlPoints.add(strValue);
					
					}
				}
			}
			
		}
		
		return controlPoints;
		
	}
	
	// Obtener el punto medio de un estado
	public float[] getStateCenter(float x, float y, float width, float height){
		float[] center= new float[2];
		
		float extremeX = x + width;
		float extremeY = y + height;
		
		float centerX = (x + extremeX)/2;
		float centerY = (y + extremeY)/2;
		
		center[0] = centerX;
		center[1] = centerY;
			
		return center;
	}

} //class end


