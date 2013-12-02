package materials;

import imagedraw.HitRecord;
import lights.Light;
import mathematics.Color3f;
import mathematics.Vector4f;
import mathematics.VectorOperations;

public class PhongMaterial extends Material{

	private float shininess;
	
	public PhongMaterial(Color3f color, float shininess, float reflectiveFactor, String name){
		super.color = color;
		super.name = name;
		super.reflectiveFactor = reflectiveFactor;
		this.shininess = shininess;
	}

	public float getShininess() {
		return shininess;
	}

	public void setShininess(float shininess) {
		this.shininess = shininess;
	}

	@Override
	public Color3f calculateShading(HitRecord hr, Light light) {
		Vector4f n = hr.getNormal(); //normalized in HR
		Vector4f l = VectorOperations.normalizeVector4f(VectorOperations.subtractPointfromPoint3f(light.getPosition(), hr.getHitPoint()));
		Vector4f v = VectorOperations.invertVector4f(VectorOperations.normalizeVector4f(hr.getRay().getDirection()));
		Vector4f h = VectorOperations.normalizeVector4f(VectorOperations.addVectors4f(v, l));
		float n_times_h = VectorOperations.scalarProduct4f(n, h);
		Color3f resultColor = new Color3f();
		if(n_times_h>0){
			resultColor = calculateShadingColor(n_times_h,hr,light);
		}
		return resultColor;
	}	
	
	private Color3f calculateShadingColor(float n_times_h, HitRecord hr, Light l){
		float intensity = l.getIntensity();
		float I = (float) (intensity*Math.pow(n_times_h,shininess));
		Color3f lightColor = l.getColor();
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
