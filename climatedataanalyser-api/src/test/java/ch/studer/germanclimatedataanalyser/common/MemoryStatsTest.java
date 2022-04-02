package ch.studer.germanclimatedataanalyser.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MemoryStatsTest {

    private static final long BYTE = 1l;
    private static final long KILO_BYTE = 1024l;
    private static final long MEGA_BYTE = 1048576l;
    private static final long GIGA_BYTE = 1073741824l;


    @Test
    public void testKiloBytes() {
        MemoryStats test = new MemoryStats();

        test.setHeapFreeSize(KILO_BYTE);
        String here = test.getHeapFreeSize().getInMb();
        String here2 = test.getHeapFreeSize().getInGb();
        Assertions.assertEquals("1.0", test.getHeapFreeSize().getInKb());
        Assertions.assertEquals("9.765625E-4", test.getHeapFreeSize().getInMb());
        Assertions.assertEquals("9.536743E-7", test.getHeapFreeSize().getInGb());

    }

    @Test
    public void testMegaBytes() {
        MemoryStats test = new MemoryStats();

        test.setHeapFreeSize(MEGA_BYTE);
        String here = test.getHeapFreeSize().getInMb();
        String here2 = test.getHeapFreeSize().getInGb();
        Assertions.assertEquals("1024.0", test.getHeapFreeSize().getInKb());
        Assertions.assertEquals("1.0", test.getHeapFreeSize().getInMb());
        Assertions.assertEquals("9.765625E-4", test.getHeapFreeSize().getInGb());

    }

    @Test
    public void testGigaBytes() {
        MemoryStats test = new MemoryStats();

        test.setHeapFreeSize(GIGA_BYTE);
        String here = test.getHeapFreeSize().getInMb();
        String here2 = test.getHeapFreeSize().getInGb();
        Assertions.assertEquals("1048576.0", test.getHeapFreeSize().getInKb());
        Assertions.assertEquals("1024.0", test.getHeapFreeSize().getInMb());
        Assertions.assertEquals("1.0", test.getHeapFreeSize().getInGb());

    }
}
