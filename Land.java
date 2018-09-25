package Photosynthesis;

public class Land{
	
	// Sun exposure data here
        private int dimensionX; // Length of matrix 
        private int dimensionY; // Height of matrix
        private float[][] sunExposure; // Sun exposed landscape
        private float[][] shaded; // Shaded landscape
        
	static float shadefraction = 0.1f; // Only this fraction of light is transmitted by a tree
        
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
	public int getDimX() {
		return dimensionX;
	}
	
        /**
         * Obtain the y dimension
         * @return Height of matrix
         */
	public int getDimY() {
		return dimensionY; 
	}
	
        /**
         * Reset the shaded landscape to the same as the initial sun exposed landscape
         * Needs to be done after each growth pass of the simulator
         */
	public void resetShade() {
		for (int row = 0; row < sunExposure.length; row++) {
                    for (int column = 0; column < sunExposure[row].length; column++) {
                        shaded [row][column] = sunExposure[row][column];
                    }
                }
	}
	
        /**
         * Obtain sun exposure value at position x and y
         * @param x x position 
         * @param y y position
         * @return sun exposure value
         */
	public float getFull(int x, int y) {
		return sunExposure[x][y];
	}
	
        /**
         * Set sun exposure value at position x and y
         * @param x x position 
         * @param y y position
         * @param val sun exposure value
         */
	public void setFull(int x, int y, float val) {
		sunExposure[x][y] = val;
	}
	
        /**
         * Obtain shaded value at position x and y
         * @param x x position
         * @param y y position
         * @return Shade value  
         */
	public float getShade(int x, int y) { 
		return shaded[x][y]; 
	}
	
        /**
         * Set shaded value at position x and y 
         * @param x x position
         * @param y y position
         * @param val Shade value
         */
	public void setShade(int x, int y, float val){
		shaded[x][y] = val;
	}
	
	// reduce the 
	void shadow(Tree tree) {
            for (int row = tree.getX() - Math.round(tree.getExt()); row <= tree.getX() + Math.round(tree.getExt()); row++) {
                for (int column = tree.getY() - Math.round(tree.getExt()); column <= tree.getY() + Math.round(tree.getExt()); column++) {
                    shaded [row][column] = shaded[row][column]*shadefraction;
                }
            }
	}
}