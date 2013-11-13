package cameras;

import mathematics.*;

public abstract class Camera {
	
	protected Point3f viewPoint;
	protected Vector4f viewDirection;
	protected Vector4f u;
	protected Vector4f v;
	protected Vector4f w;
	protected float fovy;
	protected String name;
	protected Screen screen;

	/**
	 * Make a new camera in the given viewpoint, with the given parameters
	 * 
	 * @param viewpoint
	 * @param u
	 * @param w
	 * @param fovy
	 * @param name
	 */
	public Camera(Point3f viewPoint, Vector4f w, Vector4f u, float fovy, String name){
		if(!(VectorOperations.scalarProduct4f(u, w) == 0)){
			System.out.println("u and w have to be perpendicular");
			throw new IllegalArgumentException();
		}
		this.viewPoint = viewPoint;
		this.u = VectorOperations.normalizeVector4f(u);
		this.w = VectorOperations.normalizeVector4f(w);
		this.viewDirection = VectorOperations.invertVector4f(w);
		this.v = VectorOperations.normalizeVector4f(VectorOperations.crossProduct4f(u,w));
		this.fovy = fovy;
		this.name = name;
    	this.screen = new Screen(fovy);
	}
	
	public Point3f getViewPoint() {
		return viewPoint;
	}

	public void setViewPoint(Point3f viewPoint) {
		this.viewPoint = viewPoint;
	}

	public Vector4f getU() {
		return u;
	}

	public void setU(Vector4f u) {
		this.u = u;
	}

	public Vector4f getV() {
		return v;
	}

	public float getFovy() {
		return fovy;
	}

	public void setV(Vector4f v) {
		this.v = v;
	}

	public Vector4f getW() {
		return w;
	}

	public void setW(Vector4f w) {
		this.w = w;
	}

	public Vector4f getViewDirection() {
		return viewDirection;
	}

	public void setViewDirection(Vector4f viewDirection) {
		this.viewDirection = viewDirection;
	}

	public void setFovy(float fovy) {
		this.fovy = fovy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}
}
