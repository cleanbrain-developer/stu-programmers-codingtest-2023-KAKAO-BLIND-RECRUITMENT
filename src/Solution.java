import java.util.ArrayList;
import java.util.List;

class Solution {
	public int[] solution(String today, String[] terms, String[] privacies) {
		List<Integer> answerList = new ArrayList<Integer>();
		for (int i = 0; i < privacies.length; i++) {
			String[] privacyAry = privacies[i].split(" ");
			String privacyDate = privacyAry[0];
			String privacyTermKey = privacyAry[1];
			processAnswerList(answerList, i, terms, today, privacyTermKey, privacyDate);
		}
		if (answerList.size() > 0) {
			int[] answerAry = new int[answerList.size()];
			for (int i = 0; i < answerList.size(); i++) {
				answerAry[i] = answerList.get(i);
			}
			return answerAry;
		} else {
			return new int[0];
		}
	}

	private int getTermValue(String[] terms, String paramTermKey) {
		for (String term : terms) {
			String[] termAry = term.split(" ");
			String key = termAry[0];
			String value = termAry[1];
			if (key.equalsIgnoreCase(paramTermKey)) {
				return Integer.parseInt(value);
			}
		}
		return 0;
	}

	private String[] getExpiredDateAry(String privacyDate, int termValue) {
		String[] todayAry = privacyDate.split("\\.");
		int yyyy = Integer.parseInt(todayAry[0]);
		int mm = Integer.parseInt(todayAry[1]);
		int dd = Integer.parseInt(todayAry[2]);

		if ((mm + termValue) / 12 > 0) {
			if ((mm + termValue) % 12 > 0) {
				yyyy = yyyy + ((mm + termValue) / 12);
				mm = (mm + termValue) % 12;
			} else {
				yyyy = yyyy + ((mm + termValue) / 12) - 1;
				mm = 12;
			}
		} else {
			mm = mm + termValue;
		}
		return this.getDateAry(yyyy, mm, dd);
	}

	private String[] getDateAry(int yyyy, int mm, int dd) {
		String yyyyStr = String.valueOf(yyyy);
		String mmStr;
		if (mm < 10) {
			mmStr = "0" + mm;
		} else {
			mmStr = String.valueOf(mm);
		}
		String ddStr = String.valueOf(dd);
		return new String[] { yyyyStr, mmStr, ddStr };
	}

	private void processAnswerList(List answerList, int index, String[] terms, String today, String privacyTermKey, String privacyDate) {
		String[] todayAry = today.split("\\.");
		int termValue = this.getTermValue(terms, privacyTermKey);
		String[] expiredDateAry = getExpiredDateAry(privacyDate, termValue);

		int yyyy = Integer.parseInt(expiredDateAry[0]) - Integer.parseInt(todayAry[0]);
		int mm = Integer.parseInt(expiredDateAry[1]) - Integer.parseInt(todayAry[1]);
		int dd = Integer.parseInt(expiredDateAry[2]) - Integer.parseInt(todayAry[2]);

		if (yyyy < 0) {
			answerList.add(index + 1);
		} else if (yyyy == 0) {
			if (mm < 0) {
				answerList.add(index + 1);
			} else if (mm == 0) {
				if (dd < 0) {
					answerList.add(index + 1);
				} else if (dd == 0) {
					answerList.add(index + 1);
				}
			}
		}
	}
}