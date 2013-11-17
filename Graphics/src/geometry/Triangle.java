package geometry;

import rays.Ray;
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
public class Triangle {

	private Point3f[] points = new Point3f[3];
	private Vector4f[] normal = new Vector4f[3];
	private TexCoord2f[] texture = new TexCoord2f[3];
	private float alpha;
	private float beta;
	private float gamma;
	
	public Triangle(Point3f[] points){
		this.points = points;
	}
	
	public Triangle(Point3f point1, Point3f point2, Point3f point3){
		this.setPoints(point1, point2, point3);
	}
	
	public float rayObjectHit(Ray ray){
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
		if(normal[0] != null && normal[1] != null && normal[2] != null){
			Vector4f vectorA = VectorOperations.multiplyFloatandVector4f(alpha, normal[0]);	
			Vector4f vectorB = VectorOperations.multiplyFloatandVector4f(beta, normal[1]);	
			Vector4f vectorC = VectorOperations.multiplyFloatandVector4f(gamma, normal[2]);	
			
			result = VectorOperations.addVectors4f(vectorC, VectorOperations.addVectors4f(vectorA, vectorB));
		}
		else{
			Vector4f vector1A = VectorOperations.subtractPointfromPoint3f(points[0], points[1]);
			Vector4f vector2A = VectorOperations.subtractPointfromPoint3f(points[0], points[2]);
			Vector4f vectorA = VectorOperations.crossProduct4f(vector1A, vector2A);
			normal[0] = vectorA;
			Vector4f alphaVectorA = VectorOperations.multiplyFloatandVector4f(alpha, vectorA);

			Vector4f vector1B = VectorOperations.subtractPointfromPoint3f(points[1], points[2]);
			Vector4f vector2B = VectorOperations.subtractPointfromPoint3f(points[1], points[0]);
			Vector4f vectorB = VectorOperations.crossProduct4f(vector1B, vector2B);
			normal[1] = vectorB;
			Vector4f alphaVectorB = VectorOperations.multiplyFloatandVector4f(beta, vectorB);

			Vector4f vector1C = VectorOperations.subtractPointfromPoint3f(points[2], points[0]);
			Vector4f vector2C = VectorOperations.subtractPointfromPoint3f(points[2], points[1]);
			Vector4f vectorC = VectorOperations.crossProduct4f(vector1C, vector2C);
			normal[2] = vectorC;
			Vector4f alphaVectorC = VectorOperations.multiplyFloatandVector4f(gamma, vectorC);
			
			result = VectorOperations.addVectors4f(alphaVectorC, VectorOperations.addVectors4f(alphaVectorA, alphaVectorB));
		}
		return result;
	}

	public void setNormal(Vector4f normal1, Vector4f normal2, Vector4f normal3) {
		normal[0] = normal1;
		normal[1] = normal2;
		normal[2] = normal3;
	}

	public void setTexture(TexCoord2f texture1, TexCoord2f texture2, TexCoord2f texture3) {
		texture[0] = texture1;
		texture[1] = texture2;
		texture[2] = texture3;
	}
}
