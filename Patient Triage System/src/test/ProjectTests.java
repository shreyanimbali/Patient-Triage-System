package test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static org.junit.Assert.*;
import priorityqueue.*;
import app.*;

import org.junit.Before;
import org.junit.Test;

// these are the same as public tests
public class ProjectTests {
  Heap<Patient> minHeap;
  Heap<Patient> maxHeap;

  @Before
  public void before() {
    PatientAgeComparator pAC = new PatientAgeComparator();
    minHeap = new Heap<Patient>(pAC, false);
    maxHeap = new Heap<Patient>(pAC, true);
  }

  @Test 
  public void testIsEmptyEnqueueDequeue() {
    assertTrue(minHeap.isEmpty());
    assertTrue(maxHeap.isEmpty());
    Condition condition = Condition.valueOf("SEVERE".toUpperCase());
    Patient p = new Patient("a'", "b", 3, condition);
    minHeap.enqueueElement(p);
    maxHeap.enqueueElement(p);
    assertFalse(minHeap.isEmpty());
    assertFalse(maxHeap.isEmpty());
    minHeap.dequeueElement();
    maxHeap.dequeueElement();
    assertTrue(minHeap.isEmpty());
    assertTrue(maxHeap.isEmpty());
  }

  @Test 
  public void testEnqueuePeekSize() {
    assertEquals(minHeap.getSize(), 0);
    assertEquals(minHeap.getSize(), 0);
    Condition condition = Condition.valueOf("SEVERE".toUpperCase());
    Patient p1 = new Patient("a", "b", 3, condition);
    minHeap.enqueueElement(p1);
    maxHeap.enqueueElement(p1);
    assertEquals(minHeap.getSize(), 1);
    assertEquals(minHeap.getSize(), 1);
    Patient p2 = new Patient("a'", "b", 4, condition);
    minHeap.enqueueElement(p2);
    maxHeap.enqueueElement(p2);
    assertEquals(minHeap.getSize(), 2);
    assertEquals(minHeap.getSize(), 2);
    assertEquals(p2, maxHeap.peek());
    assertEquals(p1, minHeap.peek());
    Patient p3 = new Patient("a'", "b", 2, condition);
    minHeap.enqueueElement(p3);
    maxHeap.enqueueElement(p3);
    assertEquals(minHeap.getSize(), 3);
    assertEquals(minHeap.getSize(), 3);
    assertEquals(p2, maxHeap.peek());
    assertEquals(p3, minHeap.peek());
  }

  @Test(expected = QueueUnderflowException.class)
  public void testPeekMinHeap() {
    minHeap.peek();
  }

  @Test(expected = QueueUnderflowException.class)
  public void testPeekMaxHeap() {
    maxHeap.peek();
  }

  @Test 
  public void testPeekEnqueueDequeue() {
    boolean exceptionOccurred = false;
    try {
      minHeap.dequeueElement();
    } catch (Exception e) {
      exceptionOccurred = true;
    }
    assertTrue("Peeking from empty list", exceptionOccurred);
    assertEquals("Size of list should be 0", 0, minHeap.getSize());
    exceptionOccurred = false;
    try {
      maxHeap.dequeueElement();
    } catch (Exception e) {
      exceptionOccurred = true;
    }
    assertTrue("Dequeuing from empty list", exceptionOccurred);
    assertEquals("Size of list should be 0", 0, maxHeap.getSize());
    Condition condition = Condition.valueOf("SEVERE".toUpperCase());
    Patient p1 = new Patient("a", "b", 3, condition);
    minHeap.enqueueElement(p1);
    maxHeap.enqueueElement(p1);
    minHeap.dequeueElement();
    maxHeap.dequeueElement();
    exceptionOccurred = false;
    try {
      minHeap.dequeueElement();
    } catch (Exception e) {
      exceptionOccurred = true;
    }
    assertTrue("Dequeuing from empty list", exceptionOccurred);
    assertEquals("Size of list should be 0", 0, minHeap.getSize());
    exceptionOccurred = false;
    try {
      maxHeap.dequeueElement();
    } catch (Exception e) {
      exceptionOccurred = true;
    }
    assertTrue("Dequeuing from empty list", exceptionOccurred);
    assertEquals("Size of list should be 0", 0, maxHeap.getSize());
  }

  @Test(expected = QueueUnderflowException.class)
  public void testDequeueMinHeap() {
    minHeap.dequeueElement();
  }

  @Test(expected = QueueUnderflowException.class)
  public void testDequeueMaxHeap() {
    maxHeap.dequeueElement();
  }

  @Test 
  public void testEnqueueSizeIncreasing() {
    assertEquals(minHeap.getSize(), 0);
    assertEquals(minHeap.getSize(), 0);
    Condition condition = Condition.valueOf("SEVERE".toUpperCase());
    Patient first = null;
    for (int i = 1; i < 200; i++) {
      Patient p = new Patient("a", "b", i, condition);
      if (i == 1) {
        first = p;
      }
      minHeap.enqueueElement(p);
      maxHeap.enqueueElement(p);
      assertEquals(minHeap.getSize(), i);
      assertEquals(p, maxHeap.peek());
      assertEquals(first, minHeap.peek());

    }
  }

