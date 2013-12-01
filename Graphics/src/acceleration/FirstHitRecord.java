package acceleration;

import mathematics.Point3f;

public class FirstHitRecord {

	float hitT;
	float tMinX;
	float tMaxX;
	float tMinY;
	float tMaxY;
	float tMinZ;
	float tMaxZ;
	public FirstHitRecord(float hitT, float tMinX,
			float tMaxX, float tMinY, float tMaxY, float tMinZ, float tMaxZ) {
		this.hitT = hitT;
		this.tMinX = tMinX;
		this.tMaxX = tMaxX;
		this.tMinY = tMinY;
		this.tMaxY = tMaxY;
		this.tMinZ = tMinZ;
		this.tMaxZ = tMaxZ;
	}
	public float getHitT() {
		return hitT;
	}
	public void setHitT(float hitT) {
		this.hitT = hitT;
	}
	public float gettMinX() {
		return tMinX;
	}
	public void settMinX(float tMinX) {
		this.tMinX = tMinX;
	}
	public float gettMaxX() {
		return tMaxX;
	}
	public void settMaxX(float tMaxX) {
		this.tMaxX = tMaxX;
	}
	public float gettMinY() {
		return tMinY;
	}
	public void settMinY(float tMinY) {
		this.tMinY = tMinY;
	}
	public float gettMaxY() {
		return tMaxY;
	}
	public void settMaxY(float tMaxY) {
		this.tMaxY = tMaxY;
	}
	public float gettMinZ() {
		return tMinZ;
	}
	public void settMinZ(float tMinZ) {
		this.tMinZ = tMinZ;
	}
	public float gettMaxZ() {
		return tMaxZ;
	}
	public void settMaxZ(float tMaxZ) {
		this.tMaxZ = tMaxZ;
	}	
}
