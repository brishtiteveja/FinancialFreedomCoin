package rebase;

import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PseudoPrimeIteratorTest {


    @Test
    void happyPathTest() {

        var actual = PseudoPrimeStream.pseudoPrimeStream(2, 12)
                                                .map(BigInteger::intValueExact)
                                                .collect(Collectors.toList());

        assertEquals(actual, List.of(2, 3, 5, 7, 11));
    }

    @Test
    void parrellelHappyPathTest() {

        var measurement = timer(() -> {
            Stream<BigInteger> stream = PseudoPrimeStream.pseudoPrimeStream(1_000_000, 10_000_000).parallel();
            return stream.parallel().count();
        });

        System.out.println("Parallel count");
        System.out.println(measurement);

        var normalMeasurement = timer(() -> {
            Stream<BigInteger> normalStream = PseudoPrimeStream.pseudoPrimeStream(1_000_000, 10_000_000);
            return normalStream.count();
        });
        System.out.println("Regular count");
        System.out.println(normalMeasurement);
    }

    public  <T> Pair<T, Duration> timer(Supplier<T> supplier) {
        long current = System.nanoTime();
        var duration = Duration.ofNanos(System.nanoTime() - current);
        return Pair.create(supplier.get(), duration);
    }
}