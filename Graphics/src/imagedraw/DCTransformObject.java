package imagedraw;

import java.util.ArrayList;
import java.util.List;

import rays.Ray;
import scenebuilder.Scene;
import geometry.ConcreteGeometry;
import geometry.Geometry;


/**
 * Subclass of DrawController.
 * If you choose this dynamic type, raytracing will be done by transforming all objects
 * and sending rays against all of them for every pixel.
 * 
 * @author Dieter
 *
 */
public class DCTransformObject extends DrawController{

	private List<ConcreteGeometry> geometry = new ArrayList<ConcreteGeometry>();
	
	public DCTransformObject(Scene scene){
		super(scene);
	}

	@Override
	public HitRecord calculateHitRecord(Ray ray) {
		HitRecord smallest = null;
		float smallest_t = Float.POSITIVE_INFINITY;	
		if(geometry.isEmpty()){
			geometry = scene.getScenegraph().traverseTransformObject();
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
			geometry = scene.getScenegraph().traverseTransformObject();
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

	public List<ConcreteGeometry> getGeometry() {
		return geometry;
	}
}
