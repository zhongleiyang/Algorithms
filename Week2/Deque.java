import java.util.Iterator;
import java.util.NoSuchElementException;
public class Deque<Item> implements Iterable<Item> 
{
    private int N;          // size of the Deque
    private Node first;     // first of Deque
    private Node last;     // last of Deque

    // helper linked list class
    private class Node {
        private Node prior;
        private Item item;
        private Node next;
    }
   public Deque()                     // construct an empty deque
   {
       first = null;
       last = null;
       N = 0;
   }
   public boolean isEmpty()           // is the deque empty?
   {
       return N == 0;
   }
   public int size()                  // return the number of items on the deque
   {
       return N;
   }
   public void addFirst(Item item)    // insert the item at the front
   {
       if(item == null)
           throw new NullPointerException();
       
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prior = null;
        
        if(oldfirst != null)
            oldfirst.prior =  first;
        
        if(last == null)
            last = first;         
        N++;
   }
   public void addLast(Item item)     // insert the item at the end
   {
       if(item == null)
           throw new NullPointerException();
       
       Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prior = oldLast;
        
        if(oldLast != null)
        {
            oldLast.next = last;
        }
        
        if(first == null)
            first = last;
        
        N++;
   }
   public Item removeFirst()          // delete and return the item at the front
   {
       if(N == 0)
           throw new NoSuchElementException();
       Node oldfirst = first;
       first = first.next;
       oldfirst.next = null;
       
       if(first != null)
           first.prior = null;
        else
        {
            first = null;
            last = null;
        }
       
       N--;
       return oldfirst.item;      
   }
   public Item removeLast()           // delete and return the item at the end
   {
       if(N == 0)
           throw new NoSuchElementException();

        Node oldLast = last;
        last = oldLast.prior;
        oldLast.prior = null;
        
        if(last != null)
        {
            last.next = null;
        }
        else
        {
            first = null;
            last = null;
        }
        
        N--;
        return oldLast.item;    
   }
   public Iterator<Item> iterator()   // return an iterator over items in order from front to end
   {
       return new DequeIterator();
   }
  
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  
        {
            return current != null;                     
        }
        public void remove()      
        { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
}