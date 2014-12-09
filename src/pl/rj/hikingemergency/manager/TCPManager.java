package pl.rj.hikingemergency.manager;

import pl.rj.hikingemergency.Constants;
import pl.rj.hikingemergency.model.Log;
import pl.rj.hikingemergency.model.Message;
import pl.rj.hikingemergency.utils.MessagesHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by radoslawjarzynka on 29.10.14.
 */
public class TCPManager {

    private BufferedReader in;
    private PrintWriter out;
    private volatile ConcurrentLinkedQueue<String> outgoingMessages;

    private Thread readerThread;
    private Thread writerThread;
    private Reader reader;
    private Writer writer;

    private Socket socket;

    private static volatile TCPManager instance = new TCPManager();

    public static TCPManager getInstance() {
        return instance;
    }
    private TCPManager() {
        outgoingMessages = new ConcurrentLinkedQueue<>();
        Connect();
    }

    public void SendMessage(String str) {
        outgoingMessages.add(str);
    }

    private void Connect() {
        try {
            socket = new Socket(Constants.SERVER_IP, Constants.SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            reader = new Reader();
            writer = new Writer();
            readerThread = new Thread(reader);
            writerThread = new Thread(writer);
            readerThread.start();
            writerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
            Log.getInstance().addLine(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void Disconnect() {
        reader.setWork(false);
        writer.setWork(false);
        in = null;
        out = null;
        socket = null;
        reader = null;
        writer = null;
    }

    private class Reader implements Runnable {
        private boolean doWork;
        @Override
        public void run() {
            try {
                while (doWork) {
                    try {
                        String line = in.readLine();
                        Message msg = new Message(line);
                        MessagesHandler.getInstance().addMessage(msg);
                        Log.getInstance().addLine("Incoming TCP message: " + line);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.getInstance().addLine(e.getClass().getName() + ": " + e.getMessage());
                    }
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Log.getInstance().addLine(e.getClass().getName() + ": " + e.getMessage());
            }
        }

        public Reader() {
            doWork = true;
        }

        public void setWork(boolean doWork) {
            this.doWork = doWork;
        }
    }

    private class Writer implements Runnable {
        private boolean doWork;
        @Override
        public void run() {
            try {
                while (doWork) {
                        String line = outgoingMessages.poll();
                        if (line != null) {
                            out.println(line);
                            Log.getInstance().addLine("Outgoing TCP message: " + line);
                        }
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.getInstance().addLine(e.getClass().getName() + ": " + e.getMessage());
            }
        }

        public Writer() {
            doWork = true;
        }

        public void setWork(boolean doWork) {
            this.doWork = doWork;
        }
    }
}
