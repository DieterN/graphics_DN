package rays;

import cameras.Camera;
import cameras.OrthograpicCamera;
import cameras.Screen;
import imagedraw.DrawController;
import mathematics.*;

/**
 * Class representing an orthograpic ray, used with Orthograpic Camera
 * 
 * @author Dieter
 *
 */
public class OrthograpicRay extends Ray{
	
	/**
	 * Construct a new Orthograpic ray out of the given parameters.
	 * ATTENTION : Camera should be Orthograpic
	 * The best way to construct this kind of ray is by calling the getRay() method
	 * in the abstract superclass Ray.
	 * 
	 * @param camera (OrthograpicCamera)
	 * @param screen
	 * @param pixelX
	 * @param pixelY
	 */
	protected OrthograpicRay(Camera camera, float pixelX, float pixelY) {
		super();
		if(!(camera instanceof OrthograpicCamera)){
			System.out.println("Camera should be Orthograpic");
			throw new IllegalArgumentException();
		}
		super.direction = camera.getViewDirection();
		super.viewPoint = computeViewingPoint(camera, pixelX, pixelY);
		inv_directionX = 1/direction.x; //needed for boundingbox hitting
		inv_directionY = 1/direction.y;
		inv_directionZ = 1/direction.z;
		sign[0] = (inv_directionX > 0) ? 0 : 1; //this keeps the sign, needed for boundingbox intersection
		sign[1] = (inv_directionY > 0) ? 0 : 1; //save 0 if inv_direction is positive
		sign[2] = (inv_directionZ > 0) ? 0 : 1; //save 1 if inv_direction is negative
	}

	private Point3f computeViewingPoint(Camera camera, float i, float j){
		Screen screen = camera.getScreen();
		float l = screen.getL();
		float r = screen.getR();
		float t = screen.getT();
		float b = screen.getB();
		float nx = DrawController.getNx();
		float ny = DrawController.getNy();
		Point3f viewPoint = camera.getViewPoint();
		Vector4f uVector = camera.getU();
		Vector4f vVector = camera.getV();
		float u = b + ((t-b)*j)/ny;
		float v = l + ((r-l)*i)/nx;
		Vector4f u_times_u = VectorOperations.multiplyFloatandVector4f(u, uVector);	
		Vector4f v_times_v = VectorOperations.multiplyFloatandVector4f(v, vVector);
		Point3f tempPoint = VectorOperations.addVector4fToPoint(u_times_u, viewPoint);
		Point3f point = VectorOperations.addVector4fToPoint(v_times_v, tempPoint);
		return point;
	}
}
