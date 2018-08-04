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
        System.out.println("total count: " + this.totalCount);
        System.out.println("current count: " + this.currCount);
        LockInit();
        CondInit();
    }
    
    public void setArea(Area area){
        this.currentArea = area;
    }
    
    public void StationLoadTrain(Station station, int count){
        station.LockAcquire();

        station.TrainArrives(this);
        station.CondBroadcast();
        System.out.println("train is waiting");
        station.CondWait();
        System.out.println("train is awokened");
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
        
        //currentArea.getPassengers().isEmpty()
        while(this.currCount == 0 || !(currentArea instanceof Station)){

            if(currentArea.nextArea.trainPresent == false){
                System.out.println("potato");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                currentArea.RemoveTrain();
                this.currentArea = currentArea.nextArea;        //moves to next area
                
                System.out.println("moved to next area");

            }
            System.out.println("tomato");
        }
        
        if(currentArea instanceof Station){
            StationLoadTrain((Station)currentArea, this.currCount);
            this.run();
        }
    }
}