  @Test 
  public void testEnqueueSizeDecreasing() {
    assertEquals(minHeap.getSize(), 0);
    assertEquals(minHeap.getSize(), 0);
    Condition condition = Condition.valueOf("SEVERE".toUpperCase());
    Patient first = null;
    for (int i = 1; i < 200; i++) {
      Patient p = new Patient("a", "b", 200 - i, condition);
      if (i == 1) {
        first = p;
      }
      minHeap.enqueueElement(p);
      maxHeap.enqueueElement(p);
      assertEquals(minHeap.getSize(), i);
      assertEquals(first, maxHeap.peek());
      assertEquals(p, minHeap.peek());

    }
  }

  @Test 
  public void testEnqueueDequeueSizeIncreasing() {
    assertEquals(minHeap.getSize(), 0);
    assertEquals(minHeap.getSize(), 0);
    Condition condition = Condition.valueOf("SEVERE".toUpperCase());
    Patient first = null;
    Patient[] allPatients = new Patient[200];
    for (int i = 1; i < 200; i++) {
      Patient p = new Patient("a", "b", i, condition);
      allPatients[i] = p;
      if (i == 1) {
        first = p;
      }
      minHeap.enqueueElement(p);
      maxHeap.enqueueElement(p);
      assertEquals(minHeap.getSize(), i);
      assertEquals(p, maxHeap.peek());
      assertEquals(first, minHeap.peek());
    }
    for (int i = 1; i < 200; i++) {
      assertEquals(minHeap.getSize(), 200 - i);
      assertEquals(allPatients[200 - i], maxHeap.peek());
      assertEquals(allPatients[i], minHeap.peek());
      minHeap.dequeueElement();
      maxHeap.dequeueElement();
    }

  }

  @Test
  public void testEnqueueDequeueSizeDecreasing() {
    assertEquals(minHeap.getSize(), 0);
    assertEquals(minHeap.getSize(), 0);
    Condition condition = Condition.valueOf("SEVERE".toUpperCase());
    Patient first = null;
    Patient[] allPatients = new Patient[200];
    for (int i = 1; i < 200; i++) {
      Patient p = new Patient("a", "b", 200 - i, condition);
      allPatients[i] = p;
      if (i == 1) {
        first = p;
      }
      minHeap.enqueueElement(p);
      maxHeap.enqueueElement(p);
      assertEquals(minHeap.getSize(), i);
      assertEquals(first, maxHeap.peek());
      assertEquals(p, minHeap.peek());
    }
    for (int i = 1; i < 200; i++) {
      assertEquals(minHeap.getSize(), 200 - i);
      assertEquals(allPatients[i], maxHeap.peek());
      assertEquals(allPatients[200 - i], minHeap.peek());
      minHeap.dequeueElement();
      maxHeap.dequeueElement();
    }
  }

  @Test
  public void stressTest() {    
    String fileName = "large_input.txt";
    TriageSystemParser triageFileParser = new TriageSystemParser();
    try {
      triageFileParser.readPatientFile(fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }

    PatientConditionComparator conditionComparator = new PatientConditionComparator();
    ArrayList<Patient> patients = triageFileParser.getPatients();

    boolean[] maxMinHeaps = { true, false };
    for (int i = 0; i < 2; i++) {
      boolean isMaxHeap = maxMinHeaps[i];

      final long begHeap = System.nanoTime();
      PriorityQueueADT<Patient> conditionPQ = new Heap<Patient>(conditionComparator, isMaxHeap);
      for (Patient patient : patients) {
        conditionPQ.enqueueElement(patient);
      }

      long ellapsedHeap = System.nanoTime() - begHeap;
      final long ourTime = 5061555;
      int insertionIndex = 0;
      ArrayList<Patient> insertionArray = new ArrayList<Patient>();
      insertionArray.add(patients.get(0));
      for (int j = 1; j < patients.size(); ++j) { // insertion sort algorithm int
        insertionIndex = 0;
        Collections.binarySearch(insertionArray, patients.get(j), conditionComparator);
        insertionIndex += (insertionIndex < 0) ? 1 : 0;
        insertionArray.add(Math.abs(insertionIndex), patients.get(j));
      }
      assertTrue(comparePatientOrder(conditionPQ, insertionArray, conditionComparator, isMaxHeap));
    }

  }

  private static final boolean comparePatientOrder(PriorityQueueADT<Patient> heap, ArrayList<Patient> array,
      Comparator<Patient> comparator, boolean isMaxHeap) {

    int order = 1;
    int i = 0;
    if (isMaxHeap) {
      i = array.size() - 1;
      order = -1;
    }

    Collections.sort(array, comparator);
    boolean isCorrect = true;
    while (!heap.isEmpty()) {
      Patient patient = heap.dequeueElement();
      isCorrect = isCorrect && (comparator.compare(patient, array.get(i)) == 0);
      i += order;
    }

    return isCorrect;

  }

}
