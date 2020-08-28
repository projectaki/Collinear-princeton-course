import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private final LineSegment[] ls;
    private int count;

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (i != j && points[i] == points[j]) {
                    throw new IllegalArgumentException();
                }

            }
        }

        ls = new LineSegment[(int) Math.pow(points.length, 2)];
        count = 0;

        for (int j = 0; j < points.length; j++) {
            Point firstElementP = points[j];
            Point[] pointsToSort = new Point[points.length];
            for (int k = 0; k < points.length; k++) {
                pointsToSort[k] = points[k];
            }

            // System.out.println(Arrays.toString(pointsToSort));
            Arrays.sort(pointsToSort, firstElementP.slopeOrder());
            // System.out.println("Original sorted array:" + Arrays.toString(pointsToSort));

            int keepTrack = 1;
            for (int i = 2; i < points.length; i++) {
                // System.out.println(firstElementP + " " + pointsToSort[i]);


                if (pointsToSort[i].slopeTo(firstElementP) == pointsToSort[i - 1].slopeTo(firstElementP) && keepTrack < 3) {

                    keepTrack++;
                    // System.out.println("= & c<3");
                    if (i == points.length - 1 && keepTrack == 3) {
                        // System.out.println("before subSort:" + pointsToSort[i - (keepTrack - 1)] + " " + pointsToSort[i]);

                        Arrays.sort(pointsToSort, i - (keepTrack - 1), i + 1);
                        // System.out.println("SubSorted array: " + Arrays.toString(pointsToSort));
                        // System.out.println("Before last if:" + pointsToSort[i - (keepTrack - 1)] + " " + pointsToSort[i]);

                        if (firstElementP.compareTo(pointsToSort[i - (keepTrack - 1)]) < 0) {
                            ls[count++] = new LineSegment(firstElementP, pointsToSort[i]);
                            // System.out.println("-----NEW LINE SEGGY:" + firstElementP + ", " + pointsToSort[i]);
                        }


                    }


                } else if (pointsToSort[i].slopeTo(firstElementP) == pointsToSort[i - 1].slopeTo(firstElementP) && keepTrack >= 3) {

                    keepTrack++;
                    // System.out.println("= & c>=3");
                    if (i == points.length - 1) {
                        // System.out.println("before subSort:" + pointsToSort[i - (keepTrack - 1)] + " " + pointsToSort[i]);

                        Arrays.sort(pointsToSort, i - (keepTrack - 1), i + 1);
                        // System.out.println("SubSorted array: " + Arrays.toString(pointsToSort));
                        // System.out.println("Before last if:" + pointsToSort[i - (keepTrack - 1)] + " " + pointsToSort[i]);

                        if (firstElementP.compareTo(pointsToSort[i - (keepTrack - 1)]) < 0) {
                            ls[count++] = new LineSegment(firstElementP, pointsToSort[i]);
                            // System.out.println("-----NEW LINE SEGGY:" + firstElementP + ", " + pointsToSort[i]);
                        }


                    }


                } else if (pointsToSort[i].slopeTo(firstElementP) != pointsToSort[i - 1].slopeTo(firstElementP) && keepTrack < 3) {
                    keepTrack = 1;
                    // System.out.println("!= & c<3");

                } else {
                    // System.out.println("!= & c>=3");

                    // System.out.println("before subSort:" + pointsToSort[i - (keepTrack)] + " " + pointsToSort[i]);

                    Arrays.sort(pointsToSort, i - (keepTrack), i);
                    // System.out.println("SubSorted array: " + Arrays.toString(pointsToSort));
                    // System.out.println("Before last if:" + pointsToSort[i - (keepTrack)] + " " + pointsToSort[i - 1]);


                    if (firstElementP.compareTo(pointsToSort[i - (keepTrack)]) < 0) {
                        ls[count++] = new LineSegment(firstElementP, pointsToSort[i - 1]);
                        // System.out.println("-----NEW LINE SEGGY:" + firstElementP + ", " + pointsToSort[i - 1]);
                    }

                    keepTrack = 1;


                }

                // System.out.println("KEEPTRACK: " + keepTrack);


            }
        }


    }

    public int numberOfSegments()        // the number of line segments
    {
        return count;
    }

    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] tempLs = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            tempLs[i] = ls[i];
        }
        return tempLs;
    }

    public static void main(String[] args) {
/*
        Point[] points = new Point[10];
        points[0] = new Point(1, 1);
        points[1] = new Point(2, 1);
        points[2] = new Point(3, 1);
        points[3] = new Point(4, 1);
        points[4] = new Point(1, 2);
        points[5] = new Point(1, 3);
        points[6] = new Point(1, 4);
        points[7] = new Point(4, 3);
        points[8] = new Point(7, 5);
        points[9] = new Point(2, 7);

        FastCollinearPoints fc = new FastCollinearPoints(points);
        System.out.println(Arrays.toString(fc.segments()));


 */


        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();


    }


}
