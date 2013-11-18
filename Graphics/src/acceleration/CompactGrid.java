package acceleration;


import scenebuilder.Scene;

public class CompactGrid {

	private float gridDensity;
	private float cellDimensionX;
	private float cellDimensionY;
	private float cellDimensionZ;
	private float minX;
	private float maxX;
	private float minY;
	private float maxY;
	private float minZ;
	private float maxZ;
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
		float x = minX;
		float y = minY;
		float z = minZ;
		while(x<maxX){
			int j = 0;
			while(y<maxY){
				int k = 0;
				while(z<maxZ){
					Cell cell = new Cell(x,(x+cellDimensionX),y,(y+cellDimensionY),z,(z+cellDimensionZ),(i+j+k));
					cells[i+j+k] = cell;
					k++;
					z += cellDimensionZ;
				}
				j++;
				y += cellDimensionY;
			}
			i++;
			x += cellDimensionX;
		}
	}
}
