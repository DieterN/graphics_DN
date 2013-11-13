package tests;

import static org.junit.Assert.*;
import mathematics.*;

import org.junit.*;

public class MathTest {
	
	private static Point3f point0;
	private static Point3f point1;
	private static Point3f point2;
	private static Vector4f vector0;
	private static Vector4f vector1;
	private static Vector4f vector2;
	private static Vector4f vector3;
	private static Vector3f vector4;
	private static Vector3f vector5;
	private static Vector3f vector6;
	private static Vector3f vector7;
	private static Matrix4f matrix0;
	private static Matrix4f matrix1;
	private static Matrix4f matrix2;

	@Before
	public void setUp() throws Exception {

		point0 = new Point3f();
		point1 = new Point3f(1,2,3);
		point2 = new Point3f(20,12,14);
		vector0 = new Vector4f();
		vector1 = new Vector4f(1,2,3,4);
		vector2 = new Vector4f(8,12,16,22);
		vector3 = new Vector4f(3,4,5,0);
		vector4 = new Vector3f();
		vector5 = new Vector3f(1,2,3);
		vector6 = new Vector3f(13,2,6);
		vector7 = new Vector3f(8,0,5);
		matrix0 = new Matrix4f();
		matrix1 = new Matrix4f(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16);
		matrix2 = new Matrix4f(5,8,3,2,12,15,14,13,9,8,7,8,6,10,11,13);
	}
	
	@Test
	public void testScalarProduct3f() {
		float result1 = VectorOperations.scalarProduct3f(vector4, vector5);
		float result2 = VectorOperations.scalarProduct3f(vector4, vector6);
		float result3 = VectorOperations.scalarProduct3f(vector4, vector7);
		float result4 = VectorOperations.scalarProduct3f(vector5, vector6);
		float result5 = VectorOperations.scalarProduct3f(vector5, vector7);
		float result6 = VectorOperations.scalarProduct3f(vector6, vector7);
		assertEquals(result1,0,0.01);
		assertEquals(result2,0,0.01);
		assertEquals(result3,0,0.01);
		assertEquals(result4,35,0.01);
		assertEquals(result5,23,0.01);
		assertEquals(result6,134,0.01);
	}

	@Test
	public void testScalarProduct4f() {
		float result1 = VectorOperations.scalarProduct4f(vector0, vector1);
		float result2 = VectorOperations.scalarProduct4f(vector0, vector2);
		float result3 = VectorOperations.scalarProduct4f(vector0, vector3);
		float result4 = VectorOperations.scalarProduct4f(vector1, vector2);
		float result5 = VectorOperations.scalarProduct4f(vector1, vector3);
		float result6 = VectorOperations.scalarProduct4f(vector2, vector3);
		assertEquals(result1,0,0.01);
		assertEquals(result2,0,0.01);
		assertEquals(result3,0,0.01);
		assertEquals(result4,168,0.01);
		assertEquals(result5,26,0.01);
		assertEquals(result6,152,0.01);
	}
	
	@Test
	public void testCrossProduct3f() {
		Vector3f result1 = VectorOperations.crossProduct3f(vector4, vector5);
		Vector3f result2 = VectorOperations.crossProduct3f(vector4, vector6);
		Vector3f result3 = VectorOperations.crossProduct3f(vector4, vector7);
		Vector3f result4 = VectorOperations.crossProduct3f(vector5, vector6);
		Vector3f result5 = VectorOperations.crossProduct3f(vector5, vector7);
		Vector3f result6 = VectorOperations.crossProduct3f(vector6, vector7);
		assertEquals(result1.x,0,0.01);
		assertEquals(result1.y,0,0.01);
		assertEquals(result1.z,0,0.01);
		assertEquals(result2.x,0,0.01);
		assertEquals(result2.y,0,0.01);
		assertEquals(result2.z,0,0.01);
		assertEquals(result3.x,0,0.01);
		assertEquals(result3.y,0,0.01);
		assertEquals(result3.z,0,0.01);
		assertEquals(result4.x,6,0.01);
		assertEquals(result4.y,33,0.01);
		assertEquals(result4.z,-24,0.01);
		assertEquals(result5.x,10,0.01);
		assertEquals(result5.y,19,0.01);
		assertEquals(result5.z,-16,0.01);
		assertEquals(result6.x,10,0.01);
		assertEquals(result6.y,-17,0.01);
		assertEquals(result6.z,-16,0.01);
	}
	
	@Test
	public void testCrossProduct4f() {
		//TODO
	}
	
