package geometry;

import imagedraw.HitRecord;

import java.util.ArrayList;

import rays.Ray;

import mathematics.*;

public class IndexedTriangleSet extends Geometry{

	private Point3f[] coordinates;
	private Vector4f[] normals; //TODO
	private TexCoord2f [] textureCoordinates; //TODO
	private int[] coordinateIndices;
	private int[] normalIndices; //TODO
	private int[] textureCoordinateIndices; //TODO
	
	public IndexedTriangleSet(Point3f [] coordinates, Vector3f [] normals, TexCoord2f [] textureCoordinates, 
								int [] coordinateIndices, int [] normalIndices, int [] textureCoordinateIndices, String name){
		super(name);
		this.coordinates = coordinates;
		this.coordinateIndices = coordinateIndices;
		initialiseNormals(normals);
		this.normalIndices = normalIndices;
		this.textureCoordinates = textureCoordinates;
		this.textureCoordinateIndices = textureCoordinateIndices;
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
		ArrayList<Triangle> triangles = this.getTriangles();
		float smallest_t = Float.POSITIVE_INFINITY;
		HitRecord hr = null;
		for(Triangle triangle : triangles){
			float t = triangle.rayObjectHit(ray);
			if(t>0 && t<smallest_t){
				smallest_t = t;
				hr = calculateHitRecord(t,ray,triangle); 
			}
		}
		return hr;
	}
	
	private HitRecord calculateHitRecord(float t, Ray ray, Triangle triangle){
		Vector4f t_times_d = VectorOperations.multiplyFloatandVector4f(t, ray.getDirection()); // t*direction
		Point3f hitPoint = VectorOperations.addVector4fToPoint(t_times_d, ray.getViewPoint()); // hitPoint = viewPoint + t*direction
		Vector4f normal = triangle.getNormal(); // normaal
		Vector4f normalized = VectorOperations.normalizeVector4f(normal); // normaliseer normaal
		return new HitRecord(t,this,ray,hitPoint,normalized);
	}

	public ArrayList<Triangle> getTriangles(){
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
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
		return triangles;
		// TODO : INITALISE NORMALS
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
		this.coordinates = transformCoordinates(transform);
		this.normals = transformNormals(transform);
		//TODO textures
	}
	
	private Point3f[] transformCoordinates(Matrix4f transform){
		Point3f[] newCoordinates = new Point3f[coordinates.length];
		int i = 0;
		for(Point3f p : coordinates){
			Vector4f pVec = VectorOperations.getVectorFromPoint(p);
			Vector4f pVecTr = MatrixOperations.MatrixVectorProduct(transform, pVec);
			newCoordinates[i] = VectorOperations.getPointFromVector(pVecTr);
			i++;
		}
		return newCoordinates;
	}
	
	private Vector4f[] transformNormals(Matrix4f transform){
		Vector4f[] newNormals = null;
		if(normals != null){
			newNormals = new Vector4f[normals.length];
			int j = 0;
			for(Vector4f n : normals){
				newNormals[j] = VectorOperations.normalizeVector4f(MatrixOperations.MatrixVectorProduct(transform, n)); 
				//TODO : normalize?
				j++;
			}
		}
		return newNormals;
	}
}
