package imagedraw;

import acceleration.compactGrid.CompactGrid;
import rays.Ray;
import scenebuilder.Scene;

/**
 * Subclass of DrawController.
 * If you choose this dynamic type, raytracing will be done using a Compact Grid.
 * 
 * @author Dieter
 *
 */
public class DCCompactGrid extends DrawController{

	private CompactGrid grid;
	
	public DCCompactGrid(Scene scene){
		super(scene);
		accelerated = true; //set this to construct a grid in the IndexedTriangleSet
		long begintime = System.currentTimeMillis();
		this.grid = new CompactGrid(scene.getUsedGeometryTransformed());
		System.out.println("Building Compact Grid");
		long endtime = System.currentTimeMillis();
		System.out.println("Done in: " + (endtime - begintime) + " ms");
	}
	
	@Override
	public HitRecord calculateHitRecord(Ray ray) {
		return grid.hit(ray);
	}

	@Override
	public boolean lookForShadowRayHit(Ray ray) {
		boolean hit = false;
		if(grid.hit(ray) != null){
			hit = true;
		}
		return hit;
	}

}
