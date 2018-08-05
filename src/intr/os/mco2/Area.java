/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intr.os.mco2;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ashen One
 */
public abstract class Area {
    protected ArrayList <Passenger> passengers;     //list of passengers in station
    protected boolean trainPresent;     //if a train is currently on the area or not
    protected Train currentTrain;       //the train currently on it
    protected Area nextArea;
    protected ReentrantLock lock;
    protected Condition cond;
    
    public void TrainArrives(Train train){
        this.trainPresent = true;
        this.currentTrain = train;
    }
    
    public void RemoveTrain(){
        this.trainPresent = false;
        this.currentTrain = null;
    }
    
    public void LockInit(){
        this.lock = new ReentrantLock();
    }
    
    public void LockAcquire(){
        this.lock.lock();
    }
    
    public void LockRelease(){
        this.lock.unlock();
    }
    
    public void CondInit(){
        cond = this.lock.newCondition();
    }
    
    public void CondWait(){
        try {
            cond.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(Area.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void CondSignal(){
        cond.signal();
    }
    
    public void CondBroadcast(){
        cond.signalAll();
    }
    
    public ArrayList <Passenger> getPassengers(){
        return this.passengers;
    }
}
