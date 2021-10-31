package rebase;

import org.apache.commons.math3.util.Pair;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RebaseScheduler {

    private static final int CONCURRENCY = Runtime.getRuntime().availableProcessors();

    private static final ExecutorService executor = Executors.newFixedThreadPool(CONCURRENCY);

    public static List<BigInteger> findPrimesAllProbablePrimes(long lowerInclusive, long upperExclusive) {

        int capacity = (int) ((upperExclusive / Math.log(upperExclusive)) - ( lowerInclusive / Math.log(lowerInclusive)));

        var probablePrimes = new ArrayList<BigInteger>(capacity);

        var lower  = BigInteger.valueOf(lowerInclusive);
        var upper = BigInteger.valueOf(upperExclusive);

        for (var curr = lower; curr.compareTo(upper) < 0; curr = curr.nextProbablePrime()) {
            probablePrimes.add(curr);
        }

        return probablePrimes;
    }

    public static <E> List<E> pickNRandomElements(List<E> list, int n, Random r) {
        int length = list.size();

        if (length < n) return null;

        //We don't need to shuffle the whole list
        for (int i = length - 1; i >= length - n; --i) {
            Collections.swap(list, i , r.nextInt(i + 1));
        }

        return list.subList(length - n, length);
    }

    public static <E> List<E> pickNRandomElements(List<E> list, int n) {
        return pickNRandomElements(list, n, ThreadLocalRandom.current());
    }


    public static PrimeCandidates findPrimesAllProbablePrimesParallel(long lowerInclusive, long upperExclusive) {
        var partitions = findDiscretePartition(lowerInclusive, upperExclusive, CONCURRENCY);
        Stream<Supplier<List<BigInteger>>> tasks = partitions.stream().map(pair -> () -> findPrimesAllProbablePrimes(pair.getFirst(), pair.getSecond()));

        List<CompletableFuture<List<BigInteger>>> futures = tasks.map(CompletableFuture::supplyAsync).toList();
        CompletableFuture<?>[] futureArray = futures.toArray(CompletableFuture[]::new);
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(futureArray);

        return voidCompletableFuture.thenApply(v -> {
            var list = futures.stream().map(CompletableFuture::join).toList();
            return new PrimeCandidates(list);
        }).join();
    }

    public static List<Pair<Long, Long>> findDiscretePartition(long lowerInclusive, long upperExclusive, int partitions) {
        long partitionSize = (upperExclusive - lowerInclusive) / partitions + 1;
        var ret = new ArrayList<Pair<Long,Long>>(partitions);
        for (var curr = lowerInclusive; curr < upperExclusive; curr += partitionSize) {
            ret.add(Pair.create(curr, Math.min(curr + partitionSize, upperExclusive)));
        }
        return ret;
    }

    /**
     * Find target token rebase supple amounts ranging from  [currentTokenSupply / desiredPriceChange , currentTokenSupply)
     * When the number of rebases are less than the number of candidates, the rebase amounts are chosen uniformly random
     *
     * Math
     * market_cap_curr = price_curr * supply_curr
     * market_cap_target = price_target * supply_target
     * market_cap_curr = market_cap_target
     * price_next = price_curr * desiredPriceChange
     * price_curr * supply_curr = price_curr * desiredPriceChange * market_cap_target
     * supply_curr / desiredPriceChange = market_cap_target
     *
     * Example: price: 3$,  supply: 10000 coins -> market_cap = 30000
     * desiredPriceChange = 4 -> 3$ becomes 12$ -> 300% price change
     * 3 * 10000 = (3 * 4) * supply_target
     *1000 / 4 = supply_target = 250
     *
     * @param currentTokenSupply : current supply, assumed to be prime
     * @param desiredPriceChange: percent increase desired
     * @param desiredNumberOfRebases: how many rebases to achieve desired price target
     * @returns list of new rebased supply. All elements of the list will themselves be prime
     */
    public static List<Long> findRebaseScheduleSingle(long currentTokenSupply,
                                  double desiredPriceChange,
                                  int desiredNumberOfRebases) {

        long lowerBound = (long) (currentTokenSupply / desiredPriceChange);
        var primes = findPrimesAllProbablePrimesParallel(lowerBound, currentTokenSupply);
        return primes.chooseRebases(desiredNumberOfRebases);
    }

    public static void main(String[] args) {
        var currentTokenSupply = 524287;
        var tokenAmounts = findRebaseScheduleSingle(21_000_000, 4, 24);
        tokenAmounts.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
//        System.out.println("Initial amount " + initial);
//        for (Long amt : tokenAmounts) {
//            System.out.println("Next: " + amt);
//        }
    }
}
