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
 * Input should be stated as (input.txt): [file] 13 15 10 8 7 [/file]
 * 
 * Script commands stated as (script.txt): [file] deleteMin decreaseKey(2, 6)
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
	 * @param args does nothing
	 */
	public static void main(String[] args)
	{
		PriorityQueue queue = new PriorityQueue();
		queue.scanInput();
		queue.heapSort(heap);
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
	 */
	public void scanInstruct()
	{
		boolean instruction = false; // if instructions are not valid print not
										// valid
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
						if (inputLine.substring(0, 7).equalsIgnoreCase(
								"insert ")
								&& isNumber(num))
						{
							System.out.println(inputLine);
							insert(num);
							print(heap);
							instruction = true;
						}
					}
					if (inputLine.equals("deleteMin"))
					{
						System.out.println("deleteMin");
						deleteMin();
						print(heap);
						instruction = true;
						
					}
					if (inputLine.contains("decreaseKey("))
					{
						// Parse the parameters in script.txt
						int p = Integer.parseInt(inputLine.substring(12, 13));
						int delta = Integer.parseInt(inputLine
								.substring(15, 16));
						
						System.out.println("decreaseKey");
						decreaseKey(p, delta);
						print(heap);
						instruction = true;
					}
					if (inputLine.contains("increaseKey("))
					{
						// Parse the parameters in script.txt
						int p = Integer.parseInt(inputLine.substring(12, 13));
						int delta = Integer.parseInt(inputLine
								.substring(15, 16));
						
						System.out.println("increaseKey");
						
						increaseKey(p, delta);
						print(heap);
						instruction = true;
					}
					if (inputLine.contains("delete "))
					{
						int p = 0;
						System.out.println("delete"); // It it reaches the right
						
						if (inputLine.length() == 7)
						{
							p = Integer.parseInt(inputLine.substring(7));
						}
						else
							p = Integer.parseInt(inputLine.substring(7,
									inputLine.length()));
						
						delete(p);
						print(heap);
						instruction = true;
					} 
					if (instruction == false) // if instructions are not valid print not valid
					{
						System.out.println(inputLine
								+ " is not a valid instruction");
					}
					instruction = false;
				}
				else
				{
					break;
				}
			}
			
		}
		// Unable to find files
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
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
	 * Insert objects to the end of the array and then sorts them
	 * 
	 * @param addNum the number to be added to the heap
	 */
	public void insert(String addNum)
	{
		int numberToAdd = Integer.parseInt(addNum);
		heap = Arrays.copyOf(numInt, numInt.length + 1);
		heap[heap.length - 1] = numberToAdd;
		heapSort(heap);
	}
	
	/**
	 * Prints the heap.
	 * 
	 * @param array the heap to be printed
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
	 * @param array the heap array to be sorted
	 */
	public void heapSort(int array[])
	{
		size = array.length - 1;
		for (int i = size / 2; i >= 0; i--)
		{
			maxHeap(array, i);
		}
		for (int i = size; i > 0; i--)
		{
			swap(array, 0, i);
			size = size - 1;
			maxHeap(array, 0);
		}
	}
	
	/**
	 * Swaps largest(max) element in heap
	 * 
	 * @param array the array to be sorted
	 * @param maxElement the largest element
	 */
	public void maxHeap(int array[], int maxElement)
	{
		int lChild = 2 * maxElement;
		int rChild = 2 * maxElement + 1;
		int maximum = maxElement;
		if ((lChild <= size) && (array[lChild] > array[maxElement]))
			maximum = lChild;
		if ((rChild <= size) && (array[rChild] > array[maximum]))
			maximum = rChild;
		if (maximum != maxElement)
		{
			swap(array, maxElement, maximum);
			maxHeap(array, maximum);
		}
	}
	
	/**
	 * Swaps itemOne and itemTwo in the array
	 * 
	 * @param array the array to have its items swapped
	 * @param itemOne the first item to be swapped
	 * @param itemTwo the second item to be swapped
	 */
	public void swap(int array[], int itemOne, int itemTwo)
	{
		int tempItem = array[itemOne];
		array[itemOne] = array[itemTwo];
		array[itemTwo] = tempItem;
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
	 * 
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
		heap[p] = heap[p] - delta;
		heapSort(heap);
	}
	
	/**
	 * Increases the node's value by delta
	 * 
	 * @param p represents the node's index
	 * @param delta the value to increase
	 */
	public void increaseKey(int p, int delta)
	{
		heap[p] = heap[p] + delta;
		heapSort(heap);
	}
	
}
