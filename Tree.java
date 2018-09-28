package Photosynthesis;

// Trees define a canopy which covers a square area of the landscape
public class Tree{
	
private
    int xpos;	// x-coordinate of center of tree canopy
    int ypos;	// y-coorindate of center of tree canopy
    float ext;	// extent of canopy out in vertical and horizontal from center

    static float growfactor = 1000.0f; // divide average sun exposure by this amount to get growth in extent
	
public	
    Tree(int x, int y, float e){
            xpos=x; ypos=y; ext=e;
    }

    synchronized int getX() {
            return xpos;
    }

    synchronized int getY() {
            return ypos;
    }

    synchronized float getExt() {
            return ext;
    }

    synchronized void setExt(float e) {
            ext = e;
    }

    /**
     * Average intensity of sunlight covered by tree
     * @param land Sun exposed landscape
     * @return The average sunlight for the cells covered by the tree 
     */
    public float sunexposure(Land land){
        synchronized (land){
            float sum = 0.0f;
            int number = 0;
            for (int i = Math.abs(xpos - Math.round(ext)); i < xpos + Math.round(ext); i++) {
                for (int j = Math.abs(ypos - Math.round(ext)); j < ypos + Math.round(ext); j++) {
                    if ((i < land.getDimX()) && (j < land.getDimY())) {
                        sum = sum + land.getFull (i, j);
                        number++;
                    } 
                }
            }
            return sum/number;
        } 
    }

    /**
     * Check if the extent of tree is within extent range [minr, maxr)
     * @param minr Minimum extent of tree
     * @param maxr Maximum extent of tree
     * @return is the tree extent within the provided range [minr, maxr)
     */
    synchronized boolean inrange(float minr, float maxr) {
            return (ext >= minr && ext < maxr);
    }

    /**
     * Grow a tree according to its sun exposure
     * @param land Sun exposed landscape
     */
    synchronized void sungrow(Land land) {
            ext = ext + ext*(sunexposure(land)/growfactor);
    }
}