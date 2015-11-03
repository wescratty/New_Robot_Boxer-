/**
 * Created by wescratty on 10/31/15.boolean  checkDidBLock();
 */
public interface Observer {

     void update();

    //    HurtBox calculation;
     boolean  notifyRange();
     boolean  notifyPunch();


     int notifyDamage();
     int  calculateDamage();
    int getObserverId();
    void observerCheckDidBLock();

}