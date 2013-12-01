package acceleration;

import rays.Ray;
import mathematics.Point3f;
import mathematics.VectorOperations;

public class Cell {

	private Point3f[] bounds = new Point3f[2];
	private int cellNumber;
	
	public Cell(float minX, float maxX, float minY, float maxY, float minZ, float maxZ, int cellNumber) {
		bounds[0] = new Point3f(minX,minY,minZ);
		bounds[1] = new Point3f(maxX,maxY,maxZ);
		this.cellNumber = cellNumber;
	}

	public float getMinX() {
		return bounds[0].x;
	}

	public void setMinX(float minX) {
		this.bounds[0].x = minX;
	}

	public float getMaxX() {
		return bounds[1].x;
	}

	public void setMaxX(float maxX) {
		this.bounds[1].x = maxX;
	}

	public float getMinY() {
		return bounds[0].y;
	}

	public void setMinY(float minY) {
		this.bounds[0].y = minY;
	}

	public float getMaxY() {
		return bounds[1].y;
	}

	public void setMaxY(float maxY) {
		this.bounds[1].y = maxY;
	}

	public float getMinZ() {
		return bounds[0].z;
	}

	public void setMinZ(float minZ) {
		this.bounds[0].z = minZ;
	}

	public float getMaxZ() {
		return bounds[1].z;
	}

	public void setMaxZ(float maxZ) {
		this.bounds[1].z = maxZ;
	}

	public int getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(int cellNumber) {
		this.cellNumber = cellNumber;
	}

	public float[] getDeltas(Ray ray){
		float[] t = new float[6];
		int[] sign = ray.getSign();
		float invDirectionX = ray.getInv_directionX();
		float invDirectionY = ray.getInv_directionY();
		float invDirectionZ = ray.getInv_directionZ();
		float originX = ray.getViewPoint().x;
		float originY = ray.getViewPoint().y;
		float originZ = ray.getViewPoint().z;
		
		t[0] = (bounds[sign[0]].x - originX) * invDirectionX; //tMinX //take using sign the right value, so min and max are right
		t[1] = (bounds[1-sign[0]].x - originX) * invDirectionX; //tMaxX
		t[2] = (bounds[sign[1]].y - originY) * invDirectionY; //tMinY
		t[3] = (bounds[1-sign[1]].y - originY) * invDirectionY; //tMaxY
		t[4] = (bounds[sign[2]].z - originZ) * invDirectionZ; //tMinX
		t[5] = (bounds[1-sign[2]].z - originZ) * invDirectionZ; //tMinX
		
		return t;
	}
	

	/**
	 * Return if this box is hit
	 */
	public FirstHitRecord firstGridHit(Ray ray) {
		int[] sign = ray.getSign();
		float invDirectionX = ray.getInv_directionX();
		float invDirectionY = ray.getInv_directionY();
		float originX = ray.getViewPoint().x;
		float originY = ray.getViewPoint().y;
		
		float tmin = (bounds[sign[0]].x - originX) * invDirectionX; //take using sign the right value, so min and max are right
		float tmax = (bounds[1-sign[0]].x - originX) * invDirectionX;
		float txmin = tmin;
		float txmax = tmax;
		float tymin = (bounds[sign[1]].y - originY) * invDirectionY;
		float tymax = (bounds[1-sign[1]].y - originY) * invDirectionY;
		//check if these two intervals overlap, if not, return null
		if(tmin > tymax || tymin > tmax){
			return null;
		}//else take the greatest min value
		if(tymin > tmin){
			tmin = tymin;
		}//and take the smallest max value
		if(tymax < tmax){
			tmax = tymax;
		}
		
		float invDirectionZ = ray.getInv_directionZ();
		float originZ = ray.getViewPoint().z;
		
		float tzmin = (bounds[sign[2]].z - originZ) * invDirectionZ; 
		float tzmax = (bounds[1-sign[2]].z - originZ) * invDirectionZ;
		
		//check if the three intervals overlap, if not, return null
		if(tmin > tzmax || tzmin > tmax){
			return null;
		}//else take the greatest min value
		if(tzmin > tmin){
			tmin = tzmin;
		}//and take the smallest max value
		if(tzmax < tmax){
			tmax = tzmax;
		}
		
		return new FirstHitRecord(tmin, txmin, txmax, tymin, tymax, tzmin, tzmax);
	}

}
