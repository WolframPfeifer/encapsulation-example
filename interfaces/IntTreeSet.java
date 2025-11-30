import universe.qual.*;

// implementation of a set as unbalanced sorted tree
public final class IntTreeSet {
    private /*@ nullable */ TreeNode root;

    //@ public ghost \dl_Set absVal;

    //@ public invariant \subset(\singleton(fp), fp);

    //@ public accessible \inv: fp;

    /*@ normal_behavior
      @  ensures absVal == \dl_sEmpty();
      @  ensures \fresh(fp);
      @  assignable \nothing;
      @*/
    IntTreeSet() {
        //@ set absVal = \dl_sEmpty();
    }

    /*@ normal_behavior
      @  ensures absVal == \dl_sUnion(\old(absVal), \dl_sSingleton(v));
      @  assignable fp;
      @*/
    public void add(int v) {
        if (root == null) {
            root = new TreeNode(v);
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
      @  accessible fp;
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
