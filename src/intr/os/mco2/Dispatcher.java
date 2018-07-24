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
public class Dispatcher {
    //the "user". decides how many trains to send.
    
    public void StationInit(){
        Area[] areas = new Area[16];
        
        for(int i = 0; i < 16; i++){
            if(i % 2 == 0){
                areas[i] = new Station();
            }else{
                areas[i] = new EmptyArea();
            }
        }
    }
    
    public void CreateTrain(Area area, int count){
        Train train = new Train(count);
        train.setArea(area);
        
        if(area instanceof Station){
            ((Station) area).TrainArrives(train);
        }else{
            area.NoTrain();
        }
    }
    
    
}
