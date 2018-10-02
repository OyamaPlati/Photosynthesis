package Photosynthesis;

public class Land{

    // Sun exposure data here
    private int dimensionX; // Length of matrix 
    private int dimensionY; // Height of matrix
    private float[][] sunExposure; // Sun exposed landscape
    private float[][] shaded; // Shaded landscape
    
    // Only this fraction of light is transmitted by a tree
    static float shadefraction = 0.1f; 

    /**
     * Create Land object with dimension x and y
     * @param dx Length of matrix
     * @param dy Height of matrix
     */
    public Land (int dx, int dy) {
        dimensionX = dx;
        dimensionY = dy;
        sunExposure = new float[dimensionX][dimensionY];
        shaded = new float[dimensionX][dimensionY];
    }

    /**
     * Obtain the x dimension 
     * @return Length of matrix
     */
    public synchronized int getDimX() {
            return dimensionX;
    }

    /**
     * Obtain the y dimension
     * @return Height of matrix
     */
    public synchronized int getDimY() {
            return dimensionY; 
    }

    /**
     * Reset the shaded landscape to the same as the initial sun exposed landscape
     * Needs to be done after each growth pass of the simulator
     */
    public synchronized void resetShade() {
            for (int row = 0; row < dimensionX; row++) {
                System.arraycopy(sunExposure[row], 0, shaded [row], 0, dimensionY);
            }
    }

    /**
     * Obtain sun exposure value at position x and y
     * @param x x position 
     * @param y y position
     * @return sun exposure value
     */
    public synchronized float getFull(int x, int y) {
            return sunExposure[x][y];
    }

    /**
     * Set sun exposure value at position x and y
     * @param x x position 
     * @param y y position
     * @param val sun exposure value
     */
    public synchronized void setFull(int x, int y, float val) {
            sunExposure[x][y] = val;
    }

    /**
     * Obtain shaded value at position x and y
     * @param x x position
     * @param y y position
     * @return Shade value  
     */
    public synchronized float getShade(int x, int y) { 
            return shaded[x][y]; 
    }

    /**
     * Set shaded value at position x and y 
     * @param x x position
     * @param y y position
     * @param val Shade value
     */
    public synchronized void setShade(int x, int y, float val){
            shaded[x][y] = val;
    }
 
    /**
     * Reduce the amount of sun exposure by 10% 
     * @param tree 
     */
    synchronized void shadow(Tree tree) {
        for (int row = Math.abs(tree.getX() - Math.round(tree.getExt())); row < tree.getX() + Math.round(tree.getExt()); row++) {
            for (int column = Math.abs(tree.getY() - Math.round(tree.getExt())); column < tree.getY() + Math.round(tree.getExt()); column++) {                
                if ((row < dimensionX) && (column < dimensionY)) {
                    sunExposure[row][column] = sunExposure[row][column]*shadefraction;
                }               
            }
        }
    }
}