package lights;

import imagedraw.DrawController;
import imagedraw.HitRecord;
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

	@Override
	public Color3f calculateShading(HitRecord hr, DrawController dc) {
		// TODO Auto-generated method stub
		return null;
	}
}
