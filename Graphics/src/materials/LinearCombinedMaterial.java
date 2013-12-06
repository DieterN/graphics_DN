package materials;

import imagedraw.HitRecord;
import lights.Light;
import mathematics.Color3f;


/**
 * Class representing a linear combined material, this is a combination of
 * two or more materials, that has the properties of both.
 *  
 * @author Dieter
 *
 */
public class LinearCombinedMaterial extends Material{

	private Material material1;
	private float weight1;
	private Material material2;
	private float weight2;
	
	public LinearCombinedMaterial(Material material1, float weight1, Material material2, float weight2, String name){
		super.name = name;
		this.material1 = material1;
		this.weight1 = weight1;
		this.material2 = material2;
		this.weight2 = weight2;
		super.color = calculateColor();
		super.reflectiveFactor = calculateReflectiveFactor();
	}

	public Material getMaterial1() {
		return material1;
	}

	public void setMaterial1(Material material1Name) {
		this.material1 = material1Name;
	}

	public float getWeight1() {
		return weight1;
	}

	public void setWeight1(float weight1) {
		this.weight1 = weight1;
	}

	public Material getMaterial2() {
		return material2;
	}

	public void setMaterial2(Material material2) {
		this.material2 = material2;
	}

	public float getWeight2() {
		return weight2;
	}

	public void setWeight2(float weight2) {
		this.weight2 = weight2;
	}
	
	private Color3f calculateColor(){
		Color3f result = new Color3f();
		Color3f color1 = material1.getColor();
		Color3f color2 = material2.getColor();
		float w1 = weight1/(weight1+weight2);
		float w2 = weight2/(weight1+weight2);
		result.set((color1.x*w1+color2.x*w2),(color1.y*w1+color2.y*w2),(color1.z*w1+color2.z*w2));
		return result;
	}
	
	private float calculateReflectiveFactor(){
		float aF1 = material1.getReflectiveFactor();
		float aF2 = material2.getReflectiveFactor();
		float w1 = weight1/(weight1+weight2);
		float w2 = weight2/(weight1+weight2);
		float result = aF1*w1 + aF2*w2;
		return result;
	}

	@Override
	public Color3f calculateShading(HitRecord hr, Light l) {
		Color3f resultColor = new Color3f();
		Color3f material1Color = material1.calculateShading(hr, l);
		Color3f material2Color = material2.calculateShading(hr, l);
		float w1 = weight1/(weight1+weight2);
		float w2 = weight2/(weight1+weight2);
		resultColor.x += w1*material1Color.x + w2*material2Color.x;
		resultColor.y += w1*material1Color.y + w2*material2Color.y;
		resultColor.z += w1*material1Color.z + w2*material2Color.z;
		return resultColor;
	}
}
