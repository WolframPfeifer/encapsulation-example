/*@ predicate payload(Object o); @*/

/*@ predicate llist(LinkedCellList obj, list<Cell> ll) = 
      obj != null &*&
      switch(ll) {
        case nil: return obj.value |-> null &*& obj.next |-> null;
        case cons(h, t):
            return obj.next |-> ?nxt
               &*& nxt != null
               &*& obj.value |-> h
               &*& h != null
               &*& [_]payload(h)
               &*& llist(nxt, t);
          };
@*/

/*@ lemma void payload_duplicate(Object o)
      requires payload(o);
      ensures payload(o) &*& payload(o);
    { assume(false); }
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

    public LinkedCellList()
        //@ requires true;
        //@ ensures llist(this, nil);
    {
        value = null;
        next = null;
        // @ close llist(null, nil);
        //@ close llist(this, nil);
    }

/*
    public LinkedCellList(Cell v)
        //@ requires v != null &*& payload(v);
        //@ ensures llist(this, cons(v, nil));
    {
        value = v;
        next = new LinkedCellList();
        // @ close llist(next, nil);
        //@ payload_duplicate(v);
        //@ close llist(this, cons(v, nil));
    }*/

    public void add(Cell val)
        //@ requires val != null &*& llist(this, ?l) &*& [_]payload(val);
        //@ ensures llist(this, addEnd(val, l));
    {
        //@ open llist(this, l);
        if (value == null) {
            value = val;
            next = new LinkedCellList();
            return;
            // @ assert next |-> ?x;
            // @ open llist(x, ?c);
            // @ close llist(x, nil);
            //@ close llist(this, cons(val, nil));
        }
        if (next == null) {
            //@ open llist(this.next, _);
            //@ assert l == cons(?h, nil);
            LinkedCellList n = new LinkedCellList();
            n.add(val);
            //@ open llist(n, cons(_, nil));
            //@ close llist(n, cons(val, nil));
            next = n;
            //@ close llist(this, cons(h, cons(val, nil)));
        } else {
            next.add(val);
            //@ close llist(this, addEnd(val, l));
        }
    }

    public Cell getLast()
        //@ requires llist(this, ?l) &*& l != nil;
        //@ ensures llist(this, l) &*& [_]payload(result) &*& result == last(l);
    {
        //@ open llist(this, l);
        Cell result;
        if (next == null) {
            //@ open llist(next, ?x);
            //@ close llist(next, nil);
            result = this.value;
        } else {
            //@ open llist(next, ?x);
            //@ close llist(next, x);
            if (value == null) {
            	result = null;
            } else {
            	result = next.getLast();
            	//@ assert last(x) == last(l);
            }
        }
        return result;
        //@ close llist(this, l);
    }
}
