package acceleration.compactGrid;

import geometry.BoundingBox;
import geometry.Geometry;
import imagedraw.HitRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mathematics.Point3f;
import mathematics.Vector4f;
import rays.Ray;

/**
 * This class represents a compact grid acceleration structure.
 * 
 * @author Dieter
 *
 */
public class CompactGrid {

	private final int gridDensity = 8;
	private final double safety = 0.1; //number between 0 and 0.5
	//the higher this number, the safer you map boxes to cells
	//the lower this number, the less safer
	//a lower number has the advantage of drawing faster
	private float epsilon = 0.05f;
	private int nbOfCellsX; //number of cells in x-direction
	private int nbOfCellsY; //number of cells in y-direction
	private int nbOfCellsZ; //number of cells in z-direction
	private float cellDimensionX; //cell width in x-direction
	private float cellDimensionY; //cell width in y-direction
	private float cellDimensionZ; //cell width in z-direction
	private BoundingBox root; //this box contains the grid
	private int[] cells;
	private BoundingBox[] boxes;
	private Map<Integer,ArrayList<BoundingBox>> tempMap = new HashMap<Integer,ArrayList<BoundingBox>>(); //tempMap for boxes to cell
	
	/*******************
	 *** CONSTRUCTOR ***
	 *******************/
	public CompactGrid(List<? extends Geometry> geometry){
		calculateRootBoundingBox(geometry); //this initalises the bouding box around the given objects
		calculateCellsInEachDirection(geometry); //make a grid of the root bounding box
		cells = new int[(nbOfCellsX*nbOfCellsY*nbOfCellsZ)+1]; //instantiate cells array, +1 for index of last
		int nbBoxes = calculateCellArray(geometry);
		boxes = new BoundingBox[nbBoxes]; //instantiate boxes array
		calculateFinalArrays();
	}
	
	/***********************
	 *** INITIALISE GRID ***
	 ***********************/
	
	/**
	 * Calculate the dimensions for this grid, so it contains all objects in the given list
	 */
	private void calculateRootBoundingBox(List<? extends Geometry> geo){
		if(geo.isEmpty()){
			System.out.println("Making empty grid");
			throw new IllegalArgumentException();
		}
		float minX = Float.MAX_VALUE; //minX
		float maxX = -Float.MAX_VALUE; //maxX
		float minY = Float.MAX_VALUE; //minY
		float maxY = -Float.MAX_VALUE; //maxY
		float minZ = Float.MAX_VALUE; //minZ
		float maxZ = -Float.MAX_VALUE; //maxZ
		
		for(Geometry g : geo){
			BoundingBox box = g.getBox();
			if(box.getMinX() < minX){ minX = box.getMinX();}
			if(box.getMaxX() > maxX){ maxX = box.getMaxX();}
			if(box.getMinY() < minY){ minY = box.getMinY();}
			if(box.getMaxY() > maxY){ maxY = box.getMaxY();}
			if(box.getMinZ() < minZ){ minZ = box.getMinZ();}
			if(box.getMaxZ() > maxZ){ maxZ = box.getMaxZ();}
		}
		
		root = new BoundingBox(minX-epsilon,maxX+epsilon,minY-epsilon,maxY+epsilon,minZ-epsilon,maxZ+epsilon);
	}
	
	/**
	 * Calculate the number of Cells in each direction and the dimension of these cells
	 */
	private void calculateCellsInEachDirection(List<? extends Geometry> geometry){
		float xWidth = (root.getMaxX()-root.getMinX());
		float yWidth = (root.getMaxY()-root.getMinY());
		float zWidth = (root.getMaxZ()-root.getMinZ());
		float volume = xWidth*yWidth*zWidth; //volume of bounding box
		float nbObjects = geometry.size();

		nbOfCellsX = (int) Math.ceil(xWidth*Math.pow(((gridDensity*nbObjects)/volume),1/3.0)); //round nb of Cells up
		nbOfCellsY = (int) Math.ceil(yWidth*Math.pow(((gridDensity*nbObjects)/volume),1/3.0)); //round nb of Cells up
		nbOfCellsZ = (int) Math.ceil(zWidth*Math.pow(((gridDensity*nbObjects)/volume),1/3.0)); //round nb of Cells up
				
		this.cellDimensionX = xWidth/nbOfCellsX;
		this.cellDimensionY = yWidth/nbOfCellsY;
		this.cellDimensionZ = zWidth/nbOfCellsZ;
	}
	
