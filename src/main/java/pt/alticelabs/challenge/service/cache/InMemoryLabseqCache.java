package pt.alticelabs.challenge.service.cache;

import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class InMemoryLabseqCache implements ILabseqCache {

    private final Map<Long, BigInteger> cache = new ConcurrentHashMap<>();
    private final AtomicLong lastComputedIndex = new AtomicLong(0);

    public InMemoryLabseqCache() {
        init();
    }

    private void init() {
        for (long i = 0; i <= 3; i++) {
            this.put(i, BigInteger.valueOf(i % 2));
        }
    }

    @Override
    public boolean contains(long n) {
        return cache.containsKey(n);
    }

    @Override
    public BigInteger get(long n) {
        return cache.get(n);
    }

    @Override
    public void put(long n, BigInteger value) {
        cache.put(n, value);
        lastComputedIndex.set(n);
    }

    @Override
    public long lastIndex() {
        return lastComputedIndex.get();
    }
}
