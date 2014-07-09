import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by alexchoi1 on 7/8/14.
 */

public class Fast {
    public static void main(String[] args) {
        new Fast(args);
    }

    private void outputSegment(Point[] pointArray, int originIndex, int startIndex,  int segmentLength) {
        //StdOut.println("print segment!");
        //StdOut.println("origin" + pointArray[originIndex]);

        Point[] outputArray = new Point[segmentLength];
        outputArray[0] = pointArray[originIndex];

        int outputArrayCounter = 1;

        for (int i = startIndex; i < startIndex + segmentLength - 1; i++) {
          //  StdOut.println(pointArray[i] + " - >");
          //  StdOut.println("slopeTo" + pointArray[originIndex].slopeTo(pointArray[i]));
            outputArray[outputArrayCounter] = pointArray[i];
            outputArrayCounter++;
        }

        Arrays.sort(outputArray);

        for (Point point : outputArray) {
            StdOut.print(point + " -> ");
        }
        outputArray[0].drawTo(outputArray[segmentLength-1]);

        //pointArray[originIndex].drawTo(pointArray[startIndex + segmentLength - 2]);
    }
        private Fast(String[] args) {
            String filename = args[0];
            In input = new In(filename);

            int N = input.readInt();
            Point[] pointsArray = new Point[N];

            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            StdDraw.show();

            // read input, load points array, and draw each point
            for (int i = 0; i < N; i++) {
                int x = input.readInt();
                int y = input.readInt();

                Point point = new Point(x, y);
                Point point2 = new Point(x, y);

                pointsArray[i] = point;

                point.draw();
            }

            Arrays.sort(pointsArray);

            // for each point p, find slope against each other point
            // and sort those points to find segments
            // actually dont need to iterate through last few... min segment length is 4
            for (int i = 0; i < N - 1; i++) {

                Point point = pointsArray[i];
                //StdOut.println("SLOPE ORDER SORT FOR i" + point);
                Arrays.sort(pointsArray, i + 1, N, point.SLOPE_ORDER);
                for (int s = i; s < N; s ++) {
                    //StdOut.println(pointsArray[s]);
                }

                // look for 3 or more adjacent points
                // with same slope - those will be collinear with p
                int segmentLength = 2;
                // negative zero is not used anywhere else, so it is a safe init value
                double previousSlopeTo = -0.0;

                for (int j = i + 1; j < N; j++) {
                    Point otherPoint = pointsArray[j];
                   // StdOut.println("segmentLength: " + segmentLength + " slope to: " + point.slopeTo(otherPoint)
                    //        + " point: " + point + " otherpoint: " + otherPoint);

                    // matching slope: add to adjacent count
                    if (point.slopeTo(otherPoint) == previousSlopeTo) {
                        segmentLength ++;
                        //StdOut.println("Slope match found. Increment segment length to: " + segmentLength);

                        // not end of loop. print segment if next point is not collinear.
                        if (j != N - 1) {
                            //StdOut.println("not end of loop. analyze next point");
                            Point nextPoint = pointsArray[j+1];
                            if (point.slopeTo(nextPoint) != previousSlopeTo && segmentLength > 3) {
                                //StdOut.println("next point slop not match. segment length > 3. OUTPUT");
                                outputSegment(pointsArray,i,j+2-segmentLength,segmentLength);
                                segmentLength = 2;
                            }
                        }
                        // end of loop. print if segment long enough
                        else if (segmentLength > 3) {
                            //StdOut.println("end of loop. segmentLength>3. OUTPUT");
                            outputSegment(pointsArray,i,j+2-segmentLength,segmentLength);
                        }
                    }
                    // not matching slope. go on
                    else {
                       // StdOut.println("no match. proceed");
                        segmentLength = 2;
                    }
                    previousSlopeTo = point.slopeTo(otherPoint);
                }

            }


        }






}
