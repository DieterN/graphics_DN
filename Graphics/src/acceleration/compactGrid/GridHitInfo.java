package acceleration.compactGrid;

import mathematics.Point3f;

/**
 * This object is created when the bounding box around the whole compact grid was hit.
 * The entrypoint, where the bounding box was entered and the distance are contained in this object.
 * 
 * @author Dieter
 *
 */
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
