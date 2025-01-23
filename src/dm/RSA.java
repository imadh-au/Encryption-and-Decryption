package dm;
import java.util.Random;

public class RSA {

	private int p, q, n, phi, e, d;
	private String rsakeys;

	// Generate keys using random prime numbers
	public void generateKeysWithRandomPrimes() {
	Random random = new Random();

	// Generate random primes for p and q
	p = generateRandomPrime(random);
	q = generateRandomPrime(random);

	// Ensure p and q are not the same
	while (p == q) {
	q = generateRandomPrime(random);
	}

	// Calculate n = p * q
	n = p * q;

	// Calculate phi(n) = (p - 1) * (q - 1)
	phi = (p - 1) * (q - 1);

	// Choose e such that 1 < e < phi and gcd(e, phi) = 1
	e = 3; // Start with a small odd integer
	while (gcd(e, phi) != 1) {
	e += 2; // Increment to find the next coprime
	}

	// Calculate d as the modular inverse of e modulo phi
	d = modInverse(e, phi);

	// Display the generated keys
	System.out.println("Generated Random Prime Numbers: p = " + p + ", q = " + q);
	System.out.println("Public Key (e, n): (" + e + ", " + n + ")");
	System.out.println("Private Key (d, n): (" + d + ", " + n + ")");
	
	rsakeys="Prime Numbers: p = " + p + ", q = " + q+"\nPublic Key (e, n): (" + e + ", " + n + ")"+"\nPrivate Key (d, n): (" + d + ", " + n + ")";
	
	}
	public String getrakeys() {
		return rsakeys;
	}
	// Generate a random prime number within a specific range
	private int generateRandomPrime(Random random) {
	while (true) {
	int candidate = random.nextInt(100) + 50; // Generate random number in range 50-150
	if (isPrime(candidate)) {
	return candidate;
	}
	}
	}

	// Check if a number is prime
	private boolean isPrime(int number) {
	if (number <= 1) return false;
	for (int i = 2; i <= Math.sqrt(number); i++) {
	if (number % i == 0) return false;
	}
	return true;
	}

	//Encryption
	public String encryptString(String message) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : message.toCharArray()) {
            int encryptedChar = encrypt((int) c);
            encrypted.append((char)encryptedChar);
        }
        return encrypted.toString().trim();
    }

    // Decrypt a string using RSA
	public String decryptString(String cipherText) {
	    StringBuilder decrypted = new StringBuilder();
	    for (char encryptedChar : cipherText.toCharArray()) {
	        int decryptedChar = decrypt((int) encryptedChar); // Decrypt the character's integer value
	        decrypted.append((char) decryptedChar); // Convert to character and append
	    }
	    return decrypted.toString();
	}

//    public String decryptString(String cipherText) {
//        StringBuilder decrypted = new StringBuilder();
//        String[] encryptedChars = cipherText.split(" ");
//        for (String encryptedChar : encryptedChars) {
//            int decryptedChar = decrypt(Integer.parseInt(encryptedChar));
//            decrypted.append((char) decryptedChar);
//        }
//        return decrypted.toString();
//    }

    // Encrypt a character using RSA
    private int encrypt(int message) {
        return modularExponentiation(message, e, n);
    }

    // Decrypt a character using RSA
    private int decrypt(int cipherText) {
        return modularExponentiation(cipherText, d, n);
    }

	// GCD function using the Euclidean algorithm
	private int gcd(int a, int b) {
	return b == 0 ? a : gcd(b, a % b);
	}

	// Modular inverse function
	private int modInverse(int a, int m) {
	for (int x = 1; x < m; x++) {
	if ((a * x) % m == 1) {
	return x;
	}
	}
	throw new IllegalArgumentException("No modular inverse found for " + a);
	}

	// Modular exponentiation function to compute (base^exp) % mod efficiently
	private int modularExponentiation(int base, int exp, int mod) {
	int result = 1;
	base = base % mod;

	while (exp > 0) {
	if ((exp & 1) == 1) { // If exp is odd
	result = (result * base) % mod;
	}

	exp = exp >> 1; // Divide exp by 2
	base = (base * base) % mod;
	}

	return result;
	}

}
