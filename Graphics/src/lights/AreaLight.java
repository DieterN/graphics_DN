package lights;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rays.Ray;
import rays.ShadowRay;
import imagedraw.DrawController;
import imagedraw.HitRecord;
import mathematics.Color3f;
import mathematics.Point3f;
import mathematics.Vector4f;
import mathematics.VectorOperations;

public class AreaLight extends Light{

	private Point3f u;
	private Point3f w;
	private Vector4f widthVector;
	private Vector4f lengthVector;
	private float length;
	private float width;
	private List<Point3f> randomViewPoints = new ArrayList<Point3f>(); //needed when anti-aliasing and soft shading
	
	public AreaLight(Point3f position, Point3f u, Point3f w, float intensity, Color3f color, String name) {
		super(position, intensity, color, name);
		this.u = u;
		this.w = w;
		widthVector = VectorOperations.subtractPointfromPoint3f(u, position);
		lengthVector = VectorOperations.subtractPointfromPoint3f(w, position);
		width = VectorOperations.vectorNorm4f(widthVector);
		length = VectorOperations.vectorNorm4f(lengthVector);
	}

	public Point3f getU() {
		return u;
	}

	public void setU(Point3f u) {
		this.u = u;
	}

	public Point3f getW() {
		return w;
	}

	public void setW(Point3f w) {
		this.w = w;
	}

	public Vector4f getWidthVector() {
		return widthVector;
	}

	public void setWidthVector(Vector4f widthVector) {
		this.widthVector = widthVector;
	}

	public Vector4f getLengthVector() {
		return lengthVector;
	}

	public void setLengthVector(Vector4f lengthVector) {
		this.lengthVector = lengthVector;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	@Override
	public Color3f calculateShading(HitRecord hr, DrawController dc) {
		if(DrawController.softShadow){
			if(DrawController.antiAliasing){
				return calculateShadingAA(hr,dc);
			}
			else{
				return calculateShadingSS(hr,dc);
			}
		}
		else{ //normal hitting, just return shadow from position of arealight
			Color3f color = new Color3f();
			Vector4f direction = VectorOperations.subtractPointfromPoint3f(position, hr.getHitPoint()); //richting naar licht
			Vector4f normalizedDirection = VectorOperations.normalizeVector4f(direction); // genormaliseerde richting naar licht
			Ray ray = new ShadowRay(hr.getHitPoint(), normalizedDirection); // nieuwe SchaduwRay (aparte klasse voor epsilon)
			if(!inShadow(dc,ray)){
				Color3f shadingColor = hr.getGeometry().getMaterial().calculateShading(hr, this);
				color.x += shadingColor.x;
				color.y += shadingColor.y;
				color.z += shadingColor.z;
			}
			return color;
		}
	}

	private boolean inShadow(DrawController dc, Ray ray){
		boolean inShadow = true;
		if(!dc.lookForShadowRayHit(ray)){
			inShadow = false;
		}
		return inShadow;
	}
	
	/**
	 * Soft shading method, but anti-aliasing is also activated.
	 * Make a list with randomPoints and select one for every ray of every pixel.
	 * Make the list exact long enough to serve one pixel, this will create the soft shadow effect.
	 */
	private Color3f calculateShadingAA(HitRecord hr, DrawController dc) {
		// TODO lijst bijhouden en telkens eentje random verwijderen
		// als lijst leeg, genereer nieuwe --> normaal komt dit dan overeen pixel per pixel, aangezien die parameters gebruikt worden
		if(randomViewPoints.isEmpty()){
			calculateNewRandomPointsList();
		}
		Random random = new Random();
		int index = random.nextInt(randomViewPoints.size());
		Point3f position = randomViewPoints.remove(index);
		Vector4f direction = VectorOperations.subtractPointfromPoint3f(position, hr.getHitPoint()); //richting naar licht
		Vector4f normalizedDirection = VectorOperations.normalizeVector4f(direction); // genormaliseerde richting naar licht
		Ray ray = new ShadowRay(hr.getHitPoint(), normalizedDirection); // nieuwe SchaduwRay (aparte klasse voor epsilon)
		Color3f color = new Color3f();
		if(!inShadow(dc,ray)){
			Color3f shadingColor = hr.getGeometry().getMaterial().calculateShading(hr, this, position);
			color.x += shadingColor.x;
			color.y += shadingColor.y;
			color.z += shadingColor.z;
		}
		return color;
	}
	
	private void calculateNewRandomPointsList(){
		int n = DrawController.nbOfSamples;
		Random random = new Random();
		for(int p=0; p<n ; p++){
			for(int q=0; q<n ; q++){
				Vector4f randomWidthDistance = VectorOperations.multiplyFloatandVector4f((p+random.nextFloat())/n, widthVector);
				Vector4f randomLengthDistance = VectorOperations.multiplyFloatandVector4f((q+random.nextFloat())/n, lengthVector);
				Point3f posi = VectorOperations.addVector4fToPoint(randomWidthDistance, position);
				Point3f pos = VectorOperations.addVector4fToPoint(randomLengthDistance, posi);
				randomViewPoints.add(pos);
			}
		}
	}

	/**
	 * Soft shading method, send multiple rays to this arealight
	 */
	private Color3f calculateShadingSS(HitRecord hr, DrawController dc) {
		Color3f color = new Color3f();
		int n = DrawController.nbOfShadowSamples;
		Random random = new Random();
		for(int p=0; p<n ; p++){
			for(int q=0; q<n ; q++){
				Vector4f randomWidthDistance = VectorOperations.multiplyFloatandVector4f((p+random.nextFloat())/n, widthVector);
				Vector4f randomLengthDistance = VectorOperations.multiplyFloatandVector4f((q+random.nextFloat())/n, lengthVector);
				Point3f posi = VectorOperations.addVector4fToPoint(randomWidthDistance, position);
				Point3f pos = VectorOperations.addVector4fToPoint(randomLengthDistance, posi);
				Vector4f direction = VectorOperations.subtractPointfromPoint3f(pos, hr.getHitPoint()); //richting naar licht
				Vector4f normalizedDirection = VectorOperations.normalizeVector4f(direction); // genormaliseerde richting naar licht
				Ray ray = new ShadowRay(hr.getHitPoint(), normalizedDirection); // nieuwe SchaduwRay (aparte klasse voor epsilon)
				if(!inShadow(dc,ray)){
					Color3f shadingColor = hr.getGeometry().getMaterial().calculateShading(hr, this, pos);
					color.x += shadingColor.x;
					color.y += shadingColor.y;
					color.z += shadingColor.z;
				}
			}
		}
		color.x = (float) (color.x/Math.pow(n, 2));
		color.y = (float) (color.y/Math.pow(n, 2));
		color.z = (float) (color.z/Math.pow(n, 2));
		return color;
	}
}
