/**
 * Attention: Generated source! Do not modify by hand!
 * Generated by: Merode Code Generator 2.0
 */
package dao; //.gui;

import javax.swing.JTextArea;
import tescav.SessionLogger;

public class MerodeLogger 
{
	// Not suited for multiple clients...
	public static StringBuffer buffer = new StringBuffer();
	
	// visual component to include somewhere if required
	public static JTextArea log = new JTextArea();
	
	public static void log (String message) 
	{
		buffer.append(message);
		
		//fill in the textArea
		log.append(message);
	    MerodeLogger.log.getCaret().setVisible(true);
	}

	public static void logln (String message) 
	{
		buffer.append(message);
		buffer.append("\n");
		
		//fill in the textArea
		log.append(message + "\n");
	    MerodeLogger.log.getCaret().setVisible(true);
		SessionLogger.getInstance().writeMsg(message+"\n");
	}
    
	public static String flush () 
	{
		if (buffer.length()>0 && (buffer.lastIndexOf ("\n") == buffer.length() - 1))
			buffer = buffer.deleteCharAt(buffer.length() - 1);
		String result = buffer.toString();
		buffer = new StringBuffer();
		return result;
	}
	
	
}