	/**
	 * Calculate two arrays needed for ray tracing:
	 * boxes : array with all bounding boxes in this scene
	 * cells : array with startingIndex of boxes in the cell with number 'index'
	 */
	private void calculateFinalArrays() {
		// add everything at right places in boxes en cells array
		for(int i=(cells.length-1); i>=0 ; i--){ //start at highest index, go down to first cell
			List<BoundingBox> boxForCell = tempMap.get(i); //boxes for cell number i
			if(boxForCell != null){ //check if there are boxes in this cell, if not, then go to next, else, add them
				for(BoundingBox box : boxForCell){ //add the boxes backwards to the list
					cells[i]--;
					boxes[cells[i]] = box;
				}
			}
		}
	}
	
	/**
	 * Make a tempMap containing the mapping between cellnumber and boundingbox
	 * This method returns the number of boundingboxes in the hashmap, this is needed to calculate the size of the boxes array
	 */
	private int calculateCellArray(List<? extends Geometry> geometrys){
		for(Geometry g : geometrys){ //loop over all bounding boxes and calculate the cell(s) they belong too
			BoundingBox box = g.getBox();
			for(Integer i : mapBoundingBoxToCellNumber(box)){ //add all cells where box is in to temporary hashmap
				ArrayList<BoundingBox> mapping = tempMap.get(i); //get all boxes belonging to the cell with the given number
				if(mapping == null){
					mapping = new ArrayList<BoundingBox>(); //create new list if doesn't exist already
				}
				mapping.add(box); //add box to list with boxes in this cell
				tempMap.put(i, mapping); //add new list
				cells[i]++; //adjust the number of boxes in cellNumber i
			}
		}
		return countCellArray(); //sum all numbers in the cell array and return total nb of boxes
	}
	
	/**
	 * Count everything in cellArray
	 */
	private int countCellArray(){
		for(int i = 1; i<cells.length; i++){
			cells[i] += cells[i-1]; //sum everything in the cell
		}
		return cells[cells.length-1];
	}
	
	/**
	 * Map the given bounding box to cells and return a list with the cells he's in
	 * 
	 * To do this, get the 2 extreme points of the bounding box
	 * Map the leftbottomfront point to the most leftbottomfront cell if he's near an edge
	 * Map the righttopback point to the most righttopback cell if he's near an edge
	 * In this way, we're sure that the bounding box will be in the right cells,
	 * and there're no problems with floating point calculations
	 */
	private List<Integer> mapBoundingBoxToCellNumber(BoundingBox box){
		List<Integer> cells = new ArrayList<Integer>();
		Point3f[] bounds = box.getBounds();
		//leftdowncorner on most leftdown cell
		int leftBottomFront = mapCoordinateToCellNumberDown(bounds[0]);
		//righttopcorner on most righttop cell
		int rightTopBack = mapCoordinateToCellNumberUp(bounds[1]);

		//check if the box is outside the grid
		if(leftBottomFront<0 || rightTopBack<0){
		System.out.println("Box outside grid, impossible!");
		throw new IllegalArgumentException();
		}
		
		//get the cellnumber divided in x,y,z, all are starting at 0 and counting up
		int[] min = mapCellNumberToXYZ(leftBottomFront);
		int[] max = mapCellNumberToXYZ(rightTopBack);
		
		int cellsXTimescellsY = nbOfCellsX*nbOfCellsY;
		
		for(int i = 0; i <= (max[0]-min[0]); i++){
			for(int j = 0; j <= (max[1]-min[1]); j ++){
				for(int k = 0; k <= (max[2]-min[2]); k++){
					cells.add(leftBottomFront+i+j*nbOfCellsX+k*cellsXTimescellsY);
				}
			}
		}
		return cells;
	}
	
	
	/**
	 * Method to check in which cell of the grid the given point is located
	 * If close to edge, the smallest cell is taken
	 * 
	 * @param point
	 * @return cellNumber where the given point belongs too
	 * 			-1 if point doesn't belong to grid
	 */
	private int mapCoordinateToCellNumberDown(Point3f point){
		int cellNumber = -1;
		if(isInGrid(point)){
			int x = (int) (Math.round(((point.x-root.getMinX())/cellDimensionX)-(0.5+safety)));
			int y = (int) (Math.round(((point.y-root.getMinY())/cellDimensionY)-(0.5+safety)));
			int z = (int) (Math.round(((point.z-root.getMinZ())/cellDimensionZ)-(0.5+safety)));
			
			if(x < 0) { x = 0; }
			if(y < 0) { y = 0; }
			if(z < 0) { z = 0; }

			cellNumber = mapXYZToCellNumber(x, y, z);
		}
		return cellNumber;
	}
	
	
	/**
	 * Method to check in which cell of the grid the given point is located
	 * If close to edge, the largest cell is taken
	 * 
	 * @param point
	 * @return cellNumber where the given point belongs too
	 * 			-1 if point doesn't belong to grid
	 */
	private int mapCoordinateToCellNumberUp(Point3f point){
		int cellNumber = -1;
		if(isInGrid(point)){
			int x = (int) (Math.round(((point.x-root.getMinX())/cellDimensionX)-(0.5-safety)));
			int y = (int) (Math.round(((point.y-root.getMinY())/cellDimensionY)-(0.5-safety)));
			int z = (int) (Math.round(((point.z-root.getMinZ())/cellDimensionZ)-(0.5-safety))); 
			
			if(x > (nbOfCellsX-1)) { x = (nbOfCellsX-1); }
			if(y > (nbOfCellsY-1)) { y = (nbOfCellsY-1); }
			if(z > (nbOfCellsZ-1)) { z = (nbOfCellsZ-1); }

			cellNumber = mapXYZToCellNumber(x, y, z);
		}
		return cellNumber;
	}
	
