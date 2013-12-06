package rays;

import mathematics.Point3f;
import mathematics.Vector4f;
import mathematics.VectorOperations;

/**
 * Class representing a reflective ray, epsilon is needed, cause otherwise you would have self reflectance.
 * 
 * @author Dieter
 *
 */
public class ReflectiveRay extends Ray{

	private final float epsilon = 0.05f; //TODO

	public ReflectiveRay(Point3f viewPoint, Vector4f direction){
		super();
		Vector4f epsInDirection = VectorOperations.multiplyFloatandVector4f(epsilon, direction); //add little epsilon to viewpoint
		super.viewPoint = VectorOperations.addVector4fToPoint(epsInDirection, viewPoint);
		super.direction = direction;
		inv_directionX = 1/direction.x; //needed for boundingbox hitting
		inv_directionY = 1/direction.y;
		inv_directionZ = 1/direction.z;
		sign[0] = (inv_directionX > 0) ? 0 : 1; //this keeps the sign, needed for boundingbox intersection
		sign[1] = (inv_directionY > 0) ? 0 : 1; //save 0 if inv_direction is positive
		sign[2] = (inv_directionZ > 0) ? 0 : 1; //save 1 if inv_direction is negative
	}
}
