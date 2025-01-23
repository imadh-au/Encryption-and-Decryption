package dm;
import java.util.*;
public class BreakCeaserCipher {
	private static final int MOD = 256;
	private static final char MOST_COMMON_LETTER = 'E'; // Assuming 'E' is the most frequent letter in English

	// Break the Caesar cipher using frequency analysis
	public String breakCipher(String cipherText) {
	Map<Character, Integer> frequencyMap = new HashMap<>();

	// Count frequency of each character
	for (char c : cipherText.toCharArray()) {
	frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
	}

	// Find the most frequent character
	char mostFrequentChar = Collections.max(frequencyMap.entrySet(), Map.Entry.comparingByValue()).getKey();
	int probableKeyB = (mostFrequentChar - MOST_COMMON_LETTER + MOD) % MOD;

	System.out.println("Most Frequent Character: " + mostFrequentChar);
	System.out.println("Probable Shift Key (b): " + probableKeyB);

	CeaserCipher caesar = new CeaserCipher();
	return caesar.decrypt(cipherText, 1, probableKeyB); // Assume multiplier a = 1 (standard Caesar cipher)
	}

}
