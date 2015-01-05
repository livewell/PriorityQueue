import java.io.FileNotFoundException;
import java.util.*;

/**
 * The program reads the initial integer values from an input file called
 * "input.txt" and insert them into a heap by "heapify" technique and shows the
 * content of the heap in a space-delimited numbers by level-order traversing.
 * All initial integers are delimited by a space.
 * 
 * After that, program reads the instructions from another input file called
 * "script.txt" and execute them one by one and after each operation, it shows
 * the content of the heap by showing them in a space delimited numbers as
 * described above.
 * 
 * Input should be stated as (input.txt): [file] 13 15 10 8 7 [/file]
 * 
 * Script commands stated as (script.txt): [file] deleteMin decreaseKey(2, 6)
 * [/file]
 * 
 * 
 * CS: 146 Professor Yazdankah; Fall 2014; Section 8
 * 
 * @author Team LinkedList:  Kory Le, Megan
 *         Chan
 * 
 */

public class PriorityQueue<AnyType extends Comparable<AnyType>>
{
  private static int size = 0; // size of heap
  private AnyType[] heap; // The heap array
  private static Scanner scanner; // scans the file input.txt
  private static int numInt[]; // Copy of the heap array
  public static PriorityQueue<Integer> h;
  public AnyType temp; // just to hold things
  public int tmpChild; // holds the children for sink
  
  /**
   * Build heap
   */
  @SuppressWarnings("unchecked")
  public PriorityQueue()
  {
    heap = (AnyType[]) new Comparable[10];
  }
  
  /**
   * Sink the Heap (also known as Perclorate)
   * 
   * @param i the integer
   */
  private void sink(int i)
  {
    temp = heap[i];
    while (2 * i <= size)
    {
      tmpChild = 2 * i;
      if (size != tmpChild)
      {
        if (heap[tmpChild].compareTo(heap[tmpChild + 1]) > 0)
        {
          tmpChild++;
        }
      }
      
      if (temp != heap[tmpChild])
      {
        heap[i] = heap[tmpChild];
        i = tmpChild;
      }
      else
      {
        i = tmpChild;
        break;
      }
    }
    heap[i] = temp;
  }
  
  
  /**
   * Copies arrays from src to destination
   * 
   * @param srcStart the src position
   * @param destStart the destination position
   * @param size the size of the arrays
   * @param arrayOne the src array
   * @param arrayTwo the destination array
   */
  public void copyArray(int srcStart, int destStart, int size,
      AnyType[] arrayOne, AnyType[] arrayTwo)
  {
    System.arraycopy(arrayOne, srcStart, arrayTwo, destStart, size);
  }
  
  /**
   * Deletes the top item (aka the minimum)
   */
  public void deleteMin()
  {
    if (size != 0)
    {
      heap[1] = heap[size--];
      sink(1);
    }
  }
  
  /**
   * Decrease the node's value by delta
   * 
   * @param p represents the node's index
   * @param delta the value to decrease
   */
  public void decreaseKey(int p, int delta)
  {
    if (p >= heap.length)
    {
      return;
    }
    
    heap[p] = heap[p];
    int x = (Integer) heap[p + 1] - delta;
    h.insert(x);
    heap[p + 1] = heap[size];
    h.delete(size);
    size--;
  }
  
  /**
   * Increases the node's value by delta
   * 
   * @param p represents the node's index
   * @param delta the value to increase
   */
  public void increaseKey(int p, int delta)
  {
    heap[p] = heap[p];
    int x = (Integer) heap[p + 1] + delta;
    h.insert(x);
    heap[p + 1] = heap[size];
    h.delete(size);
    size--;
  }
  
