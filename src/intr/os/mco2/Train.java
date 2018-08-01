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
public class Train implements Runnable{
    //thread that moves between stations and picks up passengers
    //trains are threads and they acquire locks on stations
    private Area currentArea;
    private final int totalCount; //number of total seats
    private int currCount;
    private ReentrantLock lock;
    private Condition trainCond;
    private ArrayList <Passenger> passengers;                               //maybe need this for passengers leaving the train???
    
    public Train(int totalCount){
        this.totalCount = totalCount;
        this.currCount = totalCount;
    }
    
    public void setArea(Area area){
        this.currentArea = area;
    }
    
    public void StationLoadTrain(Station station, int count){
        station.LockAcquire();
        
        if(count == 0 || station.getPassengers().isEmpty()){
            if(station.nextArea.trainPresent == false){
                this.currentArea = station.nextArea;        //moves to next area
            }else{
                
            }
        }else{
            station.trainPresent = true;
            station.currentTrain = this;
        }
        
        station.LockRelease();
    }

    public int getCurrCount(){
        return this.currCount;
    }
    
    public void addCurrCount(){
        currCount++;
    }
    
    public void subCurrCount(){
        currCount--;
    }
    
    public int getTotalCount(){
        return this.totalCount;
    }
    
    public void LockAcquire(){
        this.lock.lock();
    }
    
    public void LockRelease(){
        this.lock.unlock();
    }
    
    public void CondInit(){
        trainCond = this.lock.newCondition();
    }
    
    public void CondWait(){             //process of waiting for train
        try {
            trainCond.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(Area.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void CondSignal(){           //signal to wait for train
        trainCond.signal();
    }
    
    public void CondBroadcast(){
        trainCond.signalAll();
    }
    
    
    @Override
    public void run() {
        while(true){
            if(currentArea instanceof Station){
                StationLoadTrain((Station)currentArea, 10);
            }
        }
    }
}
