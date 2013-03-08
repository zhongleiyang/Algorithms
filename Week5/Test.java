class Test
{
       
     public static void main(String[] args)
   {
        String fileName = args[0];
        In in = new In(fileName);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }
        
        Point2D p1 = kdtree.nearest(new Point2D(0.4, 0.3));
        Point2D p2 = brute.nearest(new Point2D(0.4, 0.3));
        StdOut.println(p1.x() + "  " + p1.y());
        StdOut.println(p2.x() + "  " + p2.y());
         

        

    }
}
    