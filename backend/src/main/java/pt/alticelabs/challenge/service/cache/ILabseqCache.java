package pt.alticelabs.challenge.service.cache;

import java.math.BigInteger;

public interface ILabseqCache {
    boolean contains(long n);
    BigInteger get(long n);
    void put(long n, BigInteger value);
    long lastIndex();
}
