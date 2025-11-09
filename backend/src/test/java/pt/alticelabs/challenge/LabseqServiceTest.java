package pt.alticelabs.challenge;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.alticelabs.challenge.exception.LabseqException;
import pt.alticelabs.challenge.service.LabseqService;
import pt.alticelabs.challenge.service.cache.ILabseqCache;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LabseqServiceTest {

    private LabseqService service;
    private ILabseqCache cache;

    @BeforeEach
    void setUp() {
        cache = mock(ILabseqCache.class);
        service = new LabseqService(cache);
    }

    @Test
    void testNegativeIndex() {
        assertThrows(LabseqException.class, () -> service.computeLabseq(-1));
    }

    @Test
    void testComputeLabseqFromCache() {
        long n = 5;
        when(cache.contains(n)).thenReturn(true);
        when(cache.get(n)).thenReturn(BigInteger.ONE);

        BigInteger result = service.computeLabseq(n);

        assertEquals(BigInteger.ONE, result);
        verify(cache).get(n);
        verify(cache, never()).put(anyLong(), any());
    }

    @Test
    void testComputeLabseqComputeNewValue() {
        for (long i = 0; i <= 3; i++) {
            when(cache.contains(i)).thenReturn(true);
            when(cache.get(i)).thenReturn(BigInteger.valueOf(i % 2));
        }
        when(cache.lastIndex()).thenReturn(3L);

        long n = 5;

        when(cache.contains(n)).thenReturn(false);

        doAnswer(invocation -> {
            long index = invocation.getArgument(0);
            BigInteger value = invocation.getArgument(1);
            when(cache.contains(index)).thenReturn(true);
            when(cache.get(index)).thenReturn(value);
            when(cache.lastIndex()).thenReturn(index);
            return null;
        }).when(cache).put(anyLong(), any());

        BigInteger result = service.computeLabseq(n);

        assertEquals(BigInteger.ONE, result);
        verify(cache, times(2)).put(anyLong(), any());
    }
}