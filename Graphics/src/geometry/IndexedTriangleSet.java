package geometry;

import imagedraw.HitRecord;

import java.util.List;
import java.util.ArrayList;

import rays.Ray;
import mathematics.*;

public class IndexedTriangleSet extends Geometry{

	private Point3f[] coordinates;
	private Vector4f[] normals;
	private TexCoord2f [] textureCoordinates;
	private int[] coordinateIndices;
	private int[] normalIndices;
	private int[] textureCoordinateIndices;
	private List<Triangle> triangles = new ArrayList<Triangle>();
	
	public IndexedTriangleSet(Point3f [] coordinates, Vector3f [] normals, TexCoord2f [] textureCoordinates, 
								int [] coordinateIndices, int [] normalIndices, int [] textureCoordinateIndices, String name){
		super(name);
		this.coordinates = coordinates;
		this.coordinateIndices = coordinateIndices;
		initialiseNormals(normals);
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
			int i = 0;
			for(Vector3f normal : normals){
				Vector4f normal4 = new Vector4f(normal);
				this.normals[i] = normal4;
				i++;
			}
		}
	}
	
	@Override
	public HitRecord rayObjectHit(Ray ray){
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
	}

	@Override
	public void initialiseBBParameters() {
		minX = triangles.get(0).getMinX();
		maxX = triangles.get(0).getMaxX();
		minY = triangles.get(0).getMinY();
		maxY = triangles.get(0).getMaxY();
		minZ = triangles.get(0).getMinZ();
		maxZ = triangles.get(0).getMaxZ();
		for(Triangle t : triangles){
			 minX = Math.floor(Math.max(minX,t.getMinX()));
			 maxX = Math.ceil(Math.max(maxX,t.getMaxX()));
			 minY = Math.floor(Math.max(minY,t.getMinY()));
			 maxY = Math.ceil(Math.max(maxY,t.getMaxY()));
			 minZ = Math.floor(Math.max(minZ,t.getMinZ()));
			 maxZ = Math.ceil(Math.max(maxZ,t.getMaxZ()));
		}
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
}
