public class RandomizedQueue<Item> implements Iterable<Item> 
{
    private Item[] items;
    private int N;
   public RandomizedQueue()           // construct an empty randomized queue
   {
       N = 0;
       items = new Item[1];
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
       if (N == items.length) resize(2 * items.length);
           items[N++] = item;
   }
   
   private void resize(int capacity)
 {
       Item[] copy = new items[capacity];
       for (int i = 0; i < N; i++)
           copy[i] = items[i];
      items = copy;
 }
   public Item dequeue()              // delete and return a random item
   {
       int i = StdRandom.uniform(0 , N);
       Item result = items[i];
       item[i] = item[N - 1];
       N--;
       return result;
   }
   public Item sample()               // return (but do not delete) a random item
   {
       return items[StdRandom.uniform(0 , N)];
   }
   public Iterator<Item> iterator()   // return an independent iterator over items in random order
   {             
        return new RandomizedQueueIterator();
   }
  
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = 0;
        public boolean hasNext()  
        {
            return current < N;                     
        }
        public void remove()      
        { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = items[current];
            current++; 
            return item;
        }
    }
}