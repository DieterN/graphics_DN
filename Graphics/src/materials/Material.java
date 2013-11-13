package materials;

import lights.Light;
import mathematics.Color3f;
import imagedraw.HitRecord;

public abstract class Material {

	protected Color3f color;
	protected String name;
	protected float ambientFactor;
	
	public Material(){
	}

	/**
	 * Calculate shading for this material.
	 * Diffuse shading for diffuse material
	 * Phong shading for phong material
	 * A combination of both shadings for linearCombinedMaterial
	 * 
	 * @param hr : hitRecord with information about ray-object hit
	 * @param l : lightSource
	 * @return resulting color of shading
	 */
	public abstract Color3f calculateShading(HitRecord hr, Light l);
	
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

	public float getAmbientFactor() {
		return ambientFactor;
	}

	public void setAmbientFactor(float ambientFactor) {
		this.ambientFactor = ambientFactor;
	}
	
}
