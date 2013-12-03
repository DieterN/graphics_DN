package acceleration.boundingIntervalHierarchy;

import geometry.BoundingBox;

import java.util.List;

import mathematics.Point3f;

public class Node {

	private final int nbOfObjectsInOneNode = 1;
	private int splitPlane; //(00=x,01=y,10=z,11=leaf,-1=invalid)
	private int left; //first object of this node in array
	private int right; //last object of this node in array
	private float leftClip; //value of left clipPlane, empty when leafNode
	private float rightClip; //value of right clipPlane, empty when leafNode
	private Node leftNode; // left child node
	private Node rightNode; // right child node
	private BoundingBox[] objects; //list with bounding boxes of all objects in this node
	
	public Node(BoundingBox box, BoundingBox[] objects, int left, int right){ //TODO : depth??
		initialiseNode(box,objects,left,right);
	}
	
	private void initialiseNode(BoundingBox box, BoundingBox[] objects, int left, int right){
		int nbObjects = right-left; //right is first element of this node, left is first element of this node
		if(nbObjects <= 0) { 
			//empty node (TODO?)
		}
		else if(nbObjects <= nbOfObjectsInOneNode){ //min nbOfObjectsInNode reached
			makeLeaf(box, objects,left,right);
		}
		else{
			makeNormalNode(box, objects, left, right);
		}
	}
	
	private void makeLeaf(BoundingBox box, BoundingBox[] objects, int left, int right){
		this.splitPlane = 11; //leaf
		this.leftClip = 0;
		this.rightClip = 0;
		this.left = left;
		this.right = right;
		this.leftNode = null;
		this.rightNode = null;
		this.objects = objects;
	}
	
	private void makeNormalNode(BoundingBox box, BoundingBox[] objects, int left, int right){
		splitPlane = box.getLongestAxis(); // return the longestAxis of this box, so you know which
		this.left = left; //initialise this box
		this.right = right; //initialise this box
		float splitValue = box.getCenterOfAxis(splitPlane); //return the center of the axis, value to split at
		SplitInfo info = splitObjectsInTwoGroups(splitValue,objects); //information about the split
		//We now have information about which objects should be in which child node
		//To continue, we calculate the new bounding box for this child nodes, so we can initialise them recursively
		BoundingBox[] boxes = calculateBoundingBoxes(box, splitValue); //first element in array is new left box, second is new right box
		//Finally use all this information to make the child nodes
		makeChildNodes(info,boxes);
	}
	
	private SplitInfo splitObjectsInTwoGroups(float splitValue, BoundingBox[] objects){
		float minL = Float.MAX_VALUE; //minX
		float maxL = -Float.MAX_VALUE; //maxX
		float minR = Float.MAX_VALUE; //minY
		float maxR = -Float.MAX_VALUE; //maxY
		int j = right; //index of 
		int i = left;
		while(i <= j){
			BoundingBox box = objects[i].getBox(); //get box of everything in this node
			float center = box.getCenterOfAxis(splitPlane); //attention, splitPlane should be initialised
			float minB = box.getMinOfAxis(splitPlane); //minimum of box along axis
			float maxB = box.getMaxOfAxis(splitPlane); //maximum of box along axis
			if(center <= splitValue){ //check side at which box is, left
				if(minB < minL){ minL = minB; } //adjust box parameters
				if(maxB > maxL){ maxL = maxB; } 
				//don't sort, left objects already left, just increase i, cause this element is sorted
				i++;
			}
			else{ //right
				if(minB < minR){ minR = minB; } //adjust box parameters
				if(maxB > maxR){ maxR = maxB; } 
				//sort, this object is right, so should be at other side of array
				if(i != j){ //if i = j, than it's already sorted --> only one element to sort left
					BoundingBox tempBox = objects[i];
					objects[i] = objects[j];
					objects[j] = tempBox;
				}
				//now decrease j, cause j th element is sorted
				j--;
			}
		}
		//i is now the first element that will be in the right box after splitting
		return new SplitInfo(minL,maxL,minR,maxR,i,j);
	}

	
	private BoundingBox[] calculateBoundingBoxes(BoundingBox box, float splitValue) {
		BoundingBox[] resultBoxes = new BoundingBox[2];
		Point3f[] bounds = box.getBounds(); //get 2 points of box, adjust planes along the splitAxis
		Point3f small = bounds[0];
		Point3f large = bounds[1];
		Point3f newSmallRight = small;
		Point3f newLargeLeft = large;
		newSmallRight.setPointValueOfAxis(splitPlane,splitValue); //replace smallest value along axis with splitValue for right node
		newLargeLeft.setPointValueOfAxis(splitPlane,splitValue); //replace greatest value along axis with splitValue for left node
		resultBoxes[0] = new BoundingBox(small,newLargeLeft); //new left box
		resultBoxes[1] = new BoundingBox(newSmallRight,large); //new right box
		return resultBoxes;
	}

	private void makeChildNodes(SplitInfo info, BoundingBox[] boxes) {
		int i = info.getI();
		if(i > right){ //i was first element of right box, so if i is greater than right, than right box is empty
			leftClip = info.getMinL(); //FIXME: single node --> check clipping --> special way? //minR en maxR are infinity
			rightClip = info.getMaxL(); 
			leftNode = new Node(boxes[0],objects,left,i-1); //recursively split this node further
			rightNode = null;
		}
		else if(i <= left){ //i was first element of right box, so if i is equam to or smaller than left, left box is empty
			leftClip = info.getMinR(); //FIXME: single node --> check clipping --> special way? //minL en maxL are infinity
			rightClip = info.getMaxR(); 
			leftNode = null;
			rightNode = new Node(boxes[1],objects,i,right); //recursively split this node further
		}
		else{//both nodes are filled
			leftClip = info.getMaxL();
			rightClip = info.getMinR(); 
			leftNode = new Node(boxes[0],objects,left,i-1); //recursively split this node further
			rightNode = new Node(boxes[1],objects,i,right); //recursively split this node further
		}
	}

}
