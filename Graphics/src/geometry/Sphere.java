package geometry;

import rays.Ray;
import imagedraw.HitRecord;
import mathematics.Matrix4f;
import mathematics.MatrixOperations;
import mathematics.Point3f;
import mathematics.Vector4f;
import mathematics.VectorOperations;

/**
 * Class that represents a sphere, that is charcterized by it's middlepoint and radius.
 * 
 * @author Dieter
 *
 */
public class Sphere extends Geometry{

	private Point3f middlePoint;
	private float radius;
	
	public Sphere(float radius, String name){
		super(name);
		this.middlePoint = new Point3f();
		this.radius = radius;
	}
	
	@Override
	public HitRecord rayObjectHit(Ray ray) {
		HitRecord hr = null;
		Vector4f direction = ray.getDirection();
		Point3f viewPoint = ray.getViewPoint();
		float A = VectorOperations.scalarProduct4f(direction, direction);
		Vector4f e_minus_c = VectorOperations.subtractPointfromPoint3f(viewPoint, middlePoint);
		float B = 2 * VectorOperations.scalarProduct4f(direction, e_minus_c);
		float C = (float) (VectorOperations.scalarProduct4f(e_minus_c, e_minus_c) - Math.pow(radius,2));
		float discriminant = (float) (Math.pow(B,2) - 4*A*C);
		if(!(discriminant < 0)){
			float t = calculateSmallestT(discriminant,A,B,C);
			hr = calculateHitRecord(t,ray);
		}
		return hr;
	}
	
	private float calculateSmallestT(float discriminant, float A, float B, float C){
		float t = -1;
		float sqrt_D = (float) Math.sqrt(discriminant);
		float t1 = (-B + sqrt_D)/(2*A);
		float t2 = (-B - sqrt_D)/(2*A);
		if((t1<=t2 && t1 >0) || (t1>0 && t2<0)){
			t = t1;
		}
		else if((t2<t1 && t2>0) || (t2>0 && t1<0)){
			t = t2;
		}
		return t;
	}
	
	private HitRecord calculateHitRecord(float t, Ray ray){
		Vector4f t_times_d = VectorOperations.multiplyFloatandVector4f(t, ray.getDirection()); //t*direction
		Point3f hitPoint = VectorOperations.addVector4fToPoint(t_times_d, ray.getViewPoint()); //hitPoint = viewPoint + t*direction
		Vector4f normal = VectorOperations.subtractPointfromPoint3f(hitPoint, middlePoint); // normaal = hitPoint-middlePoint
		Vector4f normalized = VectorOperations.normalizeVector4f(normal); // normaliseer normaal
		return new HitRecord(t,this,ray,hitPoint,normalized);
	}
	
	public Point3f getMiddlePoint() {
		return middlePoint;
	}
	
	public void setMiddlePoint(Point3f middlePoint) {
		this.middlePoint = middlePoint;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public void transform(Matrix4f transform){
		Vector4f middlePointVec = VectorOperations.getVectorFromPoint(middlePoint);
		Vector4f middelPointVecTr = MatrixOperations.MatrixVectorProduct(transform, middlePointVec);
		this.middlePoint = VectorOperations.getPointFromVector(middelPointVecTr);
	}

	@Override
	public void initialiseBBParameters() {
		this.minX = Math.floor(middlePoint.x-radius);
		this.maxX = Math.ceil(middlePoint.x+radius);
		this.minY = Math.floor(middlePoint.y-radius);
		this.maxY = Math.ceil(middlePoint.y+radius);
		this.minZ = Math.floor(middlePoint.z-radius);
		this.maxZ = Math.ceil(middlePoint.z+radius);
	}
}