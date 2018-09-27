/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Photosynthesis;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oyama
 */
public class TestLoadDataThread {
    
    public static void main (String [] args) {
        // Declare and initialise 
        int size = 3000;
        int threads = 100;
        LoadDataThread[] t = new LoadDataThread[threads]; 
        for (int i = 0; i < threads; i++) {
            t[i] = new LoadDataThread (threads, args[0], size);
        }
        
        for (int i = 0; i < threads; i++) {
            t[i].start();
        }
        
        for (int i = 0; i < threads; i++) {
            try {
                t[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(TestLoadDataThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println ("Data loaded");
    }
}
