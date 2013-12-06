package lights;

import mathematics.*;

/**
 * Class representing a directional light.
 * 
 * @author Dieter
 *
 */
public class DirectionalLight extends Light{

	private Vector4f direction;
	
	public DirectionalLight(Vector4f direction, float intensity, Color3f color, String name){
		super(new Point3f(), intensity, color, name);
		this.direction = direction;
	}

	public Vector4f getDirection() {
		return direction;
	}

	public void setDirection(Vector4f direction) {
		this.direction = direction;
	}
}
