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
    private ArrayList <Passenger> passengers;     //list of passengers in station
    
    //stations are monitors for train threads
    //when a train arrives, it will acquire the lock on the station.
    //train will wait, passengers will get notified (notifyall()) and load on the train
    //
    public Station(){
        NoTrain();
        passengers = new ArrayList();
    }
    
    public void CreatePassengers() throws InterruptedException{
        Passenger passenger = new Passenger(this);
        passengers.add(passenger);
        passenger.run();
        System.out.println("After run");
        passenger.StationWaitForTrain(this);
    }
    
    public void PassengerBoarded(Passenger passenger){
        passengers.remove(passenger);
    }
}
