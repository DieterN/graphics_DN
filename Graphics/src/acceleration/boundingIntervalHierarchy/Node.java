package acceleration.boundingIntervalHierarchy;

import geometry.BoundingBox;
import imagedraw.HitRecord;

import rays.Ray;
import mathematics.Point3f;

public class Node {

	private static final int nbOfObjectsInOneNode = 1;
	private static final int maxNbOfSplits = 10;
	private int splitPlane; //(0=x,1=y,2=z,3=leaf,-1=invalid)
	private int indexFirstElement; //first object of this node in array
	private int indexLastElement; //last object of this node in array
	private float[] clipPlanes = new float[2]; //value of left and right clipPlane, empty when leafNode, this is array, cause useful when hitting
	private Node[] nodes = new Node[2]; // left and right child node
	private BoundingBox[] objects; //list with bounding boxes of all objects in this node
	
	
	/**********************
	 *** CONSTRUCT NODE ***
	 **********************/
	
	public Node(BoundingBox box, BoundingBox[] objects){
		initialiseNode(box,objects,0,objects.length-1,0);
	}
	
	private Node(BoundingBox box, BoundingBox[] objects, int indexFirstElement, int indexLastElement, int nbOfSplits){
		initialiseNode(box,objects,indexFirstElement,indexLastElement,nbOfSplits);
	}
	
	private void initialiseNode(BoundingBox box, BoundingBox[] objects, int indexFirstElement, int indexLastElement, int nbOfSplits){
		this.objects = objects;
		int nbObjects = indexLastElement-indexFirstElement+1; //right is first element of this node, left is first element of this node
		if(nbObjects <= 0) { 
			//empty node --> shouldn't happen
			System.out.println("Impossible empty node");
		}
		else if(nbObjects <= nbOfObjectsInOneNode || nbOfSplits >= maxNbOfSplits){ //min nbOfObjectsInNode or max nb splits reached
			makeLeaf(box, objects,indexFirstElement,indexLastElement);
		}
		else{
			makeNormalNode(box, objects, indexFirstElement, indexLastElement, nbOfSplits);
		}
	}
	
	private void makeLeaf(BoundingBox box, BoundingBox[] objects, int indexFirstElement, int indexLastElement){
		this.splitPlane = 3; //leaf
		this.clipPlanes[0] = 0;
		this.clipPlanes[1] = 0;
		this.indexFirstElement = indexFirstElement;
		this.indexLastElement = indexLastElement;
		nodes[0] = null;
		nodes[1] = null;
		this.objects = objects;
	}
	
	private void makeNormalNode(BoundingBox box, BoundingBox[] objects, int indexFirstElement, int indexLastElement, int nbOfSplits){
		splitPlane = box.getLongestAxis(); // return the longestAxis of this box, so you know which
		this.indexFirstElement = indexFirstElement; //initialise this box
		this.indexLastElement = indexLastElement; //initialise this box
		float splitValue = box.getCenterOfAxis(splitPlane); //return the center of the axis, value to split at
		SplitInfo info = splitObjectsInTwoGroups(splitValue,objects); //information about the split
		//We now have information about which objects should be in which child node
		//To continue, we calculate the new bounding box for this child nodes, so we can initialise them recursively
		BoundingBox[] boxes = calculateBoundingBoxes(box, splitValue); //first element in array is new left box, second is new right box
		//Finally use all this information to make the child nodes
		makeChildNodes(info,boxes,nbOfSplits);
	}
	
	private SplitInfo splitObjectsInTwoGroups(float splitValue, BoundingBox[] objects){
		float minL = Float.MAX_VALUE; //minX
		float maxL = -Float.MAX_VALUE; //maxX
		float minR = Float.MAX_VALUE; //minY
		float maxR = -Float.MAX_VALUE; //maxY
		int left = indexFirstElement;
		int right = indexLastElement;
		while(left <= right){
			BoundingBox box = objects[left]; //get box of everything in this node
			float center = box.getCenterOfAxis(splitPlane); //attention, splitPlane should be initialised
			float minB = box.getMinOfAxis(splitPlane); //minimum of box along axis
			float maxB = box.getMaxOfAxis(splitPlane); //maximum of box along axis
			if(center <= splitValue){ //check side at which box is, left
				if(minB < minL){ minL = minB; } //adjust box parameters
				if(maxB > maxL){ maxL = maxB; } 
				//don't sort, left objects already left, just increase left, cause this element is sorted
				left++;
			}
			else{ //right
				if(minB < minR){ minR = minB; } //adjust box parameters
				if(maxB > maxR){ maxR = maxB; } 
				//sort, this object is right, so should be at other side of array
				if(left != right){ //if left = right, than it's already sorted --> only one element to sort left
					BoundingBox tempBox = objects[left];
					objects[left] = objects[right];
					objects[right] = tempBox;
				}
				//now decrease right, cause right th element is sorted
				right--;
			}
		}
		//left is now the first element that will be in the right box after splitting
		return new SplitInfo(minL,maxL,minR,maxR,left,right);
	}

	
	private BoundingBox[] calculateBoundingBoxes(BoundingBox box, float splitValue) {
		BoundingBox[] resultBoxes = new BoundingBox[2];
		Point3f[] bounds = box.getBounds(); //get 2 points of box, adjust planes along the splitAxis
		Point3f small = new Point3f(bounds[0]);
		Point3f large = new Point3f(bounds[1]);
		Point3f newSmallRight = new Point3f(small);
		Point3f newLargeLeft = new Point3f(large);
		newSmallRight.setPointValueOfAxis(splitPlane,splitValue); //replace smallest value along axis with splitValue for right node
		newLargeLeft.setPointValueOfAxis(splitPlane,splitValue); //replace greatest value along axis with splitValue for left node
		resultBoxes[0] = new BoundingBox(small,newLargeLeft); //new left box
		resultBoxes[1] = new BoundingBox(newSmallRight,large); //new right box
		return resultBoxes;
	}

