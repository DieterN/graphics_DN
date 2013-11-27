package acceleration;

import mathematics.Point3f;

public class CellHitRecord {

	private float tmin;
	private float tmax;
	public CellHitRecord(float tmin, float tmax) {
		this.tmin = tmin;
		this.tmax = tmax;
	}
	public float getTmin() {
		return tmin;
	}
	public void setTmin(float tmin) {
		this.tmin = tmin;
	}
	public float getTmax() {
		return tmax;
	}
	public void setTmax(float tmax) {
		this.tmax = tmax;
	}
}
