package acceleration;

import geometry.Geometry;

import java.util.ArrayList;
import java.util.List;

public class BoundingBox {

	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private double minZ;
	private double maxZ;
	private List<Geometry> geometry = new ArrayList<Geometry>();
	
	public BoundingBox(int minX, int maxX, int minY, int maxY, int minZ, int maxZ){
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.minZ = minZ;
		this.maxZ = maxZ;
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

	public void setMaxZ(int maxZ) {
		this.maxZ = maxZ;
	}

	public List<Geometry> getGeometry() {
		return geometry;
	}

	public void setGeometry(List<Geometry> geometry) {
		this.geometry = geometry;
	}

}
