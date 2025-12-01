public final class LinkedCellList {

    /*@ datatype list {
      @     nil()
      @   | cons(Cell h, list tail);
      @
      @   int compareTo(cell this, cell o) {
      @     return val(this) - val(o);
      @   }
      @
      @   Cell last(cell this) {
      @     return switch (this) {
      @       case nil() -> ???   // TODO: what to do here?
      @       case cons(h, t) -> last(t);
      @     };
      @   }
      @
      @   list snoc(list this, Cell v) {
      @     return switch (this) {
      @       case nil() -> cons(v, nil());
      @       case cons(h, t) -> cons(h, snoc(t, v));
      @     };
      @   }
      @ }
      @*/

    //@ ghost list absVal;

    /*@ normal_behavior
      @  ensures absVal == cons(v, nil());
      @  ensures \fresh(fp);
      @  assignable \nothing;
      @*/
    public LinkedCellList(Cell v) { super(v); }

    //@ ensures absVal == snoc(\old(absVal), v);
    public void addLast(Cell v) { super.addLast(v); }

    //@ ensures \result == last(this);
    public Cell getLast() { return super.getLast(); }
}
