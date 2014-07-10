import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by alexchoi1 on 7/8/14.
 */

public class Fast {
    public static void main(String[] args) {
        String filename = args[0];
        In input = new In(filename);

        int N = input.readInt();
        Point[] naturalOrderPoints = new Point[N];
        HashSet<Point> outputtedPoints = new HashSet<Point>();

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show();

        // read input, load points array, and draw each point
        for (int i = 0; i < N; i++) {
            int x = input.readInt();
            int y = input.readInt();

            Point point = new Point(x, y);
            naturalOrderPoints[i] = point;
            point.draw();
        }

        Arrays.sort(naturalOrderPoints);

        // loop through each naturally sorted point
        for (Point naturalPoint : naturalOrderPoints) {

            // clone natural order, then sort to slope order based on slope to first point
            Point[] slopeOrderPoints = naturalOrderPoints.clone();
            Arrays.sort(slopeOrderPoints, naturalPoint.SLOPE_ORDER);

            // create new segment
            LinkedList<Point> segment = new LinkedList<Point>();
            // add point i as origin
            segment.add(naturalPoint);

            // index 0 in slope order sorted is always the origin point (slope negative infinity)
            // this loop compares i to j and i to j+1. j only needs to loop through N-2
            for (int j = 1; j < N-1; j++) {

                Point slopePoint = slopeOrderPoints[j];
                Point nextSlopePoint = slopeOrderPoints[j+1];

                double slope = naturalPoint.slopeTo(slopePoint);
                double nextSlope = naturalPoint.slopeTo(nextSlopePoint);

                if (slope == nextSlope) {
                    if (segment.peekLast() != slopePoint) {
                        segment.add(slopePoint);
                    }
                    segment.add(nextSlopePoint);
                }

                // clear segment if no match (end of segment or loop)
                if (slope != nextSlope || j == N-2) {
                    // first output segment if it is large enough
                    if (segment.size() > 3) {
                        outputSegment(segment,outputtedPoints);
                    }
                    segment.clear();
                    segment.add(naturalPoint);
                }
            }
        }
    }

    private static void outputSegment(LinkedList<Point> segment, HashSet<Point> outputtedPoints) {
        //hashset addAll returns true if any unique objects were added
        boolean containsUnique = outputtedPoints.addAll(segment);

        if (containsUnique) {
            Point start = segment.peekFirst();
            Point end = segment.peekLast();
            start.drawTo(end);

            for (Point point : segment) {
                if (point != end) {
                    StdOut.print(point + " -> ");
                }
                else {
                    StdOut.print(point);
                    StdOut.println();
                }
            }
        }
    }
}
