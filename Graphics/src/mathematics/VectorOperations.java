package mathematics;

/**
 * Class that defines a number of useful operations on vectors.
 * 
 * @author Dieter
 *
 */
public class VectorOperations {
	
	/**
	 * This method calculates a scalar product between 2 vectors 
	 * 
	 * @param vector1 (vector3f)
	 * @param vector2 (vector3f)
	 * @return result of cross product between two given vectors
	 */
	public static float scalarProduct3f(Vector3f vector1, Vector3f vector2){
		float xProduct = vector1.x*vector2.x;
		float yProduct = vector1.y*vector2.y;
		float zProduct = vector1.z*vector2.z;
		return xProduct+yProduct+zProduct;
	}
	
	/**
	 * This method calculates a cross product between 2 vectors 
	 * 
	 * @param vector1 (vector3f)
	 * @param vector2 (vector3f)
	 * @return result of cross product between two given vectors
	 */
	public static Vector3f crossProduct3f(Vector3f vector1, Vector3f vector2){
		float xProduct = (vector1.y*vector2.z) - (vector1.z*vector2.y);
		float yProduct = (vector1.z*vector2.x) - (vector1.x*vector2.z);
		float zProduct = (vector1.x*vector2.y) - (vector1.y*vector2.x);
		return new Vector3f(xProduct,yProduct,zProduct);
	}

	/**
	 * Calculate the norm of a vector
	 * 
	 * @param vector (vector3f)
	 * @return norm of the given vector
	 */
	public static float vectorNorm3f(Vector3f vector){
		float x = (float) Math.pow(vector.x,2);
		float y = (float) Math.pow(vector.y,2);
		float z = (float) Math.pow(vector.z,2);
		return (float) Math.sqrt(x+y+z);
	}
	
	/**
	 * Calculate corner between two given vectors
	 * 
	 * @param vector1 (vector3f)
	 * @param vector2 (vector3f)
	 * @return theta : corner between two vectors
	 */
	public static float getCornerBetweenVectors3f(Vector3f vector1, Vector3f vector2){
		float norm1 = vectorNorm3f(vector1);
		float norm2 = vectorNorm3f(vector2);
		float scalar = scalarProduct3f(vector1, vector2);
		float theta = (float) Math.acos(scalar/(norm1*norm2));
		return theta;
	}
	
	/**
	 * Add the two given vectors
	 * 
	 * @param vector1 (vector3f)
	 * @param vector2 (vector3f)
	 * @return new Vector3f that is the result of adding the two given vectors
	 */
	public static Vector3f addVectors3f(Vector3f vector1, Vector3f vector2){
		float[] result = new float[3];
		result[0] = vector1.x + vector2.x;
		result[1] = vector1.y + vector2.y;
		result[2] = vector1.z + vector2.z;
		return new Vector3f(result);		
	}
	
	/**
	 * Subtract the second given vector from the first one
	 * 
	 * @param vector1 (vector3f)
	 * @param vector2 (vector3f)
	 * @return new Vector3f that is the result of subtracting vector2 from vector1
	 */
	public static Vector3f subtractVectors3f(Vector3f vector1, Vector3f vector2){
		float[] result = new float[3];
		result[0] = vector1.x - vector2.x;
		result[1] = vector1.y - vector2.y;
		result[2] = vector1.z - vector2.z;
		return new Vector3f(result);		
	}	

	/**
	 * Invert the given vector
	 * 
	 * @param vector (vector3f)
	 * @return new Vector3f that is the inverted vector of the given one
	 */
	public static Vector3f invertVector3f(Vector3f vector){
		float[] result = new float[3];
		result[0] = -vector.x;
		result[1] = -vector.y;
		result[2] = -vector.z;
		return new Vector3f(result);		
	}	
	
