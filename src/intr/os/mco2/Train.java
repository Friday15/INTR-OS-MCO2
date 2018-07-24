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
public class Train implements Runnable{
    //thread that moves between stations and picks up passengers
    //trains are threads and they acquire locks on stations
    private Area currentArea;
    private final int totalCount; //number of total seats
    
    public Train(int totalCount){
        this.totalCount = totalCount;
    }
    
    public void setArea(Area area){
        this.currentArea = area;
    }
    
    public void StationLoadTrain(Station station, int count){
        
    }

    @Override
    public void run() {
        
    }
}
