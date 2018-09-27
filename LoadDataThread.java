package Photosynthesis;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author oyama
 */
public class LoadDataThread extends Thread {
    private Land sunmap; 
    private Tree [] trees;
    private static String fileName;
    private int threads;
    private int size;
    private int rows;

    public LoadDataThread (int threads, String fileName, int size) {
        this.fileName = fileName;
        this.threads = threads;
        this.size = size;
        this.rows = size/threads;
    } 
    
    @Override
    public void run () {
         try{ 
            Scanner sc = new Scanner(new File(fileName));

            // load sunmap
            int dimx = sc.nextInt(); 
            int dimy = sc.nextInt();
            assert (dimx == 3000 && dimy == 300) : "Not 3000 x 3000 matrix";
            if (dimx == dimy) {
                size = dimx;
            }
            
            assert (size == dimx) : "Size not 3000";
            
            sunmap = new Land(rows,size);
            for(int x = 0; x < rows; x++)
                for(int y = 0; y < size; y++) {
                    float val = sc.nextFloat();
                    sunmap.setFull(x,y,val);	
                    //System.out.println ("Sun exposure :=" + val);
            }
            sunmap.resetShade(rows, size);

            // load forest
            int numt = sc.nextInt();
            trees = new Tree[numt];
            for(int t=0; t < numt; t++)
            {
                int xloc = sc.nextInt();
                int yloc = sc.nextInt();
                float ext = (float) sc.nextInt();
                trees[t] = new Tree(xloc, yloc, ext);
            }
            sc.close(); 
        } 
        catch (IOException e){ 
            System.out.println("Unable to open input file "+fileName);
            System.out.println(e.getMessage());
        }
        catch (java.util.InputMismatchException e){ 
            System.out.println("Malformed input file "+fileName);
            System.out.println(e.getMessage());
        }
    }
}
