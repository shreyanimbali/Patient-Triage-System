package test;

import app.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import java.util.concurrent.TimeUnit;

public class ProjectTests {
    @Rule
	public Timeout globalTimeout = new Timeout(1000L, TimeUnit.MILLISECONDS);

	private static final Comparator<Integer> INTEGER_COMPARATOR = new IntegerComparator();
	private static final Comparator<String> STRING_COMPARATOR = new LexicographicStringComparator();

	SwapList<Integer> emptyList;
	AbstractSorter<Integer> emptySorterSelection;
    AbstractSorter<Integer> emptySorterInsertion;
    AbstractSorter<Integer> emptySorterQuick;

	SwapList<Integer> sortedList;
	SwapList<Integer> notSortedList;
	AbstractSorter<Integer> sortedSorterSelection;
    AbstractSorter<Integer> sortedSorterInsertion;
    AbstractSorter<Integer> sortedSorterQuick;

	SwapList<String> reversedList;
	AbstractSorter<String> reversedSorterSelection;
    AbstractSorter<String> reversedSorterInsertion;
    AbstractSorter<String> reversedSorterQuick;

	SwapList<Integer> firstAsLastList;
	AbstractSorter<Integer> firstAsLastSorterSelection;
    AbstractSorter<Integer> firstAsLastSorterInsertion;
    AbstractSorter<Integer> firstAsLastSorterQuick;

    @Before
	public void before() {
		//financial and location data stored in arrays, where NI=Net Incomes
		//note: location is in alphabetical order
		Integer[] emptyNI = {};
		Integer[] sortedNI = {-112, -35, 0, 24, 89};
		Integer[] notSortedNI = {8, -2, 12, 3, 7, 1, 9, 5, 8};
		Integer[] firstAsLastNI = {300, 400, 500, 600, 700, 800, 900, 200};
		String[] location = {"Albany", "Boston", "Charleston", "Denver", "Frankfort", "Honolulu", "Indianapolis", 
						   "Jackson", "Lincoln", "Montgomery", "Nashville", "Olympia", "Phoenix", "Raleigh", 
						   "Sacramento", "Tallahassee"};

		emptyList = new ArrayBasedSwapList<Integer>(emptyNI);
		emptySorterSelection = new SelectionSorter<Integer>(emptyList, INTEGER_COMPARATOR);
        emptySorterInsertion = new InsertionSorter<Integer>(emptyList, INTEGER_COMPARATOR);
        emptySorterQuick = new QuickSorter<Integer>(emptyList, INTEGER_COMPARATOR);

		sortedList = new ArrayBasedSwapList<Integer>(sortedNI);
		notSortedList = new ArrayBasedSwapList<Integer>(notSortedNI);
		sortedSorterSelection = new SelectionSorter<Integer>(sortedList, INTEGER_COMPARATOR);
        sortedSorterInsertion = new InsertionSorter<Integer>(sortedList, INTEGER_COMPARATOR);
        sortedSorterQuick = new QuickSorter<Integer>(sortedList, INTEGER_COMPARATOR);

		firstAsLastList = new ArrayBasedSwapList<Integer>(firstAsLastNI);
		firstAsLastSorterSelection = new SelectionSorter<Integer>(firstAsLastList, INTEGER_COMPARATOR);
        firstAsLastSorterInsertion = new InsertionSorter<Integer>(firstAsLastList, INTEGER_COMPARATOR);
        firstAsLastSorterQuick = new QuickSorter<Integer>(firstAsLastList, INTEGER_COMPARATOR);

		List<String> reverseAlphaList = new ArrayList<String>();		   
		for (int i = location.length-1; i >= 0; i--) {
			reverseAlphaList.add(location[i]);
		}
		reversedList = new ArrayBasedSwapList<String>(reverseAlphaList);
		reversedSorterSelection = new SelectionSorter<String>(reversedList, STRING_COMPARATOR);
        reversedSorterInsertion = new InsertionSorter<String>(reversedList, STRING_COMPARATOR);
        reversedSorterQuick = new QuickSorter<String>(reversedList, STRING_COMPARATOR);
	}

