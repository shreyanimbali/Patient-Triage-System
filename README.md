# Patient-Triage-System

## Overview 
This is an implementation of a futuristic automated emergency room (ER) triage system which receives data in the form of patient and injury pairs over time and automatically
assign patients to doctors as the resources become available depending on the severity of the patient’s injury. This is implemented using a data structure called **priority queue** – more
specifically, a **heap-based priority queue**. 

As patients are treated and released, they are removed from the waiting list. Priority is given to the patient's condition, which has the following priority levels: LIGHT, MILD, SEVERE, and CRITICAL. As a result, all CRITICAL patients would receive treatment first, followed by SEVERE patients, etc. This application can also prioritize patients by age to provide greater flexibility.

## Code Structure
The **Heap.java** file contains the definition of a Heap class. The **Patient.java** file contains all the relevant patient information for your triage. Patients' health conditions are defined in the **Condition.java** file with an Enum. This is an elegant way to define the constant values of conditions that the triage system uses. The **TriageSystemParser** class implements all the necessary operations to parse a triage input data file (for e.g. small_input.txt or large_input.txt).

**Comparator Interface** : The program makes use of the Java Comparator interface. Like the Comparable interface, it allows data to be compared and thus ordered. Comparator is more flexible as the way data can be compared can be implemented by different Comparator classes. These are the two Comparators used in this program:

**PatientAgeComparator.java**
The compare method performs the comparison between the age attribute of patients pt1 and pt2. The age of a patient is an integer.

**PatientConditionComparator.java**
The compare method for this Comparator performs the comparison between the condition attribute of patients pt1 and pt2.





