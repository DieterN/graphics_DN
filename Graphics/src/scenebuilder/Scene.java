package scenebuilder;

import java.util.*;

import cameras.Camera;
import lights.*;
import materials.*;
import mathematics.Color3f;
import mathematics.Matrix4f;
import geometry.*;
import imagedraw.*;

public class Scene {
	
	private HashMap<String,Camera> cameras = new HashMap<String,Camera>();
	private HashMap<String,ConcreteGeometry> geometrics = new HashMap<String,ConcreteGeometry>();
	private HashMap<String,Light> lights = new HashMap<String,Light>();
	private HashMap<String,Material> materials = new HashMap<String,Material>();
	
	private Camera camera;
	private List<Light> usedLights = new ArrayList<Light>();
	private List<ConcreteGeometry> usedGeometryTransformed = new ArrayList<ConcreteGeometry>(); 
	
	private SceneGraph scenegraph;
	
	private Color3f backgroundColor;
	
	public Scene(SceneGraph scenegraph){
		this.scenegraph = scenegraph;
	}

	public HashMap<String, Camera> getCameras() {
		return cameras;
	}

	public void addCamera(Camera camera) {
		cameras.put(camera.getName(),camera);
	}

	public HashMap<String, ConcreteGeometry> getGeometrics() {
		return geometrics;
	}

	public void addGeometry(ConcreteGeometry geometry) {
		geometrics.put(geometry.getName(),geometry);
	}

	public HashMap<String, Light> getLights() {
		return lights;
	}

	public void addLight(Light light) {
		lights.put(light.getName(), light);
	}

	public HashMap<String, Material> getMaterials() {
		return materials;
	}

	public void addMaterial(Material material) {
		materials.put(material.getName(), material);
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCameraToUsed(String cameraName) {
		Camera usedCamera = cameras.get(cameraName);
		camera = usedCamera;
	}

	public void addGeometryToSceneGraph(ConcreteGeometry geometry) {
		scenegraph.addGeometry(geometry);
	}
	
	/**
	 * First is regular matrix, second is inverse
	 * 
	 * @param matrix
	 */
	public void addTransformationMatrices(Matrix4f[] matrix){
		scenegraph.addMatrices(matrix);
	}
	
	public void removeTransformationMatrices(){
		scenegraph.removeMatrices();
	}

	public List<Light> getUsedLights() {
		return usedLights;
	}

	public void setLightToUsed(String[] lightNames) {
		for(String l : lightNames){
			Light usedLight = lights.get(l);
			usedLights.add(usedLight);	
		}
	}

	public Color3f getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color3f backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public SceneGraph getScenegraph() {
		return scenegraph;
	}

	public void setScenegraph(SceneGraph scenegraph) {
		this.scenegraph = scenegraph;
	}
	
	public List<ConcreteGeometry> getUsedGeometryTransformed() {
		if(this.usedGeometryTransformed.isEmpty()){
			usedGeometryTransformed = scenegraph.traverseTransformObject();
		}
		return usedGeometryTransformed;
	}
}
