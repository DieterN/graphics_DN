package geometry;

import imagedraw.HitRecord;
import rays.Ray;
import mathematics.Matrix4f;

public class Torus extends Geometry{

	private float innerRadius;
	private float outerRadius;
	
	public Torus(float innerRadius, float outerRadius, String name){
		super(name);
		this.innerRadius = innerRadius;
		this.outerRadius = outerRadius;
	}

	@Override
	public HitRecord rayObjectHit(Ray ray){
		return null;
		// TODO
	}
	
	public float getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(float innerRadius) {
		this.innerRadius = innerRadius;
	}

	public float getOuterRadius() {
		return outerRadius;
	}

	public void setOuterRadius(float outerRadius) {
		this.outerRadius = outerRadius;
	}
	
	public void transform(Matrix4f transform){
		// TODO
	}
}
