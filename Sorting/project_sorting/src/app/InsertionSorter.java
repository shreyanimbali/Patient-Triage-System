package app;

import java.util.Comparator;


public class InsertionSorter<T> extends AbstractSorter<T> {

	public InsertionSorter(SwapList<T> list, Comparator<T> comparator) {
		super(list, comparator);
	}

	@Override
	public SwapList<T> sort() {
		// TODO sort
		if(list.size() != 0){
			for (int i = 1; i < list.size(); i++) {
				int j = i;
				while (j>0 && list.compare(j,j-1,comparator) < 0){
					list.swap(j, j-1);
					j--;
				}
			
			}
			return list;
		}
		return list;
		
	}
}
