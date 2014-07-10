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
                        outputSegment(segment);
                    }
                    segment.clear();
                    segment.add(naturalPoint);
                }
            }
        }
    }

    private static void outputSegment(LinkedList<Point> segment) {
        // to remove sub-segments, we rely on the following logic:
        // the outer loop's array is sorted via natural order
        // the inner loop is sorted in slope order according
        // to the current number in the outer loop
        // a discovered segment should always start at
        // its naturally lowest point
        // in the case of a sub-segment, the outer loop will
        // start the segment at somewhere other than its lowest
        // in this case, we can discover this by comparing
        // whether the first point of the segment is in fact the lowest

        Point first = segment.removeFirst();
        Point second = segment.removeFirst();
        Point last = segment.removeLast();
        if (first.compareTo(second) < 0) {

            first.drawTo(last);
            StdOut.print(first + " -> " + second + " -> ");

            for (Point point : segment) {
                StdOut.print(point + " -> ");
            }

            StdOut.print(last);
            StdOut.println();
        }
    }
}
