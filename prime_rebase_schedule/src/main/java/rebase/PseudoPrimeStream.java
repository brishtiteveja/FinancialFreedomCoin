package rebase;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PseudoPrimeStream implements Spliterator<BigInteger> {

    private BigInteger currentPrime;
    private final BigInteger lowerBoundInclusive;
    private BigInteger upperBoundExclusive;

//    private static final double PRIME_COUNTING_ERROR_FACTOR = 1.05;

    private PseudoPrimeStream(long lowerBoundInclusive, long upperBoundExclusive) {
        this(BigInteger.valueOf(lowerBoundInclusive - 1), BigInteger.valueOf(upperBoundExclusive));
    }


    private PseudoPrimeStream(BigInteger lowerBoundExact, BigInteger upperBoundExclusive) {
        this.currentPrime = lowerBoundExact.nextProbablePrime();
        this.lowerBoundInclusive = this.currentPrime;
        this.upperBoundExclusive = upperBoundExclusive;
    }

    public static Stream<BigInteger> pseudoPrimeStream(long lowerBoundInclusive, long upperBoundExclusive) {
        if (lowerBoundInclusive >= upperBoundExclusive) throw new IllegalStateException(String.format("%d cannot be greater than or equal to %d", lowerBoundInclusive, upperBoundExclusive));
        return StreamSupport.stream(new PseudoPrimeStream(lowerBoundInclusive, upperBoundExclusive), false);
    }

    @Override
    public boolean tryAdvance(Consumer<? super BigInteger> action) {
        if (currentPrime.compareTo(upperBoundExclusive) < 0) {
            action.accept(currentPrime);
            currentPrime = currentPrime.nextProbablePrime();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Spliterator<BigInteger> trySplit() {
        // split current
        var middle = this.upperBoundExclusive.add(this.lowerBoundInclusive).divide(BigInteger.TWO);
        var split = new PseudoPrimeStream(middle, this.upperBoundExclusive);
        this.upperBoundExclusive = middle;
        return split;
    }

    @Override
    public Comparator<BigInteger> getComparator() {
        return null;
    }

    @Override
    public long estimateSize() {
//        var estimate =  (long) ((this.upperBoundExclusive.longValue() / Math.log(this.upperBoundExclusive.longValue())) -
//                ( this.lowerBoundInclusive.longValue() / Math.log(lowerBoundInclusive.longValue())));
//        return (long) (estimate * PRIME_COUNTING_ERROR_FACTOR);
        return this.upperBoundExclusive.subtract(this.lowerBoundInclusive).longValue();
    }

    @Override
    public int characteristics() {
        return ORDERED | SORTED | DISTINCT;
    }
}
