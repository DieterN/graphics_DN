package imagedraw;

import acceleration.boundingIntervalHierarchy.BoundingIntervalHierarchy;
import rays.Ray;
import scenebuilder.Scene;

public class DCBoundingIntervalHierarchy extends DrawController{

	private BoundingIntervalHierarchy bih;
	
	public DCBoundingIntervalHierarchy(Scene scene) {
		super(scene);
		bih = new BoundingIntervalHierarchy(scene.getUsedGeometryTransformed()); //time
	}

	@Override
	public HitRecord calculateHitRecord(Ray ray) {
		return bih.hit(ray);
	}

	@Override
	public boolean lookForShadowRayHit(Ray ray) {
		boolean hit = false;
		if(bih.hit(ray) != null){
			hit = true;
		}
		return hit;
	}

}
