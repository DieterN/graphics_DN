package imagedraw;

import lights.Light;
import mathematics.Color3f;
import mathematics.Vector4f;
import mathematics.VectorOperations;
import rays.Ray;
import rays.ReflectiveRay;
import rays.ShadowRay;
import scenebuilder.Scene;

public abstract class DrawController {

	protected Scene scene;
	// Ambient
	protected final boolean ambient = true;
	protected final float ambientFactor = 0.2f;
	// Shading
	protected final boolean shading = true;
	// Reflection
	protected final boolean reflection = true;
	protected final int reflectionDepth = 1;
	// Anti-Aliasing
	public static final boolean antiAliasing = true;
	public static final int nbOfSamples = 5;
	// Soft Shadow
	public static final boolean softShadow = true;
	public static final int nbOfShadowSamples = 5;
	// Acceleration
	public static boolean accelerated = false;
	public static boolean useTrianglesInsteadOfMesh = false;
	// False Color Image
	public static final boolean falseColorImage = false;
	public static int currentPixel = 0;
	private static int[] intersectionsPerPixel; //countNumberOfIntersectionsForEveryPixel
	
	private static final int nx = 640; //number of pixels, x-direction
	private static final int ny = 480; //number of pixels, y-direction
	
	public DrawController(Scene scene){
		this.scene = scene;
		intersectionsPerPixel = new int[nx*ny];
	}
	
	public Color3f calculatePixelColor(float pixelX, float pixelY){
		HitRecord hr = lookForRayHit(pixelX,pixelY);
		return calculateColor(hr,reflectionDepth);
	}

	private Color3f calculateColor(HitRecord hr, int i) {
		Color3f color = new Color3f(); //TODO :background?
		if(hr != null){	
			if(ambient){ // ambient
				color.x = hr.getColor().x*ambientFactor;
				color.y = hr.getColor().y*ambientFactor;
				color.z = hr.getColor().z*ambientFactor;
			}
			if(shading){ //shading
				for(Light l : scene.getUsedLights()){
					Color3f lightColor = l.calculateShading(hr, this);
					color.x += lightColor.x;
					color.y += lightColor.y;
					color.z += lightColor.z;
				}
			}
			if(reflection){ //reflection
				calculateReflection(hr, i, color);
			}
			// TODO : refraction
		}
		Color3f rightColor = Color3f.checkColorsGreaterThanOne(color);
		return rightColor;
	}
	
	/**
	 * Calculate the closest ray hit, template method
	 * 
	 * @param pixelX
	 * @param pixelY
	 * @return
	 */
	public HitRecord lookForRayHit(float pixelX, float pixelY){
		Ray ray = scene.getCamera().getRay(pixelX, pixelY);
		HitRecord hr = calculateHitRecord(ray);
		return hr;		
	}
	
	/**
	 * Calculate the closest ray hit, should be overriden
	 * 
	 * @param ray
	 * @return
	 */
	public abstract HitRecord calculateHitRecord(Ray ray);
	
	/**
	 * Calculate if the hitpoint (saved in the hitRecord) is in shadow
	 * Template Method
	 * 
	 * @param hr
	 * @return
	 */
	public boolean inShadow(HitRecord hr, Light l) {
		boolean inShadow = true; // in de schaduw tenzij er geen hit is bij bepaalde ray
		Vector4f direction = VectorOperations.subtractPointfromPoint3f(l.getPosition(), hr.getHitPoint()); //richting naar licht
		Vector4f normalizedDirection = VectorOperations.normalizeVector4f(direction); // genormaliseerde richting naar licht
		Ray ray = new ShadowRay(hr.getHitPoint(), normalizedDirection); // nieuwe SchaduwRay (aparte klasse voor epsilon)
		if(!lookForShadowRayHit(ray)){
			inShadow = false;
		}
		return inShadow;
	}
	
	/**
	 * Check to see if this ray hits another object in the direction of the light source
	 * 
	 * @param hr
	 * @return
	 */
	public abstract boolean lookForShadowRayHit(Ray ray);	
	
	/**
	 * Calculate shading for an object, these methods are located in the material class
	 * Phong and Diffuse shading are implemented
	 * 
	 * @param hr
	 * @return
	 */
	public Color3f calculateShading(HitRecord hr, Light l){
		return hr.getGeometry().getMaterial().calculateShading(hr, l);
	}
	
	/**
	 * Reflective calculation; i = depth of reflection, color = calculated color till now
	 */
	public Color3f calculateReflection(HitRecord hr, int i, Color3f color){
		if(i==0){
			return color;
		}
		Vector4f direction = hr.getRay().getDirection();
		Vector4f normal = hr.getNormal();
		float two_d_times_n = 2*VectorOperations.scalarProduct4f(direction, normal); //2*d*n
		Vector4f subtract = VectorOperations.multiplyFloatandVector4f(two_d_times_n, normal);
		Vector4f reflectiveDirection = VectorOperations.subtractVectors4f(direction, subtract);
		Vector4f normalizedDirection = VectorOperations.normalizeVector4f(reflectiveDirection); // genormaliseerde richting van weerkaatsing
		Ray ray = new ReflectiveRay(hr.getHitPoint(), normalizedDirection); // nieuwe ReflectiveRay (aparte klasse voor epsilon)
		HitRecord hit = calculateHitRecord(ray);
		if(hit == null){
			return new Color3f(); //return black
		}
		Color3f reflectiveColor = calculateColor(hit, i-1);
		float reflectiveFactor = hr.getReflectiveFactor();
		color.x += reflectiveFactor*reflectiveColor.x;
		color.y += reflectiveFactor*reflectiveColor.y;
		color.z += reflectiveFactor*reflectiveColor.z;
		return color;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public static int getNx() {
		return nx;
	}

	public static int getNy() {
		return ny;
	}	
	
	public static void addIntersection(){
		intersectionsPerPixel[currentPixel]++;
	}

	public static int[] getIntersectionsPerPixel() {
		return intersectionsPerPixel;
	}
	
	public static void setCurrentPixel(int i){
		currentPixel = i;
	}
}
