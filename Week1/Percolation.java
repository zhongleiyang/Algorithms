public class Percolation 
{
    private boolean[] status;        //flase when blocked,true when open
    private int width;
    private WeightedQuickUnionUF uf;
  
    public Percolation(int N)              // create N-by-N grid, with all sites blocked
    {
        width = N;
        status = new boolean[N * N];
        for(int i= 0; i < N * N; i++)
            status[i] = false;
        uf = new WeightedQuickUnionUF(N * N);
    }
    public void open(int i, int j)         // open site (row i, column j) if it is not already
    {
        if(i < 0 || i > width - 1 || j < 0 || j > width - 1)
            throw new IndexOutOfBoundsException ();
        status[i * width + j] = true;
        if(i > 0 && isOpen(i - 1, j) == true)
            uf.union((i - 1) * width + j, i * width + j);  
        
        if(j > 0 && isOpen(i, j - 1) == true)
               uf.union(i * width + j - 1, i * width + j);         
    }
    public boolean isOpen(int i, int j)    // is site (row i, column j) open?
    {   
        if(i < 0 || i > width - 1 || j < 0 || j > width - 1)
            throw new IndexOutOfBoundsException ();
        return status[i * width + j] == true;
    }
    public boolean isFull(int i, int j)    // is site (row i, column j) full?
    {
        if(i < 0 || i > width - 1 || j < 0 || j > width - 1)
            throw new IndexOutOfBoundsException ();
        if(i == 0)
        {
            if(isOpen(i , j))
                return true;
        }
        else
        {
            for(int k = 0; k < width; k++)
                if(uf.connected(k, i * width + j))
                return true; 
        }        
        return  false;
    }
    public boolean percolates()            // does the system percolate?
    {
        for(int i = 0; i < width; i++)
            if(isFull(width - 1, i))
                return true;
        
        return false;
    }
    public static void main(String[] args)
    {  
        int N = Integer.parseInt(args[0]);              
        for(int p = 0; p < 100; p++)
        {
            int num = 0;
            for(int count = 0; count < 1000; count++)
            {               
                Percolation percolation = new Percolation(N);
                for(int i = 0; i < N; i++)
                    for(int j = 0; j < N; j++)
                {
                    if(StdRandom.uniform(0,100) < p)
                        percolation.open(i ,j);
                }
                
                if(percolation.percolates())
                    num++;         
            }
            StdOut.println(p + "  " + 1.0 * num / 1000);
        }  
    } 
}