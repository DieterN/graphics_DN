package scenebuilder;

import geometry.*;

import java.util.*;

import mathematics.Matrix4f;

public class GeometryGroup {
	
	private List<ConcreteGeomerty> geometry = new ArrayList<ConcreteGeomerty>();
	private Matrix4f transformationMatrix;
	private Matrix4f inverseTransformationMatrix;
	private List<GeometryGroup> children = new ArrayList<GeometryGroup>();
	private boolean closed = false;
	
	public GeometryGroup(ConcreteGeomerty geometry){
		addGeometry(geometry);
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		this.transformationMatrix = matrix; // initialiseer op identiteit
		this.inverseTransformationMatrix = matrix;// initialiseer op identiteit
	}

	public List<ConcreteGeomerty> getGeometry() {
		return geometry;
	}

	public void addGeometry(ConcreteGeomerty geometry) {
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
}
