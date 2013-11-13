package lights;

import mathematics.*;

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
}
