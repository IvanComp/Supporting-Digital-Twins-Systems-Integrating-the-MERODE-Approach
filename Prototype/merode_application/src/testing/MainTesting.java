package testing;

import com.sun.org.apache.xpath.internal.operations.Mod;
import dao.MerodeLogger;
import tescav.MainTescav;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import validation.MermaidImageGallery;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.util.Pair;

import testing.Model;
import testing.ObjectM;
/**
 * Created by Arturo Mantinetti.
 * Edited by Sofia Alarcon.
 */
public class MainTesting {
    final static String OBJECTS = "mxp:metaobject";
    final static String OBJECT_ATTRIBUTE = "mxp:metaattribute";
    final static String FSM = "mxp:metafsm";
    final static String DEPENDENCY = "mxp:metadependency";
    final static String CHILD = "mxp:metainheritance";

    final static String METHODS = "mxp:metamethod";
    
    private static Model model;
    private static MainTescav tescav;
    
    public String getH(){
    	return "Hola, este es testing";
    }
    
    public void runTest(String testClass){
    	
    System.out.println("++++++++++++++++++++++++++++++++Ejecuto primero");
        //model = makeModel("model.mxp");
    	makeModel("model.mxp");
    	
    	/*
        ArrayList<ClassMap> classMapList = new ArrayList<ClassMap>();
        Testings testings = new Testings();

        //ClassAttribute
        for(int i=0; i<model.modelObjects.size();i++){
            String className = model.modelObjects.get(i).name;
            
            if(testClass == null || testClass.equals(className)){
                ClassMap classMapTemp = makeClassMap(className);
                classMapList.add(classMapTemp);
                try{
                    testings.ClassAttribute(classMapTemp);
                }catch (Exception e){
                    System.out.println("ClassAttribute Error");
                    System.out.println("------>" + e.toString());
                }
            }
        }

        //AssociationEndMultiplicity
        for(int i=0; i<classMapList.size();i++){
            ClassMap classMapTemp = classMapList.get(i);
            String className = model.modelObjects.get(i).name;
            if(testClass == null || testClass.equals(className)) {
                if (testClass == null || testClass.equals(className)) {
                    try {
                        testings.AssociationEndMultiplicity(classMapTemp);
                    } catch (Exception e) {
                        System.out.println("AssociationEndMultiplicity Error");
                        System.out.println("------>" + e.toString());
                    }
                }
            }
        }

        //AllState
        for(int i=0; i<model.modelObjects.size();i++){
            ObjectM tempClass = model.modelObjects.get(i);
            String className = tempClass.name;
            if(testClass == null || testClass.equals(className)) {
                try {
                    testings.AllState(tempClass);
                } catch (Exception e) {
                    System.out.println("AllState Error");
                    System.out.println("------>" + e.toString());
                }
            }
        }

        //AllTransitions     
        for(int i=0; i<model.modelObjects.size();i++){
            ObjectM tempClass = model.modelObjects.get(i);
            String className = tempClass.name;
            if(testClass == null || testClass.equals(className)) {
                try {
                    testings.AllTransitions(tempClass);
                } catch (Exception e) {
                    System.out.println("AllTransitions Error");
                    System.out.println("------>" + e.toString());
                }
            }
        }
        
        */
    }

