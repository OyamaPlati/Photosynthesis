package Photosynthesis;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TreeGrow extends JFrame implements ActionListener {
    static long startTime = 0;
    static int frameX;
    static int frameY;
    static ForestPanel fp;

    private static JButton reset;
    private static JButton pause;
    private static JButton play;
    private static JButton end;

    private static int years; // generations
    private static final int PAUSE = 20;
    private static SunData sundata;
    // start timer
    private static void tick(){
        startTime = System.currentTimeMillis();
    }

    // stop timer, return time elapsed in seconds
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f; 
    }

    public TreeGrow(int frameX,int frameY,Tree [] trees) {
        Dimension fsize = new Dimension(400, 400);
        // Frame init and dimensions
        setTitle ("Photosynthesis");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(fsize);
        setSize(400, 400);

        JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
        g.setPreferredSize(fsize);

        fp = new ForestPanel(trees);
        fp.setPreferredSize(new Dimension(frameX,frameY));
        JScrollPane scrollFrame = new JScrollPane(fp);
        fp.setAutoscrolls(true);
        scrollFrame.setPreferredSize(fsize);
        g.add(scrollFrame);
            
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
        
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
        
        // Either starts the simulation or 
        // Allows it to continue running if it was previously paused
        play = new JButton ("play");
        play.addActionListener (this);

        end = new JButton ("end");
        end.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                // Closes the window and exits the program
                System.exit(0);
            }            
        });

        buttons.add(reset);
        buttons.add(pause);
        buttons.add(play);
        buttons.add(end);
        g.add(buttons);
        
        setLocationRelativeTo(null);  // Center window on screen.
        add(g); //add contents to window
        setContentPane(g);  
        pack();
        Thread fpt = new Thread(fp);
        fpt.start();
    }

    public static void main(String[] args) {
        sundata = new SunData();
        // check that number of command line arguments is correct
        if(args.length != 1)
        {
            System.out.println("Incorrect number of command line arguments. Should have form: java treeGrow.java intputfilename");
            System.exit(0);
        }
        // Read in forest and landscape information from file supplied as argument
        sundata.readData(args[0].trim());
        System.out.println("Data loaded"); 

        frameX = sundata.sunmap.getDimX();
        frameY = sundata.sunmap.getDimY();
        TreeGrow grow = new TreeGrow (frameX, frameY, sundata.trees); 
        grow.setVisible (true);    
        
        // create and start simulation loop here as separate thread 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Simulation simulate = new Simulation ();
        simulate.start();
    }
    
    private class Simulation extends Thread {
        @Override
        public void run () {
            for (int range = 0; range <= 18; range += 2) {
                for (Tree aTree : sundata.trees) {
                    if (aTree.inrange((float)range, (float)range + 2.0f)) {
                        aTree.sungrow(sundata.sunmap);
                        sundata.sunmap.shadow(aTree);                       
                        years++;
                        System.out.println ("Year := " + years);
                    }                    
                    // doNothing (PAUSE);
                }
            }           
        }
        
        public void doNothing (int millisecond) {
            try {
                Thread.sleep (millisecond);
            } catch (InterruptedException ex) {
                Logger.getLogger (TreeGrow.class.getName ()).log (Level.SEVERE, null, ex);
            }            
        }
    }
}