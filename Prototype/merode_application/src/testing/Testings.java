package testing;

//import dao.Examattempt;
import org.fluttercode.datafactory.impl.DataFactory;
import validation.ErrorWindow;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Arturo Mantinetti.
 */

public class Testings {

    public void GeneralizationTest(ClassMap classModel){
        //Instancia Clase Hija y Verifica que se puedan correr las funciones padres.

        //TODO: Buscar un modelo con clases hija para testear


    }

    public void AssociationEndMultiplicity(ClassMap classModel) throws Exception{
        TestingLog.getInstance().writeLog("Running AssociationEndMultiplicity on " + classModel.name.substring(4));
        System.out.println("Running AssociationEndMultiplicity");
        DataFactory df = new DataFactory();
        Object temp = Class.forName(classModel.name + "Impl").newInstance();
        boolean pass = true;
        for(int i=0; i<classModel.attributes.size(); i++){
            String tsrt = "\tTesting " + classModel.attributes.get(i).name + " on " + classModel.name;
            System.out.println(tsrt);
            TestingLog.getInstance().writeLog(tsrt);
            try{
                String functionName = "set" + classModel.attributes.get(i).name.substring(0, 1).toUpperCase() + classModel.attributes.get(i).name.substring(1);
                Class<?> c = Class.forName(classModel.attributes.get(i).type);
                Method method = temp.getClass().getMethod (functionName, c);
                if(classModel.attributes.get(i).type.equals("java.lang.String"))
                    method.invoke(temp,10000);
                else if (classModel.attributes.get(i).type.equals("int"))
                    method.invoke(temp,"String");
                else if (classModel.attributes.get(i).type.equals("float")){
                    method.invoke(temp,"String");
                }
                else{
                    TestingLog.getInstance().writeLog("--> Error unknown type " + classModel.attributes.get(i).name + " on " + classModel.name );
                }

                TestErrorWindow.main("Error Setting " + classModel.attributes.get(i).name + " on " + classModel.name.substring(4),"Attribute Assign Error");
                pass = false;
            }catch (Exception e){
                System.out.println("Force Error AssociationEndMultiplicity");
            }
        }
        if(pass){
            TestingLog.getInstance().writeLog("\tAssociationEndMultiplicity Pass on " + classModel.name.substring(4) );
        }


    }

    public void ClassAttribute(ClassMap classModel) throws Exception {
        //Testea los Atributos de la Clase
        System.out.println("Running ClassAttribute");
        TestingLog.getInstance().writeLog("Running ClassAttribute on " + classModel.name.substring(4) );
        DataFactory df = new DataFactory();
        Object temp = Class.forName(classModel.name + "Impl").newInstance();
        boolean pass = true;
        for(int i=0; i<classModel.attributes.size(); i++){

            System.out.println("Testing " + classModel.attributes.get(i).name + " on " + classModel.name);
            try{
                String functionName = "set" + classModel.attributes.get(i).name.substring(0, 1).toUpperCase() + classModel.attributes.get(i).name.substring(1);
                
                Class<?> c = Class.forName(classModel.attributes.get(i).type);
                Method method = temp.getClass().getMethod (functionName, c);
                if(classModel.attributes.get(i).type.equals("java.lang.String"))
                    method.invoke(temp,df.getName());
                else if (classModel.attributes.get(i).type.equals("int"))
                    method.invoke(temp,df.getNumber());
                else if (classModel.attributes.get(i).type.equals("float")){
                    float a = (float) (df.getNumber() /  0.2);
                    method.invoke(temp,a);
                }
                else
                    TestingLog.getInstance().writeLog("--> Error unknown type " + classModel.attributes.get(i).name + " on " + classModel.name );

            }catch (Exception e){
                TestingLog.getInstance().writeLog("--> Error Setting " + classModel.attributes.get(i).name + " on " + classModel.name );
                String str = "Error Setting " + classModel.attributes.get(i).name + " on " + classModel.name.substring(4);
                TestErrorWindow.main(str,"Attribute Assign Error");
                TestingLog.getInstance().writeLog(str);
                pass = false;
            }
        }
        if(pass) {
            TestingLog.getInstance().writeLog("\tClassAttribute Pass on " + classModel.name.substring(4));
        }
    }

    public void AllState(ObjectM classObject) throws Exception {
        TestingLog.getInstance().writeLog("Running AllState on " + classObject.name);
        System.out.println("Running AllState");
        System.out.println("Class ->" + classObject.name);
        for (ObjectM.Fsm fsm: classObject.fsms) {
            ArrayList<State> states = fsm.states;
            /*for (State state: states) {
                System.out.println("\t State ->" + state.id + " name " + state.name);
            }*/
            ArrayList<Transition> transitions = fsm.transitions;
            Hashtable <Integer,Boolean> statesVisited = new Hashtable<Integer,Boolean>( states.size() + 10 + states.size() / 10 );
            Hashtable <Integer,Boolean> transitionsVisited = new Hashtable<Integer,Boolean>( transitions.size() + 10 + states.size() / 10);

            runState(states,transitions,0, statesVisited, transitionsVisited);
            boolean pass = true;
            String errors = "";
            for (State state: states) {
                if(!statesVisited.containsKey(state.id)){
                    TestingLog.getInstance().writeLog("\tError in state id=" + state.id + " name=" + state.name);
                    errors += state.name + ",";
                    pass = false;
                }else {
                    TestingLog.getInstance().writeLog("\tstate id=" + state.id + " name=" + state.name + " Visited");
                }
            }
            if(pass) {
                TestingLog.getInstance().writeLog("\tAllState Pass on  " + classObject.name);
            } else
                TestErrorWindow.main2("Missing State", classObject.name, errors.substring(0,errors.length()-1));
        }
    }

