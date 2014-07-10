import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by alexchoi1 on 7/8/14.
 */

public class Fast {
    public static void main(String[] args) {
        new Fast(args);
    }

    private void outputSegment(LinkedList<Point> segment, HashSet<LinkedList<Point>> duplicates) {

        boolean isUnique = !duplicates.contains(segment);



        if (!isUnique) {
            StdOut.println("duplicate segment:");
            for (Point point : segment) {
                StdOut.println(point);
            }
            return;
        }



        for (LinkedList<Point> duplicateSegment : duplicates) {
                if (segment.containsAll(duplicateSegment)) {
                    StdOut.println("duplicate subsegment:");
                    for (Point point : duplicateSegment) {
                        StdOut.println(point);
                    }
                    StdOut.println("containing segment:");
                    for (Point point : duplicateSegment) {
                        StdOut.println(point);
                    }
                    return;
                }
        }

        if (isUnique) {
            StdOut.println("unique segment:");
            for (Point point : segment) {
                StdOut.println(point);
            }
            duplicates.add(segment);
            return;
        }



    }
        private Fast(String[] args) {
            String filename = args[0];
            In input = new In(filename);

            int N = input.readInt();
            Point[] naturalSortPoints = new Point[N];
            HashSet<LinkedList<Point>> duplicates = new HashSet<LinkedList<Point>>();

            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            StdDraw.show();

            // read input, load points array, and draw each point
            for (int i = 0; i < N; i++) {
                int x = input.readInt();
                int y = input.readInt();

                Point point = new Point(x, y);
                naturalSortPoints[i] = point;
                point.draw();
            }

            Arrays.sort(naturalSortPoints);

            // loop through each naturally sorted point
            for (int i = 0; i < N - 1; i++) {

                Point point = naturalSortPoints[i];

                Point[] slopeSortPoints = naturalSortPoints.clone();
                Arrays.sort(slopeSortPoints, i + 1, N, point.SLOPE_ORDER);

                LinkedList<Point> segment = new LinkedList<Point>();
                segment.add(point);

                // negative zero is not used anywhere else, so it is a safe init value
                double previousSlopeTo = -0.0;

                for (int j = i + 1; j < N; j++) {

                    Point otherPoint = slopeSortPoints[j]; // i in both arrays should be same

                    if (point.slopeTo(otherPoint) == previousSlopeTo) {

                        Point previousPoint = slopeSortPoints[j - 1];
                        if (segment.peekLast() != previousPoint) {
                            segment.add(previousPoint);
                        }
                        segment.add(otherPoint);
                    }

                    // segment is sufficient size
                    if (segment.size() > 3
                            // and either is last iteration or next point's slope doesn't match (last point in segment)
                            && (j == N-1 || point.slopeTo(slopeSortPoints[j+1]) != point.slopeTo(otherPoint))) {
                        outputSegment(segment,duplicates);
                    }
                    previousSlopeTo = point.slopeTo(otherPoint);
                }
            }
        }
}
