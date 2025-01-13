package thread.cas.increment;

public class VolatileInteger implements IncrementInteger {

    private volatile int value; // volatile 키워드를 써도 문제는 해겱되지 않음

    @Override
    public void increment() {
        value++;
    }

    @Override
    public int get() {
        return value;
    }
}
