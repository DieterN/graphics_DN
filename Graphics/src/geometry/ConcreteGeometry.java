package geometry;

import materials.Material;
import mathematics.Color3f;
import mathematics.Matrix4f;

public abstract class ConcreteGeometry extends Geometry{
	
	protected String name;
	protected Material material;
	protected Color3f color;

	public ConcreteGeometry(String name) {
		super();
		this.name = name;
	}
	
	public abstract ConcreteGeometry getInstance();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}

	public Color3f getColor() {
		return color;
	}

	public void setColor(Color3f color) {
		this.color = color;
	}

	public abstract void transform(Matrix4f transformation);

}
