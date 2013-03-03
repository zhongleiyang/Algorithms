public class Board 
{
    private int[][] interBlocks
    private int dim;
    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
    {
        interBlocks = blocks
        dim = interBlocks[0].length;
    }
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension N
    {
        return dim;
    }
    
    public int hamming()                   // number of blocks out of place
    {
        int count = 0;
        for(int i = 0; i < dim; i++)
            for(int j = 0; j < dim; j++)
            {
                if(interBlocks[i][j] != i * dim + j && interBlocks[i][j] != 0)
                    count++; 
            }
        return count;
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int sum = 0;
        int x,y;
        for(int i = 0; i < dim; i++)
            for(int j = 0; j < dim; j++)
            {
                if(interBlocks[i][j] != 0)
                {
                    x = interBlocks[i][j] / dim;
                    y = interBlocks[i][j] % dim;
                    sum += Math.abs(x - i) + Math.abs(y - j);
                }
            }
        
        return sum;
    }
    
    public boolean isGoal()                // is this board the goal board?
    {
        int dim = dimension();
        for(int i = 0; i < dim; i++)
            for(int j = 0; j < dim; j++)
            {
                if(interBlocks[i][j] != i * dim + j)
                    return false;
            }
        return true;
    }
    
    public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
    {
        int newBlocks = new int[dim][dim];
         for(int i = 0; i < dim; i++)
            for(int j = 0; j < dim; j++)
                newBlocks[i][j] = interBlocks[i][j];
         newBlocks[0][0] = interBlocks[0][1];
         newBlocks[0][1] = interBlocks[0][0];
         return new Board(newBlocks);        
    }
    
    public boolean equals(Object y)        // does this board equal y?
    {
        return ((Board)y).toString() == this.toString();
        
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Iterable<Board> boardList = new List<Board>();
        int blankBlockX, blankBlockY;
        for(int i = 0; i < dim; i++)
            for(int j = 0; j < dim; j++)
            {
                if(interBlocks[i][j] == 0)
                {
                    blankBlockX = i;
                    blankBlockY = j;
                    break;
                }
            }
        
        if(blankBlockY - 1 >= 0)
        {
             int leftBlocks = new int[dim][dim];
             copyTo(leftBlocks);
             leftBlocks[blankBlockX][blankBlockY - 1] = 0;
             leftBlocks[blankBlockX][blankBlockY] = interBlocks[blankBlockX][blankBlockY - 1];
             boardList.add(new Board(leftBlocks));
        }
        
        if(blankBlockY + 1 < dim)
        {
             int rightBlocks = new int[dim][dim];
             copyTo(rightBlocks);
             rightBlocks[blankBlockX][blankBlockY + 1] = 0;
             rightBlocks[blankBlockX][blankBlockY] = interBlocks[blankBlockX][blankBlockY + 1];
             boardList.add(new Board(rightBlocks));
        }
        
        if(blankBlockX - 1 >= 0)
        {
             int topBlocks = new int[dim][dim];
             copyTo(topBlocks);
             topBlocks[blankBlockX - 1][blankBlockY] = 0;
             topBlocks[blankBlockX][blankBlockY] = interBlocks[blankBlockX - 1][blankBlockY];
             boardList.add(new Board(topBlocks));
        }
        
        if(blankBlockX + 1 < dim)
        {
             int downBlocks = new int[dim][dim];
             copyTo(downBlocks);
             downBlocks[blankBlockX + 1][blankBlockY] = 0;
             downBlocks[blankBlockX][blankBlockY] = interBlocks[blankBlockX + 1][blankBlockY];
             boardList.add(new Board(downBlocks));
        }
    }
    
    public String toString()               // string representation of the board (in the output format specified below)
    {
        string s; 
        s += dim + "\n";
        for(int i = 0; i < dim; i++)
        {
            for(int j = 0; j < dim; j++)
                 s += " " + interBlocks[i][j] + " ";
            s += "\n";
        }
    }
        
    private void copyTo(int[][] blocks)
    {
        for(int i = 0; i < dim; i++)
            for(int j = 0; j < dim; j++)
            blocks[i][j] = interBlocks[i][j];
    }
}