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
    
    public void StationWaitForTrain(Station station) throws InterruptedException{   //only boards when a train is present and it has available seats
        synchronized(station){
            if(station.trainPresent == false){
                System.out.println("waiting...");           //wait on station, if train is present, gets notified and starts boarding
            }else{
                station.CreatePassengers();
            }
            station.wait();
        }
    }
    
    public void StationOnBoard(Station station){        //to notify that the patient has boarded a train
        synchronized(station.currentTrain){
            station.currentTrain.notify();   
        }
        
        if(station.currentTrain.getCurrCount() == 0){
            station.currentTrain.setArea(station.nextArea);
        }
    }

    @Override
    public void run() {
        try {
            StationWaitForTrain(currentStation);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
}
