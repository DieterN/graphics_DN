package acceleration;


import scenebuilder.Scene;

public class CompactGrid {

	private float gridDensity;
	private float cellDimensionX;
	private float cellDimensionY;
	private float cellDimensionZ;
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private double minZ;
	private double maxZ;
	private Cell[] cells;
	private BoundingBox[] boxes;
	private Scene scene;
	
	public CompactGrid(float gridDensity, Scene scene){
		this.gridDensity = gridDensity;
		this.scene = scene;
		calculateCellDimensions();
		makeCells();
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
		this.minX = scene.getDimensions()[0];
		this.maxX = scene.getDimensions()[1];
		this.minY = scene.getDimensions()[2];
		this.maxY = scene.getDimensions()[3];
		this.minZ = scene.getDimensions()[4];
		this.maxZ = scene.getDimensions()[5];
		this.cellDimensionX = (float) ((this.maxX - this.minX)/gridDensity); //TODO
		this.cellDimensionY = (float) ((this.maxY - this.minY)/gridDensity); //TODO
		this.cellDimensionZ = (float) ((this.maxZ - this.minZ)/gridDensity); //TODO
	}
	
	/**
	 * Make the cells for this CompactGrid. 
	 */
	private void makeCells(){
		int i = 0;
		double x = minX;
		double y = minY;
		double z = minZ;
		while(x<maxX){
			int j = 0;
			while(y<maxY){
				int k = 0;
				while(z<maxZ){
					Cell cell = new Cell(x,(x+cellDimensionX),y,(y+cellDimensionY),z,(z+cellDimensionZ),(i+j+k));
					cells[i+j+k] = cell;
					k++;
				}
				j++;
			}
			i++;
		}
	}
}
