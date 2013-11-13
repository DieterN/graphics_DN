package geometry;

import imagedraw.HitRecord;
import rays.Ray;
import mathematics.Matrix4f;

public class Cone extends Geometry{

	private float radius;
	private float height;
	private boolean capped;
	
	public Cone(float radius, float height, boolean capped, String name){
		super(name);
		this.radius = radius;
		this.height = height;
		this.capped = capped;
	}
	
	@Override
	public HitRecord rayObjectHit(Ray ray){
		return null;
		// TODO
	}
	
	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public boolean isCapped() {
		return capped;
	}

	public void setCapped(boolean capped) {
		this.capped = capped;
	}
	
	public void transform(Matrix4f transform){
		// TODO
	}
}
