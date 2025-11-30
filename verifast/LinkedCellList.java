/*@ predicate payload(Object o); @*/

/*@ predicate llist(LinkedCellList obj, list<Cell> ll) = 
      switch(ll) {
        case nil: return obj == null;
        case cons(h, t): 
            return obj != null 
               &*& obj.next |-> ?nxt
               &*& obj.value |-> h
               &*& [_]payload(h)
               &*& llist(nxt, t);
          };
@*/

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

public class LinkedCellList {

    private Cell value;
    private LinkedCellList next;

    public LinkedCellList(Cell v)
        //@ requires [_]payload(v);
        //@ ensures llist(this, cons(v, nil));
    {
        value = v;
        next = null;
        //@ close llist(null, nil);
        //@ close llist(this, cons(v, nil));
    }

    public void add(Cell val)
        //@ requires llist(this, ?l) &*& [_]payload(val);
        //@ ensures llist(this, addEnd(val, l));
    {
        //@ open llist(this, l);
        if(next == null) {
            //@ open llist(this.next, _);
            //@ assert l == cons(?h, nil);
            LinkedCellList n = new LinkedCellList(val);
            //@ open llist(n, cons(_, nil));
            //n.value = val;
            //@ close llist(n, cons(val, nil));
            next = n;
            //@ close llist(this, cons(h, cons(val, nil)));
        } else {
            next.add(val);
            //@ close llist(this, addEnd(val, l));
        }
    }
    
    public Cell getLast()
        //@ requires llist(this, ?l);
        //@ ensures llist(this, l) &*& result == last(l) &*& [_]payload(result);
    {
        //@ open llist(this, l);
        Cell result;
        if(next == null) {
            //@ open llist(next, ?x);
            //@ close llist(next, nil);
            result = this.value;
        } else {
            //@ open llist(next, ?x);
            //@ close llist(next, x);
            result = next.getLast();
            //@ assert last(x) == last(l);
        }

        //@ close llist(this, l);
        return result;
    }
}
