package example;

/*@ predicate payload(Object o); @*/

/*@ fixpoint list<T> addEnd<T>(T val, list<T> ll) {
      switch(ll) {
        case nil: return cons(val, ll);
        case cons(h, t): return cons(h, addEnd(val, t));
      }
    }
@*/

/*@ fixpoint Cell last(list<Cell> ll) {
       switch(ll) {
       case nil: return null;
       case cons(h, t): return t==nil ? h : last(t);
       }
    }
@*/

//@ predicate_family llist(Class c)(LinkedCellList l, list<Cell> xs);

public abstract class LinkedCellList {
    // @ predicate llist(list<Cell> ll);

    public static LinkedCellList init()
        //@ requires true;
        //@ ensures llist(result.getClass())(result, ?result_absVal) &*& result_absVal == nil &*& result != null;
    {
        return new LinkedCellListImpl();
    }

    public abstract void add(Cell v);
        //@ requires llist(this.getClass())(this, ?l_old) &*& [_]payload(v);
        //@ ensures llist(this.getClass())(this, ?l) &*& l == addEnd(v, l_old);

    public abstract Cell getLast();
        //@ requires llist(this.getClass())(this, ?l_old) &*& !(l_old == nil);
        //@ ensures llist(this.getClass())(this, l_old) &*& result == last(l_old) &*& [_]payload(result);
}
