package acceleration.boundingIntervalHierarchy;

/**
 * Class representing info about splitting a Node in two parts.
 * This info is needed later in the recursive construction of the childnodes.
 * 
 * @author Dieter
 *
 */
public class SplitInfo {

	private float minL;
	private float maxL;
	private float minR;
	private float maxR;
	private int left;
	private int right;
	
	public SplitInfo(float minL, float maxL, float minR, float maxR, int left, int right) {
		super();
		this.minL = minL;
		this.maxL = maxL;
		this.minR = minR;
		this.maxR = maxR;
		this.left = left;
		this.right = right;
	}

	public float getMinL() {
		return minL;
	}

	public void setMinL(float minL) {
		this.minL = minL;
	}

	public float getMaxL() {
		return maxL;
	}

	public void setMaxL(float maxL) {
		this.maxL = maxL;
	}

	public float getMinR() {
		return minR;
	}

	public void setMinR(float minR) {
		this.minR = minR;
	}

	public float getMaxR() {
		return maxR;
	}

	public void setMaxR(float maxR) {
		this.maxR = maxR;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}
}
