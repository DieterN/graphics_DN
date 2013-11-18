package acceleration;

public class Cell {

	private float minX;
	private float maxX;
	private float minY;
	private float maxY;
	private float minZ;
	private float maxZ;
	private int cellNumber;
	
	public Cell(float x, float d, float y, float e, float z, float f, int cellNumber) {
		this.minX = x;
		this.maxX = d;
		this.minY = y;
		this.maxY = e;
		this.minZ = z;
		this.maxZ = f;
		this.cellNumber = cellNumber;
	}

	public float getMinX() {
		return minX;
	}

	public void setMinX(float minX) {
		this.minX = minX;
	}

	public float getMaxX() {
		return maxX;
	}

	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}

	public float getMinY() {
		return minY;
	}

	public void setMinY(float minY) {
		this.minY = minY;
	}

	public float getMaxY() {
		return maxY;
	}

	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}

	public float getMinZ() {
		return minZ;
	}

	public void setMinZ(float minZ) {
		this.minZ = minZ;
	}

	public float getMaxZ() {
		return maxZ;
	}

	public void setMaxZ(float maxZ) {
		this.maxZ = maxZ;
	}
}
