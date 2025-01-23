package dm;

public class CeaserCipher {
	private static final int MOD = 256; // Modulo value to handle all ASCII characters

	// ENCRYPTION FROM TEXT TO GENERALIZED CAESAR CIPHER
	public String encrypt(String text, int a, int b) {
	// Ensure a is invertible under modulo MOD
	if (gcd(a, MOD) != 1) {
	throw new IllegalArgumentException("a must be invertible under modulo " + MOD);
	}

	StringBuilder result = new StringBuilder(); // Mutable StringBuilder for efficient string manipulation
	// Loop through each character in the text
	for (int i = 0; i < text.length(); i++) {
	char c = text.charAt(i); // Get the character at index i
	// Apply encryption formula
	result.append((char) ((a * c + b) % MOD)); 
	}
	return result.toString(); // Return the encrypted string
	}

	// DECRYPTION FROM GENERALIZED CAESAR CIPHER TO TEXT
	public String decrypt(String text, int a, int b) {
	// Find modular inverse of a under MOD
	int aInverse = modInverse(a, MOD);
	StringBuilder result = new StringBuilder(); // Mutable StringBuilder for decrypted text
	// Loop through each character
	for (int i = 0; i < text.length(); i++) {
	char c = text.charAt(i); // Get the character at index i
	// Apply decryption formula
	result.append((char) ((aInverse * ((c - b + MOD) % MOD)) % MOD)); 
	}
	return result.toString(); // Return the decrypted string
	}

	// GCD function using recursion
	private int gcd(int a, int b) {
	return b == 0 ? a : gcd(b, a % b); // Base case: b == 0, return a
	}

	// Modular inverse function using brute force
	private int modInverse(int a, int m)
	{
	// Ensure a is invertible by checking GCD
	if (gcd(a, m) != 1) {
	throw new IllegalArgumentException("a is not invertible under modulo " + m);
	}

	for (int x = 1; x < m; x++) {
	if ((a * x) % m == 1) {
	return x; // Return modular inverse when condition is satisfied
	}
	}
	throw new IllegalArgumentException("No modular inverse found for " + a); // If no inverse exists
	}

}
