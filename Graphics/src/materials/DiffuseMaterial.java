package materials;

import imagedraw.HitRecord;
import lights.Light;
import mathematics.Color3f;
import mathematics.Point3f;
import mathematics.Vector4f;
import mathematics.VectorOperations;

/**
 * Class representing a diffuse material, if you hit an object of this material,
 * diffuse shading will be calculated.
 *  
 * @author Dieter
 *
 */
public class DiffuseMaterial extends Material{
	
	public DiffuseMaterial(Color3f color, float reflectiveFactor, String name) {
		super.color = color;
		super.name = name;
		super.reflectiveFactor = reflectiveFactor;
	}

	
	@Override
	public Color3f calculateShading(HitRecord hr, Light light) {
		Vector4f normal = hr.getNormal(); //normalized in HR
		Vector4f toLight = VectorOperations.subtractPointfromPoint3f(light.getPosition(), hr.getHitPoint());
		Vector4f normalizedToLight = VectorOperations.normalizeVector4f(toLight);
		float n_times_l = VectorOperations.scalarProduct4f(normal, normalizedToLight);
		Color3f resultColor = new Color3f();
		if(n_times_l>0){
			resultColor = calculateShadingColor(n_times_l,hr,light);
		}
		return resultColor;
	}
	
	@Override
	public Color3f calculateShading(HitRecord hr, Light light, Point3f viewPoint) {
		Vector4f normal = hr.getNormal(); //normalized in HR
		Vector4f toLight = VectorOperations.subtractPointfromPoint3f(viewPoint, hr.getHitPoint());
		Vector4f normalizedToLight = VectorOperations.normalizeVector4f(toLight);
		float n_times_l = VectorOperations.scalarProduct4f(normal, normalizedToLight);
		Color3f resultColor = new Color3f();
		if(n_times_l>0){
			resultColor = calculateShadingColor(n_times_l,hr,light);
		}
		return resultColor;
	}
	
	private Color3f calculateShadingColor(float n_times_l, HitRecord hr, Light light){
		float intensity = light.getIntensity();
		float I = intensity*n_times_l;
		Color3f lightColor = light.getColor();
		Color3f surfaceColor = hr.getColor();
		float red = lightColor.x*surfaceColor.x*I;
		if(red > 1){
			red = 1;
		}
		float blue = lightColor.y*surfaceColor.y*I;
		if(blue > 1){
			blue = 1;
		}
		float green = lightColor.z*surfaceColor.z*I;
		if(green > 1){
			green = 1;
		}
		return new Color3f(red,blue,green);
	}
}
