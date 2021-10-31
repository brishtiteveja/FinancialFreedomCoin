package rebase;

import org.apache.commons.math3.util.ArithmeticUtils;
import org.apache.commons.math3.util.FastMath;

import java.math.BigInteger;

public class PrimalitySolver {

    public static final long MERSENNE_PRIME_MAX = 2305843009213693951L;

    public static long[] isPrime(long candidate) {
        var prime = millerRabinTest(candidate);
        return prime ? new long[]{1L, candidate} : new long[]{0L, candidate};
    }

    /**
     * Miller-Rabin primality test is the fastest way to check if number is prime.
     * Regular version of this algorithm returns false when number is composite and true
     * when number is probably prime. Here is implemented a deterministic version of this
     * algorithm, witnesses are not randomized. Used set of witnesses guarantees that result
     * will be correct for sure (not probably) for any number lower than 10^18.
     * <p>
     * @see <a href="https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test">Miller-Rabin Primality Test (Wikipedia)</a>
     * <br>
     */

    private static final long[] witnesses = {2, 325, 9375, 28178, 450775, 9780504, 1795265022};


    public static boolean millerRabinTest(long number) {

        if (number == 0 || number == 1)
            return false;
        if (number == 2 || number == 3)
            return true;

        var maximumPowerOf2 = 0;
        while (((number - 1) % pow(2, maximumPowerOf2)) == 0)
            maximumPowerOf2++;
        maximumPowerOf2--;

        long d = (number - 1) / pow(2, maximumPowerOf2);
        var isPrime = true;
        int i = 0, witnessesLength = witnesses.length;

        while (i < witnessesLength) {
            long a = witnesses[i];

            if (a > number)
                break;
            if (modulorExponentation(a, d, number) != 1) {
                var isLocalPrime = false;
                for (var r = 0; r < maximumPowerOf2; r++) {

                    if (modulorExponentation(a, d * pow(2, r), number) == (number - 1)) {
                        isLocalPrime = true;
                        break;
                    }
                }
                if (!isLocalPrime) {
                    isPrime = false;
                    break;
                }
            }
            i++;
        }

        return isPrime;
    }

    private static long pow(long base, int power) {
//        return ArithmeticUtils.pow(base, power);
        return fastRecursiveExponentiation(base, power);
    }

    public static long modulorExponentation(long base, long exponent, long mod) {
        return BigInteger.valueOf(base).modPow(BigInteger.valueOf(exponent), BigInteger.valueOf(mod)).longValue();
    }

    public static long fastRecursiveExponentiation(long base, long exponent) {
        if (exponent == 0)
            return 1L;
        if (exponent == 1)
            return base;

        final long resultOnHalfExponent = fastRecursiveExponentiation(base, exponent / 2);
        if ((exponent % 2) == 0)
            return resultOnHalfExponent * resultOnHalfExponent;
        else
            return resultOnHalfExponent * resultOnHalfExponent * base;
    }

    public static long fastRecursiveExponentiationModulo(long base, long exponent, long mod) {
        if (exponent == 0)
            return 1L;
        if (exponent == 1)
            return base;

        final long resultOnHalfExponent = fastRecursiveExponentiationModulo(base, exponent / 2, mod);
        if ((exponent % 2) == 0)
            return (resultOnHalfExponent * resultOnHalfExponent) % mod;
        else
            return (((resultOnHalfExponent * resultOnHalfExponent) % mod) * base) % mod;
    }
}
