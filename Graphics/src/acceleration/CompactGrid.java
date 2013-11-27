package acceleration;


import imagedraw.HitRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import mathematics.Point3f;
import rays.Ray;
import scenebuilder.Scene;

public class CompactGrid {

	private int gridDensity; //number of cells in every direction
	private float cellDimensionX; //cell width in x-direction
	private float cellDimensionY; //cell width in y-direction
	private float cellDimensionZ; //cell width in z-direction
	private BoundingBox box; //this box contains the grid
	private int[] cells;
	private BoundingBox[] boxes;
	private Map<Integer,ArrayList<BoundingBox>> tempMap = new HashMap<Integer,ArrayList<BoundingBox>>(); //tempMap for boxes to cell
	private Scene scene;
	private Point3f current;
	
	public CompactGrid(int gridDensity, Scene scene){
		this.gridDensity = gridDensity;
		this.scene = scene;
		calculateCellDimensions(); //this initalises the scene by transforming all objects and calculating boxes around them
		int nbBoxes = calculateCellArray(scene);
		boxes = new BoundingBox[nbBoxes]; //instantiate boxes array
		cells = new int[gridDensity*gridDensity*gridDensity]; //instantiate cells array
		calculateFinalArrays();
	}
	
	private void calculateFinalArrays() {
		// add everything at right places in boxes en cells array
		int j = 0;
		for(int i=0; i<cells.length ; i++){ 
			List<BoundingBox> boxForCell = tempMap.get(i); //boxes for cell number i
			cells[i] = j; //j is place where first element of this cell is located
			//this is the same number as the previous cell is there are no elements in the cell
			if(boxForCell != null){ //check if there are boxes in this cell, if not, then go to next, else, add them
				for(BoundingBox box : boxForCell){
					boxes[j] = box;
					j++;
				}
			}
		}
	}
	
	/**
	 * Calculate the dimensions for the given scene
	 * 
	 * @param minX
	 * @param maxX
	 * @param minY
	 * @param maxY
	 * @param minZ
	 * @param maxZ
	 */
	private void calculateCellDimensions(){ //numbers used to calculate number of boxes
		box.setMinX(scene.getDimensions()[0] - 0.05f); // TODO 0.5 voor safety dat alles er binnen valt?
		box.setMaxX(scene.getDimensions()[1] + 0.05f);
		box.setMinY(scene.getDimensions()[2] - 0.05f);
		box.setMaxY(scene.getDimensions()[3] + 0.05f);
		box.setMinZ(scene.getDimensions()[4] - 0.05f);
		box.setMaxZ(scene.getDimensions()[5] + 0.05f);
		this.cellDimensionX = ((box.getMaxX() - box.getMinX())/gridDensity); //FIXME
		this.cellDimensionY = ((box.getMaxY() - box.getMinY())/gridDensity); //FIXME
		this.cellDimensionZ = ((box.getMaxZ() - box.getMinZ())/gridDensity); //FIXME
	}
	
	/**
	 * Make the cells for this CompactGrid, don't do this, calculate arrays immediatly.
	 * This method returns the number of boundingboxes in the hashmap, this is needed to calculate the size of the boxes array
	 * 
	 * @return
	 */
	private int calculateCellArray(Scene scene){
		int nbBoxes = 0;
		for(BoundingBox box : scene.getBoxes()){ //loop over all bounding boxes and calculate the cell(s) they belong too
			for(Integer i : mapBoundingBoxToCellNumber(box)){ //add all keys to temporary hashmap
				ArrayList<BoundingBox> mapping = tempMap.get(i); //get all boxes belonging to the cell with the given number
				if(mapping == null){
					mapping = new ArrayList<BoundingBox>(); //create new list if doesn't exist already
				}
				mapping.add(box); //add box to list with boxes in this cell
				tempMap.put(i, mapping); //add new list
				nbBoxes++;
			}
		}
		return nbBoxes;
	}
	
	/**
	 * Map the given bounding box to cells and return a list with the cells he's in
	 * 
	 * @param box
	 * @return
	 */
	public List<Integer> mapBoundingBoxToCellNumber(BoundingBox box){
		List<Integer> cells = new ArrayList<Integer>();
		//linkeronderhoek mappen op cell
		int leftBottomFront = mapCoordinateToCellNumber(new Point3f(box.getMinX(),box.getMinY(),box.getMinZ()));
		int rightBottomFront = mapCoordinateToCellNumber(new Point3f(box.getMaxX(),box.getMinY(),box.getMinZ()));
		int leftTopFront = mapCoordinateToCellNumber(new Point3f(box.getMinX(),box.getMaxY(),box.getMinZ()));
		int leftBottomBack = mapCoordinateToCellNumber(new Point3f(box.getMinX(),box.getMinY(),box.getMaxZ()));
		// add everything between leftBottomFront and rightBottomFront
		// all boxes should be in grid, since grid was defined to reach that
		if(leftBottomBack<0 || rightBottomFront<0 || leftTopFront<0 || leftBottomBack<0){
			System.out.println("Box outside grid, impossible!");
			throw new IllegalArgumentException();
		}
		else{
			for(int i = leftBottomFront; i<=rightBottomFront; i += 1){ //x-direction, add 1
				for(int j = 0; (leftBottomFront+j)<=leftTopFront; j += gridDensity){ //y-direction, add griddensity
					for(int k = 0; (leftBottomFront+k)<=leftBottomBack; k+= gridDensity*gridDensity){ //z-direction, add griddesity
						cells.add(i+j+k);
					}
				}
			}
		}
		return cells;
	}
	
