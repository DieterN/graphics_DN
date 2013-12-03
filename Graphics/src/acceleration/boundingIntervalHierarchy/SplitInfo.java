package acceleration.boundingIntervalHierarchy;

public class SplitInfo {

	private float minL;
	private float maxL;
	private float minR;
	private float maxR;
	private int i;
	private int j;
	
	public SplitInfo(float minL, float maxL, float minR, float maxR, int i,int j) {
		super();
		this.minL = minL;
		this.maxL = maxL;
		this.minR = minR;
		this.maxR = maxR;
		this.i = i;
		this.j = j;
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

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
}
