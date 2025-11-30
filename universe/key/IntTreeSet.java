import universe.qual.*;

// implementation of a set as unbalanced sorted tree
public final class IntTreeSet {
    private @Rep /*@ nullable */ TreeNode root;

    //@ public ghost \dl_Set absVal;

    //@ private invariant root == null <==> absVal == \dl_sEmpty();
    //@ private invariant root != null ==> \invariant_for(root) && absVal == root.absVal;
    //@ private invariant root != null ==> \disjoint(this.*, \dl_createdRepfp(root));

    //@ public accessible \inv: \dl_createdRepfp(this);

    /*@ normal_behavior
      @  ensures absVal == \dl_sEmpty();
      @  ensures \fresh(\dl_createdRepfp(this));
      @  assignable \nothing;
      @*/
    @RepOnly
    IntTreeSet() {
        //@ set absVal = \dl_sEmpty();
    }

    /*@ normal_behavior
      @  ensures absVal == \dl_sUnion(\old(absVal), \dl_sSingleton(v));
      @  assignable \dl_createdRepfp(this);
      @*/
    @RepOnly
    public void add(int v) {
        if (root == null) {
            root = new @Rep TreeNode(v);
            //@ set absVal = \dl_sSingleton(v);
            //@ assert \dl_owner(root) == this;
        } else {
            root.add(v);
            //@ set absVal = \dl_sUnion(absVal, \dl_sSingleton(v));
        }
    }

    /*@ normal_behavior
      @  ensures \result <==> \dl_sElementOf(v, absVal);
      @  assignable \strictly_nothing;
      @  accessible \dl_createdRepfp(this);
      @*/
    @RepOnly
    public boolean contains(int v) {
        if (root == null) {
            return false;
        } else {
            return root.contains(v);
        }
    }
}
