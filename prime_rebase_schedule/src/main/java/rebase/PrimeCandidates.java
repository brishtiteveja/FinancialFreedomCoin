package rebase;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PrimeCandidates {

    final List<List<BigInteger>> primeContainer;

    final BigInteger bucketSize;

    PrimeCandidates(List<List<BigInteger>> primeContainer) {
        this.primeContainer = primeContainer;
        this.bucketSize = BigInteger.valueOf(primeContainer.size());
    }

    int[] computeRebaseBucketAmounts(int rebases) {

        var ret = BigInteger.valueOf(rebases).divideAndRemainder(bucketSize);

        int[] rebasesForEachBucket = new int[primeContainer.size()];
        int minForEachBucket = ret[0].intValueExact();
        int remainder = ret[1].intValueExact();

        Arrays.fill(rebasesForEachBucket, minForEachBucket);


        for (var i = 0; i < remainder; i++) {
            rebasesForEachBucket[i]++;
        }

        return rebasesForEachBucket;
    }

    List<Long> chooseRebases(int rebases) {
        List<Long> results = new ArrayList<>(rebases);

        int[] bucketAmounts = computeRebaseBucketAmounts(rebases);

        for (int i = 0; i < bucketAmounts.length; i++) {
            var n = bucketAmounts[i];
            var primes = primeContainer.get(i);
            RebaseScheduler.pickNRandomElements(primes, n).forEach(chosen -> {
                results.add(chosen.longValue());
            });
        }
        return results;
    }
}
