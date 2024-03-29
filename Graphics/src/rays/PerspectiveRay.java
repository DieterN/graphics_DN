package rays;

import cameras.Camera;
import cameras.PerspectiveCamera;
import cameras.Screen;
import imagedraw.DrawController;
import mathematics.*;

/**
 * Class representing a persepective ray, used with Perspective Camera
 * 
 * @author Dieter
 *
 */
public class PerspectiveRay extends Ray{
		
	/**
	 * Construct a new Perspective ray out of the given parameters.
	 * ATTENTION : Camera should be Perspective
	 * The best way to construct this kind of ray is by calling the getRay() method
	 * in the abstract superclass Ray.
	 * 
	 * @param camera (PerspectiveCamera)
	 * @param screen
	 * @param pixelX
	 * @param pixelY
	 */
	protected PerspectiveRay(Camera camera, float pixelX, float pixelY) {
		super();
		if(!(camera instanceof PerspectiveCamera)){
			System.out.println("Camera should be Perspective");
			throw new IllegalArgumentException();
		}		
		super.direction = computeViewingDirection(camera,pixelX,pixelY);
		super.viewPoint = camera.getViewPoint();
		inv_directionX = 1/direction.x; //needed for boundingbox hitting
		inv_directionY = 1/direction.y;
		inv_directionZ = 1/direction.z;
		sign[0] = (inv_directionX > 0) ? 0 : 1; //this keeps the sign, needed for boundingbox intersection
		sign[1] = (inv_directionY > 0) ? 0 : 1; //save 0 if inv_direction is positive
		sign[2] = (inv_directionZ > 0) ? 0 : 1; //save 1 if inv_direction is negative
	}
		
	private Vector4f computeViewingDirection(Camera camera, float i, float j){
		Screen screen = camera.getScreen();
		float l = screen.getL();
		float r = screen.getR();
		float t = screen.getT();
		float b = screen.getB();
		float nx = DrawController.getNx();
		float ny = DrawController.getNy();
		float distance = PerspectiveCamera.getFocalLength();
		Vector4f uVector = VectorOperations.normalizeVector4f(camera.getU());
		Vector4f vVector = VectorOperations.normalizeVector4f(camera.getV());
		Vector4f direction = VectorOperations.normalizeVector4f(camera.getViewDirection());
		Vector4f d_times_D = VectorOperations.multiplyFloatandVector4f(distance, direction);
		float u = (b + ((t-b)*j)/ny);
		float v = (l + ((r-l)*i)/nx);
		Vector4f u_times_u = VectorOperations.multiplyFloatandVector4f(u, uVector);	
		Vector4f v_times_v = VectorOperations.multiplyFloatandVector4f(v, vVector);
		Vector4f tempVector = VectorOperations.addVectors4f(u_times_u, v_times_v);
		Vector4f vector = VectorOperations.addVectors4f(d_times_D, tempVector);
		Vector4f normalizedVector = VectorOperations.normalizeVector4f(vector);
		return normalizedVector;
	}
}
