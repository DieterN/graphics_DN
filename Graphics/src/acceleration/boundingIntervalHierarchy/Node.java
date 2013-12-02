package acceleration.boundingIntervalHierarchy;

import java.util.List;

import acceleration.BoundingBox;

public class Node {

	private int splitPlane; //(00=x,01=y,10=z,11=leaf,-1=invalid)
	private float[] clip = new float[2]; //value of left and right split value respectively, empty when leaf
	private BoundingBox box; // box around all objects of this node
	private List<BoundingBox> objects; //list with bounding boxes of all objects in this node
	
	public Node(BoundingBox box){
		this.box = box;
		this.splitPlane = -1; //initialise splitPlane to -1
	}
	
	/**
	 * Return the split plane for this node
	 */
	private void calculateSplitPlane(){
		splitPlane = box.getLongestAxis();
	}

	public int getSplitPlane() {
		if(splitPlane == -1){
			calculateSplitPlane();
		}
		return splitPlane;
	}

	public void setSplitPlane(int splitPlane) {
		this.splitPlane = splitPlane;
	}

	public float[] getClip() {
		return clip;
	}

	public void setClip(float[] clip) {
		this.clip = clip;
	}

	public BoundingBox getBox() {
		return box;
	}

	public void setBox(BoundingBox box) {
		this.box = box;
	}

	public List<BoundingBox> getObjects() {
		return objects;
	}

	public void setObjects(List<BoundingBox> objects) {
		this.objects = objects;
	}
	
}
