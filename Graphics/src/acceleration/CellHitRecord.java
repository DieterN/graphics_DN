package acceleration;

import mathematics.Point3f;

public class CellHitRecord {

	private Point3f enterPoint;
	private Point3f leavePoint;
	private float tmin;
	private float tmax;
	public CellHitRecord(Point3f enterPoint, Point3f leavePoint, float tmin, float tmax) {
		this.enterPoint = enterPoint;
		this.leavePoint = leavePoint;
		this.tmin = tmin;
		this.tmax = tmax;
	}
	
	public Point3f getEnterPoint() {
		return enterPoint;
	}
	public void setEnterPoint(Point3f enterPoint) {
		this.enterPoint = enterPoint;
	}
	public Point3f getLeavePoint() {
		return leavePoint;
	}
	public void setLeavePoint(Point3f leavePoint) {
		this.leavePoint = leavePoint;
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
