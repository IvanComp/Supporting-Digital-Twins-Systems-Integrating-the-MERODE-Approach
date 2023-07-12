package tescav;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * 
 * @author Sofia Alarcon
 * This class will be called from src/dao/MerodeLogger.java
 */
public class SessionLogger {
	//Attributes
	private static SessionLogger instance = null;
	BufferedWriter bw = null;
	FileWriter fw = null;
	private static final String FILENAME = "session.tmp";
	
	// Methods
    public static SessionLogger getInstance() {
        if(instance==null)
            instance = new SessionLogger();
        return instance;
    }
    
    SessionLogger(){
		try{
    	
			Path filepath = Paths.get(FILENAME);
			Files.deleteIfExists(filepath);
    		
    		fw = new FileWriter(FILENAME,true);
    		bw = new BufferedWriter(fw);
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    /* In this method we need to open and close the filewriter every time so
     * there can be multiple instances of the MERODE application open.
     * FIX ME. This doesn't work, the process still keeps the file locked.
     */
    public void writeMsg(String message){
    	try{
    		
    		fw = new FileWriter(FILENAME,true);
    		
    		bw.append(message);
    		//bw.flush();	
    		
    		if (fw != null)
				fw.close();
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    /* Execute this when tescav module begins to make sure everything from
     * the current session is writen to the file before we begin processing.
     */
    public void flushBuffer(){
    	try {
    		fw = new FileWriter(FILENAME,true);
    		
    		bw.flush();
			
			if (fw != null)
				fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
