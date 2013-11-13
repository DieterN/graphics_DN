package rays;

import mathematics.*;

/**
 * Class representing a shadow ray, used to see if an object is in shadow
 * 
 * @author Dieter
 *
 */
public class ShadowRay extends Ray {

	private final float epsilon = 0.015f;
	
	public ShadowRay(Point3f viewPoint, Vector4f direction){
		super();
		Vector4f epsInDirection = VectorOperations.multiplyFloatandVector4f(epsilon, direction);
		super.viewPoint = VectorOperations.addVector4fToPoint(epsInDirection, viewPoint);
		super.direction = direction;
	}
}
