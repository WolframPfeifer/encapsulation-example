package example;

public abstract class Cell {
    private int value;

    //@ public ghost \locset fp;
    //@ public ghost int absVal;

    //@ public invariant \subset(\singleton(fp), fp);
    //@ accessible \inv: fp;

    /*@ normal_behavior
      @  requires true;
      @  ensures absVal == v;
      @  ensures \fresh(fp);
      @  assignable \nothing;
      @*/
    public static void init(int v) {
        return new CellImpl(v);
    }

    /*@ normal_behavior
      @  requires true;
      @  ensures \result == absVal;
      @  assignable \strictly_nothing;
      @  accessible fp;
      @*/
    public int value();

    /*@ normal_behavior
      @  requires true;
      @  ensures absVal == v;
      @  ensures \new_elems_fresh(fp);
      @  assignable fp;
      @*/
    public void set(int v);
}
