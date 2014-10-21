package pl.rj.hikingemergency.utils;

import pl.rj.hikingemergency.Constants;

import java.io.*;
import java.net.URL;

/**
 * Created by radoslawjarzynka on 21.10.14.
 */
public class MapDownloader extends NotifyingThread {

    private String URL;

    public MapDownloader(String URL) {
        this.URL = URL;
    }

    public void doRun() {
        try {
            File destinationFile = new File(Constants.DESTINATION_MAP_LOCATION);
            destinationFile.getParentFile().mkdirs();
            java.net.URL url = new URL(this.URL);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
