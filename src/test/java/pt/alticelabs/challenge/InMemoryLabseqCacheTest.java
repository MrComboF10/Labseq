package pt.alticelabs.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.alticelabs.challenge.service.cache.InMemoryLabseqCache;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryLabseqCacheTest {

    private InMemoryLabseqCache cache;

    @BeforeEach
    void setUp() {
        cache = new InMemoryLabseqCache();
    }

    @Test
    void testInitialValues() {
        assertTrue(cache.contains(0));
        assertEquals(BigInteger.ZERO, cache.get(0));

        assertTrue(cache.contains(1));
        assertEquals(BigInteger.ONE, cache.get(1));

        assertTrue(cache.contains(2));
        assertEquals(BigInteger.ZERO, cache.get(2));

        assertTrue(cache.contains(3));
        assertEquals(BigInteger.ONE, cache.get(3));

        assertFalse(cache.contains(4));

        assertEquals(3, cache.lastIndex());
    }

    @Test
    void testPutAndGet() {
        cache.put(4L, BigInteger.valueOf(1));

        assertTrue(cache.contains(4));
        assertEquals(BigInteger.valueOf(1), cache.get(4));

        assertEquals(4, cache.lastIndex());
    }
}
