package scenebuilder;

import java.util.*;

import acceleration.BoundingBox;
import cameras.Camera;
import lights.*;
import materials.*;
import mathematics.Color3f;
import mathematics.Matrix4f;
import geometry.*;
import imagedraw.*;

public class Scene {
	
	private HashMap<String,Camera> cameras = new HashMap<String,Camera>();
	private HashMap<String,Geometry> geometrics = new HashMap<String,Geometry>();
	private HashMap<String,Light> lights = new HashMap<String,Light>();
	private HashMap<String,Material> materials = new HashMap<String,Material>();
	
	private Camera camera;
	private List<Light> usedLights = new ArrayList<Light>();
	private List<Geometry> usedGeometryTransformed = new ArrayList<Geometry>(); 
	//This arrayList is used to save the transformed object from the sceneGraph. It's only called when needed.
	private List<BoundingBox> boxes = new ArrayList<BoundingBox>();
	//List needed for acceleration, attention, transform objects first!!!
	private boolean dimensionsCalculated = false;
	
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

	public HashMap<String, Geometry> getGeometrics() {
		return geometrics;
	}

	public void addGeometry(Geometry geometry) {
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

	public void addGeometryToSceneGraph(String geometryName) {
		Geometry usedGeometry = geometrics.get(geometryName);
		scenegraph.addGeometry(usedGeometry);
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
	
	public List<Geometry> getUsedGeometryTransformed() {
		if(this.usedGeometryTransformed.isEmpty()){
			usedGeometryTransformed = scenegraph.traverseTransformObject();
		}
		return usedGeometryTransformed;
	}

	public void setUsedGeometryTransformed(ArrayList<Geometry> usedGeometryTransformed) {
		this.usedGeometryTransformed = usedGeometryTransformed;
	}

	public float[] getDimensions(){
		float[] result = new float[6];
		List<Geometry> geo = getUsedGeometryTransformed();
		
		result[0] = Float.MAX_VALUE; //minX
		result[1] = -Float.MAX_VALUE; //maxX
		result[2] = Float.MAX_VALUE; //minY
		result[3] = -Float.MAX_VALUE; //maxY
		result[4] = Float.MAX_VALUE; //minZ
		result[5] = -Float.MAX_VALUE; //maxZ
		
		for(Geometry g : geo){
			this.boxes.add(g.getBox());
			if(g.getBox().getMinX() < result[0]){ result[0] = g.getBox().getMinX();}
			if(g.getBox().getMaxX() > result[1]){ result[1] = g.getBox().getMaxX();}
			if(g.getBox().getMinX() < result[2]){ result[2] = g.getBox().getMinY();}
			if(g.getBox().getMaxX() > result[3]){ result[3] = g.getBox().getMaxY();}
			if(g.getBox().getMinX() < result[4]){ result[4] = g.getBox().getMinZ();}
			if(g.getBox().getMaxX() > result[5]){ result[5] = g.getBox().getMaxZ();}
		}
		dimensionsCalculated = true;
		return result;
	}

	public List<BoundingBox> getBoxes() {
		if(!dimensionsCalculated){
			getDimensions();
		}
		return boxes;
	}
}
