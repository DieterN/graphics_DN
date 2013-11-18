package acceleration;

import geometry.Geometry;

import imagedraw.HitRecord;

import java.util.ArrayList;
import java.util.List;

import mathematics.Matrix4f;
import rays.Ray;

public class BoundingBox extends Geometry{

	private float minX;
	private float maxX;
	private float minY;
	private float maxY;
	private float minZ;
	private float maxZ;
	private List<Geometry> geometry = new ArrayList<Geometry>();
	
	public BoundingBox(float minX, float maxX, float minY, float maxY, float minZ, float maxZ){
		super("");
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.minZ = minZ;
		this.maxZ = maxZ;
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

	public List<Geometry> getGeometry() {
		return geometry;
	}

	public void setGeometry(List<Geometry> geometry) {
		this.geometry = geometry;
	}
	
	public void addGeometry(Geometry geo) {
		this.geometry.add(geo);
	}

	@Override
	public HitRecord rayObjectHit(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void transform(Matrix4f transformation) {
		// TODO Auto-generated method stub
		// doe niets, boxes moeten niet getransformed
		System.out.println("Transforming Bounding Box --> not good");
	}

	@Override
	public void initialiseBBParameters() {
		// TODO Auto-generated method stub
	}

}