	@Test
	public void testVectorNorm3f() {
		float result1 = VectorOperations.vectorNorm3f(vector4);
		float result2 = VectorOperations.vectorNorm3f(vector5);
		float result3 = VectorOperations.vectorNorm3f(vector6);
		float result4 = VectorOperations.vectorNorm3f(vector7);
		assertEquals(result1,0,0.01);
		assertEquals(result2,3.74165,0.01);
		assertEquals(result3,14.4568,0.01);
		assertEquals(result4,9.43398,0.01);
	}
	

	@Test
	public void testVectorNorm4f() {
		float result1 = VectorOperations.vectorNorm4f(vector0);
		float result2 = VectorOperations.vectorNorm4f(vector1);
		float result3 = VectorOperations.vectorNorm4f(vector2);
		float result4 = VectorOperations.vectorNorm4f(vector3);
		assertEquals(result1,0,0.01);
		assertEquals(result2,5.477,0.01);
		assertEquals(result3,30.7896,0.01);
		assertEquals(result4,7.07106,0.01);
	}

	@Test
	public void testGetCornerBetweenTwoVectors3f() {
		float result1 = VectorOperations.getCornerBetweenVectors3f(vector5,vector6);
		float result2 = VectorOperations.getCornerBetweenVectors3f(vector5,vector7);
		float result3 = VectorOperations.getCornerBetweenVectors3f(vector6,vector7);
		assertEquals(result1,0.86710,0.01);
		assertEquals(result2,0.86113,0.01);
		assertEquals(result3,0.18730,0.01);
	}

	@Test
	public void testGetCornerBetweenTwoVectors4f() {
		float result1 = VectorOperations.getCornerBetweenVectors4f(vector1,vector2);
		float result2 = VectorOperations.getCornerBetweenVectors4f(vector1,vector3);
		float result3 = VectorOperations.getCornerBetweenVectors4f(vector2,vector3);
		assertEquals(result1,0.08725,0.01);
		assertEquals(result2,0.83481,0.01);
		assertEquals(result3,0.79797,0.01);
	}
	
	@Test
	public void testAddVectors3f() {
		Vector3f result1 = VectorOperations.addVectors3f(vector4, vector5);
		Vector3f result2 = VectorOperations.addVectors3f(vector4, vector6);
		Vector3f result3 = VectorOperations.addVectors3f(vector4, vector7);
		Vector3f result4 = VectorOperations.addVectors3f(vector5, vector6);
		Vector3f result5 = VectorOperations.addVectors3f(vector5, vector7);
		Vector3f result6 = VectorOperations.addVectors3f(vector6, vector7);
		assertEquals(result1.x,1,0.01);
		assertEquals(result1.y,2,0.01);
		assertEquals(result1.z,3,0.01);
		assertEquals(result2.x,13,0.01);
		assertEquals(result2.y,2,0.01);
		assertEquals(result2.z,6,0.01);
		assertEquals(result3.x,8,0.01);
		assertEquals(result3.y,0,0.01);
		assertEquals(result3.z,5,0.01);
		assertEquals(result4.x,14,0.01);
		assertEquals(result4.y,4,0.01);
		assertEquals(result4.z,9,0.01);
		assertEquals(result5.x,9,0.01);
		assertEquals(result5.y,2,0.01);
		assertEquals(result5.z,8,0.01);
		assertEquals(result6.x,21,0.01);
		assertEquals(result6.y,2,0.01);
		assertEquals(result6.z,11,0.01);
	}
	
	@Test
	public void testAddVectors4f() {
		Vector4f result1 = VectorOperations.addVectors4f(vector0, vector1);
		Vector4f result2 = VectorOperations.addVectors4f(vector0, vector2);
		Vector4f result3 = VectorOperations.addVectors4f(vector0, vector3);
		Vector4f result4 = VectorOperations.addVectors4f(vector1, vector2);
		Vector4f result5 = VectorOperations.addVectors4f(vector1, vector3);
		Vector4f result6 = VectorOperations.addVectors4f(vector2, vector3);
		assertEquals(result1.x,1,0.01);
		assertEquals(result1.y,2,0.01);
		assertEquals(result1.z,3,0.01);
		assertEquals(result1.w,4,0.01);
		assertEquals(result2.x,8,0.01);
		assertEquals(result2.y,12,0.01);
		assertEquals(result2.z,16,0.01);
		assertEquals(result2.w,22,0.01);
		assertEquals(result3.x,3,0.01);
		assertEquals(result3.y,4,0.01);
		assertEquals(result3.z,5,0.01);
		assertEquals(result3.w,0,0.01);
		assertEquals(result4.x,9,0.01);
		assertEquals(result4.y,14,0.01);
		assertEquals(result4.z,19,0.01);
		assertEquals(result4.w,26,0.01);
		assertEquals(result5.x,4,0.01);
		assertEquals(result5.y,6,0.01);
		assertEquals(result5.z,8,0.01);
		assertEquals(result5.w,4,0.01);
		assertEquals(result6.x,11,0.01);
		assertEquals(result6.y,16,0.01);
		assertEquals(result6.z,21,0.01);
		assertEquals(result6.w,22,0.01);
	}
	
