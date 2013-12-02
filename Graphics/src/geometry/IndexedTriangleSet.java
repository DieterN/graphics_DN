package geometry;

import imagedraw.DrawController;
import imagedraw.HitRecord;

import java.util.List;
import java.util.ArrayList;

import acceleration.BoundingBox;
import acceleration.CompactGrid;
import rays.Ray;
import materials.Material;
import mathematics.*;

public class IndexedTriangleSet extends Geometry{

	private Point3f[] coordinates;
	private Vector4f[] normals;
	private TexCoord2f [] textureCoordinates;
	private int[] coordinateIndices;
	private int[] normalIndices;
	private int[] textureCoordinateIndices;
	private List<Triangle> triangles = new ArrayList<Triangle>();
	private CompactGrid grid;
	private boolean usingGrid = false;
	
	public IndexedTriangleSet(Point3f [] coordinates, Vector3f [] normal, TexCoord2f [] textureCoordinates, 
								int [] coordinateIndices, int [] normalIndices, int [] textureCoordinateIndices, String name){
		super(name);
		this.coordinates = coordinates;
		this.coordinateIndices = coordinateIndices;
		initialiseNormals(normal);
		this.normalIndices = normalIndices;
		this.textureCoordinates = textureCoordinates;
		this.textureCoordinateIndices = textureCoordinateIndices;
		this.calculateTriangles();
		this.calculateNormals();
		this.calculateTextures();
	}
	
	private void calculateTriangles(){
		this.triangles.clear();
		int i = 2;
		while(i<coordinateIndices.length){
			int[] cornerPoints = new int[3];
			cornerPoints[0] = coordinateIndices[i-2];
			cornerPoints[1] = coordinateIndices[i-1];
			cornerPoints[2] = coordinateIndices[i];
			Triangle t = new Triangle(coordinates[cornerPoints[0]],coordinates[cornerPoints[1]],coordinates[cornerPoints[2]]);
			triangles.add(t);
			i += 3;
		}
	}
	
	private void calculateNormals(){
		if(this.normals != null){
			int i = 2; //coordinate index
			int j = 0; //triangle index
			while(i<normalIndices.length){
				int[] normal = new int[3];
				normal[0] = normalIndices[i-2];
				normal[1] = normalIndices[i-1];
				normal[2] = normalIndices[i];
				Triangle t = triangles.get(j);
				t.setNormal(normals[normal[0]],normals[normal[1]],normals[normal[2]]);
				i += 3;
				j++;
			}
		}
	}
	
	private void calculateTextures(){
		if(this.textureCoordinates != null){
			int i = 2; //coordinate index
			int j = 0; //triangle index
			while(i<textureCoordinateIndices.length){
				int[] texture = new int[3];
				texture[0] = textureCoordinateIndices[i-2];
				texture[1] = textureCoordinateIndices[i-1];
				texture[2] = textureCoordinateIndices[i];
				Triangle t = triangles.get(j);
				t.setTexture(textureCoordinates[texture[0]],textureCoordinates[texture[1]],textureCoordinates[texture[2]]);
				i += 3;
				j++;
			}
		}
	}
	
	private void initialiseNormals(Vector3f [] normals){
		if(normals != null){
			Vector4f[] newNormals = new Vector4f[normals.length];
			int i = 0;
			for(Vector3f normal : normals){
				Vector4f normal4 = new Vector4f(normal);
				newNormals[i] = normal4;
				i++;
			}
			this.normals = newNormals;
		}
	}
	
	@Override
	public HitRecord rayObjectHit(Ray ray){
		if(DrawController.accelerated && usingGrid){ //if accelerated and using grid, use the grid in this class
			return rayObjectHitAccelerated(ray);
		} //else use normal method
		float smallest_t = Float.POSITIVE_INFINITY;
		HitRecord smallest = null;
		for(Triangle triangle : triangles){
			HitRecord hr = triangle.rayObjectHit(ray);
			if(hr.getT()>0 && hr.getT()<smallest_t){
				smallest_t = hr.getT();
				smallest = hr; 
			}
		}
		return smallest;
	}
	
	private HitRecord rayObjectHitAccelerated(Ray ray){
		return grid.hit(ray);
	}

	public Point3f[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Point3f[] coordinates) {
		this.coordinates = coordinates;
	}

	public int[] getCoordinateIndices() {
		return coordinateIndices;
	}

	public void setCoordinateIndices(int[] coordinateIndices) {
		this.coordinateIndices = coordinateIndices;
	}
	
	public void transform(Matrix4f transform){
		for(Triangle t : triangles){
			t.transform(transform);
		}
		if(DrawController.accelerated){
			if(triangles.size() > 1){
				grid = new CompactGrid(triangles); //TODO : controleer en gebruik in raytracing
				usingGrid = true;
			}
		}
	}

	@Override
	public void initialiseBBParameters() {
		float minX = Float.MAX_VALUE;
		float maxX = -Float.MAX_VALUE;
		float minY = Float.MAX_VALUE;
		float maxY = -Float.MAX_VALUE;
		float minZ = Float.MAX_VALUE;
		float maxZ = -Float.MAX_VALUE;
		for(Triangle t : triangles){
			if(!t.initialised){
				t.initialiseBBParameters();
			}//TODO : check formules nog is
			 if(minX > t.getBox().getMinX()) {minX = t.getBox().getMinX(); }
			 if(maxX < t.getBox().getMaxX()) {maxX = t.getBox().getMaxX(); }
			 if(minY > t.getBox().getMinY()) {minY = t.getBox().getMinY(); }
			 if(maxY < t.getBox().getMaxY()) {maxY = t.getBox().getMaxY(); }
			 if(minZ > t.getBox().getMinZ()) {minZ = t.getBox().getMinZ(); }
			 if(maxZ < t.getBox().getMaxZ()) {maxZ = t.getBox().getMaxZ(); }
		}
		this.box = new BoundingBox(minX, maxX, minY, maxY, minZ, maxZ);
		box.addGeometry(this);
	}

	public Vector4f[] getNormals() {
		return normals;
	}

	public void setNormals(Vector4f[] normals) {
		this.normals = normals;
	}

	public TexCoord2f[] getTextureCoordinates() {
		return textureCoordinates;
	}

	public void setTextureCoordinates(TexCoord2f[] textureCoordinates) {
		this.textureCoordinates = textureCoordinates;
	}

	public int[] getNormalIndices() {
		return normalIndices;
	}

	public void setNormalIndices(int[] normalIndices) {
		this.normalIndices = normalIndices;
	}

	public int[] getTextureCoordinateIndices() {
		return textureCoordinateIndices;
	}

	public void setTextureCoordinateIndices(int[] textureCoordinateIndices) {
		this.textureCoordinateIndices = textureCoordinateIndices;
	}

	public List<Triangle> getTriangles() {
		return triangles;
	}

	public void setTriangles(List<Triangle> triangles) {
		this.triangles = triangles;
	}
	
	@Override
	public void setMaterial(Material material){
		for(Triangle t : triangles){
			t.setMaterial(material);
		}
	}
}
