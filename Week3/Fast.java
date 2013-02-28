import java.util.Arrays;

public class Fast {
   public static void main(String[] args)
   {
        String fileName = args[0];
        In fileIn = new In(fileName);
        int num = fileIn.readInt();
        Point[] points = new Point[num];
        Point[] copyPoints = new Point[num];
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for(int i = 0; i < num; i++)
        {
              points[i] = new Point(fileIn.readInt(), fileIn.readInt()); 
              copyPoints[i] = points[i];
              points[i].draw();
        }
        double[] slopes = new double[num];
        Point[] haveOutStartPoints = new Point[num];
        double[] haveOutSlopes = new double[num];
        int count = 0;
        
        for(int i = 0; i < num; i++)
        {
            for(int k = 0; k < num; k++)
            {
                copyPoints[k] = points[k];
            }
            Arrays.sort(copyPoints, points[i].SLOPE_ORDER);
            
            for(int j = 0; j < num; j++)
            {
                slopes[j] = points[i].slopeTo(copyPoints[j]);
            }
            
             int first = i + 1;
             int last = i + 1;          
             while(first < num - 2)
             {
                 while(last < num && (Double.compare(slopes[first],slopes[last]) == 0))
                     last++;
                 if(last - first > 2)
                 {
                     Point[] sortPoints = new Point[last - first + 1];
                     sortPoints[0] = points[i];
                     for(int t = 1,k = first; k < last; k++)
                     {
                         sortPoints[t++]  = copyPoints[k];
                     }
                     Arrays.sort(sortPoints); 
                     int z;
                     for(z = 0; z < count; z++)
                     {
                          if(haveOutStartPoints[z] == sortPoints[0] 
                                 && (Double.compare(haveOutSlopes[z], slopes[first]) == 0))
                              break;
                     }
                     if(z == count)
                     {
                         haveOutStartPoints[count] = sortPoints[0];
                         haveOutSlopes[count] = slopes[first];
                         count++;
                         StdOut.print(sortPoints[0].toString());
                         Point perPoint = sortPoints[0];
                         sortPoints[0].drawTo(sortPoints[last - first]);
                         for(int k = 1; k < last - first + 1; k++)
                         {
                             StdOut.print(" -> " + sortPoints[k].toString());
                         }
                         StdOut.println();
                     }
                 }
                 first = last;
             }                      
        }
   }
}