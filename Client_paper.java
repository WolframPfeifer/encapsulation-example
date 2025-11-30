class Client {
    public static void m() {
        // Cell: proven with DF + KeY
        Cell c0 = new Cell(); c0.set(5);
        Cell c1 = new Cell(); c1.set(9);

        // IntTreeSet: proven with UT + KeY
        IntTreeSet set = new IntTreeSet();
        set.add(5); set.add(9);

        // LinkedCellList: proven with VeriFast
        LinkedCellList l1 = new LinkedCellList();
        l1.add(c0); l1.add(c1);

        Cell last1 = l1.getLast();
        int r1 = last1.value();
        assert set.contains(r1);
        assert r1 == 9;
    }
}