    public void AllTransitions(ObjectM classObject) throws Exception {
        TestingLog.getInstance().writeLog("Running AllTransitions on " + classObject.name.substring(4));
        System.out.println("Running AllTransitions");
        System.out.println("Class ->" + classObject.name);
        
        // For each FSM of the class
        for (ObjectM.Fsm fsm: classObject.fsms) {
            ArrayList<State> states = fsm.states;
            ArrayList<Transition> transitions = fsm.transitions;
            System.out.println("\t\t\t Transitions----");
            
            // Show the available transitions for this FSM
            for (Transition transition: transitions) {
                System.out.println("\t\t\t find transition id->" + transition.id);
            }
            
            Hashtable <Integer,Boolean> transitionsVisited = new Hashtable<Integer,Boolean>( transitions.size() + 10 + states.size() / 10);

            // For the FSM, pass all the states, transitions to this method
            runTransition(states,transitions,0, transitionsVisited);
            System.out.println("End Cycle----------------------------------");
            
            
            
            boolean pass = true;
            String errors = "";
            for (Transition transition: transitions) {
                if(!transitionsVisited.containsKey(transition.id)){
                    TestingLog.getInstance().writeLog("\tError in transition id=" + transition.id );
                    //String source = transition.sourcestateid
                    errors += transition.name + ",";
                    //TestErrorWindow.main("Error on " + classObject.name + " in transition id=" + transition.id ,"AllTransitions Error");
                    pass = false;
                }else {
                    TestingLog.getInstance().writeLog("\t" + classObject.name + " transition id=" + transition.id + " name=" + transition.name + " Visited");
                }
            }
            if(pass) {
                TestingLog.getInstance().writeLog("\tAllTransitions Pass on  " + classObject.name);
            }else{
                // Passes: "Missing Transition", object name, transition name
            	
                TestErrorWindow.main2("Missing Transition", classObject.name, errors.substring(0,errors.length()-1));
            }
        }
    }


    private void runState(ArrayList<State> states, ArrayList<Transition> transitions, int statePos,
                          Hashtable <Integer,Boolean> statesVisited, Hashtable <Integer,Boolean> transitionsVisited){
        System.out.println("State Pos->" + statePos);
        State state = states.get(statePos);
        System.out.println("State id->" + state.id);
        System.out.println("State Visited->" + statesVisited.containsKey(state.id));
        statesVisited.put(state.id,true);
        for(int z=0;z<transitions.size();z++){
            Transition transition = transitions.get(z);
            if(!transitionsVisited.containsKey(transition.id) && transition.sourcestateid == state.id && !statesVisited.containsKey(transition.targetstateid) ){
                transitionsVisited.put(transition.id,true);
                int pos=0;
                for(int i = 0; i<states.size();i++){
                    if(states.get(i).id==transition.targetstateid){
                        pos=i;
                    }
                }
                runState(states,transitions,pos, statesVisited, transitionsVisited);
            }
        }
    }

    /* Recursive. Does Depth First Search (DFS) and shows which transitions are 
     * unreachable from the source state.
    */
    private void runTransition(ArrayList<State> states, ArrayList<Transition> transitions, int statePos,
                           Hashtable <Integer,Boolean> transitionsVisited){
        State state = states.get(statePos);
        System.out.println("State id->" + state.id);

        // For each transition
        for (Transition transition: transitions) {
            System.out.print("\t\t\tfind transition id->" + transition.id);
            
            // Do you contain this id? No? Then enter if statement.
            if(!transitionsVisited.containsKey(transition.id))
                System.out.print("\t No Visitada");
            else
                System.out.print("\t Visitada");

            // Does this transition start from current state?
            if(transition.sourcestateid == state.id)
                System.out.println("\t id igual");
            else
                System.out.println("\t id distinto source = " + transition.sourcestateid + " state = " + state.id);

            // If it doesn't contain the id AND the transition starts from current state, enter:
            if(!transitionsVisited.containsKey(transition.id) && transition.sourcestateid == state.id ){   
                
                transitionsVisited.put(transition.id,true);       
                System.out.println("\tTransition Visited id->" + transition.id);
                
                // If source = target. It doesn't finish recursive, it just 
                // continues with the for loop.
                if(transition.sourcestateid == transition.targetstateid){
                    System.out.println("\tFinish Recursive / Source = Target");
                }
                // source != target
                else{
                    int pos=-1;
                    for(int i = 0; i<states.size();i++){
                        if(states.get(i).id == transition.targetstateid){
                            pos=i;
                        }
                    }
                    if(pos==-1){
                        System.out.println("\t Transition Not Found");
                        return;
                    }
                    runTransition(states,transitions,pos, transitionsVisited);
                }
            }
        }
        System.out.println("\tFinish Recursive");
    }


}
