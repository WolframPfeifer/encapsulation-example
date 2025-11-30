/*@ lemma void payload_create(Object o)
      requires true;
      ensures payload(o);
    { assume(false); }
@*/

class Client {
    public static void m()
    	//@ requires true;
    	//@ ensures true;
    {
        // Cell: proven with DF + KeY
        Cell c0 = new Cell(); c0.set(5);
        Cell c1 = new Cell(); c1.set(9);

	//@ payload_create(c0);
        //@ payload_create(c1);

        // LinkedCellList: proven with VeriFast
        LinkedCellList l1 = new LinkedCellList(c0);
        /*l1.add(c0);*/ l1.add(c1);

        Cell last1 = l1.getLast();
        int r1 = last1.value();
        //assert r1 == 9;
    }
}
