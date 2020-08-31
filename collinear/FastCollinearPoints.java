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
                if (i != j && points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }

            }
        }

        ls = new LineSegment[(int) Math.pow(points.length, 2)];
        count = 0;
        // Loop through all points, every time the first point is the origin point P
        for (int j = 0; j < points.length; j++) {
            // firstElementP is the first element of the array which is the origin Point p
            Point firstElementP = points[j];
            // Make a copy of the array so that the array we use is always unsorted
            Point[] pointsToSort = new Point[points.length];
            System.arraycopy(points, 0, pointsToSort, 0, points.length);

            // sort the temporary array base on the slope order relevant to p
            Arrays.sort(pointsToSort, firstElementP.slopeOrder());

            // Loop through the array to find adjacent points whiches slop are equal with p
            int keepTrack = 1;
            for (int i = 2; i < points.length; i++) {
                // when the next element is equal to previous one but not yet reached 3 or more adjacent points.
                if (pointsToSort[i].slopeTo(firstElementP) == pointsToSort[i - 1].slopeTo(firstElementP) && keepTrack < 3) {

                    keepTrack++;
                    // CORNER CASE when it is the last element and adjacent points are just increased to 3.
                    if (i == points.length - 1 && keepTrack == 3) {
                        // Sub-sort the array on natural order starting from the first adjacent element until the last adjecent element.
                        Arrays.sort(pointsToSort, i - (keepTrack - 1), i + 1);
                        //check if p is smaller than the first (now smallest) adjecent sequence
                        // if it is than add LineSegment(p,last element of adjacent sequence)
                        // To avoid duplicate entries
                        if (firstElementP.compareTo(pointsToSort[i - (keepTrack - 1)]) < 0) {
                            ls[count++] = new LineSegment(firstElementP, pointsToSort[i]);
                        }


                    }

                    // Next element is equal and we are at 3 or more adjacent elements
                } else if (pointsToSort[i].slopeTo(firstElementP) == pointsToSort[i - 1].slopeTo(firstElementP) && keepTrack >= 3) {
                    keepTrack++;
                    // CORNER CASE when all of the entries are adjacent elements
                    if (i == points.length - 1) {
                        Arrays.sort(pointsToSort, i - (keepTrack - 1), i + 1);
                        if (firstElementP.compareTo(pointsToSort[i - (keepTrack - 1)]) < 0) {
                            ls[count++] = new LineSegment(firstElementP, pointsToSort[i]);
                        }


                    }

                    // When next element is not equal and we dont have 3 adjacent points
                } else if (pointsToSort[i].slopeTo(firstElementP) != pointsToSort[i - 1].slopeTo(firstElementP) && keepTrack < 3) {
                    keepTrack = 1;
                    // when next element is not equal and we have reach 3 or more adjacent points
                } else {
                    Arrays.sort(pointsToSort, i - (keepTrack), i);
                    if (firstElementP.compareTo(pointsToSort[i - (keepTrack)]) < 0) {
                        ls[count++] = new LineSegment(firstElementP, pointsToSort[i - 1]);
                    }
                    keepTrack = 1;


                }

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
