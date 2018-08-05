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
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        HomeScreen home = new HomeScreen();
        home.setVisible(true);
        
        

//        Scanner memes = new Scanner(System.in);
//        Dispatcher disp = new Dispatcher();
//        disp.StationInit();
//        
//        try {
//            ((Station)disp.getAreas()[0]).CreatePassengers();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Driver.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        //Thread.sleep(5000);
//        System.out.println("choose number of seats");
//        int choice = memes.nextInt();
//        
//        Train train = new Train(choice);
//        
//        System.out.println("Dispatch train at station number: ");
//        int staNum = memes.nextInt();
//        train.setArea(disp.getAreas()[staNum]);
    }
    
}
