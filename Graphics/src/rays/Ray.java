package rays;



import cameras.Camera;
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
	
	public Ray(Point3f viewPoint, Vector4f direction){
		this.viewPoint = viewPoint;
		this.direction = direction;
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
}
