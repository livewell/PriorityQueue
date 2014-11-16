import java.util.*;

/**
 * The program reads the initial integer values from an input file called
 * "input.txt" and insert them into a heap by "buildHeap" technique and shows
 * the content of the heap in a space-delimited numbers by level-order
 * traversing. All initial integers are delimited by a space.
 * 
 * After that, program reads the instructions from another input file called
 * "script.txt" and execute them one by one and after each operation, it shows
 * the content of the heap by showing them in a space delimited numbers as
 * described above.
 * 
 * Input should be stated as (input.txt):
 * [file]
 * 13 15 10 8 7
 * [/file]
 * 
 * Script commands stated as (script.txt): 
 * [file]
 * deleteMin 
 * decreaseKey(2, 6)
 * [/file]
 * 
 * 
 * CS: 146 Professor Yazdankah; Fall 2014; Section 8
 * 
 * @author Team LinkedList: Su Liang, Zeran Wang, Andrew Acmoedy, Kory Le, Megan
 *         Chan
 * 
 */

public class PriorityQueue
{
   private static int heap[]; // The original heap array
   private static int size; // How big is the heap array
   private static Scanner scanner; // scans the file input.txt
   private static int numInt[]; // Copy of the heap array

   /**
    * Initializes the heap
    */
   public PriorityQueue()
   {
      size = 0;
   }

   /**
    * Main, sorts the heap and computes instructions
    * 
    * @param args
    *           does nothing
    */
   public static void main(String[] args)
   {
      PriorityQueue queue = new PriorityQueue();
      queue.scanInput();
      queue.heapSort(heap, size);
      queue.print(heap);
      queue.scanInstruct();
   }

   /**
    * Scans for file "input.txt".
    */
   public void scanInput()
   {
      try
      {
         scanner = new Scanner(new java.io.File("input.txt"));

         while (true)
         {
            String inputLine = scanner.nextLine();
            if (!inputLine.equals(""))
            {
               String[] numString = inputLine.split(" ");
               numInt = new int[numString.length];
               heap = new int[numString.length];
               for (int i = 0; i < numString.length; i++)
               {
                  numInt[i] = Integer.parseInt(numString[i]);
                  heap[i] = Integer.parseInt(numString[i]);
               }
               size = numInt.length;
               break;
            }
            else
            {
               break;
            }
         }

      }
      // If no files found.
      catch (Exception e)
      {
         System.out.println("No Files Found :(");
         System.exit(1);
      }

      print(numInt);

   }

   /**
    * Scans for the instructions.
    * 
    */
   public void scanInstruct()
   {
      try
      {
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
                     System.out.println(inputLine);
                     insert(num);
                  }
               }
               if (inputLine.equals("deleteMin"))
               {
                  System.out.println("deleteMin");
                  deleteMin();
                  print(heap);

               }
               if (inputLine.contains("decreaseKey("))
               {
                  // Parse the parameters in script.txt
                  int p = Integer.parseInt(inputLine.substring(12, 13));
                  int delta = Integer.parseInt(inputLine.substring(15, 16));

                  System.out.println("decreaseKey");
                  decreaseKey(p, delta);
                  print(heap);
               }
               if (inputLine.contains("increaseKey("))
               {
                  // Parse the parameters in script.txt
                  int p = Integer.parseInt(inputLine.substring(12, 13));
                  int delta = Integer.parseInt(inputLine.substring(15, 16));

                  System.out.println("increaseKey");

                  increaseKey(p, delta);
                  print(heap);
               }
               if (inputLine.contains("delete "))
               {
                  int p = 0;
                  System.out.println("delete");

                  if (inputLine.length() == 7)
                  {
                     p = Integer.parseInt(inputLine.substring(7));
                  }
                  else
                     p = Integer.parseInt(inputLine.substring(7,
                           inputLine.length()));

                  delete(p);
                  print(heap);
               }
            }
            else
            {
               break;
            }
         }

      }
      // Herp derp. We can't find any files.
      catch (Exception e)
      {
         e.printStackTrace();
         System.exit(1);
      }

   }

   /**
    * Checks if Strings are numbers.
    * 
    * @param string
    *           the string to parse
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
    * Insert objects
    */
   public void insert(String addNum)
   {
      int numberToAdd = Integer.parseInt(addNum);
      heap = Arrays.copyOf(numInt, numInt.length + 1);
      heap[heap.length - 1] = numberToAdd;
      heapSort(heap, size);
      print(heap);
   }

   /**
    * Prints the heap.
    * 
    * @param array
    *           the heap to be printed
    */
   public void print(int[] array)
   {
      for (int i = 0; i < array.length; i++)
      {
         System.out.print(array[i] + " ");
      }
      System.out.println();
   }

   
   /**
    * Sorts the heap
    * 
    * @param numbers
    *           the heap
    * @param array_size
    *           sorts by array size
    */
   public void heapSort(int numbers[], int array_size)
   {
      int i, temp;

      for (i = (array_size / 2) - 1; i >= 0; i--)
         siftDown(numbers, i, array_size);

      for (i = array_size - 1; i >= 1; i--)
      {
         temp = numbers[0];
         numbers[0] = numbers[i];
         numbers[i] = temp;
         siftDown(numbers, 0, i - 1);
      }
   }

   /**
    * Shifts the numbers around
    * 
    * @param numbers
    *           the heap
    * @param root
    *           the top integer
    * @param bottom
    *           the bottom integer
    */
   public void siftDown(int numbers[], int root, int bottom)
   {
      int done, maxChild, temp;

      done = 0;
      while ((root * 2 <= bottom) && (done != 1))
      {
         if (root * 2 == bottom)
            maxChild = root * 2;
         else if (numbers[root * 2] > numbers[root * 2 + 1])
            maxChild = root * 2;
         else
            maxChild = root * 2 + 1;

         if (numbers[root] < numbers[maxChild])
         {
            temp = numbers[root];
            numbers[root] = numbers[maxChild];
            numbers[maxChild] = temp;
            root = maxChild;
         }
         else
            done = 1;
      }
   }

   /**
    * Deletes the min value (root)
    */

   public void deleteMin()
   {
      int[] tempHeap = new int[heap.length - 1];

      for (int i = 0; i < heap.length - 1; i++)
      {
         tempHeap[i] = heap[i + 1];
      }
      heap = tempHeap;
   }

   /**
    * Deletes node at p index
    * @param p the index passed in to delete
    */
   public void delete(int p)
   {
      int[] tempHeap = new int[heap.length - 1];
      int index = 0;

      if (p >= heap.length) // checks for out of bounds
      {
         throw new ArrayIndexOutOfBoundsException();
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
    * Decrease the node's value by delta
    * @param p represents the node's index
    * @param delta the value to decrease
    */

   public void decreaseKey(int p, int delta)
   {
      heap[p] = heap[p] - delta;
      heapSort(heap, heap.length);
   }

   /**
    * Increases the node's value by delta
    * @param p represents the node's index
    * @param delta the value to increase
    */

   public void increaseKey(int p, int delta)
   {
      heap[p] = heap[p] + delta;
      heapSort(heap, heap.length);
   }

}