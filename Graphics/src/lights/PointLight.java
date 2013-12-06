package lights;

import rays.Ray;
import rays.ShadowRay;
import imagedraw.DrawController;
import imagedraw.HitRecord;
import mathematics.*;

/**
 * Class representing a point light.
 * 
 * @author Dieter
 *
 */
public class PointLight extends Light{

	
	public PointLight(Point3f position, float intensity, Color3f color, String name){
		super(position,intensity, color, name);
	}

	@Override
	public Color3f calculateShading(HitRecord hr, DrawController dc) {
		Color3f color = new Color3f();
		Vector4f direction = VectorOperations.subtractPointfromPoint3f(position, hr.getHitPoint()); //richting naar licht
		Vector4f normalizedDirection = VectorOperations.normalizeVector4f(direction); // genormaliseerde richting naar licht
		Ray ray = new ShadowRay(hr.getHitPoint(), normalizedDirection); // nieuwe SchaduwRay (aparte klasse voor epsilon)
		
		boolean inShadow = true; // in de schaduw tenzij er geen hit is bij bepaalde ray
		if(!dc.lookForShadowRayHit(ray)){
			inShadow = false;
		}
		if(!inShadow){
			Color3f shadingColor = dc.calculateShading(hr,this);
			color.x += shadingColor.x;
			color.y += shadingColor.y;
			color.z += shadingColor.z;
		}
		return color;
	}
}
