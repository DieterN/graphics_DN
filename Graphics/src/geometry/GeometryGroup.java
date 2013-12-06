package geometry;

import java.util.*;

import mathematics.Matrix4f;

/**
 * Class used in the scenegraph, it contains:
 * - transformationmatrices, needed for raytracing
 * - a list of contained geometry
 * - a list with it's children in the scenegraph
 * - boolean closed, needed for building the scenegraph
 * 
 * @author Dieter
 *
 */
public class GeometryGroup {
	
	private List<ConcreteGeometry> geometry = new ArrayList<ConcreteGeometry>();
	private Matrix4f transformationMatrix;
	private Matrix4f inverseTransformationMatrix;
	private List<GeometryGroup> children = new ArrayList<GeometryGroup>();
	private boolean closed = false;
	
	public GeometryGroup(ConcreteGeometry geometry){
		addGeometry(geometry);
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		this.transformationMatrix = matrix; // initialiseer op identiteit
		this.inverseTransformationMatrix = matrix;// initialiseer op identiteit
	}	
	
	/**
	 * Method needed for recursively travelling the scenegraph.
	 * It returns itself and all its children and the children of those children ...
	 */
	public List<GeometryGroup> getThisAndAllChildren(){
		ArrayList<GeometryGroup> result = new ArrayList<GeometryGroup>();
		result.add(this);
		if(!children.isEmpty()){
			for(GeometryGroup geo : children){
				result.addAll(geo.getThisAndAllChildren());
			}
		}
		return result;
	}

	public List<ConcreteGeometry> getGeometry() {
		return geometry;
	}

	public void addGeometry(ConcreteGeometry geometry) {
		this.geometry.add(geometry);			
	}

	public Matrix4f getTransformationMatrix() {
		return transformationMatrix;
	}

	public void setTransformationMatrix(Matrix4f transformationMatrix) {
		this.transformationMatrix = transformationMatrix;
	}

	public Matrix4f getInverseTransformationMatrix() {
		return inverseTransformationMatrix;
	}

	public void setInverseTransformationMatrix(Matrix4f inverseTransformationMatrix) {
		this.inverseTransformationMatrix = inverseTransformationMatrix;
	}
	
	public List<GeometryGroup> getChildren() {
		return children;
	}

	public void addChild(GeometryGroup child) {
		this.children.add(child);
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}	
}
