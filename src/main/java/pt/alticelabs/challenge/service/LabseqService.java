package pt.alticelabs.challenge.service;

import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pt.alticelabs.challenge.interceptor.MeasureTime;
import pt.alticelabs.challenge.service.cache.ILabseqCache;
import pt.alticelabs.challenge.exception.LabseqException;

import java.math.BigInteger;


@ApplicationScoped
public class LabseqService {

    private final ILabseqCache cache;

    @Inject
    public LabseqService(ILabseqCache cache) {
        this.cache = cache;
    }

    @CacheResult(cacheName = "labseq-cache")
    @MeasureTime
    public synchronized BigInteger computeLabseq(long n) {
        if (n < 0) {
            throw new LabseqException("Index must be non-negative.");
        }

        if (cache.contains(n)) {
            return cache.get(n);
        }

        long lastComputed = cache.lastIndex();
        for (long i = lastComputed + 1; i <= n; i++) {
            BigInteger value = cache.get(i - 4).add(cache.get(i - 3));
            cache.put(i, value);
        }

        return cache.get(n);
    }

    public boolean isCached(long n) {
        return cache.contains(n);
    }
}
