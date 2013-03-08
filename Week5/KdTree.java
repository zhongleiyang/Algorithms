


public class KdTree {
    
    private static final boolean XAXIS = true;
    private static final boolean YAXIS = false;
    private static final double MAX = 10.0;
    
    private class Node
    {
        private Point2D point;
        private boolean axis;     
        private Node left, right; 
        private int N;             // number of nodes in subtree
        public Node(Point2D point,boolean axis, int N) 
        {
            this.point = point;
            this.axis = axis;
            this.N = N;
        }
    }
    
   private Node root;             // root of KdTree
   public KdTree()                               // construct an empty set of points
   {
       
   }
   public boolean isEmpty()                        // is the set empty?
   {
       return size() == 0;
   }
   public int size()                               // number of points in the set
   {
       return size(root);
   }
   
   private int size(Node x) 
   {
       if (x == null) return 0;
       else return x.N;
   }
       
   public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
   {
       if(p == null)
           return;
       root = insert(root, p);
       
   }
   
   private Node insert(Node node, Point2D point) 
   {
       if(node == null)
           return new Node(point, XAXIS, 1);
       
        int cmp = compare(point, node.point, node.axis);
        if      (cmp < 0) node.left  = insert(node.left, point); 
        else if (cmp > 0) node.right = insert(node.right, point); 
        else           return node;
       
        if(node.left != null) 
           node.left.axis = !node.axis;
               
        if(node.right != null) 
           node.right.axis = !node.axis;
        
         node.N = size(node.left) + size(node.right) + 1;
        
        return node;
   }
   
   private int compare(Point2D p1, Point2D p2, boolean axis)
   {
       if(axis == XAXIS)
       {
           int cmp = Point2D.X_ORDER.compare(p1, p2);
           if(cmp != 0)
               return cmp;
           
           return Point2D.Y_ORDER.compare(p1, p2);       
       }
       else
       {
           int cmp = Point2D.Y_ORDER.compare(p1, p2);
           if(cmp != 0)
               return cmp;
           return Point2D.X_ORDER.compare(p1, p2);  
       }
   }
   
   
   public boolean contains(Point2D p)              // does the set contain the point p?
   {
       return contains(root, p);
   }
   
       // value associated with the given key in subtree rooted at x; null if no such key
    private boolean contains(Node node, Point2D p) {
        while (node != null) {
            int cmp = compare(p, node.point, node.axis);
            if      (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else              return true;
        }
        return false;
    }
     
   public void draw()                              // draw all of the points to standard draw
   {
       draw(root);
   }
   
   private void draw(Node node)                          
   {
       if(node == null)
           return;
       node.point.draw();
       draw(node.left);    
       draw(node.right);    
   }
  
   public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
   {
       return range(root, rect);
   }
   
   
   private Iterable<Point2D> range(Node node, RectHV rect)    
   {
       if(node == null)
           return null;

       Stack<Point2D> result = new  Stack<Point2D>();
       Iterable<Point2D> leftTreePoints = null;
       Iterable<Point2D> rightTreePoints = null;
       
       if(rect.contains(node.point))
           result.push(node.point);

       if(node.axis == XAXIS)
       {
           if(node.point.x() >=  rect.xmin() && node.point.x() <= rect.xmax())
           {
              leftTreePoints = range(node.left, rect);
              rightTreePoints = range(node.right, rect);
           }
           else if(node.point.x() > rect.xmax())
           {
               leftTreePoints = range(node.left, rect);
           }
           else
           {
               rightTreePoints = range(node.right, rect);
           }
       }
       else
       {
           if(node.point.y() >=  rect.ymin() && node.point.y() <= rect.ymax())
           {
              leftTreePoints = range(node.left, rect);
              rightTreePoints = range(node.right, rect);
           }
           else if(node.point.y() > rect.ymax())
           {
               leftTreePoints = range(node.left, rect);
           }
           else
           {
               rightTreePoints = range(node.right, rect);
           }
       }
       
       if(leftTreePoints != null)
       {
           for(Point2D point: leftTreePoints)
               result.push(point);
       }
       
       if(rightTreePoints != null)
       {
           for(Point2D point: rightTreePoints)
               result.push(point);
       }
       
       if(result.size() != 0)
           return result;
       
       return null;         
   }
   
   public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
   {
       if(root == null)
           return null;
       
       return nearest(root, p, root.point);
   }
   
   private Point2D nearest(Node node, Point2D p, Point2D currentNearestPoint, RectHV parentRect)        
   {
       if(node == null)
           return null;
       
       Point2D point;
       double currentNearestLength = p.distanceTo(currentNearestPoint);
       double lenth = node.point.distanceTo(p);
       if(lenth <  currentNearestLength)
       {
           currentNearestLength = lenth;
           currentNearestPoint = node.point;
       }
       double axisLength;  
       
       if(node.axis == XAXIS)
           axisLength = p.x() - node.point.x();
       else
           axisLength = p.y() - node.point.y();
       
       Node first,second;
       if(axisLength < 0)
       {
           first = node.left;
           second = node.right;
       }
       else
       {
           first = node.right;
           second = node.left;
       }
           
       point = nearest(first, p, currentNearestPoint);
       if(point != null)
       {
           lenth = point.distanceTo(p);
           if(lenth <  currentNearestLength)
           {
               currentNearestLength = lenth;
               currentNearestPoint = point;
           }
       }
       
       if(currentNearestLength > Math.abs(axisLength))
       {
           point = nearest(second, p, currentNearestPoint);
           if(point != null)
           {
               lenth = point.distanceTo(p);
               if(lenth <  currentNearestLength)
               {
                   currentNearestLength = lenth;
                   currentNearestPoint = point;
               }
           }
       }
       
       return currentNearestPoint;  
   }

}