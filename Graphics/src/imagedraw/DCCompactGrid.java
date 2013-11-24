package imagedraw;

import geometry.Geometry;

import java.util.List;

import mathematics.Point3f;
import acceleration.BoundingBox;
import acceleration.Cell;
import acceleration.CellHitRecord;
import acceleration.CompactGrid;
import rays.Ray;
import scenebuilder.Scene;

public class DCCompactGrid extends DrawController{

	private CompactGrid grid;
	private final int gridDensity = 10; //nbOfBoxesInEveryDirection
	
	public DCCompactGrid(Scene scene){
		super(scene);
		this.grid = new CompactGrid(gridDensity, scene);
	}
	
	@Override
	public HitRecord calculateHitRecord(Ray ray) {
		HitRecord hr = null;
		Cell cell;
		CellHitRecord chr;
		if(grid.isInGrid(ray.getViewPoint())){
			cell = grid.mapCoordinateToCell(ray.getViewPoint()); //if ray starts in grid, get starting cell
			chr = cell.travel(ray); 
			chr.setTmin(0); //ray starts in cell, so Tmin will be negative, you don't want this
			// TODO : check dit
		}
		else{
			Point3f enterPoint = grid.hit(ray); //get entrancePoint in grid
			cell = grid.mapCoordinateToCell(enterPoint); //get cell where ray enters grid
			chr = cell.travel(ray); 
		}
		hr = calculateHitRecordWithGrid(cell,ray,chr);
		return hr;
	}

	private HitRecord calculateHitRecordWithGrid(Cell cell, Ray ray, CellHitRecord chr){
		//first try to hit a boundingBox in this cell
		List<BoundingBox> boxes = grid.getBoxesForCellNumber(cell.getCellNumber()); //get all bounding boxes in this cell
		HitRecord smallest = null;
		float min_t = chr.getTmin(); //entering t of cell
		float max_t = chr.getTmax(); //leaving t of cell
		for(BoundingBox b : boxes){
			for(Geometry g : b.getGeometry()){
				HitRecord hr = g.rayObjectHit(ray); //hit all geometry in the box with the ray
				if(hr != null){
					if(hr.getT()<max_t && hr.getT()>min_t){
						min_t = hr.getT();
						smallest = hr;
					}
				}
			}
		}
		if(smallest != null){
			return smallest; //hitRecord found, don't travel grid further
		}
		else{
			// no hitrecord found, go to next cell
			int nextCellNb = grid.mapCoordinateToCellNumber(chr.getLeavePoint());
			// if nextCell is outside grid
			if(nextCellNb < 0){
				return null;
			}
			else{
				// if next cell is in grid, calculate needed parameters and recursively call this method
				Cell nextCell = grid.mapCellNumberToCell(nextCellNb);
				CellHitRecord nChr = nextCell.travel(ray);
				return calculateHitRecordWithGrid(nextCell, ray, nChr);
			}
		}		
	}

	@Override
	public boolean lookForShadowRayHit(Ray ray) {
		// TODO Auto-generated method stub
		return false;
	}

}