	/**
	 * This method calculates a scalar product between 2 vectors 
	 * 
	 * @param vector1 (vector4f)
	 * @param vector2 (vector4f)
	 * @return result of scalar product between two given vectors
	 */
	public static float scalarProduct4f(Vector4f vector1, Vector4f vector2){
		float xProduct = vector1.x*vector2.x;
		float yProduct = vector1.y*vector2.y;
		float zProduct = vector1.z*vector2.z;
		float wProduct = vector1.w*vector2.w;
		return xProduct+yProduct+zProduct+wProduct;
	}
	
	/**
	 * This method calculates a cross product between 2 vectors 
	 * 
	 * @param vector1 (vector4f)
	 * @param vector2 (vector4f)
	 * @return result of cross product between two given vectors
	 */
	public static Vector4f crossProduct4f(Vector4f vector1, Vector4f vector2){
		//TODO
		Vector3f vector3 = new Vector3f(vector1.x, vector1.y, vector1.z);
		Vector3f vector4 = new Vector3f(vector2.x, vector2.y, vector2.z);
		Vector3f vector5 = crossProduct3f(vector3,vector4);
		return new Vector4f(vector5.x,vector5.y,vector5.z,0);
	}
	
	/**
	 * Calculate the norm of a vector
	 * 
	 * @param vector (vector4f)
	 * @return norm of the given vector
	 */
	public static float vectorNorm4f(Vector4f vector){
		float x = (float) Math.pow(vector.x,2);
		float y = (float) Math.pow(vector.y,2);
		float z = (float) Math.pow(vector.z,2);
		float w = (float) Math.pow(vector.w,2);
		return (float) Math.sqrt(x+y+z+w);
	}
	
	/**
	 * Calculate corner between two given vectors
	 * 
	 * @param vector1 (vector4f)
	 * @param vector2 (vector4f)
	 * @return theta : corner between two vectors
	 */
	public static float getCornerBetweenVectors4f(Vector4f vector1, Vector4f vector2){
		float norm1 = vectorNorm4f(vector1);
		float norm2 = vectorNorm4f(vector2);
		float scalar = scalarProduct4f(vector1, vector2);
		float theta = (float) Math.acos(scalar/(norm1*norm2));
		return theta;
	}
	
	/**
	 * Add the two given vectors
	 * 
	 * @param vector1 (vector4f)
	 * @param vector2 (vector4f)
	 * @return new Vector4f that is the result of adding the two given vectors
	 */
	public static Vector4f addVectors4f(Vector4f vector1, Vector4f vector2){
		float[] result = new float[4];
		result[0] = vector1.x + vector2.x;
		result[1] = vector1.y + vector2.y;
		result[2] = vector1.z + vector2.z;
		result[3] = vector1.w + vector2.w;
		return new Vector4f(result);		
	}
	
	/**
	 * Subtract the second given vector from the first one
	 * 
	 * @param vector1 (vector4f)
	 * @param vector2 (vector4f)
	 * @return new Vector4f that is the result of subtracting vector2 from vector1
	 */
	public static Vector4f subtractVectors4f(Vector4f vector1, Vector4f vector2){
		float[] result = new float[4];
		result[0] = vector1.x - vector2.x;
		result[1] = vector1.y - vector2.y;
		result[2] = vector1.z - vector2.z;
		result[3] = vector1.w - vector2.w;
		return new Vector4f(result);		
	}	

	/**
	 * Invert the given vector
	 * 
	 * @param vector (vector4f)
	 * @return new Vector4f that is the inverted vector of the given one
	 */
	public static Vector4f invertVector4f(Vector4f vector){
		float[] result = new float[4];
		result[0] = -vector.x;
		result[1] = -vector.y;
		result[2] = -vector.z;
		result[3] = -vector.w;
		return new Vector4f(result);		
	}	
	
	/**
	 * Add the given vector to the given point
	 * 
	 * @param vector (vector4f)
	 * @param point
	 * @return Point, result of adding the given vector to the given point
	 */
	public static Point3f addVector4fToPoint(Vector4f vector, Point3f point){
		float[] result = new float[3];
		result[0] = vector.x + point.x;
		result[1] = vector.y + point.y;
		result[2] = vector.z + point.z;
		return new Point3f(result);
	}
	
