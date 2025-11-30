import universe.qual.*;

// implementation of a set as unbalanced sorted tree
@DTI
public class IntTreeSet {
    private /*@ nullable */ @Rep TreeNode root;

    //@ public ghost \dl_set absVal;

    //@ private invariant root == null <==> absVal == \dl_sEmpty();

    /*@ normal_behavior
      @  ensures absVal == \dl_sEmpty();
      @  assignable \dl_repfp();
      @*/
    @RepOnly
    IntTreeSet() {
    }

    /*@ normal_behavior
      @  ensures absVal == \dl_sUnion(absVal, \dl_sSingleton(v));
      @  assignable \dl_repfp();
      @*/
    @RepOnly
    public void add(int v) {
        if (root == null) root = new @Rep TreeNode(v);
        else root.add(v);
    }

    /*@ normal_behavior
      @  ensures \result <==> \dl_sElementOf(v, absVal);
      @  assignable \dl_repfp();
      @*/
    @RepOnly
    public boolean contains(int v) {
        if (root == null) return false;
        else return root.contains(v);
    }

    @SuppressWarnings("enc")
    public String toString() {
        if (root == null) return "{}";
        else return "{ " + root.toString() + " }";
    }
}
