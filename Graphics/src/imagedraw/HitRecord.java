package imagedraw;

import rays.Ray;
import geometry.Geometry;
import materials.Material;
import mathematics.Color3f;
import mathematics.Point3f;
import mathematics.Vector4f;
import mathematics.VectorOperations;

/**
 * Class that is used to save information about hitting a certain object with a ray.
 * 
 * @author Dieter
 *
 */
public class HitRecord {

	private float t;
	private Ray ray;
	private Point3f hitPoint;
	private Geometry geometry;
	private Material material;
	private float reflectiveFactor;
	private Color3f color;
	private Vector4f normal;
	
	public HitRecord(float t, Geometry geometry, Ray ray, Point3f hitPoint, Vector4f normal){
		this.t = t;
		this.ray = ray;
		this.geometry = geometry;
		this.hitPoint = hitPoint;
		this.material = geometry.getMaterial();
		this.reflectiveFactor = material.getReflectiveFactor();
		this.color = geometry.getMaterial().getColor();
		Vector4f normalizedNormal = VectorOperations.normalizeVector4f(normal); //NORMALIZE
		this.normal = normalizedNormal;
	}
	
	public float getT() {
		return t;
	}
	
	public void setT(float t) {
		this.t = t;
	}
	
	public Point3f getHitPoint() {
		return hitPoint;
	}
	
	public void setHitPoint(Point3f hitPoint) {
		this.hitPoint = hitPoint;
	}
	
	public Geometry getGeometry() {
		return geometry;
	}
	
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	
	public Color3f getColor() {
		return color;
	}

	public void setColor(Color3f color) {
		this.color = color;
	}

	public Vector4f getNormal() {
		return normal;
	}

	public void setNormal(Vector4f normal) {
		Vector4f normalized = VectorOperations.normalizeVector4f(normal);
		this.normal = normalized;
	}

	public Ray getRay() {
		return ray;
	}

	public void setRay(Ray ray) {
		this.ray = ray;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public float getReflectiveFactor() {
		return reflectiveFactor;
	}

	public void setReflectiveFactor(float reflectiveFactor) {
		this.reflectiveFactor = reflectiveFactor;
	}
}
