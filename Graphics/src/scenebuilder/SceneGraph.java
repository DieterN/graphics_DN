package scenebuilder;

import geometry.ConcreteGeomerty;
import geometry.Geometry;

import java.util.*;

import mathematics.Matrix4f;
import mathematics.MatrixOperations;

public class SceneGraph {
	
	private List<GeometryGroup> roots = new ArrayList<GeometryGroup>();
	private Stack<Matrix4f[]> matrixStack = new Stack<Matrix4f[]>();  
	private Stack<GeometryGroup> geometryStack = new Stack<GeometryGroup>(); 
	
	public SceneGraph() {
		
	}
	
	public void addGeometry(ConcreteGeomerty g){
		if(geometryStack.isEmpty()){ // nieuwe groep, eerste element in sceneGraph, voeg toe als root, voeg transform toe
			GeometryGroup geometry = new GeometryGroup(g);
			if(!matrixStack.isEmpty()){
				geometry.setTransformationMatrix(matrixStack.peek()[0]);
				geometry.setInverseTransformationMatrix(matrixStack.peek()[1]);
			}
			this.roots.add(geometry);
			this.geometryStack.push(geometry);
		}
		else{ // er zit al iets in de sceneGraph
			GeometryGroup geo = geometryStack.peek();
			if(!geo.isClosed()){ // eerste element nog niet closed, voeg dan geometry toe
				geo.addGeometry(g);
			}
			else{ // eerste element is closed, maak nieuwe groep, voeg toe aan stack en zet als kind bij vorige groep, zet ook transform
			 GeometryGroup geometry = new GeometryGroup(g);
			 if(!matrixStack.isEmpty()){
					geometry.setTransformationMatrix(matrixStack.peek()[0]);
					geometry.setInverseTransformationMatrix(matrixStack.peek()[1]);
				}
			 geo.addChild(geometry);
			 this.geometryStack.push(geometry);
			}
		}
	}
	
	public void addMatrices(Matrix4f[] m){
		if(!(this.geometryStack.isEmpty())){ // als er geometry is, sluiten, er komt nieuwe transformatie bij
			GeometryGroup geo = geometryStack.peek();
			geo.setClosed(true);
		}
		if(this.matrixStack.isEmpty()){ // als er nog geen transformaties zijn, voeg dan deze gewoon toe als eerste
			matrixStack.push(m);
		}
		else{ // als er al wel transformaties zijn, vermenigvuldig dan deze transformatie met de vorige van de stack en voeg product toe
			Matrix4f top = matrixStack.peek()[0];
			Matrix4f multi = MatrixOperations.MatrixProduct(top, m[0]);
			Matrix4f inverseTop = matrixStack.peek()[1];
			Matrix4f inverseMulti = MatrixOperations.MatrixProduct(m[1],inverseTop);
			Matrix4f[] newTop = new Matrix4f[2];
			newTop[0] = multi;
			newTop[1] = inverseMulti;
			matrixStack.push(newTop);
		}
	}
	
	public void removeMatrices(){
		if(!(this.geometryStack.isEmpty())){ // als er geometry is, sluiten, er komt nieuwe transformatie bij
			GeometryGroup geo = geometryStack.pop();
			geo.setClosed(true);
		}
		this.matrixStack.pop(); // haal matrix van de stack
	}

	public List<GeometryGroup> getRoots() {
		return roots;
	}

	public Stack<Matrix4f[]> getMatrixStack() {
		return matrixStack;
	}

	public Stack<GeometryGroup> getGeometryStack() {
		return geometryStack;
	}
	
	public List<GeometryGroup> traverseTransformRay(){ // geef scenegraph lijsten terug om later rays te transformeren
		ArrayList<GeometryGroup> graph = new ArrayList<GeometryGroup>();
		for(GeometryGroup geo : roots){
			graph.addAll(geo.getThisAndAllChildren());
		}
		return graph;
	}
	
	public List<ConcreteGeomerty> traverseTransformObject(){ //transformeer alle geometrie
		ArrayList<GeometryGroup> graph = new ArrayList<GeometryGroup>();
		ArrayList<ConcreteGeomerty> geometry = new ArrayList<ConcreteGeomerty>();
		for(GeometryGroup geo : roots){
			graph.addAll(geo.getThisAndAllChildren());
		}
		for(GeometryGroup geo : graph){
			for(ConcreteGeomerty g : geo.getGeometry()){
				g.transform(geo.getTransformationMatrix());
				geometry.add(g);
			}
		}
		return geometry;
	}
}
