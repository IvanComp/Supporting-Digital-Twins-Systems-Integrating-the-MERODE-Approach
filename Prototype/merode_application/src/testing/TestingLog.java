package testing;
import dao.MerodeLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 * Created by Arturo Mantinetti
 */
// Final: objeto se crea una vez y no se puede modificar. Se suele usar para el uso de variables globales.
 public final class TestingLog {
    private static TestingLog instance = null;
    private static String FILE_NAME = "test.log";
    private static Writer fileOut = null;

    // Patrón singleton:
    // Static: creo el objeto y queda ahí hasta que se cierre el programa. Va a estar usando la misma memoria. El dinámico (public a secas) se va creando y destruyendo.
    public static TestingLog getInstance() {
        if(instance==null)
            instance = new TestingLog();
        return instance;
    }

    TestingLog(){
        System.out.println("Log Begin Create" );
        File f = new File(FILE_NAME);

        try{
            if(!f.exists())
                f.createNewFile();
            fileOut = new BufferedWriter(new FileWriter(FILE_NAME, true));
            System.out.println("Log END Create" );
        }catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    void writeLog(String toWrite){
        System.out.println("Log Write" );
        try{
            fileOut.append(toWrite + "\n");
            fileOut.flush();
        }catch (Exception e){
            System.out.println("Log Error Write" );
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        MerodeLogger.logln(toWrite);
    }
}
