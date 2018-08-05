/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intr.os.mco2;

import java.util.ArrayList;

/**
 *
 * @author Ashen One
 */
public class Station extends Area{
    private PassengerMaker pm;
    //stations are monitors for train threads
    //when a train arrives, it will acquire the lock on the station.
    //train will wait, passengers will get notified (notifyall()) and load on the train
    //
    public Station(){
        RemoveTrain();
        passengers = new ArrayList();
        this.LockInit();
        this.CondInit();
        
//        pm = new PassengerMaker();
//        Thread pmThread = new Thread(pm);
//        pmThread.start();
    }
    
    public void CreatePassengers(){
        this.LockAcquire();
        Passenger passenger = new Passenger(this);
        passengers.add(passenger);
        
        Thread passengerThread = new Thread(passenger);
        passengerThread.start();
        
        this.LockRelease();

    }
    
    public void PassengerBoarded(Passenger passenger){
        passengers.remove(passenger);
    }
    
}
