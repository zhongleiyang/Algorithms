import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item> 
{
    private Item[] items;
    private int N;
   public RandomizedQueue()           // construct an empty randomized queue
   {
       N = 0;
       items = (Item[]) new Object[1];
   }
   public boolean isEmpty()           // is the queue empty?
   {
       return N == 0;   
   }
   public int size()                  // return the number of items on the queue
   {
       return N;   
   }
   public void enqueue(Item item)     // add the item
   {
       if(item == null)
           throw new NullPointerException();
       if (N == items.length) resize(2 * items.length);
           items[N++] = item;
   }
   
   private void resize(int capacity)
   {
       Item[] copy = (Item[])  new Object[capacity];
       for (int i = 0; i < N; i++)
           copy[i] = items[i];
      items = copy;
   }
   public Item dequeue()              // delete and return a random item
   {
       if(N == 0)
           throw new NoSuchElementException();
       int i = StdRandom.uniform(0 , N);
       Item result = items[i];
       items[i] = items[N - 1];
       items[N - 1] = null;
       N--;
       if(N > 0 && N == items.length / 4)
           resize(items.length / 2);
       return result;
   }
   public Item sample()               // return (but do not delete) a random item
   {
       if(N == 0)
           throw new NoSuchElementException();
       return items[StdRandom.uniform(0 , N)];
   }
   public Iterator<Item> iterator()   // return an independent iterator over items in random order
   {             
        return new RandomizedQueueIterator();
   }
  
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int num;
        private int[] ids;
        
        public RandomizedQueueIterator()
        {
            if(N > 0)
            {
                ids = new int[N];
                num = N;
                for(int i = 0; i < N; i++)
                    ids[i] = i;
            }
            else
            {
                num = 0;
            }
        }
        public boolean hasNext()  
        {
            return num > 0;                     
        }
        public void remove()      
        { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            
            int i = StdRandom.uniform(0 , num);
            Item item = items[ids[i]];
            ids[i] = ids[num - 1];
            num--; 
            return item;
        }
    }
}