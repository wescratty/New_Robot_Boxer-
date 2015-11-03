/**
 * Created by wescratty on 10/31/15.
 */
public interface Subject {


    void register(Observer o);

    void unregister(Observer o);

    void notifyObserver();
    void setSentMessage();
    void notifyObserverOfPunch();
    void checkDidBlock();
//    boolean observerCheckDidBLock();





}