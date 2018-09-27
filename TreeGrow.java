package Photosynthesis;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TreeGrow {
    static long startTime = 0;
    static int frameX;
    static int frameY;
    static ForestPanel fp;

    private static JButton reset;
    private static JButton pause;
    private static JButton play;
    private static JButton end;

    private static int years; // generations

    // start timer
    private static void tick(){
        startTime = System.currentTimeMillis();
    }

    // stop timer, return time elapsed in seconds
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f; 
    }

    public static void setupGUI(int frameX,int frameY,Tree [] trees) {
        Dimension fsize = new Dimension(800, 800);
        // Frame init and dimensions
        JFrame frame = new JFrame("Photosynthesis"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(fsize);
        frame.setSize(800, 800);

        JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
        g.setPreferredSize(fsize);

        fp = new ForestPanel(trees);
        fp.setPreferredSize(new Dimension(frameX,frameY));
        JScrollPane scrollFrame = new JScrollPane(fp);
        fp.setAutoscrolls(true);
        scrollFrame.setPreferredSize(fsize);
        g.add(scrollFrame);

        reset = new JButton ("reset");
        reset.addActionListener(new ActionListener () {                
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sets the extent of all trees to a value of 0.4
                for (Tree aTree : trees) {
                    aTree.setExt(0.4f);
                }
                // sets the the year count to 0
                years = 0;
            }
        });

        pause = new JButton ("pause");
        pause.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                // Temporarily stops the simulation

            }
        });

        play = new JButton ("play");
        play.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                // Either starts the simulation or 
                // Allows it to continue running if it was previously paused

            }

        });

        end = new JButton ("end");
        end.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                // Closes the window and exits the program
                System.exit(0);
            }            
        });

        frame.setLocationRelativeTo(null);  // Center window on screen.
        frame.add(g); //add contents to window
        frame.setContentPane(g);     
        frame.setVisible(true);
        Thread fpt = new Thread(fp);
        fpt.start();
    }


    public static void main(String[] args) {
        SunData sundata = new SunData();

        // check that number of command line arguments is correct
        if(args.length != 1)
        {
            System.out.println("Incorrect number of command line arguments. Should have form: java treeGrow.java intputfilename");
            System.exit(0);
        }

        // Read in forest and landscape information from file supplied as argument
        // Declare and initialise 
        int size = 3000;
        int threads = 100;
        LoadDataThread[] t = new LoadDataThread[threads]; 
        for (int i = 0; i < threads; i++) {
            t[i] = new LoadDataThread (threads, args[0].trim(), size);
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
        
        /* sundata.readData(args[0].trim());
           System.out.println("Data loaded"); 

        frameX = sundata.sunmap.getDimX();
        frameY = sundata.sunmap.getDimY();
        setupGUI(frameX, frameY, sundata.trees); */

        // create and start simulation loop here as separate thread
    }
}