	/**
	 * Method to check in which cell of the grid the given point is located
	 * This is used when entering the grid, don't use to map boundingboxes
	 * 
	 * @param point
	 * @return cellNumber where the given point belongs too
	 * 			-1 if point doesn't belong to grid
	 */
	public int mapCoordinateToCellNumber(Point3f point){
		int cellNumber = -1;
		if(isInGrid(point)){
			int x = (int) (Math.floor((point.x-root.getMinX())/cellDimensionX));
			int y = (int) (Math.floor((point.y-root.getMinY())/cellDimensionY)); // maal aantal in x-richting
			int z = (int) (Math.floor((point.z-root.getMinZ())/cellDimensionZ)); // maal aantal in x- en y-richting

			if(x < 0) { x = 0; }
			if(x > (nbOfCellsX-1)) { x = (nbOfCellsX-1); }
			
			if(y < 0) { y = 0; }
			if(y > (nbOfCellsY-1)) { y = (nbOfCellsY-1); }
			
			if(z < 0) { z = 0; }
			if(z > (nbOfCellsZ-1)) { z = (nbOfCellsZ-1); }
			
			cellNumber = mapXYZToCellNumber(x, y, z);
		}
		return cellNumber;
	}
	
	/**
	 * Get the cellnumber divided in x,y,z, all are starting at 0 and counting up
	 */
	private int[] mapCellNumberToXYZ(int cellNumber){
		int[] result = new int[3];
		if(cellNumber < 0 || cellNumber > ((nbOfCellsX*nbOfCellsY*nbOfCellsZ)-1)){
			System.out.println("Cellnumber is outside of grid");
		}
		else{
			int resolutionSquare = nbOfCellsX*nbOfCellsY;
			int k = calculateMaxValue(cellNumber,resolutionSquare);
			result[2] = k-1;
			cellNumber = cellNumber - (k-1)*resolutionSquare;
			int l = calculateMaxValue(cellNumber, nbOfCellsX);
			result[1] = l-1;
			cellNumber = cellNumber - (l-1)*nbOfCellsX;
			int m = calculateMaxValue(cellNumber, 1);
			result[0] = m-1;
		}
		return result;
	}
	
	private int calculateMaxValue(int cellNumber, int subtract){
		int k = 0;
		for(int i = cellNumber; i>=0; i -= subtract){
			k++;
		}
		return k;
	}
	
	public boolean isInGrid(Point3f point){
		double delta = 0.001;
		boolean isIn = false;
		if(point.x >= (root.getMinX()-delta) & point.x <= (root.getMaxX()+delta)){
			if(point.y >= (root.getMinY()-delta) & point.y <= (root.getMaxY()+delta)){
				if(point.z >= (root.getMinZ()-delta) & point.z <= (root.getMaxZ()+delta)){
					isIn = true;
				}
			}
		}
		return isIn;
	}

	/******************************
	 *** TRACE RAY THROUGH GRID ***
	 ******************************/

