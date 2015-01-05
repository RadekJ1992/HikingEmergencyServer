package pl.rj.hikingemergency.utils;

/**
 * Listener nawiązujący do obiektu NotifyThread, nasłuchuje ukończenia działania wątku
 * Created by radoslawjarzynka on 21.10.14.
 */
public interface ThreadCompleteListener {
    void notifyOfThreadComplete(final Thread thread);
}
