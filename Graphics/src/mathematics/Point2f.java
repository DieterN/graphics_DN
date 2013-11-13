package mathematics;

import java.io.Serializable;
import java.util.ArrayList;

public class Point2f extends Tuple2f implements Serializable
{
	private static ArrayList<Point2f> pointList = new ArrayList<Point2f>();
	
    private Point2f(float x, float y)
    {
        super(x, y);
    }

    /**
	 * Get a Point2f object with the specified x and y values.
	 * 
	 * @param 	x
	 * @param 	y
	 * @return	The desired point object with specified x and y values.
	 */
	public static Point2f getPoint2f(float x, float y) {
		Point2f resultPoint = null;
		if(!pointList.isEmpty()){
			for(Point2f p:pointList){
				if(p.x==x && p.y==y){ //kan op floats vgln, want pixels
					resultPoint = p;
				}
			}
		}
		if(resultPoint==null){
			resultPoint = new Point2f(x,y);
			pointList.add(resultPoint);
		}
		return resultPoint;
	}
    
	/**
	 * Get a Point2f object with the specified x and y values.
	 * 
	 * @param 	x
	 * @param 	y
	 * @return	The desired point object with specified x and y values.
	 */
	public static Point2f getPoint2f(int x, int y) {
		Point2f resultPoint = null;
		if(!pointList.isEmpty()){
			for(Point2f p:pointList){
				if(p.x==x && p.y==y){ //kan op floats vgln, want pixels???
					resultPoint = p;
				}
			}
		}
		if(resultPoint==null){
			resultPoint = new Point2f(x,y);
			pointList.add(resultPoint);
		}
		return resultPoint;
	}
	
    public Point2f(float p[])
    {
        super(p);
    }

    public Point2f(Point2f p)
    {
        super(p);
    }

    public Point2f(Tuple2f t)
    {
        super(t);
    }

}
