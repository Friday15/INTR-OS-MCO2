/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intr.os.mco2;

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
        System.out.println("meep2");
        station.LockRelease();
    }
    
    public void StationOnBoard(Station station){        //to notify that the patient has boarded a train
        
        System.out.println("boarding...");
        station.LockAcquire();
        station.PassengerBoarded(this);
        station.LockRelease();
        
        station.currentTrain.LockAcquire();
        station.currentTrain.subCurrCount();
        station.currentTrain.LockRelease();
        
        currentStation = null;                          //inside the train already
    }

    @Override
    public void run() {
        
        //currentStation.TrainArrives(new Train(10));

        while(currentStation.trainPresent == false){
            StationWaitForTrain(currentStation);
            
        }
        
        while(currentStation.trainPresent == true && currentStation.currentTrain.getCurrCount() > 0){
            StationOnBoard(currentStation);
            
        }
//        if(currentStation.currentTrain.getCurrCount() == 0){                  //if train is full attempt to move to next area
//            currentStation.currentTrain.setArea(currentStation.nextArea);
//        }
        
    }
    
}
