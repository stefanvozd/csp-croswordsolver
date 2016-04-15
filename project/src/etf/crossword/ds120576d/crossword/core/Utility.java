package etf.crossword.ds120576d.crossword.core;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;

import etf.crossword.ds120576d.core.exceptions.OutOfLineRangeException;
import etf.crossword.ds120576d.core.exceptions.ParallelLineException;


/**
 * The Class Utility.
 */
public class Utility {
	
	/**
	 * Makes deep copy.
	 *
	 * @param orig the orig
	 * @return the object
	 */
	public static Object copy(Object orig) {
		Object obj = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(orig);
			out.flush();
			out.close();

			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(bos.toByteArray()));
			obj = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return obj;
	}


	
	/**
	 * Check if dot and line intersects
	 *
	 * @param C the c
	 * @param A the a
	 * @param B the b
	 * @return true, if Intersects
	 */
	public static boolean intersectsDot(Point C,Point A, Point B ) {
		   return (A.x - C.x)*(A.y - C.y) == (C.x - B.x)*(C.y - B.y);
		}
	
	
	/**
	 * Finds index of intersect between two points.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @return the int
	 */
	public static int indexOfIntersect(Point p1,Point p2){
		return  (int) Math.sqrt((p1.x-p2.x)*(p1.x-p2.x) + (p1.y-p2.y)*(p1.y-p2.y));
	}
	
	/**
	 * Finds point of intersect between two lines.
	 *
	 * @param cw1 the cw1
	 * @param cw2 the cw2
	 * @return the point
	 * @throws ParallelLineException the parallel line exception
	 * @throws OutOfLineRangeException the out of line range exception
	 */
	public static Point lineIntersect(Variable cw1, Variable cw2) throws ParallelLineException, OutOfLineRangeException {

		return lineIntersect(cw1.start.x, cw1.start.y, cw1.end.x, cw1.end.y,
				cw2.start.x, cw2.start.y, cw2.end.x, cw2.end.y);
	}

	/**
	 * Finds point of intersect between two lines.In cord form.
	 *
	 * @param x1 the x1
	 * @param y1 the y1
	 * @param x2 the x2
	 * @param y2 the y2
	 * @param x3 the x3
	 * @param y3 the y3
	 * @param x4 the x4
	 * @param y4 the y4
	 * @return the point
	 * @throws ParallelLineException the parallel line exception
	 * @throws OutOfLineRangeException the out of line range exception
	 */
	public static Point lineIntersect(int x1, int y1, int x2, int y2, int x3,
			int y3, int x4, int y4) throws ParallelLineException, OutOfLineRangeException {
		
		double denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
		if (denom == 0.0) { // Lines are parallel.
			throw new ParallelLineException();
		}
		double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denom;
		double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denom;
		if (ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f) {
			// Get the intersection point.
			return new Point((int) (x1 + ua * (x2 - x1)), (int) (y1 + ua
					* (y2 - y1)));
		}

		throw new OutOfLineRangeException();
		
	}

}