	private Point3f entryPoint;
	private float currentT; //t-value where you entered the cell
	private float tDeltaX; //distance between two hits x
	private float tDeltaY; //distance between two hits y
	private float tDeltaZ; //distance between two hits z
	private float nextTX; //t value of next hit with x
	private float nextTY; //t value of next hit with y
	private float nextTZ; //t value of next hit with z
	private int x; //x value of current cell
	private int y; //y value of current cell
	private int z; //z value of current cell
	private int stepX; //+1 if ray going positive along x, -1 otherwise
	private int stepY; //+1 if ray going positive along y, -1 otherwise
	private int stepZ; //+1 if ray going positive along z, -1 otherwise
	
	/**
	 * Hit the grid:
	 * if grid isn't hit, return null
	 * else try hitting something in the grid
	 * if nothing in the grid is hit, return null
	 * else return closest hitrecord
	 */
	public HitRecord hit(Ray ray) {
		this.clearRayInfo(); //Clear all the info of the previous ray
		HitRecord result = null;
		int cellNumber = calculateStartingCellNumber(ray); //calculate cellnumber where ray enters or starts
		if(cellNumber >= 0){ //if cellnumber is greater than 0, a hit occured
			FirstHitRecord fhr = initialiseRayHitParameters(ray, cellNumber); //initalise deltaT and nextT
			if(fhr == null){
				return null;
			}
			else{
				nextTX = fhr.gettMaxX();
				nextTY = fhr.gettMaxY();
				nextTZ = fhr.gettMaxZ();
				tDeltaX = fhr.gettMaxX()-fhr.gettMinX();
				tDeltaY = fhr.gettMaxY()-fhr.gettMinY();
				tDeltaZ = fhr.gettMaxZ()-fhr.gettMinZ();
			}
			result = traverse(cellNumber,ray); //start the recursive traversing grid method
		}		
		return result;
	}
	
	/**
	 * Clear all the info of the previous ray
	 */
	private void clearRayInfo(){
		tDeltaX = 0;
		tDeltaY = 0;
		tDeltaZ = 0;
		nextTX = 0;
		nextTY = 0;
		nextTZ = 0;
		currentT = 0;
		x = 0;
		y = 0;
		z = 0;
		stepX = 0;
		stepY = 0;
		stepZ = 0;
		entryPoint = null;
	}
	
	/**
	 * Check if the given ray hits the grid and if he does so, initalise parameters
	 */
	private int calculateStartingCellNumber(Ray ray){ //geef starting cell number terug ? --> -1 als geen hit
		int cellNumber = -1;
		if(isInGrid(ray.getViewPoint())){ //entryPoint = startPoint of ray
			entryPoint = ray.getViewPoint();
			currentT = 0; //you start in grid, so currentT should be 0
		}
		else{
			GridHitInfo ghi = this.root.getEntryPoint(ray); //entryPoint = place where grid is hit
			if(ghi != null){ //grid is hit
				if(ghi.gettHit() >= 0){
					currentT = ghi.gettHit(); //t distance of grid hit
					entryPoint = ghi.getEntryPoint();
				}
				else{
					cellNumber = -3;
				}
			}
			else{
				cellNumber = -2;
			}
		}
		if(entryPoint != null){ //entryPoint != null, so calculate starting cellNumber
			cellNumber = mapCoordinateToCellNumber(entryPoint);
		}
		return cellNumber;
	}

	private FirstHitRecord initialiseRayHitParameters(Ray ray, int cellNumber){
		Cell cell = mapCellNumberToCell(cellNumber);
		initialiseSteps(ray);
		int[] xyz = mapCellNumberToXYZ(cellNumber); //initialise xyz value of current cell
		x = xyz[0];
		y = xyz[1];
		z = xyz[2];
		FirstHitRecord fhr = cell.firstGridHit(ray);
		return fhr;
		}
	
	/**
	 * Initialise steps, if ray goes positive, this is 1, otherwise -1
	 */
	private void initialiseSteps(Ray ray){
		Vector4f direction = ray.getDirection();
		if(direction.x >= 0){
			stepX = 1;
		}
		else{
			stepX = -1;
		}
		if(direction.y >= 0){
			stepY = 1;
		}
		else{
			stepY = -1;
		}
		if(direction.z >= 0){
			stepZ = 1;
		}
		else{
			stepZ = -1;
		}
	}
	