	@Test
	public void testSubtractVectors3f() {
		Vector3f result1 = VectorOperations.subtractVectors3f(vector4, vector5);
		Vector3f result2 = VectorOperations.subtractVectors3f(vector4, vector6);
		Vector3f result3 = VectorOperations.subtractVectors3f(vector4, vector7);
		Vector3f result4 = VectorOperations.subtractVectors3f(vector5, vector6);
		Vector3f result5 = VectorOperations.subtractVectors3f(vector5, vector7);
		Vector3f result6 = VectorOperations.subtractVectors3f(vector6, vector7);
		assertEquals(result1.x,-1,0.01);
		assertEquals(result1.y,-2,0.01);
		assertEquals(result1.z,-3,0.01);
		assertEquals(result2.x,-13,0.01);
		assertEquals(result2.y,-2,0.01);
		assertEquals(result2.z,-6,0.01);
		assertEquals(result3.x,-8,0.01);
		assertEquals(result3.y,-0,0.01);
		assertEquals(result3.z,-5,0.01);
		assertEquals(result4.x,-12,0.01);
		assertEquals(result4.y,0,0.01);
		assertEquals(result4.z,-3,0.01);
		assertEquals(result5.x,-7,0.01);
		assertEquals(result5.y,2,0.01);
		assertEquals(result5.z,-2,0.01);
		assertEquals(result6.x,5,0.01);
		assertEquals(result6.y,2,0.01);
		assertEquals(result6.z,1,0.01);
	}
	
	@Test
	public void testSubtractVectors4f() {
		Vector4f result1 = VectorOperations.subtractVectors4f(vector0, vector1);
		Vector4f result2 = VectorOperations.subtractVectors4f(vector0, vector2);
		Vector4f result3 = VectorOperations.subtractVectors4f(vector0, vector3);
		Vector4f result4 = VectorOperations.subtractVectors4f(vector1, vector2);
		Vector4f result5 = VectorOperations.subtractVectors4f(vector1, vector3);
		Vector4f result6 = VectorOperations.subtractVectors4f(vector2, vector3);
		assertEquals(result1.x,-1,0.01);
		assertEquals(result1.y,-2,0.01);
		assertEquals(result1.z,-3,0.01);
		assertEquals(result1.w,-4,0.01);
		assertEquals(result2.x,-8,0.01);
		assertEquals(result2.y,-12,0.01);
		assertEquals(result2.z,-16,0.01);
		assertEquals(result2.w,-22,0.01);
		assertEquals(result3.x,-3,0.01);
		assertEquals(result3.y,-4,0.01);
		assertEquals(result3.z,-5,0.01);
		assertEquals(result3.w,-0,0.01);
		assertEquals(result4.x,-7,0.01);
		assertEquals(result4.y,-10,0.01);
		assertEquals(result4.z,-13,0.01);
		assertEquals(result4.w,-18,0.01);
		assertEquals(result5.x,-2,0.01);
		assertEquals(result5.y,-2,0.01);
		assertEquals(result5.z,-2,0.01);
		assertEquals(result5.w,4,0.01);
		assertEquals(result6.x,5,0.01);
		assertEquals(result6.y,8,0.01);
		assertEquals(result6.z,11,0.01);
		assertEquals(result6.w,22,0.01);
	}
	
	@Test
	public void testInvertVector3f() {
		Vector3f result1 = VectorOperations.invertVector3f(vector4);
		Vector3f result2 = VectorOperations.invertVector3f(vector5);
		Vector3f result3 = VectorOperations.invertVector3f(vector6);
		Vector3f result4 = VectorOperations.invertVector3f(vector7);
		assertEquals(result1.x,0,0.01);
		assertEquals(result1.y,0,0.01);
		assertEquals(result1.z,0,0.01);
		assertEquals(result2.x,-1,0.01);
		assertEquals(result2.y,-2,0.01);
		assertEquals(result2.z,-3,0.01);
		assertEquals(result3.x,-13,0.01);
		assertEquals(result3.y,-2,0.01);
		assertEquals(result3.z,-6,0.01);
		assertEquals(result4.x,-8,0.01);
		assertEquals(result4.y,0,0.01);
		assertEquals(result4.z,-5,0.01);
	}
	
