class SearchNode implements Comparable<SearchNode> 
{
    public int reachMoves;
    public Board board;
    public SearchNode previous;
    
    public SearchNode(Board _board, int _reachMoves,SearchNode _previous)
    {
        board = _board;
        reachMoves = _reachMoves;
        previous = _previous;
    }
        
     public int compareTo(SearchNode that) 
    {
         int manhattan1 = this.board.manhattan();
         int manhattan2 = that.board.manhattan();
         
         if(manhattan1 < manhattan2)
             return -1;
         
         if(manhattan1 > manhattan2)
             return 1;
         
         return 0;
    }
}

public class Solver 
{
    private MinPQ<SearchNode> priorityQueue;
    private Stack<Board> historyList;
    private Board initialBoard;
    private Stack<Board> solutionList;
    private boolean isGoal;
    private SearchNode lastNode;
    
    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
        initialBoard = initial;
        isGoal = false;
        
        if(initialBoard.isGoal())
        {
            isGoal = true;
        }
        else
        {
            priorityQueue = new MinPQ<SearchNode>();
            historyList = new Stack<Board>();
            SearchNode initialNode = new SearchNode(initialBoard, 0, null); 
            priorityQueue.insert(initialNode);
            historyList.push(initialNode.board);
            
            while(!priorityQueue.isEmpty())
            {
                StdOut.println(priorityQueue.size());
                lastNode = priorityQueue.delMin();
                                          
                if(lastNode.board.isGoal())
                {
                    isGoal = true;
                    break;
                }
                
                for (Board board : lastNode.board.neighbors())
                {
                    if(isFindedInHistory(board) == false)
                    {
                        SearchNode neighbor = new SearchNode(board, lastNode.reachMoves + 1, lastNode); 
                        priorityQueue.insert(neighbor);
                        historyList.push(neighbor.board);
                    }
                }        
            }    
        }
    }
    
    private boolean isFindedInHistory(Board board)
    {
        for (Board temp : historyList)
        {
            if(temp.equals(board))
                return true;
        }    
        
        return false;
    }
    
    private void writePathToList(Stack<Board> list,  SearchNode node)
    {
        if(node.previous != null)
            writePathToList(list, node.previous);
        solutionList.push(node.board);        
    }
    
    public boolean isSolvable()             // is the initial board solvable?
    {
        return isGoal;
    }
    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    {
        if(isGoal)
            return lastNode.reachMoves;
        
        return -1;
    }
    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    {
        if(isGoal)
        {
            if(solutionList == null)
            {
                Stack<Board> solutionList = new Stack<Board>();
                writePathToList(solutionList, lastNode);
            }
            return solutionList;
        }
        
        return null;
    }
    public static void main(String[] args) 
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}