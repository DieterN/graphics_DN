package lights;

import imagedraw.DrawController;
import imagedraw.HitRecord;
import mathematics.Color3f;
import mathematics.Point3f;

/**
 * Superclass that contains information for all lights.
 * 
 * @author Dieter
 *
 */
public abstract class Light {
	
	private float intensity;
	private Color3f color;
	private String name;
	protected Point3f position;

	public Light(Point3f point, float intensity, Color3f color, String name){
		this.position = point;
		this.intensity = intensity;
		this.color = color;
		this.name = name;
	}
	
	public abstract Color3f calculateShading(HitRecord hr, DrawController dc);

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public Color3f getColor() {
		return color;
	}

	public void setColor(Color3f color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point3f getPosition() {
		return position;
	}

	public void setPosition(Point3f position) {
		this.position = position;
	}		
}
