package geometry;

import imagedraw.DrawController;
import imagedraw.HitRecord;
import rays.Ray;
import mathematics.Matrix4f;
import mathematics.MatrixOperations;
import mathematics.Point3f;
import mathematics.TexCoord2f;
import mathematics.Vector4f;
import mathematics.VectorOperations;

/**
 * Class that represents a triangle, that is charcterized by it's 3 points.
 * 
 * @author Dieter
 *
 */
public class Triangle extends ConcreteGeomerty{

	private Point3f[] points = new Point3f[3];
	private Vector4f[] normals = new Vector4f[3];
	private TexCoord2f[] texture = new TexCoord2f[3]; //TODO : transformTextures?
	private float alpha;
	private float beta;
	private float gamma;
	
	public Triangle(Point3f[] points){
		super("");
		this.points = points;
	}
	
	public Triangle(Point3f point1, Point3f point2, Point3f point3){
		super("");
		this.setPoints(point1, point2, point3);
	}

	@Override
	public HitRecord rayObjectHit(Ray ray) {
		if(DrawController.falseColorImage){
			DrawController.addIntersection();
		}
		float t = calculateHitPoint(ray);
		return calculateHitRecord(t,ray);
	}
	
	private HitRecord calculateHitRecord(float t, Ray ray){
		Vector4f t_times_d = VectorOperations.multiplyFloatandVector4f(t, ray.getDirection()); // t*direction
		Point3f hitPoint = VectorOperations.addVector4fToPoint(t_times_d, ray.getViewPoint()); // hitPoint = viewPoint + t*direction
		Vector4f normal = getNormal(); // normaal (met interpolatie)
		Vector4f normalized = VectorOperations.normalizeVector4f(normal); // normaliseer normaal
		return new HitRecord(t,this,ray,hitPoint,normalized);
	}
	
	public float calculateHitPoint(Ray ray){
		float result = -1;
		Point3f pointA = points[0];
		Point3f pointB = points[1];
		Point3f pointC = points[2];
		float a = pointA.x - pointB.x;
		float b = pointA.y - pointB.y;
		float c = pointA.z - pointB.z;
		float d = pointA.x - pointC.x;
		float e = pointA.y - pointC.y;
		float f = pointA.z - pointC.z;
		float g = ray.getDirection().x;
		float h = ray.getDirection().y;
		float i = ray.getDirection().z;
		float j = pointA.x - ray.getViewPoint().x;
		float k = pointA.y - ray.getViewPoint().y;
		float l = pointA.z - ray.getViewPoint().z;
		float ei_minus_hf = e*i - h*f;
		float gf_minus_di = g*f - d*i;
		float dh_minus_eg = d*h - e*g;
		float ak_minus_jb = a*k - j*b;
		float jc_minus_al = j*c - a*l;
		float bl_minus_kc = b*l - k*c;
		float M = a*(ei_minus_hf) + b*(gf_minus_di) + c*(dh_minus_eg);
		float beta = (j*(ei_minus_hf) + k*(gf_minus_di) + l*(dh_minus_eg))/M;
		float gamma = (i*(ak_minus_jb) + h*(jc_minus_al) + g*(bl_minus_kc))/M;
		float t = -(f*(ak_minus_jb) + e*(jc_minus_al) + d*(bl_minus_kc))/M;
		if(!(t<0 || beta <0 || gamma < 0 || (beta+gamma)>1)){
			result = t;
			this.alpha = 1-beta-gamma;
			this.beta = beta;
			this.gamma = gamma;
		}
		return result;
	}
	
	public Point3f[] getPoints() {
		return points;
	}

	public void setPoints(Point3f[] points) {
		this.points = points;
	}
	
	public void setPoints(Point3f point1, Point3f point2, Point3f point3){
		Point3f[] newPoints = new Point3f[3];
		newPoints[0] = point1;
		newPoints[1] = point2;
		newPoints[2] = point3;
		this.points = newPoints;
	}
	
