// ------ linked list.

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

/*@ predicate payload(Object o); @*/

/*@ fixpoint list<T> addEnd<T>(T val, list<T> ll) {
      switch(ll) {
        case nil: return cons(val, ll);
        case cons(h, t): return cons(h, addEnd(val, t));
      }
    }
@*/

/*@ lemma void addEndNotNil<T>(T h, list<T> l) 
    requires true;
    ensures addEnd(h, l) != nil;
    {
      switch(l) {
        case nil: return;
        case cons(hd, tl): addEndNotNil(h, tl); return;
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

/*@
    lemma void lastOfCons(Cell h, list<Cell> l)
    requires l != nil;
    ensures last(cons(h, l)) == last(l);
    {
    }
@*/

/*@ lemma void lastOfAddEnd(Cell h, list<Cell> l)
    requires true;
    ensures last(addEnd(h, l)) == h;
    {
      switch(l) {
        case nil: return;
        case cons(hd, tl): 
          if(tl == nil) {
          } else {
          assert addEnd(h,l) == cons(hd, addEnd(h, tl));
          addEndNotNil(h, tl);
          lastOfCons(hd, addEnd(h, tl));
          assert last(cons(hd, addEnd(h, tl))) == last(addEnd(h, tl));
          assert last(addEnd(h,l)) == last(addEnd(h, tl));
          lastOfAddEnd(h, tl);
          return;
          }
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

    public void addLast(Cell val)
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
            next.addLast(val);
            //@ close llist(this, addEnd(val, l));
        }
    }
    
    public Cell getLast()
        //@ requires [?f]llist(this, ?l);
        //@ ensures [f]llist(this, l) &*& result == last(l) &*& [_]payload(result);
    {
        //@ open llist(this, l);
        Cell result;
        if(next == null) {
            //@ open [f]llist(next, ?x);
            //@ close [f]llist(next, nil);
            result = this.value;
        } else {
            //@ open [f]llist(next, ?x);
            //@ close [f]llist(next, x);
            result = next.getLast();
            //@ assert last(x) == last(l);
        }
        //@ close [f]llist(this, l);
        return result;
    }
}
