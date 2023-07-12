package testing;

import java.util.ArrayList;

/**
 * Created by Arturo Mantinetti
 */
public class ClassMap {
    public String name = null;
    public ArrayList<String> functions = null;
    public ArrayList<String> childrens = null;
    public ArrayList<Attribute> attributes = null;

    public ClassMap(String className) {
        name = className;
        functions = null;
        childrens = null;
        attributes = null;
    }

    public void insertAttribute(String name, String type){
        if(attributes==null)
            attributes = new ArrayList<Attribute>();
        Attribute temp = new Attribute(name,type);
        attributes.add(temp);
    }

    public void insertChildren(String name){
        if(childrens==null)
            childrens = new ArrayList<String>();
        childrens.add(name);
    }

    public void insertFunction(String name){
        if(functions==null)
            functions = new ArrayList<String>();
        functions.add(name);
    }

}
