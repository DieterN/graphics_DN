package imagedraw;

import lights.Light;
import mathematics.Color3f;
import mathematics.Vector4f;
import mathematics.VectorOperations;
import rays.Ray;
import rays.ShadowRay;
import scenebuilder.Scene;

public abstract class DrawController {

	protected Scene scene;
	protected final boolean ambient = true;
	protected final boolean shading = true;
	private static final int nx = 640; //number of pixels, x-direction
	private static final int ny = 480; //number of pixels, y-direction
	
	public DrawController(Scene scene){
		this.scene = scene;
	}
	
	public Color3f calculatePixelColor(int pixelX, int pixelY){
	HitRecord hr = lookForRayHit(pixelX,pixelY);
	Color3f color = new Color3f();
	if(hr != null){	
		if(ambient){ // ambient
			color.x = hr.getColor().x*hr.getAmbientFactor();
			color.y = hr.getColor().y*hr.getAmbientFactor();
			color.z = hr.getColor().z*hr.getAmbientFactor();
		}
		if(shading){ //shading
			if(!inShadow(hr)){
				Color3f shadingColor = calculateShading(hr);
				color.x += shadingColor.x;
				color.y += shadingColor.y;
				color.z += shadingColor.z;
			}
		}
		// TODO : reflective + refraction + anti-aliasing
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
	public HitRecord lookForRayHit(int pixelX, int pixelY){
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
	public boolean inShadow(HitRecord hr) {
		boolean inShadow = true; // in de schaduw tenzij er geen hit is bij bepaalde ray
		for(Light l : scene.getUsedLights()){
			Vector4f direction = VectorOperations.subtractPointfromPoint3f(l.getPosition(), hr.getHitPoint()); //richting naar licht
			Vector4f normalizedDirection = VectorOperations.normalizeVector4f(direction); // genormaliseerde richting naar licht
			Ray ray = new ShadowRay(hr.getHitPoint(), normalizedDirection); // nieuwe SchaduwRay (aparte klasse voor epsilon)
			if(!lookForShadowRayHit(ray)){
				inShadow = false;
				break;
			}
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
	public Color3f calculateShading(HitRecord hr){
		Color3f result = new Color3f();
		for(Light l : scene.getUsedLights()){
			Color3f color = hr.getGeometry().getMaterial().calculateShading(hr, l);
			result.x += color.x;
			result.y += color.y;
			result.z += color.z;
		}
		
		return result;
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
}
