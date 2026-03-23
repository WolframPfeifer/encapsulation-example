package example;

public final class LinkedCellListImpl extends LinkedCellList {
    /*@ predicate llist(list<Cell> ll) =
         head |-> ?h
     &*& (h == null ? ll == nil : h.nellist(ll)); @*/

    private NonEmptyLinkedCellList head;

    public LinkedCellListImpl()
        //@ requires true;
        //@ ensures llist(nil);
    {
        head = null;
    }

    public void add(Cell v)
        //@ requires llist(?l_old) &*& [_]payload(v);
        //@ ensures llist(?l) &*& l == addEnd(v, l_old);
    {
        if (head == null) {
            head = new NonEmptyLinkedCellList(v);
        } else {
            head.add(v);
        }
    }

    public Cell getLast()
        //@ requires llist(?l_old) &*& !(l_old == nil);
        //@ ensures llist(l_old) &*& result == last(l_old) &*& [_]payload(result);
    {
        Cell result;
        if (head == null) {
            result = null;
        } else {
            result = head.getLast();
        }

        return result;
    }
}

final class NonEmptyLinkedCellList {
   /*@ predicate nellist(list<Cell> ll) =
         next |-> ?n
     &*& value |-> ?v
     &*& [_]payload(v)
     &*& (n == null
         ? ll == cons(v, nil)
         : n.nellist(?x) &*& ll == cons(v, x)); @*/

    private Cell value;
    private NonEmptyLinkedCellList next;

    public NonEmptyLinkedCellList(Cell v)
        //@ requires [_]payload(v);
        //@ ensures nellist(cons(v,nil));
    {
        value =v;
        next = null;
    }

    public void add(Cell v)
        //@ requires nellist(?l_old) &*& [_]payload(v);
        //@ ensures nellist(?l) &*& l == addEnd(v, l_old);
    {
        if (next == null) {
            NonEmptyLinkedCellList n = new NonEmptyLinkedCellList(v);
            next = n;
        } else {
            next.add(v);
        }
    }

    public Cell getLast()
        //@ requires nellist(?l);
        //@ ensures nellist(l) &*& result == last(l) &*& [_]payload(result);
    {
        Cell result;
        if (next == null) {
            result = value;
        } else {
            //@ open next.nellist(?x);
            // @ close next.nellist(x);
            result = next.getLast();
            //@ assert last(x) == last(l);
        }

        return result;
    }
}
