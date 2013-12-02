package geometry;

import acceleration.BoundingBox;
import imagedraw.HitRecord;
import rays.Ray;
import materials.Material;
import mathematics.Color3f;
import mathematics.Matrix4f;

public abstract class Geometry {

	protected String name;
	protected Material material;
	protected Color3f color;
	protected BoundingBox box;
	protected boolean initialised;

	public Geometry(String name){
		this.name = name;
	}
	
	/**
	 * Method needed to cast ray's towards all geometries.
	 * Subclasses should override this method
	 * 
	 * @param ray
	 * @return HitRecord if hit, null otherwise
	 */	
	public abstract HitRecord rayObjectHit(Ray ray);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}

	public Color3f getColor() {
		return color;
	}

	public void setColor(Color3f color) {
		this.color = color;
	}

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

	public abstract void transform(Matrix4f transformation);
	
	// TEMPLATE METHOD
	public void calculateBB(){
		initialiseBBParameters();
		this.initialised = true;
	}
	
	public abstract void initialiseBBParameters();
}