	/**
	 * Map the given cellNumber to a cell object, needed when starting up the raytracing
	 */
	private Cell mapCellNumberToCell(int cellNumber) {
		int[] xyz = mapCellNumberToXYZ(cellNumber);
		float gridMinX = root.getMinX();
		float gridMinY = root.getMinY();
		float gridMinZ = root.getMinZ();
		float minX = gridMinX+xyz[0]*cellDimensionX;
		float maxX = gridMinX+(xyz[0]+1)*cellDimensionX;
		float minY = gridMinY+xyz[1]*cellDimensionY;
		float maxY = gridMinY+(xyz[1]+1)*cellDimensionY;
		float minZ = gridMinZ+xyz[2]*cellDimensionZ;
		float maxZ = gridMinZ+(xyz[2]+1)*cellDimensionZ;
		
		return new Cell(minX,maxX,minY,maxY,minZ,maxZ,cellNumber);
	}
	
	/**
	 * Recursive method for traversing the scene:
	 * 
	 * Start with getting the min_t value, this is currentT, the distance at which we entered this cell
	 * Set max_t value, this is the distance at which we're leaving the cell
	 * Get all bounding boxes in this cell
	 * Get closest hit object in this cell (to check if in this cell, use min_t and max_t value
	 * If something was hit, return it
	 * Else calculate the next cell
	 * If this cell is outside the grid, return null
	 * Else call this method again with the next cell and the same ray
	 */
	private HitRecord traverse(int cellNumber, Ray ray){
		HitRecord smallest = null;
		float max_t = Math.min(nextTX,Math.min(nextTY,nextTZ)); //leaving t of cell
		float min_t = Float.POSITIVE_INFINITY; //closest hit
		List<BoundingBox> boxes = getBoxesForCellNumber(cellNumber);
		for(BoundingBox b : boxes){ //boxes is empty list if nothing is in the cell
			HitRecord hr = b.rayObjectHit(ray); //hit all geometry in the box with the ray
			if(hr != null){
				//check of hit binnen deze cel is en of het de dichtste hit is
				if(hr.getT() <= max_t && hr.getT() >= currentT && hr.getT() <= min_t){
					min_t = hr.getT();
					smallest = hr;
				}
			}
		}
		if(smallest == null){ //nothing was hit within this cell
			int nextCell = getNextCell(); 
			if(nextCell<0){ //getNextCell returns -1 if you go outside of the grid
				return smallest; //(should be null)
			}
			return traverse(nextCell, ray);
		}
		return smallest;
	}
	
	/**
	 * Get all boundingboxes that are located in the cell with the given number
	 */
	private List<BoundingBox> getBoxesForCellNumber(int cellNumber){
		List<BoundingBox> boxList = new ArrayList<BoundingBox>();
		int index = cells[cellNumber]; //index of first element in this cell
		int nextIndex = cells[cellNumber+1]; //index of first element in next cell
		for(int i = index; i<nextIndex; i++){ //get all elements from this cell
			boxList.add(boxes[i]); //add all these elements to the returnlist
		}
		return boxList;
	}
	
	/**
	 * Get the next cell starting from the given cellNumber and along the direction of the given ray
	 * To do so, first check which is the next plane hit, is it a plane along the x,y or z-axis?
	 * Adjust the value of the x,y,z vector and check if these values are still valid, if so, than continue
	 * If you got that, call the right method that will calculate the next cell
	 * 
	 * If you go outside the grid, return -1 !!!
	 */
	private int getNextCell() {
		int nextCell = -1;
		if(nextTX <= nextTY && nextTX <= nextTZ){ //x closer than y and z
			currentT = nextTX; //currentT is now this t
			nextTX += tDeltaX; //add deltaT to initalise nextT 
			x += stepX;
			if(x>(nbOfCellsX-1) || x<0){
					return nextCell;
			}
		}
		else if(nextTY <= nextTZ){ //y closer than z and closer than x too than
				currentT = nextTY; //currentT is now this t
				nextTY += tDeltaY; //add deltaT to initalise nextT
				y += stepY;
				if(y>(nbOfCellsY-1) || y<0){
					return nextCell;
				}
			}
			else{ //z closer than y, and y closer than x, so z closest
				currentT = nextTZ; //currentT is now this t
				nextTZ += tDeltaZ; //add deltaT to initalise nextT
				z += stepZ;
				if(z>(nbOfCellsZ-1) || z<0){
					return nextCell;
				}				
			}
		return mapXYZToCellNumber(x,y,z); //map current x,y,z value to cellNumber
	}
	
	/**
	 * Use current xyz value to get current CellNumber
	 */
	private int mapXYZToCellNumber(int x, int y, int z){
		return x+y*nbOfCellsX+z*nbOfCellsX*nbOfCellsY;
	}
}
