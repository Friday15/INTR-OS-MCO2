/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intr.os.mco2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ashen One
 */
public class Passenger implements Runnable{
    //a thread that can wait and board
    private Station currentStation;
    
    public Passenger(Station currentStation){
        this.currentStation = currentStation;

    }
    
    public void StationWaitForTrain(Station station){   //only boards when a train is present and it has available seats
   
        System.out.println("waiting...");           //wait on station, if train is present, gets notified and starts boarding
        station.LockAcquire();
        
        station.CondWait();

        station.LockRelease();
    }
    
    public void StationOnBoard(Station station){        //to notify that the patient has boarded a train
        
        System.out.println("boarding...");
        station.LockAcquire();
        station.PassengerBoarded(this);
        station.LockRelease();
        
        station.currentTrain.LockAcquire();
        station.currentTrain.subCurrCount();
        if(station.currentTrain.getCurrCount() == 0){
            station.LockAcquire();
            station.CondSignal();
            station.LockRelease();
        }
        station.currentTrain.LockRelease();

        currentStation = null;                          //inside the train already
    }

    @Override
    public void run() {

        if(currentStation.trainPresent == false){
            StationWaitForTrain(currentStation);   
        }
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(currentStation.trainPresent == true && currentStation.currentTrain.getCurrCount() > 0){
            StationOnBoard(currentStation);
            
        }
        
    }
    
}
