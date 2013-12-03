package acceleration.boundingIntervalHierarchy;

import java.util.ArrayList;
import java.util.List;

import geometry.BoundingBox;
import geometry.Geometry;

public class BoundingIntervalHierarchy {

	private Node rootNode; //box containing complete scene
	
	public BoundingIntervalHierarchy(List<? extends Geometry> geometry) {
		calculateRootBoundingBox(geometry);
	}
	
	/**
	 * Calculate the bounding box around all objects in the list
	 */
	private void calculateRootBoundingBox(List<? extends Geometry> geo){ //numbers used to calculate number of boxes
		if(geo.isEmpty()){
			System.out.println("Making empty grid");
			throw new IllegalArgumentException();
		}
		BoundingBox[] objects = new BoundingBox[geo.size()];
		
		float minX = Float.MAX_VALUE; //minX
		float maxX = -Float.MAX_VALUE; //maxX
		float minY = Float.MAX_VALUE; //minY
		float maxY = -Float.MAX_VALUE; //maxY
		float minZ = Float.MAX_VALUE; //minZ
		float maxZ = -Float.MAX_VALUE; //maxZ
		int i = 0;
		for(Geometry g : geo){
			BoundingBox box = g.getBox();
			objects[i] = box;
			if(box.getMinX() < minX){ minX = box.getMinX();}
			if(box.getMaxX() > maxX){ maxX = box.getMaxX();}
			if(box.getMinY() < minY){ minY = box.getMinY();}
			if(box.getMaxY() > maxY){ maxY = box.getMaxY();}
			if(box.getMinZ() < minZ){ minZ = box.getMinZ();}
			if(box.getMaxZ() > maxZ){ maxZ = box.getMaxZ();}
			i++;
		}
		
		rootNode = new Node(new BoundingBox(minX-0.05f,maxX+0.05f,minY-0.05f,maxY+0.05f,minZ-0.05f,maxZ+0.05f),objects,0,geo.size()-1);
	}
	
//	/**
//	 * Split the given node in 2 seperate nodes
//	 */
//	private void splitNode(Node node){
//		if(node.getObjects().size() <= 1){
//			System.out.println("Don't split, only 1 or less object left");
//			node.setSplitPlane(11); //leaf
//		}
//		int splitPlane = node.getSplitPlane(); //plane to split, x=00, y=01, z=10
//		float splitValue = node.getBox().getCenterOfAxis(splitPlane);
//		List<BoundingBox> left = new ArrayList<BoundingBox>();
//		List<BoundingBox> right = new ArrayList<BoundingBox>();
//		for(BoundingBox b : node.getObjects()){ //split the boxes in this cell in left and right
//			if(b.getCenterOfAxis(splitPlane) <= splitValue){
//				left.add(b);
//			}
//			else{
//				right.add(b);
//			}
//		}
//		//calculate the splitPlanes for left and right, make new nodes and at them to node?
//	}
}
