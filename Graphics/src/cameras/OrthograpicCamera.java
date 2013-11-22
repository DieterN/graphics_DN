package cameras;

import rays.Ray;
import mathematics.Point3f;
import mathematics.Vector4f;

/**
 * Cameraclass for an Orthograpic projection. 
 * This class extends the basic class Camera.
 * 
 * @author Dieter
 *
 */
public class OrthograpicCamera extends Camera{

	public OrthograpicCamera(Point3f viewPoint, Vector4f w, Vector4f u, float fovy, String name) {
		super(viewPoint, w, u, fovy, name);
	}

	@Override
	public Ray getRay(int pixelX, int pixelY) {
		return Ray.getRay(this,pixelX,pixelY);
	}
	
}
