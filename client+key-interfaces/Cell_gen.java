package example;

public abstract class Cell {

    //@ public instance ghost \locset footprint;
    //@ public instance ghost int absVal;

    //@ public instance invariant \subset(this.*, this.footprint);
    //@ public instance accessible \inv : footprint;

    /*@  normal_behavior
        requires (false | true);
        ensures (true & (true ==> this.absVal == v));
        ensures \fresh(\result.footprint);
        accessible this.footprint;
        assignable this.footprint;
        */
    public static void init(int v) {
        return new CellImpl(v);
    }

    /*@  normal_behavior
        requires (false | true);
        ensures (true & (true ==> \result == this.absVal));
        accessible this.footprint;
        assignable this.footprint;
        */
    public abstract int value();

    /*@  normal_behavior
        requires (false | true);
        ensures (true & (true ==> this.absVal == v));
        accessible this.footprint;
        assignable this.footprint;
        */
    public abstract void set(int v);
}
