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
public class Dispatcher {
    //the "user". decides how many trains to send.
    private Area[] areas;
    
    public Dispatcher(){
        StationInit();
    }
    
    public void StationInit(){
        areas = new Area[16];
        
        for(int i = 0; i < 16; i++){
            if(i % 2 == 0){
                areas[i] = new Station();
                
            }else{
                areas[i] = new EmptyArea();
            }
            
            if(i == 15){
                areas[i].nextArea = areas[0];
            }else
                areas[i].nextArea = areas[i+1];
                   
        }
    }
    
    public void CreateTrain(Area area, int count){
        Train train = new Train(count);
        System.out.println("created train");
        
        area.TrainArrives(train);
        train.setArea(area);
        
        Thread trainThread = new Thread(train);
        trainThread.start();
    }
    
    public Area[] getAreas(){
        return this.areas;
    }
    
    public Area getArea(int num){
        return this.getAreas()[num];
    }
    
    public Train getTrain(Area area){
        return area.currentTrain;
    }
    
    public ArrayList <Passenger> getPassengersAtStation(Station station){
        return station.getPassengers();
    }
}
