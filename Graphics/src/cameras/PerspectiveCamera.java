package cameras;

import mathematics.Point3f;
import mathematics.Vector4f;

/**
 * Cameraclass for an Perspective projection. 
 * This class extends the basic class Camera and has 
 * an extra parameter that holds the distance to a screen (focal length). 
 * 
 * @author Dieter
 *
 */
public class PerspectiveCamera extends Camera{

	private static final float focalLength = 25;
	
	public PerspectiveCamera(Point3f viewPoint, Vector4f w, Vector4f u, float fovy, String name) {
		super(viewPoint, w, u, fovy, name);
	}

	public static float getFocalLength() {
		return focalLength;
	}
}
