package imagedraw;

import acceleration.boundingIntervalHierarchy.BoundingIntervalHierarchy;
import rays.Ray;
import scenebuilder.Scene;

/**
 * Subclass of DrawController.
 * If you choose this dynamic type, raytracing will be done using a Bounding Interval Hierarchy.
 * 
 * @author Dieter
 *
 */
public class DCBoundingIntervalHierarchy extends DrawController{

	private BoundingIntervalHierarchy bih;
	
	public DCBoundingIntervalHierarchy(Scene scene) {
		super(scene);
		useTrianglesInsteadOfMesh = true;
		long begintime = System.currentTimeMillis();
		bih = new BoundingIntervalHierarchy(scene.getUsedGeometryTransformed());
		System.out.println("Building Bounding Interval Hierarchy");
		long endtime = System.currentTimeMillis();
		System.out.println("Done in: " + (endtime - begintime) + " ms");
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
