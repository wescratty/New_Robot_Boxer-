/**
 * Created by wescratty on 10/31/15.boolean  checkDidBLock();
 */
public interface Observer {

    void update();
    void  notifyPunch(Attack a,int attackerID);
    int getObserverId();
    void observerCheckDidBLock();

}