	private void makeChildNodes(SplitInfo info, BoundingBox[] boxes, int nbOfSplits) {
		int left = info.getLeft();
		if(left > indexLastElement){ //left was first element of right box, so if left is greater than indexLastElement, than right box is empty
			clipPlanes[0] = info.getMinL(); //minR en maxR are infinity --> special way
			clipPlanes[1] = info.getMaxL(); //since we only have one node, we can clip left side too
			nodes[0] = new Node(boxes[0],objects,indexFirstElement,left-1,nbOfSplits+1); //recursively split this node further
			nodes[1] = null;
		}
		else if(left <= indexFirstElement){ //left was first element of right box, so if left is equal to or smaller than indexFirstElement, left box is empty
			clipPlanes[0] = info.getMinR(); //minL en maxL are infinity --> special way
			clipPlanes[1] = info.getMaxR(); 
			nodes[0] = null;
			nodes[1] = new Node(boxes[1],objects,left,indexLastElement,nbOfSplits+1); //recursively split this node further
		}
		else{//both nodes are filled
			clipPlanes[0] = info.getMaxL();
			clipPlanes[1] = info.getMinR(); 
			nodes[0] = new Node(boxes[0],objects,indexFirstElement,left-1,nbOfSplits+1); //recursively split this node further
			nodes[1] = new Node(boxes[1],objects,left,indexLastElement,nbOfSplits+1); //recursively split this node further
		}
	}

	/****************
	 *** HIT NODE ***
	 ****************/
	
	//TODO : check
	public HitRecord hit(Ray ray, float minHitT, float maxHitT){ 
		//(minHitT needed for recursion, initialise this value to min hit of boundingbox containing this bounding interval hierarchy)
		//(maxHitT needed for recursion, initialise this value to max hit of boundingbox containing this bounding interval hierarchy)
		//check for leaf node first
		if(splitPlane == 3){
			return hitLeaf(ray,minHitT,maxHitT); //check for min, max, null is done in hitLeaf method
		}
		//else : look at which childNodes of this node are hit
		//do this by hitting the two clipping planes
		int signOfRay = ray.getSign()[splitPlane];
		float invDirection = ray.getInv_directionOfAxis(splitPlane);
		float direction = ray.getDirection().getVectorValueOfAxis(splitPlane);
		float viewPoint = ray.getViewPoint().getPointValueOfAxis(splitPlane);
		float tClipSmall = (clipPlanes[signOfRay] - viewPoint) * invDirection;
		float tClipLarge = (clipPlanes[1 - signOfRay] - viewPoint) * invDirection;
		
		if(direction == 0){
			//inverseDirection is infinity, so we can't see what is hit, just try intersecting both planes
			if(nodes[0] != null && nodes[1] == null){ //if node has only one childnode, just hit that one
				return nodes[0].hit(ray, minHitT, maxHitT);
			}
			return hitBothPlanes(ray, minHitT, maxHitT);
		}
		else{
			if(nodes[0] != null && nodes[1] == null){
				//the planes for this kind of nodes are other than the planes for a normal node, since we had infinity problems
				if(maxHitT < tClipSmall || tClipLarge < minHitT){ //not hitting 
					return null;
				}
				else{
					return nodes[0].hit(ray, minHitT, maxHitT); //only this node is filled
				}
			}			
			if(tClipSmall < minHitT){ //first plane isn't hit, since box hit is further than hit of this plane
				if(maxHitT < tClipLarge){ //not going through last plane, or hit closer
					//trying to hit something but, ray goes between planes or something has been hit closer than last plane
					return null;
				}
				else{ //going through last plane
					return nodes[1-signOfRay].hit(ray, minHitT, maxHitT); //just hit last plane recursive with same parameters
				}
			}
			else{ //going through first plane
				if(maxHitT < tClipLarge){ //not going through last plane, or hit closer
					return nodes[signOfRay].hit(ray, minHitT, maxHitT); //just hit first plane recursive with same parameters
				}
				else{
					return hitBothPlanes(ray, minHitT, maxHitT);
				}				
			}
		}
	}

	private HitRecord hitBothPlanes(Ray ray, float minHitT, float maxHitT) {
		HitRecord result = null;
		float smallest_t = maxHitT;
		HitRecord left = nodes[0].hit(ray, minHitT, maxHitT);
		if(left != null){
			smallest_t = left.getT(); //normally this is closer
			result = left;
		}
		HitRecord right = nodes[1].hit(ray, minHitT, smallest_t); //hit right with updated t distance
		if(right != null){
			result = right; //something has been hit closer in right
		}
		return result;
	}
	
	private HitRecord hitLeaf(Ray ray, float minHitT, float maxHitT){
		HitRecord smallest = null;
		float smallest_t = maxHitT;
		for(int i = indexFirstElement; i<=indexLastElement; i++){
			BoundingBox box = objects[i];
			HitRecord hr = box.rayObjectHit(ray);
			if(hr != null){
				if(hr.getT() >= minHitT && hr.getT() <= smallest_t){
					smallest = hr;
					smallest_t = hr.getT();
				}
			}
		}
		return smallest;
	}
}