	/**
	 * Method to check in which cell of the grid the given point is located
	 * 
	 * @param point
	 * @return cellNumber where the given point belongs too
	 * 			-1 if point doesn't belong to grid
	 */
	public int mapCoordinateToCellNumber(Point3f point){
		//FIXME : als er een punt in 2 vakjes ligt???
		int cellNumber = -1;
		if(isInGrid(point)){
			cellNumber = (int) (Math.floor((point.x-box.getMinX())/(box.getMaxX()-box.getMinX())*gridDensity));
			cellNumber += (Math.floor((point.y-box.getMinY())/(box.getMaxY()-box.getMinY())*gridDensity))*gridDensity; // maal aantal in x-richting
			cellNumber += (Math.floor((point.z-box.getMinZ())/(box.getMaxZ()-box.getMinZ())*gridDensity))*gridDensity*gridDensity; // maal aantal in x- en y-richting
		}
		return cellNumber;
	}
	
	/**
	 * Method that returns a cell object with the given number
	 * 
	 * @param point
	 * @return Cell if cellnumber exists
	 * 			null otherwise
	 */
	public Cell mapCellNumberToCell(int cellNumber){
		Cell cell = null;
		if(cellNumber < 0 || cellNumber > (gridDensity*gridDensity*gridDensity-1)){
			System.out.println("Cellnumber is outside of grid");
		}
		else{
			int densitySquare = gridDensity*gridDensity;
			int k = calculateMaxValue(cellNumber,densitySquare);
			float maxiZ = k*cellDimensionZ;
			float miniZ = maxiZ - cellDimensionZ;
			cellNumber = cellNumber - (k-1)*densitySquare;
			int l = calculateMaxValue(cellNumber, gridDensity);
			float maxiY = l*cellDimensionY;
			float miniY = maxiY - cellDimensionY;
			cellNumber = cellNumber - (l-1)*gridDensity;
			int m = calculateMaxValue(cellNumber, 1);
			float maxiX = m*cellDimensionX;
			float miniX = maxiX - cellDimensionX;
			cell = new Cell(miniX, maxiX, miniY, maxiY, miniZ, maxiZ,cellNumber);
		}
		return cell;
	}
	
	private int calculateMaxValue(int cellNumber, int subtract){
		int k = 0;
		for(int i = cellNumber; i>=0; i -= subtract){
			k++;
		}
		return k;
	}
	
	public boolean isInGrid(Point3f point){
		boolean isIn = false;
		if(point.x >= box.getMinX() & point.x <= box.getMaxX()){
			if(point.y >= box.getMinY() & point.y <=box.getMaxY()){
				if(point.z >= box.getMinZ() & point.z <= box.getMaxZ()){
					isIn = true;
				}
			}
		}
		return isIn;
	}
	
	/**
	 * Get all boundingboxes that are located in the cell with the given number
	 * 
	 * @param cellNumber
	 * @return List with all BoundingBoxes in this cell
	 */
	public List<BoundingBox> getBoxesForCellNumber(int cellNumber){
		List<BoundingBox> boxList = new ArrayList<BoundingBox>();
		int index = cells[cellNumber]; //index of first element in this cell
		int nextIndex = cells[cellNumber+1]; //index of first element in next cell
		for(int i = index; index<nextIndex; index++){ //get all elements from this cell
			boxList.add(boxes[i]); //add all these elements to the returnlist
		}
		return boxList;
	}

	/**
	 * Return null if no hit
	 * 
	 * @param ray
	 * @return
	 */
	public Point3f hit(Ray ray) {
		HitRecord hr = this.box.rayObjectHit(ray);
		if(hr != null){
			return hr.getHitPoint();
		}
		return null;
	}
	
	public Cell mapCoordinateToCell(Point3f point){
		int cellNumber = mapCoordinateToCellNumber(point);
		return mapCellNumberToCell(cellNumber);
	}
	
	/**
	 * Call when X changed point is next
	 * 
	 * @param point
	 * @param cellNumber
	 */
	public int getNextCellNumberX(float x, int cellNumber){
		if(x < current.x && x > box.getMinX()){
			current.x = x;
			return getCellLeft(cellNumber);
		}
		else if(x > current.x && x < box.getMaxX()){ //FIXME : floats, vgl ok? (0.5f)
			current.x = x;
			return getCellRight(cellNumber);
		}
		return -1; //outside grid
	}
	
	/**
	 * Call when Y changed point is next
	 * 
	 * @param point
	 * @param cellNumber
	 */
	public int getNextCellNumberY(float y, int cellNumber){
		if(y < current.y && y > box.getMinY()){
			current.y = y;
			return getCellDown(cellNumber);
		}
		else if(y > current.y && y < box.getMaxY()){ //FIXME : floats, vgl ok?
			current.y = y;
			return getCellUp(cellNumber);
		}
		return -1; //outside grid
	}
	
	/**
	 * Call when Z changed point is next
	 * 
	 * @param point
	 * @param cellNumber
	 */
	public int getNextCellNumberZ(float z, int cellNumber){
		if(z < current.z && z > box.getMinZ()){
			current.z = z;
			return getCellFront(cellNumber);
		}
		else if(z > current.z && z < box.getMaxZ()){ //FIXME : floats, vgl ok?
			current.z = z;
			return getCellBack(cellNumber);
		}
		return -1; //outside grid
	}
	
	private int getCellLeft(int cellNumber){
		return cellNumber - 1;
	}
	
	private int getCellRight(int cellNumber){
		return cellNumber + 1;
	}
	
	private int getCellDown(int cellNumber){
		return cellNumber - gridDensity;
	}
	
	private int getCellUp(int cellNumber){
		return cellNumber + gridDensity;
	}
	
	private int getCellBack(int cellNumber){
		return cellNumber + (gridDensity*gridDensity);
	}
	
	private int getCellFront(int cellNumber){
		return cellNumber - (gridDensity*gridDensity);
	}
}
