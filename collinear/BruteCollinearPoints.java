public class BruteCollinearPoints {
    private int count;
    private final LineSegment[] ls;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
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


        ls = new LineSegment[points.length * 2];

        for (int p = 0; p < points.length; p++) {
            for (int q = 0; q < points.length; q++) {
                for (int r = 0; r < points.length; r++) {
                    for (int s = 0; s < points.length; s++) {
                        // System.out.println(tempcount++ + ":" + points[p] + "," + points[q] + "," + points[r] + "," + points[s]);


                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r])
                                && points[p].slopeTo(points[r]) == points[p].slopeTo(points[s])) {
                            if (points[p].compareTo(points[q]) < 0
                                    && points[q].compareTo(points[r]) < 0
                                    && points[r].compareTo(points[s]) < 0) {
                                ls[count++] = new LineSegment(points[p], points[s]);

                            }
                        }
                    }
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
        LineSegment[] templs = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            templs[i] = ls[i];
        }
        return templs;
    }

    public static void main(String[] args) {

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
        points[9] = new Point(7, 5);

        BruteCollinearPoints bc = new BruteCollinearPoints(points);


    }


}
