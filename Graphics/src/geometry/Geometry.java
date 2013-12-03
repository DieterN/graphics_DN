package geometry;

import imagedraw.HitRecord;
import rays.Ray;
import materials.Material;
import mathematics.Color3f;
import mathematics.Matrix4f;

public abstract class Geometry {

	protected BoundingBox box;
	protected boolean initialised;
	
	public Geometry(){
	}
	
	/**
	 * Method needed to cast ray's towards all geometries.
	 * Subclasses should override this method
	 * 
	 * @param ray
	 * @return HitRecord if hit, null otherwise
	 */	
	public abstract HitRecord rayObjectHit(Ray ray);
	
	public BoundingBox getBox() {
		if(!this.initialised){
			calculateBB(); // Als bounding boxes nog niet berekend, bereken ze dan eerst
		}
		return box;
	}

	public void setBox(BoundingBox box) {
		this.box = box;
	}

	public boolean isInitialised() {
		return initialised;
	}

	public void setInitialised(boolean initialised) {
		this.initialised = initialised;
	}
	
	// TEMPLATE METHOD
	public void calculateBB(){
		initialiseBBParameters();
		this.initialised = true;
	}
	
	public abstract void initialiseBBParameters();
}
