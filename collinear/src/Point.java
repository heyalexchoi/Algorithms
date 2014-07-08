/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;


public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER;       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    // Treat the slope of a horizontal line segment as positive zero
    public double slopeTo(Point that) {
        double deltaY = that.y - this.y;
        double deltaX = that.x - this.x;

        // treat the slope of a degenerate line segment
        // (between a point and itself) as negative infinity.
        if (deltaX == 0 && deltaY == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        // treat the slope of a vertical line segment as positive infinity;
        else if (deltaX == 0) {
            return Double.POSITIVE_INFINITY;
        }

        return deltaY/deltaX;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that.y > this.y) {
            return -1;
        }
        else if (this.y > that.y) {
            return 1;
        }
        else if (that.x > this.x) {
            return -1;
        }
        else if (this.x > that.x) {
            return 1;
        }
        else {
            return 0;
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}