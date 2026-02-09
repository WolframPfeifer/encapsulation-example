package example;

/*@ predicate_family_instance llist(LinkedCellListImpl.class)(LinkedCellListImpl o, list<Cell> ll) =
         o != null
     &*& o.head |-> ?h
     &*& (h == null ? ll == nil : nellist(h, ll)); @*/

public final class LinkedCellListImpl extends LinkedCellList {
    private NonEmptyLinkedCellList head;

    public LinkedCellListImpl()
        //@ requires true;
        //@ ensures llist(LinkedCellListImpl.class)(this, nil);
    {
        head = null;
        //@ close llist(LinkedCellListImpl.class)(this, nil);
    }

    public void add(Cell v)
        //@ requires llist(LinkedCellListImpl.class)(this, ?l_old) &*& [_]payload(v);
        //@ ensures llist(LinkedCellListImpl.class)(this, ?l) &*& l == addEnd(v, l_old);
    {
        //@ open llist(LinkedCellListImpl.class)(this, l_old);
        if (head == null) {
            head = new NonEmptyLinkedCellList(v);
            //@ close llist(LinkedCellListImpl.class)(this, cons(v, nil));
        } else {
            head.add(v);
            //@ close llist(LinkedCellListImpl.class)(this, addEnd(v, l_old));
        }
    }

    public Cell getLast()
        //@ requires llist(LinkedCellListImpl.class)(this, ?l_old) &*& !(l_old == nil);
        //@ ensures llist(LinkedCellListImpl.class)(this, l_old) &*& result == last(l_old) &*& [_]payload(result);
    {
        //@ open llist(LinkedCellListImpl.class)(this, l_old);
        Cell result;
        if (head == null) {
            //@ open head.llist(?x);
            //@ close head.llist(nil);
            result = null;
        } else {
            result = head.getLast();
        }

        //@ close llist(LinkedCellListImpl.class)(this, l_old);
        return result;
    }
}

/*@ predicate nellist(NonEmptyLinkedCellList o, list<Cell> ll) =
         o != null
     &*& o.next |-> ?n
     &*& o.value |-> ?v
     &*& ll == cons(v, ?x)
     &*& [_]payload(v)
     &*& (n == null
         ? (x == nil)
         : (nellist(n, x))); @*/

final class NonEmptyLinkedCellList {
    private Cell value;
    private NonEmptyLinkedCellList next;

    public NonEmptyLinkedCellList(Cell v)
        //@ requires [_]payload(v);
        //@ ensures nellist(this, cons(v,nil));
    {
        value =v;
        next = null;
        //@ close nellist(this, cons(v,nil));
    }

    public void add(Cell v)
        //@ requires nellist(this, ?l_old) &*& [_]payload(v);
        //@ ensures nellist(this, ?l) &*& l == addEnd(v, l_old);
    {
        //@ open nellist(this, l_old);
        if (next == null) {
            //@ assert l_old == cons(?h, nil);
            NonEmptyLinkedCellList n = new NonEmptyLinkedCellList(v);
            next = n;
            //@ assert nellist(n, cons(v, nil));
            //@ close nellist(this, cons(h, cons(v, nil)));
        } else {
            next.add(v);
            //@ assert addEnd(v, l_old) != nil;
            //@ close nellist(this, addEnd(v, l_old));
        }
    }

    public Cell getLast()
        //@ requires nellist(this, ?l);
        //@ ensures nellist(this, l) &*& result == last(l) &*& [_]payload(result);
    {
        //@ open nellist(this, l);
        Cell result;
        if (next == null) {
            result = value;
        } else {
            //@ open nellist(next, ?x);
            //@ close nellist(next, x);
            result = next.getLast();
            //@ assert last(x) == last(l);
        }

        //@ close nellist(this, l);
        return result;
    }
}
