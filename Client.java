public class Client {
    public static void main(String[] args) {

        // Cell: enc., proven with DF + KeY
        Cell c0 = new Cell(5);
        Cell c1 = new Cell(9);

        // IntTreeSet: enc., proven with UT + KeY
        IntTreeSet set = new IntTreeSet();
        set.add(5);
        set.add(9);

        System.out.println(set.contains(5));
        System.out.println(set.contains(7));
        System.out.println(set.contains(3));
        System.out.println(set.contains(0));
        System.out.println(set);

        // LinkedCellList: enc., proven with VeriFast
        LinkedCellList l1 = new LinkedCellList();
        l1.addLast(c1);
        l1.addLast(c0);

        Cell last1 = l1.getLast();
        int r1 = last1.value();
        System.out.println(r1);
        assert set.contains(r1);
        assert r1 == 9;
    }
}
