package Photosynthesis;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        Dimension fsize = new Dimension(400, 400);
        // Frame init and dimensions
        JFrame frame = new JFrame("Photosynthesis"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(fsize);
        frame.setSize(400, 400);

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

        buttons.add(reset);
        buttons.add(pause);
        buttons.add(play);
        buttons.add(end);
        g.add(buttons);
        
        frame.setLocationRelativeTo(null);  // Center window on screen.
        frame.add(g); //add contents to window
        frame.setContentPane(g);  
        frame.pack();
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
        sundata.readData(args[0].trim());
        System.out.println("Data loaded"); 

        frameX = sundata.sunmap.getDimX();
        frameY = sundata.sunmap.getDimY();
        setupGUI(frameX, frameY, sundata.trees); 

        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // create and start simulation loop here as separate thread
                for (Tree aTree : sundata.trees) {
                    for (int range = 18; range >= 0; range-= 2) {
                        if (aTree.inrange((float)range, (float)range + 2.0f)) {
                            /*  
                                1. Calculate the average sunlight (s) in the cells that the trees cover
                                2. Reduce the sunlight in these cells to 10% of their original value. 
                                   Thus, trees in later layers will receive less sunlight. 
                                   This simulates the filtering of sunlight through the canopy in a forest.
                                3. A tree then grows in proportion to the average sunlight 
                                   divided by a factor of 1000 : newextent = extent + s/ 1000.
                            
                            sundata.sunmap.shadow(aTree);
                            aTree.sungrow(sundata.sunmap);
                        }
                    }
                }
            }
        });*/
    }
}