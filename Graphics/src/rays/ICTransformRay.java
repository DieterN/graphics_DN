package rays;

import java.util.ArrayList;
import java.util.List;

import geometry.Geometry;
import imagedraw.HitRecord;
import mathematics.Matrix4f;
import mathematics.MatrixOperations;
import mathematics.Point3f;
import mathematics.Vector4f;
import mathematics.VectorOperations;
import scenebuilder.GeometryGroup;

public class ICTransformRay extends IntersectController{

	private List<GeometryGroup> geometry = new ArrayList<GeometryGroup>();
	
	public ICTransformRay(){
		
	}
	
	@Override
	public HitRecord calculateHitRecord(Ray ray) {
		HitRecord smallest = null;
		float smallest_t = Float.POSITIVE_INFINITY;
		if(geometry.isEmpty()){
			geometry = scene.getScenegraph().traverseTransformRay(); //TODO
		}
		for(GeometryGroup geo : geometry){
			Ray tRay = ray.transformRay(geo.getInverseTransformationMatrix());
			for(Geometry g : geo.getGeometry()){
				HitRecord hr = g.rayObjectHit(tRay); //TODO
				if(hr != null){
					if(hr.getT()<smallest_t && hr.getT()>0){
						smallest_t = hr.getT();
						smallest = calculateRightHitRecordParameters(hr,ray, geo.getTransformationMatrix());
					}
				}
			}
		}
		return smallest;
	}
	
	private HitRecord calculateRightHitRecordParameters(HitRecord hr, Ray ray, Matrix4f transform) {
		Point3f viewPoint = ray.getViewPoint();
		Vector4f direction = ray.getDirection();
		float t = hr.getT();
		Vector4f t_times_d = VectorOperations.multiplyFloatandVector4f(t, direction);
		Point3f hitPoint = VectorOperations.addVector4fToPoint(t_times_d,viewPoint);
		hr.setHitPoint(hitPoint);
		Vector4f normalTr = MatrixOperations.MatrixVectorProduct(transform, hr.getNormal());
		hr.setNormal(normalTr);
		return hr;
	}

	@Override
	public boolean lookForShadowRayHit(Ray ray){
		boolean hit = false;	
		if(geometry.isEmpty()){
			geometry = scene.getScenegraph().traverseTransformRay(); //TODO
		}
		for(GeometryGroup geo : geometry){
			Ray tRay = ray.transformRay(geo.getInverseTransformationMatrix());
				for(Geometry g : geo.getGeometry()){
					HitRecord hr = g.rayObjectHit(tRay);
					if(hr != null){
						if(hr.getT()>0){
							hit = true;
							break;
						}
					}
				}
		}
		return hit; // true betekent dat er iets tussen zit
	}

	public List<GeometryGroup> getGeometry() {
		return geometry;
	}
}
