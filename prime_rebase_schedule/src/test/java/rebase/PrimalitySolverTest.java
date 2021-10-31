package rebase;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;
import static rebase.PrimalitySolver.MERSENNE_PRIME_MAX;

class PrimalitySolverTest {

    @Test
    void happyPathCheck() {
        assertEquals(PrimalitySolver.isPrime(131071)[0], 1);
        assertEquals(PrimalitySolver.isPrime(2147483659L)[0], 1);
        assertEquals(PrimalitySolver.isPrime(2147483647L)[0], 1);
        assertEquals(PrimalitySolver.isPrime(3042000007L)[0], 1);
        assertEquals(PrimalitySolver.isPrime(4000000007L)[0], 1); // Always fails
        assertEquals(PrimalitySolver.isPrime(4194304903L)[0], 1); // Always fails
        assertEquals(PrimalitySolver.isPrime(4294967291L)[0], 1); // Always fails
        assertEquals(PrimalitySolver.isPrime(4294967311L)[0], 1); // Always fails
        assertEquals(PrimalitySolver.isPrime(MERSENNE_PRIME_MAX)[0], 1); // Always fails
    }

    private static long[] isPrimeJava(long candidate) {
        var prime = BigInteger.valueOf(candidate).isProbablePrime(100);
        return prime ? new long[]{1L, candidate} : new long[]{0L, candidate};
    }

    @Test
    void happyPathCheck2() {
        assertEquals(isPrimeJava(131071)[0], 1);
        assertEquals(isPrimeJava(2147483659L)[0], 1);
        assertEquals(isPrimeJava(2147483647L)[0], 1);
        assertEquals(isPrimeJava(3042000007L)[0], 1);
        assertEquals(isPrimeJava(4000000007L)[0], 1); // Always fails
        assertEquals(isPrimeJava(4194304903L)[0], 1); // Always fails
        assertEquals(isPrimeJava(4294967291L)[0], 1); // Always fails
        assertEquals(isPrimeJava(4294967311L)[0], 1); // Always fails
        assertEquals(isPrimeJava(MERSENNE_PRIME_MAX)[0], 1); // Always fails
    }

    @Test
    void quadrillion() {
        long quad = 1_000_000_000_000_000L;
        var prime = BigInteger.valueOf(quad).nextProbablePrime();
        System.out.println(prime);
    }
}