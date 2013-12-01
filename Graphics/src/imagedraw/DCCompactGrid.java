package imagedraw;

import acceleration.CompactGrid;
import rays.Ray;
import scenebuilder.Scene;

public class DCCompactGrid extends DrawController{

	private CompactGrid grid;
	
	public DCCompactGrid(Scene scene){
		super(scene);
		accelerated = true; //set this to construct a grid in the IndexedTriangleSet
		this.grid = new CompactGrid(scene.getScenegraph().traverseTransformObject());
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