	public Vector4f getNormal(){
		Vector4f result = new Vector4f();
		if(normals[0] != null && normals[1] != null && normals[2] != null){
			Vector4f vectorA = VectorOperations.multiplyFloatandVector4f(alpha, normals[0]);	
			Vector4f vectorB = VectorOperations.multiplyFloatandVector4f(beta, normals[1]);	
			Vector4f vectorC = VectorOperations.multiplyFloatandVector4f(gamma, normals[2]);	
			
			result = VectorOperations.addVectors4f(vectorC, VectorOperations.addVectors4f(vectorA, vectorB));
		}
		else{
			Vector4f[] vectors = calculateNormalVectors();
			Vector4f alphaVectorA = vectors[0];
			Vector4f alphaVectorB = vectors[1];
			Vector4f alphaVectorC = vectors[2];
			result = VectorOperations.addVectors4f(alphaVectorC, VectorOperations.addVectors4f(alphaVectorA, alphaVectorB));
		}
		return result;
	}
	
	private Vector4f[] calculateNormalVectors(){
		Vector4f[] result = new Vector4f[3];
		Vector4f vector1A = VectorOperations.subtractPointfromPoint3f(points[0], points[1]);
		Vector4f vector2A = VectorOperations.subtractPointfromPoint3f(points[0], points[2]);
		Vector4f vectorA = VectorOperations.crossProduct4f(vector1A, vector2A);
		normals[0] = vectorA;
		result[0] = VectorOperations.multiplyFloatandVector4f(alpha, vectorA);

		Vector4f vector1B = VectorOperations.subtractPointfromPoint3f(points[1], points[2]);
		Vector4f vector2B = VectorOperations.subtractPointfromPoint3f(points[1], points[0]);
		Vector4f vectorB = VectorOperations.crossProduct4f(vector1B, vector2B);
		normals[1] = vectorB;
		result[1] = VectorOperations.multiplyFloatandVector4f(beta, vectorB);

		Vector4f vector1C = VectorOperations.subtractPointfromPoint3f(points[2], points[0]);
		Vector4f vector2C = VectorOperations.subtractPointfromPoint3f(points[2], points[1]);
		Vector4f vectorC = VectorOperations.crossProduct4f(vector1C, vector2C);
		normals[2] = vectorC;
		result[2] = VectorOperations.multiplyFloatandVector4f(gamma, vectorC);
		
		return result;
	}

	public void setNormal(Vector4f normal1, Vector4f normal2, Vector4f normal3) {
		normals[0] = normal1;
		normals[1] = normal2;
		normals[2] = normal3;
	}

	public void setTexture(TexCoord2f texture1, TexCoord2f texture2, TexCoord2f texture3) {
		texture[0] = texture1;
		texture[1] = texture2;
		texture[2] = texture3;
	}

	@Override
	public void transform(Matrix4f transform) {
		transformPoints(transform);
		transformNormals(transform);
	}
	
	private void transformPoints(Matrix4f transform){
		Point3f[] newPoints = new Point3f[3];
		int i = 0;
		for(Point3f p : points){
			Vector4f pVec = VectorOperations.getVectorFromPoint(p);
			Vector4f pVecTr = MatrixOperations.MatrixVectorProduct(transform, pVec);
			newPoints[i] = VectorOperations.getPointFromVector(pVecTr);
			i++;
		}
		this.points = newPoints;
	}


	private void transformNormals(Matrix4f transform){
		Vector4f[] newNormals = new Vector4f[3];
		int i = 0;
		if(normals[0] != null){
			for(Vector4f n : normals){
				newNormals[i] = MatrixOperations.MatrixVectorProduct(transform, n);
				i++;
			}
			this.normals = newNormals;
		}
		else{
			calculateNormalVectors();
			transformNormals(transform);
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
		
		for(Point3f p : points){
			 if(minX > p.x) {minX = p.x;}
			 if(maxX < p.x) {maxX = p.x; }
			 if(minY > p.y) {minY = p.y; }
			 if(maxY < p.y) {maxY = p.y; }
			 if(minZ > p.z) {minZ = p.z; }
			 if(maxZ < p.z) {maxZ = p.z; }
		}
		this.box = new BoundingBox(minX, maxX, minY, maxY, minZ, maxZ);
		box.addGeometry(this);
	}
}
