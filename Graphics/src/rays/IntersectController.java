package rays;

import lights.Light;
import mathematics.Color3f;
import mathematics.Vector4f;
import mathematics.VectorOperations;
import imagedraw.HitRecord;
import scenebuilder.Scene;

public abstract class IntersectController {

	protected Scene scene;
	
	public IntersectController(){
		
	}
	
	public HitRecord lookForRayHit(int pixelX, int pixelY){
		Ray ray = Ray.getRay(scene.getCamera(), pixelX, pixelY);		
		HitRecord hr = calculateHitRecord(ray);
		return hr;		
	}
	
	public abstract HitRecord calculateHitRecord(Ray ray);
	
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
	
	public abstract boolean lookForShadowRayHit(Ray ray);	
	
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
}
