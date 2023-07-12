package tescav;
/**
 * 
 * @author Sofia Alarcon
 *
 */
public final class Consts {

	// MBT criteria
	public static final String AC 	= "All-configurations";
	public static final String AS 	= "All-states";
	public static final String AT 	= "All-transitions";
	public static final String ALFP = "All-loop-free-paths";
	public static final String AOLP = "All-one-loop-paths";
	public static final String AEM 	= "Association-end multiplicity";
	public static final String GEN 	= "Generalization";
	public static final String CA 	= "Class attribute";
	public static final String OV 	= "One-value";	
	public static final String TP	= "Transitions-Pair";
	public static final String AL	= "All-Loops";
	public static final String AM	= "All-Methods";


	// Criterion types
	public static final String EDG = "EDG";
	public static final String FSM = "FSM";
	public static final String TABLE = "table";

	// Element types
	public static final String ATTRIBUTE = "atrribute";
	public static final String STATE = "state";
	public static final String TRANSITION = "transition";
	public static final String CLASS = "class";
	public static final String METHOD = "method";


	//Testing types
	public static final String POSITIVE = "positive";
	public static final String NEGATIVE = "negative";
	public static final String IMPOSSIBLE = "impossible";

	private Consts() {
		throw new AssertionError();
	}

}
