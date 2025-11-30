// small example of mutable DTI class

/*@ predicate cell(Cell obj, int v) =
      obj != null &*& obj.value |-> v;
@*/

public final class Cell {
    private int value;

    public Cell()
        //@ requires true;
        //@ ensures cell(this, 0);
    {
        //@ close cell(this, 0);
    }

    public int value()
        //@ requires cell(this, ?x);
        //@ ensures cell(this, x) &*& result == x;
    {
        //@ open cell(this, x);
        return value;
        //@ close cell(this, x);
    }

    public void set(int v)
        //@ requires cell(this, ?x);
        //@ ensures cell(this, v);
    {
        //@ open cell(this, x);
        value = v;
        //@ close cell(this, v);
    }
}