	/**
	 * Add the given vector to the given point
	 * 
	 * @param vector (vector3f)
	 * @param point
	 * @return Point, result of adding the given vector to the given point
	 */
	public static Point3f addVector3fToPoint(Vector3f vector, Point3f point){
		float[] result = new float[3];
		result[0] = vector.x + point.x;
		result[1] = vector.y + point.y;
		result[2] = vector.z + point.z;
		return new Point3f(result);
	}
	
	/**
	 * Subtract the second given point from the first one
	 * 
	 * @param point1 (point3f)
	 * @param point2 (point3f)
	 * @return new Vector4f that is the result of subtracting point2 from point1
	 */
	public static Vector4f subtractPointfromPoint3f(Point3f point1, Point3f point2){
		float[] result = new float[4];
		result[0] = point1.x - point2.x;
		result[1] = point1.y - point2.y;
		result[2] = point1.z - point2.z;
		result[3] = 0;
		return new Vector4f(result);		
	}
	
	/**
	 * Normalize the given vector
	 * 
	 * @param vector (vector4f)
	 * @return The normalized vector
	 */
	public static Vector4f normalizeVector4f(Vector4f vector){
		float norm = vectorNorm4f(vector);
		if(norm == 0){
			return vector;
		}
		float[] result = new float[4];
		result[0] = vector.x/norm;
		result[1] = vector.y/norm;
		result[2] = vector.z/norm;
		result[3] = vector.w/norm;
		return new Vector4f(result);
	}
	
	/**
	 * Normalize the given vector
	 * 
	 * @param vector (vector3f)
	 * @return The normalized vector
	 */
	public static Vector3f normalizeVector3f(Vector3f vector) {
		float norm = vectorNorm3f(vector);
		if(norm == 0){
			return vector;
		}
		float[] result = new float[3];
		result[0] = vector.x/norm;
		result[1] = vector.y/norm;
		result[2] = vector.z/norm;
		return new Vector3f(result);
	}
	

	/**
	 * Multiply the given vector with a float
	 * 
	 * @param vector (vector4f)
	 * @param number (float)
	 * @return New vector that is result of multiplying number with vector
	 */
	public static Vector4f multiplyFloatandVector4f(float number, Vector4f vector){
		float[] result = new float[4];
		result[0] = vector.x*number;
		result[1] = vector.y*number;
		result[2] = vector.z*number;
		result[3] = vector.w*number;
		return new Vector4f(result);
	}
	
	/**
	 * Add a float to the given point
	 * 
	 * @param point (Point3)
	 * @param number (float)
	 * @return New Point that is result of adding number to point
	 */
	public static Point3f addFloatandPoint3f(float number, Point3f point){
		float[] result = new float[3];
		result[0] = point.x+number;
		result[1] = point.y+number;
		result[2] = point.z+number;
		return new Point3f(result);
	}
	
	/**
	 * Get a Vector from a given point, this vector has w-value = 1
	 * So it's actually an extended point.
	 * 
	 * @param point (Point3)
	 * @return New Vector that is the representation of the given point
	 */
	public static Vector4f getVectorFromPoint(Point3f point){
		float[] result = new float[4];
		result[0] = point.x;
		result[1] = point.y;
		result[2] = point.z;
		result[3] = 1;
 		return new Vector4f(result);
	}
	

	/**
	 * Get a Point from the given Vector, done by cutting of the w-value.
	 * 
	 * @param point (Point3)
	 * @return New Point that is the representation of the given vector
	 */
	public static Point3f getPointFromVector(Vector4f vector){
		float[] result = new float[3];
		result[0] = vector.x;
		result[1] = vector.y;
		result[2] = vector.z;
 		return new Point3f(result);
	}
}
