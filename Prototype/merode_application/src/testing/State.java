package testing;

import org.w3c.dom.Element;

/**
 * Created by Arturo Mantinetti
 */
class State{
    public int id;
    public String name;
    public String type;

    public State(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public State(Element state) {
        String sid = state.getAttribute("id");
        id = Integer.parseInt(sid);
        name = state.getAttribute("name");
        type = state.getAttribute("type");
        System.out.println("\t\t\t New State id:"+id+" |name:" + name + " |Type:" + type);

    }
    
    public String getType(){
    	return type;
    }
    
    public int getId(){
    	return id;
    }

    public String getName(){
    	return name;
    }
}