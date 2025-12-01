package example;

public final class Cell {
    private int value;

    //@ public ghost int absVal;
    //@ public ghost \locset fp;

    //@ public invariant \subset(\singleton(fp), fp);

    //@ accessible \inv: fp;

    /*@ normal_behavior
      @  requires true;
      @  ensures absVal == v;
      @  ensures \fresh(fp);
      @  assignable \nothing;
      @*/
    public Cell(int v) {
        this.value = v;
        //@ set fp = this.*;
        //@ set absVal = value;
    }

    /*@ normal_behavior
      @  requires true;
      @  ensures \result == absVal;
      @  assignable \strictly_nothing;
      @  accessible fp;
      @*/
    public int value() {
        return value;
    }

    /*@ normal_behavior
      @  requires true;
      @  ensures absVal == v;
      @  ensures \new_elems_fresh(fp);
      @  assignable fp;
      @*/
    public void set(int v) {
        value = v;
        //@ set absVal = value;
    }
}
