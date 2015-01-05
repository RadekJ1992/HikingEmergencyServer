package pl.rj.hikingemergency.utils;

/**
 * Interfejs będący implementacją wzorca projektowego obserwatora - obiekt obserwowany
 * Created by radoslawjarzynka on 04.11.14.
 */
public interface Subject {
    /**
     * Dodanie obserwatora
     * @param obj
     */
    public void register(Observer obj);

    /**
     * usunięcie obserwatora
     * @param obj
     */
    public void unregister(Observer obj);

    /**
     * Powiadomienie obserwatorów
     */
    public void notifyObservers();

}
