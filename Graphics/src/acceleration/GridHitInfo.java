package acceleration;

import mathematics.Point3f;

public class GridHitInfo {

	private Point3f entryPoint;
	private float tHit;
	
	public GridHitInfo(Point3f entryPoint, float tHit) {
		this.entryPoint = entryPoint;
		this.tHit = tHit;
	}

	public Point3f getEntryPoint() {
		return entryPoint;
	}

	public void setEntryPoint(Point3f entryPoint) {
		this.entryPoint = entryPoint;
	}

	public float gettHit() {
		return tHit;
	}

	public void settHit(float tHit) {
		this.tHit = tHit;
	}
	
}
