    public class Percolation 
{
    private boolean[] status;        //flase when blocked,true when open
    private int width;
    private WeightedQuickUnionUF uf;
    private boolean[] isFullArray;  
    private boolean isFullChange;
    private boolean isPercolate;
    private int count;
    
    public Percolation(int N)              // create N-by-N grid, with all sites blocked
    {       
        width = N + 1;
        status = new boolean[width * width];
        isFullArray = new boolean[width * width];
        for(int i= 0; i < width * width; i++)
        {
            status[i] = false;
            isFullArray[i]  = false;
        }
        uf = new WeightedQuickUnionUF(width * width);
        isFullChange =false;
        isPercolate = false;
        count = 0;
    }
    
    
    public void open(int i, int j)         // open site (row i, column j) if it is not already
    {
        if(i < 1 || i > width - 1 || j < 1 || j > width - 1)
            throw new IndexOutOfBoundsException ();
        
        if(!isOpen(i,j))
        {         
            count++;
            status[i * width + j] = true;
            
            if(i == 1)
            {
                isFullArray[i * width + j] = true;
                isFullChange = true;
            }
            
            if(i > 1 && isOpen(i - 1, j) == true) 
            {         
                int parent1 = uf.find((i - 1) * width + j);
                int parent2 = uf.find(i * width + j); 
                if(parent1 != parent2) 
                {
                    if(isFullArray[parent1] || isFullArray[parent2])
                    {
                        isFullArray[parent1] = true;
                        isFullArray[parent2] = true;
                        isFullArray[i * width + j] = true;
                        isFullChange = true;
                    }
                    uf.union((i - 1) * width + j, i * width + j); 
                }
            }
            
            if(j > 1 && isOpen(i, j - 1) == true)
            {                
                int parent1 = uf.find(i * width + j - 1);
                int parent2 = uf.find(i * width + j); 
                if(parent1 != parent2) 
                {
                    if(isFullArray[parent1] || isFullArray[parent2])
                    {
                        isFullArray[parent1] = true;
                        isFullArray[parent2] = true;
                        isFullArray[i * width + j] = true;
                        isFullChange = true;
                    }
                    uf.union(i * width + j - 1, i * width + j); 
                }
            }
            
            if(i < width - 1 && isOpen(i + 1, j) == true)
            {                         
                int parent1 = uf.find((i + 1) * width + j);
                int parent2 = uf.find(i * width + j); 
                if(parent1 != parent2) 
                {
                    if(isFullArray[parent1] || isFullArray[parent2])
                    {
                        isFullArray[parent1] = true;
                        isFullArray[parent2] = true;
                        isFullArray[i * width + j] = true;
                        isFullChange = true;
                    }
                    uf.union((i + 1) * width + j, i * width + j);  
                }
            }
            
            if(j < width - 1 && isOpen(i, j + 1) == true)
            {                
                int parent1 = uf.find(i * width + j + 1);
                int parent2 = uf.find(i * width + j); 
                if(parent1 != parent2) 
                {
                    if(isFullArray[parent1] || isFullArray[parent2])
                    {
                        isFullArray[parent1] = true;
                        isFullArray[parent2] = true;
                        isFullArray[i * width + j] = true;
                        isFullChange = true;
                    }
                    uf.union(i * width + j + 1, i * width + j); 
                }
            }            
        }       
    }
    public boolean isOpen(int i, int j)    // is site (row i, column j) open?
    {   
        if(i < 1 || i > width - 1 || j < 1 || j > width - 1)
            throw new IndexOutOfBoundsException ();
        return status[i * width + j] == true;
    }
    public boolean isFull(int i, int j)    // is site (row i, column j) full?
    {
        if(i < 1 || i > width - 1 || j < 1 || j > width - 1)
            throw new IndexOutOfBoundsException ();
        if(!isOpen(i, j))
            return false;
        
        if(isFullArray[i * width + j])
            return true;
        
        if(isIsland(i, j))
            return false;
        
        return isFullArray[uf.find(i * width + j)];      
    }
    
    private boolean isIsland(int i, int j)
    {  
        if(!isOpen(i, j))
            return true;
        
        if(i > 1 && isOpen(i - 1, j))
            return false;
        
        if(j > 1 && isOpen(i, j - 1))
            return false;
        
        if(i < width - 1 && isOpen(i + 1, j))
            return false;
        
        if(j < width - 1 && isOpen(i, j + 1))
            return false;
        
        return true;
    }
    
    public boolean percolates()            // does the system percolate?
    {
        if(isPercolate)
            return true;
            
        if(count >= width - 1&& isFullChange)
        {
            isFullChange = false;
            boolean perStatu = false;
            for(int i = 1; i < width; i++)
            {
                if(isOpen(width - 1, i) != perStatu)
                {
                    if(isOpen(width - 1, i) == true)
                    {
                        perStatu = true;
                        if(isFull(width - 1, i))
                        {
                            isPercolate = true;
                            return true;
                        }
                    }
                    else
                    {
                        perStatu  = false;
                    }
                }
            }
        }
                
        return false;
    }
}