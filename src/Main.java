import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * User: clean_brain
 * Date: 2023-12-04
 * Comments: 
 * </pre>
 */
public class Main {
	private static String today = "";
	private static String[] terms;
	private static String[] privacies;

	private static List<Integer> resultList = new ArrayList<>();

	public static void main(String[] args) {
		// 기댓값 : [1, 3]
		// init1();
		// 기댓값 : [1, 4, 5]
		// init2();
		// 기댓값 : [1]
		// init3();
		// 기댓값 : [2]
		// init4();
		// 기댓값 : [1, 2]
		// init5();
		// 기댓값 : [1, 2]
		init6();
		// 기댓값 : [1]
		// init7();
		// 기댓값 : [11]
		// init8();
		// 기댓값 : [2]
		// init9();
		// 기댓값 : [2]
		// init10();
		int[] answerAry = new Solution().solution(today, terms, privacies);
		List<Integer> list = new ArrayList<>();
		for (int answer : answerAry) {
			list.add(answer);
		}
		System.out.println(list);
	}

	// 기댓값 : [1, 3]
	private static void init1() {
		today = "2022.05.19";
		terms = new String[] { "A 6", "B 12", "C 3" };
		privacies = new String[] { "2021.05.02 A", "2021.07.01 B", "2022.02.19 C" };
	}

	// 기댓값 : [1, 4, 5]
	private static void init2() {
		today = "2020.01.01";
		terms = new String[] { "Z 3", "D 5" };
		privacies = new String[] { "2019.01.01 D", "2019.11.15 Z", "2019.08.02 D", "2019.07.01 D", "2018.12.28 Z" };
	}

	// 기댓값 : [1]
	private static void init3() {
		today = "2020.01.01";
		terms = new String[] { "A 1" };
		privacies = new String[] { "2019.12.01 A" };
	}

	// 기댓값 : [2]
	private static void init4() {
		today = "2021.01.28";
		terms = new String[] { "A 2" };
		privacies = new String[] { "2020.12.01 A", "2010.01.01 A" };
	}

	// 기댓값 : [1, 2]
	private static void init5() {
		today = "2021.05.15";
		terms = new String[] { "A 1", "B 18" };
		privacies = new String[] { "2000.12.01 A", "2019.10.15 B" };
	}

	// 기댓값 : [1, 2]
	private static void init6() {
		today = "2020.12.17";
		terms = new String[] { "A 12" };
		privacies = new String[] { "2010.01.01 A", "2019.12.17 A" };
	}

	// 기댓값 : [1]
	private static void init7() {
		today = "2020.01.02";
		terms = new String[] { "A 1", "B 11" };
		privacies = new String[] { "2019.12.01 A", "2020.01.01 B" };
	}

	// 기댓값 : [11]
	private static void init8() {
		today = "2020.01.01";
		terms = new String[] { "A 1" };
		privacies = new String[] { "2019.12.09 A", "2019.12.09 A", "2019.12.09 A", "2019.12.09 A", "2019.12.09 A", "2019.12.09 A", "2019.12.09 A", "2019.12.09 A", "2019.12.09 A", "2019.12.09 A", "2019.12.01 A" };
	}

	// 기댓값 : [2]
	private static void init9() {
		today = "2010.01.01";
		terms = new String[] { "Z 12" };
		privacies = new String[] { "2009.12.01 Z", "2001.01.01 Z" };
	}

	// 기댓값 : [2]
	private static void init10() {
		today = "2020.10.15";
		terms = new String[] { "A 100" };
		privacies = new String[] { "2018.06.16 A", "2008.02.15 A" };
	}
}
