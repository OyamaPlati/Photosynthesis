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
	
	int getX() {
		return xpos;
	}
	
	int getY() {
		return ypos;
	}
	
	float getExt() {
		return ext;
	}
	
	void setExt(float e) {
		ext = e;
	}

        /**
         * Average intensity of sunlight covered by tree
         * @param land Sun exposed landscape
         * @return The average sunlight for the cells covered by the tree 
         */
	float sunexposure(Land land){
            float sum = 0.0f;
            int number = 0;
            for (int i = xpos - Math.round(ext); i <= xpos + Math.round(ext); i++) {
                for (int j = ypos - Math.round(ext); j <= ypos + Math.round(ext); j++) {
                    sum = sum + land.getFull (i, j);
                    number++;
                }
            }
            return sum/number;
	}
	
        /**
         * 
         * @param minr 
         * @param maxr
         * @return is the tree extent within the provided range [minr, maxr)
         */
	boolean inrange(float minr, float maxr) {
		return (ext >= minr && ext < maxr);
	}
	
        /**
         * Grow a tree according to its sun exposure
         * @param land Sun exposed landscape
         */
	void sungrow(Land land) {
		ext = ext + ext*(sunexposure(land)/growfactor);
	}
}