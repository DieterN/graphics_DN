package geometry;

import imagedraw.HitRecord;
import rays.Ray;
import mathematics.Matrix4f;

public class Teapot extends Geometry{

	private float size;
	
	public Teapot(float size, String name){
		super(name);
		this.size = size;
	}
	
	@Override
	public HitRecord rayObjectHit(Ray ray){
		return null;
		// TODO
	}
	
	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}
	
	public void transform(Matrix4f transform){
		// TODO
	}
}
