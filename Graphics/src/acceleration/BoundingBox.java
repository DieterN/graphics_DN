package acceleration;

import geometry.Geometry;
import imagedraw.HitRecord;

import java.util.ArrayList;
import java.util.List;

import mathematics.Matrix4f;
import mathematics.Point3f;
import mathematics.VectorOperations;
import rays.Ray;

public class BoundingBox extends Geometry{

	private Point3f[] bounds = new Point3f[2];
	private List<Geometry> geometry = new ArrayList<Geometry>();
	
	public BoundingBox(float minX, float maxX, float minY, float maxY, float minZ, float maxZ){
		super("");
		bounds[0] = new Point3f(minX,minY,minZ);
		bounds[1] = new Point3f(maxX,maxY,maxZ);
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

	public List<Geometry> getGeometry() {
		return geometry;
	}

	public void setGeometry(List<Geometry> geometry) {
		this.geometry = geometry;
	}
	
	public void addGeometry(Geometry geo) {
		this.geometry.add(geo);
	}

	public Point3f[] getBounds() {
		return bounds;
	}

	public void setBounds(Point3f[] bounds) {
		this.bounds = bounds;
	}

	/**
	 * Return null if this box isn't hit, return closest hitRecord otherwise
	 * 
	 */
	@Override
	public HitRecord rayObjectHit(Ray ray){
		HitRecord result = null;
		float smallest_t = Float.POSITIVE_INFINITY;
		if(this.hits(ray)){ //check if this boundingbox is hit
			for(Geometry g : this.geometry){ //if hit, try hitting everything inside this box
				HitRecord hr = g.rayObjectHit(ray);
				float hr_t = hr.getT();
				if(hr_t < smallest_t && hr_t >= 0){
					smallest_t = hr_t;
					result = hr;
				}
			}
		}
		return result;
	}
	
	/**
	 * Return if this box is hit
	 */
	public boolean hits(Ray ray) {
		int[] sign = ray.getSign();
		float invDirectionX = ray.getInv_directionX();
		float invDirectionY = ray.getInv_directionY();
		float originX = ray.getViewPoint().x;
		float originY = ray.getViewPoint().y;
		
		float tmin = (bounds[sign[0]].x - originX) * invDirectionX; //take using sign the right value, so min and max are right
		float tmax = (bounds[1-sign[0]].x - originX) * invDirectionX;
		float tymin = (bounds[sign[1]].y - originY) * invDirectionY;
		float tymax = (bounds[1-sign[1]].y - originY) * invDirectionY;
		//check if these two intervals overlap, if not, return null
		if(tmin > tymax || tymin > tmax){
			return false;
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
			return false;
		}//else take the greatest min value
//		if(tzmin > tmin){
//			tmin = tzmin;
//		}//and take the smallest max value
//		if(tzmax < tmax){
//			tmax = tzmax;
//		}
		else{
			return true;
		}
	}
	
	/**
	 * Return point at which this box is entered, null if not entered
	 */
	public GridHitInfo getEntryPoint(Ray ray) {
		int[] sign = ray.getSign();
		float invDirectionX = ray.getInv_directionX();
		float invDirectionY = ray.getInv_directionY();
		float originX = ray.getViewPoint().x;
		float originY = ray.getViewPoint().y;
		
		float tmin = (bounds[sign[0]].x - originX) * invDirectionX; //take using sign the right value, so min and max are right
		float tmax = (bounds[1-sign[0]].x - originX) * invDirectionX;
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
		
		//grid enterPoint
		Point3f entryPoint = VectorOperations.addVector4fToPoint(VectorOperations.multiplyFloatandVector4f(tmin, ray.getDirection()), ray.getViewPoint());
		GridHitInfo ghi = new GridHitInfo(entryPoint, tmin);
		return ghi;
	}

	@Override
	public void transform(Matrix4f transformation) {
		// doe niets, boxes moeten niet getransformed
		System.out.println("Transforming Bounding Box --> not good");
	}

	@Override
	public void initialiseBBParameters() {
		this.box = new BoundingBox(bounds[0].x, bounds[1].x, bounds[0].y, bounds[1].y, bounds[0].z, bounds[1].z); //useless
		System.out.println("Not good to make BoundingBox for boundingbox");
	}

}
