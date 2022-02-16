package ch.studer.germanclimatedataanalyser.common;


public class MemoryStats {
    private Bytes heapSize = new Bytes();
    private Bytes heapMaxSize = new Bytes();
    private Bytes heapFreeSize = new Bytes();

    public Bytes getHeapSize() {
        return heapSize;
    }

    public void setHeapSize(long heapSize) {
        this.heapSize.setBytes(heapSize);
    }

    public Bytes getHeapMaxSize() {
        return heapMaxSize;
    }

    public void setHeapMaxSize(long heapMaxSize) {
        this.heapMaxSize.setBytes(heapMaxSize);
    }

    public Bytes getHeapFreeSize() {
        return heapFreeSize;
    }

    public void setHeapFreeSize(long heapFreeSize) {
        this.heapFreeSize.setBytes(heapFreeSize);
    }
}

class Bytes {
    private long bytes;
    private static long SIZE = 1024l;

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public String getInKb() {
        return String.valueOf(Float.valueOf(this.bytes) / SIZE);
    }

    public String getInMb() {
        return String.valueOf(Float.valueOf(this.getInKb()) / SIZE);
    }

    public String getInGb() {
        return String.valueOf(Float.valueOf(this.getInMb()) / SIZE);
    }


}
