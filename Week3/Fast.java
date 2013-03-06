import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class Fast {
   public static void main(String[] args)
   {
        String fileName = args[0];
        In fileIn = new In(fileName);
        int num = fileIn.readInt();
        Point[] points = new Point[num];
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        boolean[][] connectedArray = new boolean[num][num];
        for(int i = 0; i < num; i++)
        {
              points[i] = new Point(fileIn.readInt(), fileIn.readInt()); 
              points[i].draw();
        }
        
        for(int i = 0; i < num; i++)
            for(int j = 0; j < num; j++)
            {
                 connectedArray[i][j] = false;
                 if(i == j)
                     connectedArray[i][j] = true;
            }
        
        double[] slopes = new double[num];
        int count = 0;
        
        for(int i = 0; i < num - 3; i++)
        {
            ArrayList<Point> list = new ArrayList<Point>();
            
            for(int j = 0 ; j < num; j++)
                if(connectedArray[i][j] == false)
                      list.add(points[j]);
            
            Collections.sort(list, points[i].SLOPE_ORDER);

            for(int j = 0; j < list.size(); j++)
            {
                slopes[j] = points[i].slopeTo(list.get(j));
            }
            int first = 0,last;
            last = first;   
             while(first < list.size() - 2)
             {
                 while(last < list.size() && (Double.compare(slopes[first],slopes[last]) == 0))
                     last++;
                 if(last - first > 2)
                 {
                     Point[] sortPoints = new Point[last - first + 1];
                     int[] pointIndexs = new int[last - first + 1];
                     sortPoints[0] = points[i];
                     pointIndexs[0] = i;
                     for(int t = 1, z = 1,k = first; k < last; k++)
                     {
                         Point point = list.get(k);
                         sortPoints[t++]  = point;
                         int j;
                         for(j = 0; j < num; j++)
                             if(point == points[j])
                                  break;
                         pointIndexs[z++] = j;                        
                     }
                     
                     for(int x = 0; x < last - first + 1; x++)
                         for(int y = 0; y < last - first + 1; y++)
                         {
                              connectedArray[pointIndexs[x]][pointIndexs[y]] = true;
                         }
                     
                     
                     Arrays.sort(sortPoints); 
                     StdOut.print(sortPoints[0].toString());
                     Point perPoint = sortPoints[0];
                     sortPoints[0].drawTo(sortPoints[last - first]);
                     for(int k = 1; k < last - first + 1; k++)
                     {
                         StdOut.print(" -> " + sortPoints[k].toString());
                     }
                     StdOut.println();
                     
                 }
                 first = last;
            }                     
        }
   }
}
