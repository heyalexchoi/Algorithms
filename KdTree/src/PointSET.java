/**
 * Created by alexchoi1 on 7/22/14.
 */

public class PointSET {
    private SET<Point2D> set;
    // construct an empty set of points
    public PointSET() {
        set = new SET<Point2D>();
    }
    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }
    // number of points in the set
    public int size() {
        return set.size();
    }
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (!set.contains(p)) {
            set.add(p);
        }
    }
    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }
    // draw all of the points to standard draw
    public void draw() {
        for (Point2D point : set) {
            point.draw();
        }
    }
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> stack = new Stack<Point2D>();
        for (Point2D point : set) {
            if (rect.contains(point)) {
                stack.push(point);
            }
        }
        return stack;
    }
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (set.isEmpty()) return null;
        Point2D nearestPoint = set.min();
        double nearestDistance = p.distanceTo(nearestPoint);
        for (Point2D point : set) {
            if (p.distanceTo(point) < nearestDistance) {
                nearestDistance = p.distanceTo(point);
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }
/*
    public static void main(String args[]) {
        PointSET pointSet = new PointSET();
        String fileName = args[0];
        In in = new In(fileName);
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D point = new Point2D(x,y);
            pointSet.insert(point);
            StdOut.println("point:" + point);
            StdOut.println("nearest: " + pointSet.nearest(point));
        }
        pointSet.draw();
    }
    */
}
