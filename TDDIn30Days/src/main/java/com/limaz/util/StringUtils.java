package com.limaz.util;

public class StringUtils {

	public int FindNumberOfOccurences(String sentenceToScan, String characterToScanFor) {
		int numberOfOccurrences = 0;

		for (int i = 0; i < sentenceToScan.length(); i++) {
			if (sentenceToScan.charAt(i) == characterToScanFor.charAt(0)) {
				numberOfOccurrences++;
			}
		}
		return numberOfOccurrences;
	}

}
