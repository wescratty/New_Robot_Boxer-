/**
 * Created by wescratty on 10/31/15.
 */
public interface Subject {


    void register(Observer o);

    void unregister(Observer o);

    void notifyObserver();
    void setSentMessage(Attack a);
    void notifyObserverOfPunch();
    void checkDidBlock();
    void upDateLabels();
    void takeDamage(int damage);
//    void calculateDamage();

}