	@Test
	public void testInvertVector4f() {
		Vector4f result1 = VectorOperations.invertVector4f(vector0);
		Vector4f result2 = VectorOperations.invertVector4f(vector1);
		Vector4f result3 = VectorOperations.invertVector4f(vector2);
		Vector4f result4 = VectorOperations.invertVector4f(vector3);
		assertEquals(result1.x,0,0.01);
		assertEquals(result1.y,0,0.01);
		assertEquals(result1.z,0,0.01);
		assertEquals(result1.w,0,0.01);
		assertEquals(result2.x,-1,0.01);
		assertEquals(result2.y,-2,0.01);
		assertEquals(result2.z,-3,0.01);
		assertEquals(result2.w,-4,0.01);
		assertEquals(result3.x,-8,0.01);
		assertEquals(result3.y,-12,0.01);
		assertEquals(result3.z,-16,0.01);
		assertEquals(result3.w,-22,0.01);
		assertEquals(result4.x,-3,0.01);
		assertEquals(result4.y,-4,0.01);
		assertEquals(result4.z,-5,0.01);
		assertEquals(result4.w,-0,0.01);
	}	
	

	
	@Test
	public void testMatrixVectorProduct4f() {
		Vector4f result1 = MatrixOperations.MatrixVectorProduct(matrix0, vector1);
		Vector4f result2 = MatrixOperations.MatrixVectorProduct(matrix1, vector0);
		Vector4f result3 = MatrixOperations.MatrixVectorProduct(matrix1, vector1);
		Vector4f result4 = MatrixOperations.MatrixVectorProduct(matrix1, vector2);
		Vector4f result5 = MatrixOperations.MatrixVectorProduct(matrix1, vector3);
		Vector4f result6 = MatrixOperations.MatrixVectorProduct(matrix2, vector1);
		Vector4f result7 = MatrixOperations.MatrixVectorProduct(matrix2, vector2);
		Vector4f result8 = MatrixOperations.MatrixVectorProduct(matrix2, vector3);
		assertEquals(result1.x,0,0.01);
		assertEquals(result1.y,0,0.01);
		assertEquals(result1.z,0,0.01);
		assertEquals(result1.w,0,0.01);
		assertEquals(result2.x,0,0.01);
		assertEquals(result2.y,0,0.01);
		assertEquals(result2.z,0,0.01);
		assertEquals(result2.w,0,0.01);
		assertEquals(result3.x,30,0.01);
		assertEquals(result3.y,70,0.01);
		assertEquals(result3.z,110,0.01);
		assertEquals(result3.w,150,0.01);
		assertEquals(result4.x,168,0.01);
		assertEquals(result4.y,400,0.01);
		assertEquals(result4.z,632,0.01);
		assertEquals(result4.w,864,0.01);
		assertEquals(result5.x,26,0.01);
		assertEquals(result5.y,74,0.01);
		assertEquals(result5.z,122,0.01);
		assertEquals(result5.w,170,0.01);
		assertEquals(result6.x,38,0.01);
		assertEquals(result6.y,136,0.01);
		assertEquals(result6.z,78,0.01);
		assertEquals(result6.w,111,0.01);
		assertEquals(result7.x,228,0.01);
		assertEquals(result7.y,786,0.01);
		assertEquals(result7.z,456,0.01);
		assertEquals(result7.w,630,0.01);
		assertEquals(result8.x,62,0.01);
		assertEquals(result8.y,166,0.01);
		assertEquals(result8.z,94,0.01);
		assertEquals(result8.w,113,0.01);
	}	
	
