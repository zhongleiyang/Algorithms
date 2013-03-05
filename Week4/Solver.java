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
         
         if(manhattan1 +  this.reachMoves < manhattan2 + that.reachMoves)
             return -1;
         
         if(manhattan1 +  this.reachMoves > manhattan2 + that.reachMoves)
             return 1;
                       
         return 0;
    }
}

public class Solver 
{
    private MinPQ<SearchNode> priorityQueue0;
    private MinPQ<SearchNode> priorityQueue1;
    private Stack<Board> historyList0;
    private Stack<Board> historyList1;
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
        historyList0 = new Stack<Board>();
        historyList1 = new Stack<Board>();
        
        SearchNode initialNode0 = new SearchNode(initialBoard[0], 0, null);
        SearchNode initialNode1 = new SearchNode(initialBoard[1], 0, null);
        priorityQueue0.insert(initialNode0);
        historyList0.push(initialNode0.board);
        priorityQueue1.insert(initialNode1);
        historyList1.push(initialNode1.board);
        
        lastNode = new SearchNode[2];
        turn = 0;
        
        Queue<SearchNode> candidateList = new Queue<SearchNode>();
        
        while(!priorityQueue0.isEmpty() && !priorityQueue1.isEmpty())
        {
            MinPQ<SearchNode> priorityQueue;
            Stack<Board> historyList;
            if(turn == 0)
            {
                priorityQueue = priorityQueue0;
                historyList = historyList0;
            }
            else
            {
                priorityQueue = priorityQueue1;
                historyList = historyList1;
            }
            
            SearchNode node = priorityQueue.delMin();
            int manhattan = node.board.manhattan();
            candidateList.enqueue(node);
            while(!priorityQueue.isEmpty() && priorityQueue.min().compareTo(node) == 0)
                candidateList.enqueue(priorityQueue.delMin());
            
            while(!candidateList.isEmpty())
            {
                lastNode[turn] = candidateList.dequeue();
                if(lastNode[turn].board.isGoal())
                {
                    isGoal = true;
                    break;       
                    
                }               
                for (Board board : lastNode[turn].board.neighbors())
                {
                    if(isFindedInHistory(historyList, board) == false)
                    {
                        SearchNode neighbor = new SearchNode(board, lastNode[turn].reachMoves + 1, lastNode[turn]); 
                        priorityQueue.insert(neighbor);
                        historyList.push(neighbor.board);
                    }
                }    
            }
            
            if(isGoal == true)
                break;
            
            turn = 1 - turn;
        }           
    }
    
    private boolean isFindedInHistory(Stack<Board> list, Board board)
    {
        for (Board temp : list)
        {
            if(temp.equals(board))
                return true;
        }    
        
        return false;
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