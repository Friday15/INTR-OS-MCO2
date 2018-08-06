/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intr.os.mco2;

import java.util.ArrayList;
import java.util.Random;
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
    
    public Train(int totalCount){
        this.totalCount = totalCount;
        this.currCount = totalCount;
        LockInit();
        CondInit();
    }
    
    public void setArea(Area area){
        this.currentArea = area;
    }
    
    public void StationLoadTrain(Station station, int count){
        station.LockAcquire();

        station.TrainArrives(this);
        if(this.totalCount - this.currCount > 0){                               //checks if a passenger is on board
            Random rand2 = new Random();
            int passengerChance = rand2.nextInt(this.totalCount-this.currCount)+1;      //random number of passengers to disembark
        
            for(int i = 0; i < passengerChance; i++){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                }
               this.addCurrCount();
               System.out.println("passenger disembark");
            }
        }
        station.CondBroadcast();
        System.out.println("train is waiting...");
        station.CondWait();
        System.out.println("train is awokened!");
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
        boolean disembark = false;
        Random rand = new Random();
        int disembarkChance = rand.nextInt(100)+1;                  //chance for a passenger to disembark
        if(this.currCount == this.totalCount){
            if(disembarkChance % 3 == 0){
                disembark = true;
            }
        }
        
        System.out.println("disembark: "+disembark);
        //if(this.currCount == 0)
        while((disembark == false && currentArea.getPassengers().isEmpty())|| !(currentArea instanceof Station)){
            
            if(currentArea.getPassengers() != null){
                if(!(currentArea.getPassengers().isEmpty())){                   //move if instanceof EmptyArea
                    System.out.println("found passengers");                     //move if station but passenger list found and is empty
                    break;
                }
            }
            
            System.out.println(currentArea);
            if(currentArea.nextArea.trainPresent == false){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                currentArea.LockAcquire();
                if(currentArea.lock.hasWaiters(currentArea.cond))
                    currentArea.CondSignal();
                currentArea.LockRelease();
                
                currentArea.RemoveTrain();
                this.currentArea = currentArea.nextArea;        //moves to next area
                currentArea.TrainArrives(this);
                System.out.println("moved to next area");
                
                disembarkChance = rand.nextInt(100)+1;
                if(disembarkChance % 3 == 0){
                    disembark = true;
                }
            }else{
                currentArea.nextArea.LockAcquire();
                System.out.println("blocked by another train!");
                currentArea.nextArea.CondWait();
                System.out.println("no longer blocked by another train!");
                currentArea.nextArea.LockRelease();
            }
        }
        
        if(currentArea instanceof Station){
            StationLoadTrain((Station)currentArea, this.currCount);
            this.run();
        }
    }
}
