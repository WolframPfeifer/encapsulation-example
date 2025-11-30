// small example of mutable DTI class
public final class Cell {
    private int value;

    public Cell() {
    }

    public int value() {
        return value;
    }

    public void set(int v) {
        value = v;
    }
}
