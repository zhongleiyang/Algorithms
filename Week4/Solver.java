class SearchNode implements Comparable<SearchNode> 
{
    public int reachMoves;
    public Board board;
    public SearchNode previous;
    public int manhattan;

    public SearchNode(Board _board, int _reachMoves,SearchNode _previous)
    {
        board = _board;
        reachMoves = _reachMoves;
        previous = _previous;
        manhattan = board.manhattan();
    }
        
     public int compareTo(SearchNode that) 
    {      
         if(this.manhattan +  this.reachMoves < that.manhattan + that.reachMoves)
             return -1;
         
         if(this.manhattan +  this.reachMoves > that.manhattan + that.reachMoves)
             return 1;
         
         if(this.reachMoves > that.reachMoves)
            return -1;
         
         if(this.reachMoves < that.reachMoves)
             return 1;
         return 0;
    }
}

public class Solver 
{
    private MinPQ<SearchNode> priorityQueue0;
    private MinPQ<SearchNode> priorityQueue1;
    private Board[] initialBoard;
    private Stack<Board> solutionList;
    private boolean isGoal;
    private SearchNode[] lastNode;
    private int turn;
    
    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
        initialBoard = new Board[2];
        initialBoard[0] = initial;
        initialBoard[1] = initial.twin();

        isGoal = false;
        
        priorityQueue0 = new MinPQ<SearchNode>();
        priorityQueue1 = new MinPQ<SearchNode>();
        
        SearchNode initialNode0 = new SearchNode(initialBoard[0], 0, null);
        SearchNode initialNode1 = new SearchNode(initialBoard[1], 0, null);
        priorityQueue0.insert(initialNode0);
        priorityQueue1.insert(initialNode1);

        
        lastNode = new SearchNode[2];
        turn = 0;
            
        while(!priorityQueue0.isEmpty() && !priorityQueue1.isEmpty())
        {
            MinPQ<SearchNode> priorityQueue;
            if(turn == 0)
                priorityQueue = priorityQueue0;
            else
                priorityQueue = priorityQueue1;
                      
            lastNode[turn] = priorityQueue.delMin();

            if(lastNode[turn].board.isGoal())
            {
                isGoal = true;
                break;                     
            }  
    
            for (Board board : lastNode[turn].board.neighbors())
            {
                SearchNode node = lastNode[turn].previous;
   
                if(node == null || !board.equals(node.board))                    
                {
                    SearchNode neighbor = new SearchNode(board, lastNode[turn].reachMoves + 1, lastNode[turn]); 
                    priorityQueue.insert(neighbor);
                }
            }    
           
            turn = 1 - turn;
        }           
    }
    
    private void writePathToList(Stack<Board> list,  SearchNode node)
    {
        while(node != null)
        {
           list.push(node.board);  
           node = node.previous;
        }      
    }
    
    public boolean isSolvable()             // is the initial board solvable?
    {
        return isGoal && turn == 0;
    }
    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    {
        if(isGoal && turn == 0)
        { 
            if(lastNode[0] == null)
                return 0;
            return lastNode[0].reachMoves;
        }
        
        return -1;
    }
    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    {
        if(isGoal && turn == 0)
        {
            if(solutionList == null)
            {
                solutionList = new Stack<Board>();
                writePathToList(solutionList, lastNode[0]);
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