package app;

import java.util.ArrayList;
import java.util.Comparator;
import java.io.IOException;
import priorityqueue.*;

public class AppMain {

  public static void main(String[] args) {

    String fileName = "small_input.txt";
    TriageSystemParser triageFileParser = new TriageSystemParser();
    try {
      triageFileParser.readPatientFile(fileName);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    ArrayList<Patient> patients = triageFileParser.getPatients();

    Comparator<Patient> comparator = new PatientConditionComparator();
    boolean isMaxHeap = true;
    
    PriorityQueueADT<Patient> queue = new Heap<Patient> (comparator, isMaxHeap);
    for (Patient patient : patients) {
      queue.enqueueElement(patient);
    }
    while(!queue.isEmpty()){
        System.out.println(queue.dequeueElement());
    }
  }
}
