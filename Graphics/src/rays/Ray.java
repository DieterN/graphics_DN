package rays;



import cameras.OrthograpicCamera;
import cameras.PerspectiveCamera;
import mathematics.*;

/**
 * This class represents rays, that will be shot towards the image.
 * Depending on the object you want to 'hit', you have to call another method.
 * 
 * @author Dieter
 *
 */
public class Ray {
		
	protected Point3f viewPoint;
	protected Vector4f direction;
	protected float inv_directionX;
	protected float inv_directionY;
	protected float inv_directionZ;
	protected int[] sign = new int[3];
		
	public Ray(Point3f viewPoint, Vector4f direction){
		this.viewPoint = viewPoint;
		this.direction = direction;
		inv_directionX = 1/direction.x; //needed for boundingbox hitting
		inv_directionY = 1/direction.y;
		inv_directionZ = 1/direction.z;
		sign[0] = (inv_directionX > 0) ? 0 : 1; //this keeps the sign, needed for boundingbox intersection
		sign[1] = (inv_directionY > 0) ? 0 : 1; //save 0 if inv_direction is positive
		sign[2] = (inv_directionZ > 0) ? 0 : 1; //save 1 if inv_direction is negative
	}
	
	protected Ray() {
		// Needed for subclasses to be able to construct viewPoint and viewDirection
	}
	
	/**
	 * Returns the right ray for this camera object
	 * 
	 * @param camera
	 * @return
	 */
	
	public static Ray getRay(PerspectiveCamera camera, int pixelX, int pixelY){
		return new PerspectiveRay(camera, pixelX, pixelY);
	}
	
	public static Ray getRay(OrthograpicCamera camera, int pixelX, int pixelY){
		return new OrthograpicRay(camera, pixelX, pixelY);
	}

	public Point3f getViewPoint() {
		return viewPoint;
	}

	public void setViewPoint(Point3f viewPoint) {
		this.viewPoint = viewPoint;
	}

	public Vector4f getDirection() {
		Vector4f normalized = VectorOperations.normalizeVector4f(direction);
		return normalized;
	}

	public void setDirection(Vector4f direction) {
		this.direction = direction;
	}
	
	public Ray transformRay(Matrix4f transform){
		Vector4f viewVector = VectorOperations.getVectorFromPoint(viewPoint);
		Vector4f trViewVector = MatrixOperations.MatrixVectorProduct(transform, viewVector);
		Vector4f trDirection = MatrixOperations.MatrixVectorProduct(transform, direction); //niet normaliseren, dan is t hetzelfde TODO
		Ray ray = new Ray(VectorOperations.getPointFromVector(trViewVector),trDirection);
		return ray;
	}

	public float getInv_directionX() {
		return inv_directionX;
	}

	public float getInv_directionY() {
		return inv_directionY;
	}

	public float getInv_directionZ() {
		return inv_directionZ;
	}

	public int[] getSign() {
		return sign;
	}
	
	public float getInv_directionOfAxis(int axis){
		if(axis == 00){
			return inv_directionX;
		}
		if(axis == 01){
			return inv_directionY;
		}
		if(axis == 10){
			return inv_directionZ;
		}
		throw new IllegalArgumentException();
	}
}
