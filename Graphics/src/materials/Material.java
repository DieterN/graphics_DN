package materials;

import lights.Light;
import mathematics.Color3f;
import mathematics.Point3f;
import imagedraw.HitRecord;

/**
 * Superclass for all materials.
 * 
 * @author Dieter
 *
 */
public abstract class Material {

	protected Color3f color;
	protected String name;
	protected float reflectiveFactor;
	protected float refractiveCoefficient;
	
	public Material(){
	}

	/**
	 * Calculate shading for this material.
	 * Diffuse shading for diffuse material
	 * Phong shading for phong material
	 * A combination of both shadings for linearCombinedMaterial
	 */
	public abstract Color3f calculateShading(HitRecord hr, Light l);
	
	/**
	 * Same as method above, but viewpoint is given too, needed for soft shadows
	 */
	public abstract Color3f calculateShading(HitRecord hr, Light l, Point3f position);
	
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

	public float getReflectiveFactor() {
		return reflectiveFactor;
	}

	public void setReflectiveFactor(float reflectiveFactor) {
		this.reflectiveFactor = reflectiveFactor;
	}

	public float getRefractiveCoefficient() {
		return refractiveCoefficient;
	}

	public void setRefractiveCoefficient(float refractiveCoefficient) {
		this.refractiveCoefficient = refractiveCoefficient;
	}
	
}