    public void addGuiButton(){
    	
//        ImageIcon modelIcon = new ImageIcon(getClass().getResource("run-graph.png"));
//        JMenuItem eMenuItemModel = new JMenuItem("Run TesCaV", modelIcon);
//        eMenuItemModel.setMnemonic(KeyEvent.VK_C);
//        eMenuItemModel.addActionListener(new ActionListener() {
//            
//        	// Execute this function when the "Run Test" button is pressed
//        	public void actionPerformed(ActionEvent event) {
//                
                model = new Model();

                runTest(null);
//                
                // Create instance for TesCaV module

                tescav = new MainTescav(model);
                try {

					tescav.main();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

//            }
//        });
//        tools.add(eMenuItemModel);
    }

    public String getTestingLog() {
        model = new Model();

        runTest(null);     
        // Create instance for TesCaV module

        tescav = new MainTescav(model);
        try {

            tescav.main();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tescav.getCoverage().toString();
    }

    public static ClassMap makeClassMap(String className){

        String path="src/dao/mappings/";
        String endFile=".hbm.xml";
        ClassMap classMap = null;
        try {
            File inputFile = new File(path + className + endFile);
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("\nRoot Mapping: "
                    + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");

            NodeList nList = doc.getElementsByTagName("class");
            System.out.println("Mapping Class");
            for (int temp = 0; temp < nList.getLength(); temp++) {
            	
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element object = (Element) nNode;
                    classMap = new ClassMap(object.getAttribute("name"));
                    System.out.println("\t"+ "Class -> " + object.getAttribute("name"));
                    //Attributes
                    NodeList propList = object.getElementsByTagName("property");
                    for (int i = 0; i < propList.getLength(); i++) {
                        Node propNode = propList.item(i);
                        if (propNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element propObj = (Element) propNode;
                            System.out.println("\t\t" + "Property -> " + propObj.getAttribute("name"));
                            System.out.println("\t\t" + "Property -> " + propObj.getAttribute("type"));
                            classMap.insertAttribute(propObj.getAttribute("name"), propObj.getAttribute("type"));
                        }
                    }
                    //Children Class
                    NodeList childrenList = object.getElementsByTagName("subclass");
                    for (int i = 0; i < childrenList.getLength(); i++) {
                        Node childNode = childrenList.item(i);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element childObj = (Element) childNode;
                            System.out.println("\t\t" + "SubClass -> " + childObj.getAttribute("name"));
                            String ifTemp = object.getAttribute("name") + "Impl";
                            if( !ifTemp.equals( childObj.getAttribute("name") ) )
                                classMap.insertChildren(childObj.getAttribute("name"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            MerodeLogger.logln("--> Error loading "+ className +" mapping");
            classMap = null;
        }
        return classMap;
    }

    //public static Model makeModel(String file){
    public static void makeModel(String file){
        //model = new Model();
        try {
            File inputFile = new File(file);
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: "
                    + doc.getDocumentElement().getNodeName());
            System.out.println("----------------------------");           
            
            // Arturo 
            
            NodeList nList = doc.getElementsByTagName(OBJECTS);
            NodeList dependencies = doc.getElementsByTagName(DEPENDENCY);
            NodeList inheritingChildren = doc.getElementsByTagName(CHILD);


            for (int temp = 0; temp < nList.getLength(); temp++) {

                System.out.println("\n");
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                	Element object = (Element) nNode;
                	String objectName = object.getAttribute("name");
                    String objectId = object.getAttribute("id");

                    ArrayList<Pair<String, String>> dependents = new ArrayList<Pair<String, String>>();

                    for(int i = 0; i < dependencies.getLength(); i++){
                        Node depedencyNode = dependencies.item(i);
                        Element dependency = (Element) depedencyNode;

                        if(objectName.equals(dependency.getAttribute("masterrole"))){

                            String dependentName = dependency.getAttribute("dependentrole");
                            String dependencyType = dependency.getAttribute("type");
                            Pair<String, String> newDependency = new Pair<String, String>(dependentName, dependencyType);
                            dependents.add(newDependency);

                        }

                    }

                    ArrayList<String> children = new ArrayList<String>();

                    for(int i = 0; i < inheritingChildren.getLength(); i++){
                        Node inheritingChildNode = inheritingChildren.item(i);
                        Element inheritingChild = (Element) inheritingChildNode;

                        
                        if(objectId.equals(inheritingChild.getAttribute("supertypeid"))){

                            String dependentId = inheritingChild.getAttribute("subtypeid");                       
                            
                            children.add(dependentId);

                        }

                    }
                    
                    

                	// Create an ObjectM object and add the corresponding XML tag attributes
                    //ObjectM tempObj = new ObjectM(object);
                    ObjectM tempObj = new ObjectM(object, dependents, children);

                    NodeList attributesList = object.getElementsByTagName(OBJECT_ATTRIBUTE);
                    for (int nIndex = 0; nIndex < attributesList.getLength(); nIndex++) {
                        Node attrNode = attributesList.item(nIndex);

                    
                        if (attrNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element attrObject = (Element) attrNode;

                        	// Create an ObjectM object and add the corresponding XML tag attributes
                            ObjectM tempAttrObj = new ObjectM(attrObject);

                        }

                    }

                    tempObj.insertAttributes(attributesList);

                    NodeList fsmList = object.getElementsByTagName(FSM);

                    tempObj.insertFSM(fsmList);

                    model.insertObject(tempObj);

                }
            }
           
            // Sofia 
            
            nList = doc.getElementsByTagName(METHODS);

            for (int temp = 0; temp < nList.getLength(); temp++) {
            	
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                	Element object = (Element) nNode;
                    Method tempObj = new Method(object);
                    
                    model.insertMethod(tempObj);
                }
            }

            
            
            
            
            
        } catch (Exception e) {
            MerodeLogger.logln("--> Error loading model");
        }

        //return model;
    }

}
