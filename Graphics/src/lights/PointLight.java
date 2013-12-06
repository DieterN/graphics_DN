package lights;

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
}
