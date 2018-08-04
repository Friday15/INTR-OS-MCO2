/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intr.os.mco2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Emir Mendoza
 */
public class Controller {
    private final Dispatcher disp;
    private final RailroadView rv;
    
    public Controller(Dispatcher disp){
        this.disp = disp;
        this.rv = new RailroadView(disp, this);
        this.rv.setVisible(true);
        
        rv.addCreatePassengerActionListener(new PassengerListener());
        rv.addCreateTrainActionListener(new TrainListener());
    }
   
    class PassengerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            Area chosenArea;
            if(rv.getAreaList().isSelectionEmpty() == false){
                chosenArea = disp.getArea(rv.getAreaList().getSelectedIndex());

                if(chosenArea instanceof Station){
                    System.out.println("created passenger");
                    ((Station) chosenArea).CreatePassengers();
                }
            }  
        }
        
    }
    
    class TrainListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            Area chosenArea;
            if(rv.getAreaList().isSelectionEmpty() == false){
                chosenArea = disp.getArea(rv.getAreaList().getSelectedIndex());
                
                if(chosenArea.trainPresent == false && chosenArea.currentTrain == null){
                    int chosenTotalSeats = (Integer) rv.getSeatSpinner().getValue();
                    rv.getStatusLabels().get(disp.getTrainCount()).setText("hi");
                    disp.CreateTrain(chosenArea, chosenTotalSeats);
                    
                }
            }
        }
        
    }
}
