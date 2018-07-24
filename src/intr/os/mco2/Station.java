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
public class Station extends Area{
    private Passenger[] passengers;     //list of passengers in station
    private StationThread stationThread;    //thread where passengers are "created"
    
    //stations are monitors for train threads
    //passengers keep arriving at random(?)
    //when a train arrives, it will acquire the lock on the station.
    //train will wait(), passengers will get notified (notifyall()) and load on the train
    //
    public Station(){
        NoTrain();
        stationThread = new StationThread();
        stationThread.run();
    }
    
}
