package rays;

import java.util.ArrayList;
import java.util.List;

import geometry.Geometry;
import imagedraw.HitRecord;

public class ICTransformObject extends IntersectController{

	private List<Geometry> geometry = new ArrayList<Geometry>();
	
	public ICTransformObject(){
		
	}

	@Override
	public HitRecord calculateHitRecord(Ray ray) {
		HitRecord smallest = null;
		float smallest_t = Float.POSITIVE_INFINITY;	
		if(geometry.isEmpty()){
			geometry = scene.getScenegraph().traverseTransformObject(); //TODO
		}
		for(Geometry g : geometry){
			HitRecord hr = g.rayObjectHit(ray);
			if(hr != null){
				if(hr.getT()<smallest_t && hr.getT()>0){
					smallest_t = hr.getT();
					smallest = hr;
				}
			}
		}
		return smallest;
	}
	
	@Override
	public boolean lookForShadowRayHit(Ray ray){
		boolean hit = false;	
		if(geometry.isEmpty()){
			geometry = scene.getScenegraph().traverseTransformObject(); //TODO
		}
		for(Geometry g : geometry){
			HitRecord hr = g.rayObjectHit(ray);
			if(hr != null){
				if(hr.getT()>0){
					hit = true;
					break;
				}
			}
		}
		return hit; // true betekent dat er iets tussen zit, false wil dus zeggen verlicht
	}

	public List<Geometry> getGeometry() {
		return geometry;
	}
}