    // SELECTION SORTER TESTS
	@Test
	public void testEmptyListSelection() {
		SwapList<Integer> result = emptySorterSelection.sort();
		assertTrue("selection sort: test empty", result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
	public void testSortedListSelection() {
		SwapList<Integer> result = sortedSorterSelection.sort();
		assertTrue("selection sort: test sorted", result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
	public void testSortedComparisonsCountSelection() {
		SwapList<Integer> result = sortedSorterSelection.sort();
		final int n = sortedList.size();
		assertEquals("selection sort: test sorted # of comparisons", n * (n - 1) / 2, result.getComparisons());
	}

	@Test
	public void testSortedSwapsCountSelection() {
		SwapList<Integer> result = sortedSorterSelection.sort();
		final int n = sortedList.size();
		assertEquals("selection sort: test sorted # of swaps", n - 1, result.getSwaps());
	}

	@Test
	public void testFirstAsLastListSelection() {
		SwapList<Integer> result = firstAsLastSorterSelection.sort();
		assertTrue("selection sort: test first as last", result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
	public void testFirstAsLastComparisonsCountSelection() {
		SwapList<Integer> result = firstAsLastSorterSelection.sort();
		final int n = firstAsLastList.size();
		assertEquals("selection sort: test first as last # of comparisons", n * (n - 1) / 2, result.getComparisons());
	}

	@Test
	public void testFirstAsLastSwapsCountSelection() {
		SwapList<Integer> result = firstAsLastSorterSelection.sort();
		final int n = firstAsLastList.size();
		assertEquals("selection sort: test first as last # of swaps", n - 1, result.getSwaps());
	}

	@Test
	public void testReversedListSelection() {
		SwapList<String> result = reversedSorterSelection.sort();
		assertTrue("selection sort: test reversed", result.isSorted(STRING_COMPARATOR));
	}

	@Test
	public void testReversedComparisonsCountSelection() {
		SwapList<String> result = reversedSorterSelection.sort();
		final int n = reversedList.size();
		assertEquals("selection sort: test # of comparisons", (n * (n - 1) / 2), result.getComparisons());
	}

	@Test
	public void testReversedSwapsCountSelection() {
		SwapList<String> result = reversedSorterSelection.sort();
		final int n = reversedList.size();
		assertEquals("selection sort: test # of swaps", n - 1, result.getSwaps());
	}

	@Test
	public void testRandomListsSelection() {
		List<SwapList<Integer>> randomizedLists = new ArrayList<SwapList<Integer>>();
		Random random = new Random(0);

		//generate random list of net incomes (integers)
		for (int length = 1; length < Math.pow(2, 8); length *= 2) {
			for (int count = 0; count < Math.min(length, 10); count++) {
				List<Integer> list = new ArrayList<Integer>(length);

				for (int i = 0; i < length; i++) {
					int netIncome = random.nextInt();
					list.add(netIncome);
				}

				randomizedLists.add(new ArrayBasedSwapList<Integer>(list));
			}
		}

		for (SwapList<Integer> list : randomizedLists) {
			AbstractSorter<Integer> sorter = new SelectionSorter<Integer>(list, INTEGER_COMPARATOR);
			SwapList<Integer> result = sorter.sort();
			final int n = list.size();
			final int upperBound = (n * (n - 1) / 2);

			assertTrue("selection sort: test random", result.isSorted(INTEGER_COMPARATOR));
			assertTrue("selection sort: test random # of comparisons", result.getComparisons() <= upperBound);
			assertTrue("selection sort: test random # of swaps", result.getSwaps() <= upperBound);
		}
	}

    // INSERTION SORTER TESTS
    @Test
	public void testEmptyListInsertion() {
		SwapList<Integer> result = emptySorterInsertion.sort();
		assertTrue("insertion sort: test empty", result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
	public void testSortedListInsertion() {
		SwapList<Integer> result = sortedSorterInsertion.sort();
		assertTrue("insertion sort: test sorted", result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
	public void testSortedComparisonsCountInsertion() {
		SwapList<Integer> result = sortedSorterInsertion.sort();
		final int n = sortedList.size();
		assertEquals("insertion sort: test # of comparisons", n - 1, result.getComparisons());
	}

	@Test
	public void testSortedSwapsCountInsertion() {
		SwapList<Integer> result = sortedSorterInsertion.sort();
		assertEquals("insertion sort: test swaps", 0, result.getSwaps());
	}

	@Test
	public void testFirstAsLastListInsertion() {
		SwapList<Integer> result = firstAsLastSorterInsertion.sort();
		assertTrue("insertion sort: test first as last", result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
	public void testFirstAsLastComparisonsCountInsertion() {
		SwapList<Integer> result = firstAsLastSorterInsertion.sort();
		final int n = firstAsLastList.size();
		assertEquals("insertion sort: test # of comparisons", (n - 2) + (n - 1), result.getComparisons());
	}

	@Test
	public void testFirstAsLastSwapsCountInsertion() {
		SwapList<Integer> result = firstAsLastSorterInsertion.sort();
		final int n = firstAsLastList.size();
		assertEquals("insertion sort: test # of swaps", n - 1, result.getSwaps());
	}

	@Test
	public void testReversedListInsertion() {
		SwapList<String> result = reversedSorterInsertion.sort();
		assertTrue("insertion sort: test reversed", result.isSorted(STRING_COMPARATOR));
	}

	@Test
	public void testReversedComparisonsCountInsertion() {
		SwapList<String> result = reversedSorterInsertion.sort();
		final int n = reversedList.size();
		assertEquals("insertion sort: test # of comparisons", (n * (n - 1) / 2), result.getComparisons());
	}

	@Test
	public void testReversedSwapsCountInsertion() {
		SwapList<String> result = reversedSorterInsertion.sort();
		final int n = reversedList.size();
		assertEquals("insertion sort: test # of swaps", (n * (n - 1) / 2), result.getSwaps());
	}

	@Test
	public void testRandomListsInsertion() {
		List<SwapList<Integer>> randomizedLists = new ArrayList<SwapList<Integer>>();
		Random random = new Random(0);

		//generate random list of net incomes (integers)
		for (int length = 1; length < Math.pow(2, 8); length *= 2) {
			for (int count = 0; count < Math.min(length, 10); count++) {
				List<Integer> list = new ArrayList<Integer>(length);

				for (int i = 0; i < length; i++) {
					int netIncome = random.nextInt();
					list.add(netIncome);
				}

				randomizedLists.add(new ArrayBasedSwapList<Integer>(list));
			}
		}

		for (SwapList<Integer> list : randomizedLists) {
			AbstractSorter<Integer> sorter = new InsertionSorter<Integer>(list, INTEGER_COMPARATOR);
			SwapList<Integer> result = sorter.sort();
			final int n = list.size();
			final int upperBound = (n * (n - 1) / 2);

			assertTrue("insertion sort: test random list", result.isSorted(INTEGER_COMPARATOR));
			assertTrue("insertion sort: test random list # of comparisons", result.getComparisons() <= upperBound);
			assertTrue("insertion sort: test # of swaps", result.getSwaps() <= upperBound);
		}
	}

    // QUICK SORTER TESTS
    @Test
	public void testEmptyListQuick() {
		SwapList<Integer> result = emptySorterQuick.sort();
		assertTrue("quick sort: test empty", result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
	public void testSortedListQuick() {
		SwapList<Integer> result = sortedSorterQuick.sort();
		assertTrue("quick sort: test sorted", result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
	public void testSortedComparisonsCountQuick() {
		SwapList<Integer> result = sortedSorterQuick.sort();
		// note: 6 is the number the solution
		// returns; yours should not be much different
		assertTrue("quick sort: test sorted # of comparisons", result.getComparisons() <= 6);
	}

	@Test
	public void testSortedSwapsCountQuick() {
		SwapList<Integer> result = sortedSorterQuick.sort();
		// note: 8 is the number the solution
		// returns; yours should not be much different
		assertTrue("quick sort: test # of swaps", result.getSwaps() <= 8);
	}

	@Test
	public void testFirstAsLastListQuick() {
		SwapList<Integer> result = firstAsLastSorterQuick.sort();
		assertTrue("quick sort: test first as last", result.isSorted(INTEGER_COMPARATOR));
	}

	@Test
  public void testFirstAsLastComparisonsCountQuick() {
		SwapList<Integer> result = firstAsLastSorterQuick.sort();
		// note: 14 is the number the solution
		// returns; yours should not be much different
		assertTrue("quick sort: test first as last # of comparisons", result.getComparisons() <= 14);
	}

	@Test
	public void testFirstAsLastSwapsCountQuick() {
		SwapList<Integer> result = firstAsLastSorterQuick.sort();
		// note: 20 is the number the solution
		// returns; yours should not be much different
		assertTrue("quick sort: test first as last # of swaps", result.getSwaps() <= 20);
	}

	@Test
	public void testReversedListQuick() {
		SwapList<String> result = reversedSorterQuick.sort();
		assertTrue("quick sort: test reversed", result.isSorted(STRING_COMPARATOR));
	}

	@Test
	public void testReversedComparisonsCountQuick() {
		SwapList<String> result = reversedSorterQuick.sort();
		// note: 43 is the number the solution
		// returns; yours should not be much different
		assertTrue("quick sort: test reversed # of comparisons", result.getComparisons() <= 43);
	}

	@Test
	public void testReversedSwapsCountQuick() {
		SwapList<String> result = reversedSorterQuick.sort();
		// note: 42 is the number the solution
		// returns; yours should not be much different
		assertTrue("quick sort: test # of swaps", result.getSwaps() <= 42);
	}

	@Test
	public void testRandomListsQuick() {
		List<SwapList<Integer>> randomizedLists = new ArrayList<SwapList<Integer>>();
		Random random = new Random(0);

		//generate random list of net incomes (integers)
		for (int length = 1; length < Math.pow(2, 8); length *= 2) {
			for (int count = 0; count < Math.min(length, 10); count++) {
				List<Integer> list = new ArrayList<Integer>(length);

				for (int i = 0; i < length; i++) {
					int netIncome = random.nextInt();
					list.add(netIncome);
				}

				randomizedLists.add(new ArrayBasedSwapList<Integer>(list));
			}
		}

		for (SwapList<Integer> list : randomizedLists) {
			AbstractSorter<Integer> sorter = new QuickSorter<Integer>(list, INTEGER_COMPARATOR);
			SwapList<Integer> result = sorter.sort();

			assertTrue("quick sort: test random", result.isSorted(INTEGER_COMPARATOR));
		}
	}

	// SCORE LIST TESTS
	@Test
	public void testScoreList100() {
		int result = sortedList.scoreList(INTEGER_COMPARATOR);
		assertEquals("scoreList: 100% score", 100, result);
	}

	@Test
	public void testScoreList50() {
		int result = notSortedList.scoreList(INTEGER_COMPARATOR);
		assertEquals("scoreList: 50% score", 50, result);
	}

	@Test
	public void testScoreList0() {
		int result = reversedList.scoreList(STRING_COMPARATOR);
		assertEquals("scoreList: 0% score", 0, result);
	}
}
