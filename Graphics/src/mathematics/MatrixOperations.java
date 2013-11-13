package mathematics;

/**
 * Class that defines a number of useful operations on matrices
 * 
 * @author Dieter
 * 
 */
public class MatrixOperations {

	/**
	 * Calculate product between two matrices
	 * 
	 * @param matrix1
	 * @param matrix2
	 * @return matrix that is the product between two matrices
	 */
	public static Matrix4f MatrixProduct(Matrix4f matrix1, Matrix4f matrix2){
		Matrix4f matrix = new Matrix4f();
   	 	Vector4f v = new Vector4f();
   	 	Vector4f w = new Vector4f();
		for (int row=0;row<4;row++){
	         for (int column=0;column<4;column++){
	        	 matrix1.getRow(row, v);
	        	 matrix2.getColumn(column, w);
	        	 float z = VectorOperations.scalarProduct4f(v, w);
	        	 matrix.setElement(row,column,z);	        	 
	         }
		} 	
		return matrix;
	}
	
	/**
	 * Calculate product between a matrix and a vector
	 * 
	 * @param matrix1
	 * @param matrix2
	 * @return matrix that is the product between the given matrix and vector
	 */
	public static Vector4f MatrixVectorProduct(Matrix4f matrix, Vector4f vector){
   	 	Vector4f v = new Vector4f();
   	 	float[] vectorArray = new float[4];
   	 	float z;
		for (int row=0;row<4;row++){
	        	 matrix.getRow(row, v);
	        	 z = VectorOperations.scalarProduct4f(v, vector);
	        	 vectorArray[row] = z;
		} 	
		Vector4f resultVector = new Vector4f(vectorArray);
		return resultVector;
	}
	
	/**
	 * Construct a translation matrix with the given translationvector. 
	 * 
	 * @param vector
	 * @return translationmatrix
	 */
	public static Matrix4f MakeTranslationMatrix(Vector3f vector){
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Vector4f vector4f = new Vector4f(vector.x,vector.y,vector.z,1);
		matrix.setColumn(3, vector4f);
		return matrix;
	}
	
	/**
	 * Construct a rotation matrix with the given axis and angle. 
	 * 
	 * @param axis : rotationaxis
	 * @param float
	 * @return rotation matrix
	 */
	public static Matrix4f MakeRotationMatrix(Vector3f axis, float angle){
		float[] m = new float[16];
		Vector3f nA = VectorOperations.normalizeVector3f(axis);
		float theta = (float) Math.toRadians(angle); //TODO : -theta?
		float x = nA.x;
		float y = nA.y;
		float z = nA.z;
		float x_2 = (float) Math.pow(nA.x,2);
		float y_2 = (float) Math.pow(nA.y,2);
		float z_2 = (float) Math.pow(nA.z,2);
		float cos_t = (float) Math.cos(theta);
		float sin_t = (float) Math.sin(theta);
		m[0] = cos_t + x_2*(1-cos_t); 
		m[1] = (x*y)*(1-cos_t)-z*sin_t; 
		m[2] = (x*z)*(1-cos_t)+y*sin_t; 			
		m[3] = 0;
		m[4] = (y*x)*(1-cos_t)+z*sin_t;					
		m[5] = cos_t + y_2*(1-cos_t);  
		m[6] = (y*z)*(1-cos_t)-x*sin_t;	 			
		m[7] = 0;
		m[8] = (z*x)*(1-cos_t)-y*sin_t;
		m[9] = (z*y)*(1-cos_t)+x*sin_t;	 
		m[10] = cos_t + z_2*(1-cos_t); 			
		m[11] = 0;
		m[12] = 0; 
		m[13] = 0; 
		m[14] = 0; 		
		m[15] = 1;
		return new Matrix4f(m);
	}
	
	/**
	 * Construct a scaling matrix with the given scalingvector. 
	 * 
	 * @param vector
	 * @return rotation matrix
	 */
	public static Matrix4f MakeScalingMatrix(Vector3f scale){
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		matrix.setElement(0,0,scale.x);
		matrix.setElement(1,1,scale.y);
		matrix.setElement(2,2,scale.z);
		return matrix;
	}
}
