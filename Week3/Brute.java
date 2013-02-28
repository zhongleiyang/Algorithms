import java.util.Arrays;
public class Brute 
{
   public static void main(String[] args)
   {
        String fileName = args[0];
        In fileIn = new In(fileName);
        int num = fileIn.readInt();
        Point[] points = new Point[num];
        Point[] sortPoints = new Point[4];
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for(int i = 0; i < num; i++)
        {
              points[i] = new Point(fileIn.readInt(), fileIn.readInt());  
              points[i].draw();
        }
        
        for(int i = 0; i < num; i++)
            for(int j = i + 1; j < num; j++)
                for(int k = j + 1; k < num; k++)
                    for(int l = k + 1; l < num; l++)
                    {
                         if(points[i].slopeTo(points[j]) == points[j].slopeTo(points[k]) 
                                && points[j].slopeTo(points[k]) == points[k].slopeTo(points[l]))
                         {
                             sortPoints[0] = points[i];
                             sortPoints[1] = points[j];
                             sortPoints[2] = points[k];
                             sortPoints[3] = points[l];
                             Arrays.sort(sortPoints);             
                             StdOut.println(sortPoints[0].toString() + " -> "
                                           + sortPoints[1].toString() + " -> "
                                           + sortPoints[2].toString() + " -> "
                                           + sortPoints[3].toString());
                             sortPoints[0].drawTo(sortPoints[3]);
                         }           
                    }
        
   }
}