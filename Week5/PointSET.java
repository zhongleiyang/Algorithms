public class PointSET 
{
   private static final double MAX = 10.0;
   private SET<Point2D> set;
   public PointSET()                               // construct an empty set of points
   {
       set = new SET<Point2D>();
   }
   public boolean isEmpty()                        // is the set empty?
   {
       return set.isEmpty();
   }
   public int size()                               // number of points in the set
   {
       return set.size();
   }
   public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
   {
       set.add(p);
   }
   public boolean contains(Point2D p)              // does the set contain the point p?
   {
       return set.contains(p);
   }
   public void draw()                              // draw all of the points to standard draw
   {
       for(Point2D p : set)
           p.draw();
   }
   public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
   {
       Stack<Point2D> points = new Stack<Point2D>();
       for(Point2D p : set)
       {
           if(rect.contains(p))
               points.push(p);
       }    
       return points;
   }
   public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
   {
       double minLength = MAX;
       Point2D nearestPoint = null;
       for(Point2D q : set)
       {
           if(q.distanceSquaredTo(p) < minLength)
           {
               minLength = q.distanceSquaredTo(p);
               nearestPoint = q;
           }
       }
       return nearestPoint;
   }
}