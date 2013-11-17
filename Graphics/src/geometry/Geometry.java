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
	protected double minX;
	protected double maxX;
	protected double minY;
	protected double maxY;
	protected double minZ;
	protected double maxZ;

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

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public double getMinZ() {
		return minZ;
	}

	public void setMinZ(double minZ) {
		this.minZ = minZ;
	}

	public double getMaxZ() {
		return maxZ;
	}

	public void setMaxZ(double maxZ) {
		this.maxZ = maxZ;
	}

	public abstract void transform(Matrix4f transformation);
	
	public abstract void initialiseBBParameters();
}
