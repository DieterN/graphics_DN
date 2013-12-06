package lights;

import imagedraw.DrawController;
import imagedraw.HitRecord;
import mathematics.*;

/**
 * Class representing a spot light.
 * 
 * @author Dieter
 *
 */
public class SpotLight extends Light{

	private Vector4f direction;
	private float angle;
	
	public SpotLight(Point3f position, Vector4f direction, float angle, float intensity, Color3f color, String name){
		super(position, intensity, color, name);
		this.direction = direction;
		this.angle = angle;
	}

	public Vector4f getDirection() {
		return direction;
	}

	public void setDirection(Vector4f direction) {
		this.direction = direction;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	@Override
	public Color3f calculateShading(HitRecord hr, DrawController dc) {
		// TODO Auto-generated method stub
		return null;
	}
}