	@Test
	public void testMatrixProduct4f() {
		Matrix4f result1 = MatrixOperations.MatrixProduct(matrix0, matrix1);
		Matrix4f result2 = MatrixOperations.MatrixProduct(matrix1, matrix2);
		Matrix4f result3 = MatrixOperations.MatrixProduct(matrix2, matrix1);
		assertEquals(result1.m00,0,0.01);
		assertEquals(result1.m01,0,0.01);
		assertEquals(result1.m02,0,0.01);
		assertEquals(result1.m03,0,0.01);
		assertEquals(result1.m10,0,0.01);
		assertEquals(result1.m11,0,0.01);
		assertEquals(result1.m12,0,0.01);
		assertEquals(result1.m13,0,0.01);
		assertEquals(result1.m20,0,0.01);
		assertEquals(result1.m21,0,0.01);
		assertEquals(result1.m22,0,0.01);
		assertEquals(result1.m23,0,0.01);
		assertEquals(result1.m30,0,0.01);
		assertEquals(result1.m31,0,0.01);
		assertEquals(result1.m32,0,0.01);
		assertEquals(result1.m33,0,0.01);
		assertEquals(result2.m00,80,0.01);
		assertEquals(result2.m01,102,0.01);
		assertEquals(result2.m02,96,0.01);
		assertEquals(result2.m03,104,0.01);
		assertEquals(result2.m10,208,0.01);
		assertEquals(result2.m11,266,0.01);
		assertEquals(result2.m12,236,0.01);
		assertEquals(result2.m13,248,0.01);
		assertEquals(result2.m20,336,0.01);
		assertEquals(result2.m21,430,0.01);
		assertEquals(result2.m22,376,0.01);
		assertEquals(result2.m23,392,0.01);
		assertEquals(result2.m30,464,0.01);
		assertEquals(result2.m31,594,0.01);
		assertEquals(result2.m32,516,0.01);
		assertEquals(result2.m33,536,0.01);
		assertEquals(result3.m00,98,0.01);
		assertEquals(result3.m01,116,0.01);
		assertEquals(result3.m02,134,0.01);
		assertEquals(result3.m03,152,0.01);
		assertEquals(result3.m10,382,0.01);
		assertEquals(result3.m11,436,0.01);
		assertEquals(result3.m12,490,0.01);
		assertEquals(result3.m13,544,0.01);
		assertEquals(result3.m20,216,0.01);
		assertEquals(result3.m21,248,0.01);
		assertEquals(result3.m22,280,0.01);
		assertEquals(result3.m23,312,0.01);
		assertEquals(result3.m30,324,0.01);
		assertEquals(result3.m31,364,0.01);
		assertEquals(result3.m32,404,0.01);
		assertEquals(result3.m33,444,0.01);
	}	
	

	@Test
	public void testSubtractPointfromPoint3f() {
		Vector4f result1 = VectorOperations.subtractPointfromPoint3f(point1, point0);
		Vector4f result2 = VectorOperations.subtractPointfromPoint3f(point1, point2);
		Vector4f result3 = VectorOperations.subtractPointfromPoint3f(point2, point1);
		assertEquals(result1.x,1,0.01);
		assertEquals(result1.y,2,0.01);
		assertEquals(result1.z,3,0.01);
		assertEquals(result1.w,0,0.01);
		assertEquals(result2.x,-19,0.01);
		assertEquals(result2.y,-10,0.01);
		assertEquals(result2.z,-11,0.01);
		assertEquals(result2.w,0,0.01);
		assertEquals(result3.x,19,0.01);
		assertEquals(result3.y,10,0.01);
		assertEquals(result3.z,11,0.01);
		assertEquals(result3.w,0,0.01);
	}	
	
	@Test
	public void testNormalizeVector() {
		Vector4f result1 = VectorOperations.normalizeVector4f(vector0);
		Vector4f result2 = VectorOperations.normalizeVector4f(vector1);
		Vector4f result3 = VectorOperations.normalizeVector4f(vector2);
		Vector4f result4 = VectorOperations.normalizeVector4f(vector3);
		assertEquals(result1.x,0,0.01);
		assertEquals(result1.y,0,0.01);
		assertEquals(result1.z,0,0.01);
		assertEquals(result1.w,0,0.01);
		assertEquals(result2.x,0.18257,0.01);
		assertEquals(result2.y,0.36514,0.01);
		assertEquals(result2.z,0.54772,0.01);
		assertEquals(result2.w,0.73030,0.01);
		assertEquals(result3.x,0.25983,0.01);
		assertEquals(result3.y,0.38974,0.01);
		assertEquals(result3.z,0.51966,0.01);
		assertEquals(result3.w,0.71453,0.01);
		assertEquals(result4.x,0.42426,0.01);
		assertEquals(result4.y,0.56569,0.01);
		assertEquals(result4.z,0.70711,0.01);
		assertEquals(result4.w,0,0.01);
	}
	
	@Test
	public void testVectorProduct4f() {
		Vector4f vector1 = new Vector4f(0,1,0,0);
		Vector4f vector2 = new Vector4f(0,0,-1,0);
		Vector4f result = VectorOperations.crossProduct4f(vector1, vector2);
		assertEquals(result.x,-1,0.01);
		assertEquals(result.y,0,0.01);
		assertEquals(result.z,0,0.01);
		assertEquals(result.w,0,0.01);		
	}
}
