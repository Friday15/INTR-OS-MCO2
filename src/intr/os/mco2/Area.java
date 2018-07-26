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
public abstract class Area {
    protected boolean trainPresent;     //if a train is currently on the area or not
    protected Train currentTrain;       //the train currently on it
    protected Area nextArea;
    
    public void TrainArrives(Train train){
        this.trainPresent = true;
        this.currentTrain = train;
    }
    
    public void RemoveTrain(){
        this.trainPresent = false;
        this.currentTrain = null;
    }
    
}
