package cameras;

import imagedraw.DrawController;

public class Screen {

	private float l; //left
	private float r; //right
	private float t; //top
	private float b; //bottom
	
	/**
	 * Initalise a new standard Screen
	 */
	public Screen(){
		this.l = -DrawController.getNx()/2;
		this.r = DrawController.getNx()/2;
		this.t = DrawController.getNy()/2;
		this.b =  -DrawController.getNy()/2; 
	}
	
	/**
	 * Initialise a screen using the given field-of-view
	 * 
	 * @param fovy : field of view
	 */
	public Screen(float fovy){ //TODO!!!
		double fovyRad = Math.toRadians(fovy);
		r = (float) (Math.tan(fovyRad/2)*Math.abs(PerspectiveCamera.getFocalLength()));
		l = -r;
		t = (DrawController.getNy()*r)/(DrawController.getNx());
		b = -t;
	}

	public float getL() {
		return l;
	}

	public void setL(float l) {
		this.l = l;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public float getT() {
		return t;
	}

	public void setT(float t) {
		this.t = t;
	}

	public float getB() {
		return b;
	}

	public void setB(float b) {
		this.b = b;
	}
}
