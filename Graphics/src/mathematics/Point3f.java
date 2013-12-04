package mathematics;

import java.io.Serializable;

public class Point3f extends Tuple3f implements Serializable
{
    /**
     * Constructs and initializes a Point3f to (0,0,0).
     */

    public Point3f()
    {
    }


    /**
     * Constructs and initializes a Point3f from the specified xyz coordinates.
     * @param x
     * @param y
     * @param z
     */
    public Point3f(float x, float y, float z)
    {
        super(x, y, z);
    }


    /**
     * Constructs and initializes a Point3f from the specified Point3f.
     * @param p
     */

    public Point3f(Point3f p)
    {
        super(p);
    }


    /**
     * Constructs and initializes a Point3f from the specified Tuple3f.
     * @param t
     */

    public Point3f(Tuple3f t)
    {
        super(t);
    }


    /**
     * Constructs and initializes a Point3f from the array of length 3.
     * @param p
     */

    public Point3f(float p[])
    {
        super(p);
    }

    public void setPointValueOfAxis(int axis, float value){
    	if(axis == 0){//x-as
    		this.x = value;
    	}
    	else if(axis == 1){//y-as
    		this.y = value;
    	}
    	else if(axis == 2){//z-as
    		this.z = value;
    	}
    	else{
    		System.out.println("No valid plane");
    		throw new IllegalArgumentException();
    	}
    }

    public float getPointValueOfAxis(int axis){
    	if(axis == 0){//x-as
    		return this.x;
    	}
    	else if(axis == 1){//y-as
    		return this.y;
    	}
    	else if(axis == 2){//z-as
    		return this.z;
    	}
    	else{
    		System.out.println("No valid plane");
    		throw new IllegalArgumentException();
    	}
    }

}
