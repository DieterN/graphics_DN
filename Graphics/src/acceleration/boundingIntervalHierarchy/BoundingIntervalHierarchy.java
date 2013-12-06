package acceleration.boundingIntervalHierarchy;

import imagedraw.HitRecord;
import java.util.List;
import rays.Ray;
import geometry.BoundingBox;
import geometry.Geometry;

/**
 * This class represents an acceleration structure for ray tracing : bounding interval hierarchy
 * Not much functionally is in this class, everything is placed in the node class.
 * This class only keeps information about the rootNode where to start hitting and the box around the total hierarchy
 * 
 * @author Dieter
 */
public class BoundingIntervalHierarchy {

	private Node rootNode; //rootnode to start from
	private BoundingBox box; //box containing complete scene
	private static final float epsilon = 0.05f;
	
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
		this.box = new BoundingBox(minX-epsilon,maxX+epsilon,minY-epsilon,maxY+epsilon,minZ-epsilon,maxZ+epsilon);
		rootNode = new Node(box,objects);
	}
	
	/**
	 * Hit this bounding interval hierarchy by recursively hitting the rootnode
	 * Before hitting the rootNode, check if this hierarchy is hit, by hitting the box that is around it
	 * Return null if no hit occured
	 */
	public HitRecord hit(Ray ray){
		HitRecord hr = null;
		float[] t = this.box.getMinAndMaxForBIH(ray);
		if(t != null){
			hr = this.rootNode.hit(ray, t[0], t[1]);
		}
		return hr;
	}
}
