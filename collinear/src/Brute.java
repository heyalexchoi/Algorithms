/**
 * Created by alexchoi1 on 7/7/14.
 *
 * Write a program Brute.java that examines 4 points at a time
 * and checks whether they all lie on the same line segment,
 * printing out any such line segments to standard output
 * and drawing them using standard drawing.
 * To check whether the 4 points p, q, r, and s are collinear,
 * check whether the slopes between p and q,
 * between p and r, and between p and s are all equal.
 *
 * Each program should take the name of an input file
 * as a command-line argument, read the input file
 * (in the format specified below), print to standard output
 * the line segments discovered (in the format specified below),
 * and draw to standard draw the line segments discovered
 * (in the format specified below).
 *
 * Read the points from an input file in the following format:
 * An integer N, followed by N pairs of integers (x, y),
 * each between 0 and 32,767.
 *
 * Print to standard output the line segments that your program
 * discovers, one per line. Print each line segment as an ordered
 * sequence of its constituent points, separated by " -> ".
 *
 * Also, draw the points using draw() and draw the line segments
 * using drawTo(). Your programs should call draw() once for each
 * point in the input file and it should call drawTo() once for
 * each line segment discovered. Before drawing, use
 * StdDraw.setXscale(0, 32768) and StdDraw.setYscale(0, 32768)
 * to rescale the coordinate system.
 */

import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {

        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] pointsArray = new Point[N];

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show();

        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point point = new Point(x, y);
            pointsArray[i] = point;
            point.draw();
        }

        Arrays.sort(pointsArray);

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    for (int l =  k + 1; l < N; l++) {

                        Point point1 = pointsArray[i];
                        Point point2 = pointsArray[j];
                        Point point3 = pointsArray[k];
                        Point point4 = pointsArray[l];

                        if (point1.slopeTo(point2) == point2.slopeTo(point3)
                                && point2.slopeTo(point3) == point3.slopeTo(point4)
                                && point1.compareTo(point2) < 1
                                && point2.compareTo(point3) < 1
                                && point3.compareTo(point4) < 1) {

                            StdOut.println(point1.toString()
                                    + " -> " + point2.toString()
                                    + " -> " + point3.toString()
                                    + " -> " + point4.toString());

                            point1.drawTo(point4);
                        }
                    }
                }
            }
        }
    }
}
