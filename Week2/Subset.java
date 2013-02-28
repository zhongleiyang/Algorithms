public class Subset 
{
   public static void main(String[] args)
   {
       RandomizedQueue<String> s = new RandomizedQueue<String>();
        int num = Integer.parseInt(args[0]);     
        while (!StdIn.isEmpty())
        {
            String item = StdIn.readString();
            s.enqueue(item);
        }
        for(int i = 0; i < num; i++)
            StdOut.println(s.dequeue());    
   }
}