  /**
   * Checks if Strings are numbers.
   * 
   * @param string the string to parse
   * @return true false
   */
  public static boolean isNumber(String string)
  {
    try
    {
      Long.parseLong(string);
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
  
  /**
   * Deletes node at p index
   * @param p the index passed in to delete
   */
  public void delete(int p)
  {
    @SuppressWarnings("unchecked")
    AnyType[] tempHeap = (AnyType[]) new Comparable[heap.length - 1];
    int index = 0;
    
    if (p <= 0)
    {
      System.out.println("Index at " + p + " not found.");
    }
    for (int i = 0; i < heap.length - 1; i++)
    {
      if (i != p)
      {
        tempHeap[i] = heap[index];
        index++;
      }
      else
      {
        tempHeap[i] = heap[p + 1];
        index += 2;
      }
    }
    heap = tempHeap;
  }
  
  /**
   * Inserts a new item
   */
  @SuppressWarnings("unchecked")
  public void insert(AnyType arrayHeap)
  {
    if (heap.length - 1 == size)
    {
      int heapLength = heap.length * 2;
      AnyType[] original = heap;
      heap = (AnyType[]) new Comparable[heapLength];
      copyArray(1, 1, size, original, heap);
    }
    int position = ++size;
    while (position >= 2 && arrayHeap.compareTo(heap[position / 2]) < 0)
    {
      heap[position] = heap[position / 2];
      position = position / 2;
    }
    
    heap[position] = arrayHeap;
  }
  
  /**
   * Print things. Overwrites the original toString for heap.
   */
  public String toString()
  {
    for (int k = 1; k < size + 1; k++)
      System.out.print(heap[k] + " ");
    return "";
  }
  
  /**
   * Really ugly main that takes in inputs and scripts.
   * 
   * @param args does nothing
   * @throws FileNotFoundException 
   */
  public static void main(String[] args) throws FileNotFoundException
  {
    h = new PriorityQueue<Integer>();
    scanner = new Scanner(new java.io.File("input.txt"));
    
    while (true)
    {
      String inputLine = scanner.nextLine();
      if (!inputLine.equals(""))
      {
        String[] numString = inputLine.split(" ");
        numInt = new int[numString.length];
        for (int i = 0; i < numString.length; i++)
        {
          numInt[i] = Integer.parseInt(numString[i]);
          h.insert(Integer.parseInt(numString[i]));
        }
        size++;
        break;
      }
      else
      {
        break;
      }
    }
    
    size = size - 1;
    System.out.println(h);
    
    scanner = new Scanner(new java.io.File("script.txt"));
    
    while (scanner.hasNext())
    {
      String inputLine = scanner.nextLine();
      if (!inputLine.equals(""))
      {
        if (inputLine.contains("insert"))
        {
          String num = "";
          if (inputLine.length() == 7)
          {
            num = inputLine.substring(7);
          }
          else
            num = inputLine.substring(7, inputLine.length());
          if (inputLine.substring(0, 7).equalsIgnoreCase("insert ")
              && isNumber(num))
          {
            h.insert(Integer.parseInt(num));
            System.out.println(h);
          }
          if (!isNumber(num))
          {
            System.out.println(num + " is not a number.");
          }
        }
        if (inputLine.equals("deleteMin"))
        {
          h.deleteMin();
          // addtoTree();
          System.out.println(h);
        }
        if (inputLine.contains("decreaseKey("))
        {
          // Parse the parameters in script.txt
          String input = inputLine.substring(12,
              inputLine.length() - 1);
          String[] pvalue = input.split(",");
          
          int p = Integer.parseInt(pvalue[0]) - 1;
          int delta = Integer.parseInt(pvalue[1]);
          
          h.decreaseKey(p, delta);
          System.out.println(h);
        }
        if (inputLine.contains("increaseKey("))
        {
          // Parse the parameters in script.txt
          String input = inputLine.substring(12,
              inputLine.length() - 1);
          String[] pvalue = input.split(",");
          
          int p = Integer.parseInt(pvalue[0]) - 1;
          int delta = Integer.parseInt(pvalue[1]);
          
          h.increaseKey(p, delta);
          System.out.println(h);
        }
        if (inputLine.contains("delete("))
        {
          int p = Integer.parseInt(inputLine.substring(7,
              inputLine.length() - 1));
          p--;
          h.delete(p + 1);
          size--;
          System.out.println(h);
        }
      }
    }
  }
  
}