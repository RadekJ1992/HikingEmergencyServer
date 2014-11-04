package pl.rj.hikingemergency.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

/**
 * Created by radoslawjarzynka on 18.10.14.
 */
public final class Log {

    private static volatile Log instance = null;
    private static Vector<String> log;

    public static Log getInstance() {
        if (instance == null) {
            synchronized (Log.class) {
                if (instance == null) {
                    instance = new Log();
                }
            }
        }
        return instance;
    }

    private Log() {
        log = new Vector<String>();
    }

    //dodanie linii logu
    public synchronized void addLine(String line) {
        Date curDate = new Date();
        log.add(curDate.toString() + " : " + line);
        System.out.println(line);
    }

    //pobranie danej linijki kodu
    public String getLine(int lineNo) {
        return log.get(lineNo);
    }

    //zapisanie logu do pliku tekstowego
    public synchronized void saveLogToFile() {
        File file = new File("logs" + File.separator + new Date().toString().replace(" ","") + ".log");
        file.getParentFile().mkdirs();
        FileWriter fw;

        try {
            fw = new FileWriter(file);

            for (String s : this.log) {
                fw.write(s + "\n");
            }

            fw.close();
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }

    //pobranie calego wektora stringow
    public Vector<String> getLog() {
        return log;
    }
}
