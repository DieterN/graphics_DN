package geometry;

import imagedraw.HitRecord;
import rays.Ray;
import materials.Material;
import mathematics.Color3f;
import mathematics.Matrix4f;

public abstract class Geometry {

	protected String name;
	protected Material material;
	protected Color3f color;	
	protected float minX;
	protected float maxX;
	protected float minY;
	protected float maxY;
	protected float minZ;
	protected float maxZ;

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

	public float getMinX() {
		return minX;
	}

	public void setMinX(float minX) {
		this.minX = minX;
	}

	public float getMaxX() {
		return maxX;
	}

	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}

	public float getMinY() {
		return minY;
	}

	public void setMinY(float minY) {
		this.minY = minY;
	}

	public float getMaxY() {
		return maxY;
	}

	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}

	public float getMinZ() {
		return minZ;
	}

	public void setMinZ(float minZ) {
		this.minZ = minZ;
	}

	public float getMaxZ() {
		return maxZ;
	}

	public void setMaxZ(float maxZ) {
		this.maxZ = maxZ;
	}

	public abstract void transform(Matrix4f transformation);
	
	public abstract void initialiseBBParameters();
}
