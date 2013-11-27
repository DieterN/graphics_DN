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
		Point3f current = null;
		float[] deltaT = new float[6];
		int cellNumber = -1;
		//initialise
		if(grid.isInGrid(ray.getViewPoint())){
			Cell cell = grid.mapCoordinateToCell(ray.getViewPoint()); //if ray starts in grid, get starting cell
			float[] t = cell.getDeltas(ray); //check ok if negative t
			cellNumber = cell.getCellNumber();  
			current = ray.getViewPoint();
			current.x += t[0]; //minX eraf --> negatief getal, dus optellen
			current.y += t[2]; //minY eraf --> negatief getal, dus optellen
			current.z += t[4]; //minZ eraf --> negatief getal, dus optellen
			deltaT[0] = t[1] - t[0];
			deltaT[1] = t[3] - t[2];
			deltaT[2] = t[5] - t[4];
		}
		else{
			Point3f enterPoint = grid.hit(ray);//get entrancePoint in grid
			if(enterPoint != null){
				current = enterPoint;
				Cell cell = grid.mapCoordinateToCell(enterPoint);//get cell where ray enters grid
				cellNumber = cell.getCellNumber();  
				float[] t = cell.getDeltas(ray); //check ok if negative t
				deltaT[0] = t[1] - t[0];
				deltaT[1] = t[3] - t[2];
				deltaT[2] = t[5] - t[4];
			}
			else{
				return null; //no hit with grid
			}
		}
		hr = calculateHitRecordWithGrid(cellNumber,ray,deltaT, current);
		return hr;
	}

	private HitRecord calculateHitRecordWithGrid(int cellNumber, Ray ray, float[] t, Point3f current){
//		//first try to hit a boundingBox in this cell
//		List<BoundingBox> boxes = grid.getBoxesForCellNumber(cellNumber); //get all bounding boxes in this cell
//		HitRecord smallest = null;
//		float min_t = chr.getTmin(); //entering t of cell
//		float max_t = chr.getTmax(); //leaving t of cell
//		for(BoundingBox b : boxes){
//			for(Geometry g : b.getGeometry()){
//				HitRecord hr = g.rayObjectHit(ray); //hit all geometry in the box with the ray
//				if(hr != null){
//					if(hr.getT()<max_t && hr.getT()>min_t){
//						min_t = hr.getT();
//						smallest = hr;
//					}
//				}
//			}
//		}
//		if(smallest != null){
//			return smallest; //hitRecord found, don't travel grid further
//		}
//		else{
//			// no hitrecord found, go to next cell
//			// calculate closest of 3 points
//			int nextCellNb = grid.mapCoordinateToCellNumber(chr.getLeavePoint());
//			// if nextCell is outside grid
//			if(nextCellNb < 0){
//				return null;
//			}
//			else{
//				// if next cell is in grid, calculate needed parameters and recursively call this method
//				Cell nextCell = grid.mapCellNumberToCell(nextCellNb);
//				CellHitRecord nChr = nextCell.travel(ray);
//				return calculateHitRecordWithGrid(nextCell, ray, nChr);
//			}
//		}		
		return null;
	}

	@Override
	public boolean lookForShadowRayHit(Ray ray) {
		// TODO Auto-generated method stub
		return false;
	}

}
