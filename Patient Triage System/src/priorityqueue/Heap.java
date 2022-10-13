package priorityqueue;

import java.util.Comparator;

public class Heap<T> implements PriorityQueueADT<T> {

  private int numElements;
  private T[] heap;
  private boolean isMaxHeap;
  private Comparator<T> comparator;
  private final static int INIT_SIZE = 5;

  /**
   * Constructor for the heap.
   * @param comparator comparator object to define a sorting order for the heap elements.
   * @param isMaxHeap Flag to set if the heap should be a max heap or a min heap.
   */
  public Heap(Comparator<T> comparator, boolean isMaxHeap) {
      heap = (T[]) new Object[INIT_SIZE];
      this.comparator = comparator;
      this.isMaxHeap = isMaxHeap;
  }

  /**
   * This results in the entry at the specified index "bubbling up" to a location
   * such that the property of the heap are maintained. This method should run in
   * O(log(size)) time.
   * Note: When enqueue is called, an entry is placed at the next available index in 
   * the array and then this method is called on that index. 
   *
   * @param index the index to bubble up
   * @throws IndexOutOfBoundsException if invalid index
   */
  public void bubbleUp(int index) {
      if(index < 0){
        throw new IndexOutOfBoundsException();
      }
      while(index>0){
        int parentIndex = (index - 1)/2;
        if(compareElements(heap[index],heap[parentIndex])<=0){
          return;
        }
        else{
          swap(index,parentIndex);
          index = parentIndex;
        }
      }

  }

  private void swap(int index1, int index2){
    T temp = heap[index1];
    heap[index1] = heap[index2];
    heap[index2] = temp;
  }

  /**
   * This method results in the entry at the specified index "bubbling down" to a
   * location such that the property of the heap are maintained. This method
   * should run in O(log(size)) time.
   * Note: When remove is called, if there are elements remaining in this
   *  the bottom most element of the heap is placed at
   * the 0th index and bubbleDown(0) is called.
   * 
   * @param index
   * @throws IndexOutOfBoundsException if invalid index
   */
  public void bubbleDown(int index) {
      if(index <0 || index >= numElements){
        throw new IndexOutOfBoundsException();
      }
      int childIndex = (2*index)+1;
      T value = heap[index];

      while(childIndex < getSize()){
        T maxValue = value;
        int maxIndex = -1;
        for(int i = 0; i<2 && (i+childIndex)<getSize();i++){
          if(compareElements(heap[i+childIndex],maxValue)>0){
            maxValue = heap[i + childIndex];
            maxIndex = i + childIndex;
          }
        }
        if(compareElements(maxValue, value)==0){
          return;
        }
        else{
          swap(index,maxIndex);
          index = maxIndex;
          childIndex = (2*index)+1;
        }
      }
  }

  /**
   * Test for if the queue is empty.
   * @return true if queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    boolean isEmpty = false;
      if(numElements == 0){
        isEmpty = true;
      }
    return isEmpty;
  }

  /**
   * Number of data elements in the queue.
   * @return the size
   */
  public int getSize(){
    int size = -100;
      size = numElements;
    return size;
  }

  /**
   * Compare method to implement max/min heap behavior. It changes the value of a variable, compareSign, 
   * based on the state of the boolean variable isMaxHeap. It then calls the compare method from the 
   * comparator object and multiplies its output by compareSign.
   * @param element1 first element to be compared
   * @param element2 second element to be compared
   * @return positive int if {@code element1 > element2}, 0 if {@code element1 == element2}, 
   * negative int otherwise (if isMaxHeap),
   * return negative int if {@code element1 > element2}, 0 if {@code element1 == element2}, 
   * positive int otherwise (if ! isMinHeap).
   */
  public int compareElements(T element1 , T element2) {
    int result = 0;
    int compareSign =  -1;
    if (isMaxHeap) {
      compareSign = 1;
    }
    result = compareSign * comparator.compare(element1, element2);
    return result;
  }

  /**
   * Return the element with highest (or lowest if min heap) priority in the heap 
   * without removing the element.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T peek() throws QueueUnderflowException {
     T data = null;
    if (isEmpty()) {
      throw new QueueUnderflowException("Cannot peek at an empty ArrayHeap.");
    }
    return heap[0];

  }  

  /**
   * Removes and returns the element with highest (or lowest if min heap) priority in the heap.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T dequeueElement() throws QueueUnderflowException{
    if(isEmpty()){
      throw new QueueUnderflowException();
    }
    
    if(numElements==1){
      T data = heap[0];
      heap[0] = null;
      numElements--;
      return data;
    }
    
    T data = null;
    data = heap[0];
    heap[0]=heap[numElements-1];
    numElements--;
    bubbleDown(0);
    return data;
  }

  /**
   * Enqueue the element.
   * @param the new element
   */
  public void enqueueElement(T newElement) {
      if(numElements == (heap.length)){
        expandCapacity();
      }
      if(numElements == 0){
        heap[0] = newElement;
        numElements++;
        return;
      }
      heap[numElements]=newElement;
      bubbleUp(numElements);
      numElements++;
  }

  private void expandCapacity(){
    T[] newHeap;
    newHeap = (T[]) new Object[numElements*2];
    for(int i = 0; i<numElements;i++){
      newHeap[i]=heap[i];
    }
    heap = newHeap;
  }



}