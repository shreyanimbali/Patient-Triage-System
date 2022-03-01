package app;

import java.util.Comparator;


public class QuickSorter<T> extends AbstractSorter<T> {

	public QuickSorter(SwapList<T> list, Comparator<T> comparator) {
		super(list, comparator);
	}

	public void quicksort(int low, int high) {
		if (low >= high) {
			return;
		}
		int lowEnd = partition(low, high);
		quicksort(low, lowEnd-1);
   		quicksort(lowEnd+1, high);
	}

	@Override
	public SwapList<T> sort() {
		// TODO sort
		if(list.size() != 0){
			quicksort(0, list.size()-1);
			return list;
		}
		return list;
		
	}
	
	public int partition(int low,int high) {
 		int pivot = (high+low)/2;
 		int lowEnd = low;
 		int j;
		list.swap(pivot,high);
 		for(j=low; j<=high-1; j++) {
 			if(list.compare(j,high,comparator) <= 0){
 				list.swap(j, lowEnd);
 				lowEnd++;
			}
		}
 		list.swap(lowEnd, high);
 		return lowEnd;
	}
}
