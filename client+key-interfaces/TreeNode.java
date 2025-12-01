import universe.qual.*;

public final class TreeNode {
    private /*@ nullable */ TreeNode left;
    private /*@ nullable */ TreeNode right;
    private int value;

    //@ public ghost \dl_Set absVal;

    //@ accessible \inv: fp;

    // leaf
    /*@ normal_behavior
      @  ensures absVal == \dl_sSingleton(v);
      @  ensures \fresh(fp);
      @  assignable \nothing;
      @*/
    public TreeNode(int v) {
        value = v;
        //@ set absVal = \dl_sSingleton(v);
    }

    /*@ normal_behavior
      @  ensures absVal == \dl_sUnion(\old(absVal), \dl_sSingleton(v));
      @  assignable fp;
      @  measured_by \dl_sCard(absVal);
      @*/
    public void add(int v) {
        // TODO: in original CellTreeNode: method call (hashcode) on @Payload!!! "looks into" payload and thus breaks encapsulation, but: set abstraction does not depend on it, so maybe it is allowed?!
        if (v == value)
            return;
        else if (v < value) {
            if (left == null) {
                left = new TreeNode(v);
                //@ assert \dl_sCard(left.absVal) == 1;
            } else {
                left.add(v);
            }
        } else {
            if (right == null) {
                right = new TreeNode(v);
                //@ assert \dl_sCard(right.absVal) == 1;
            } else {
                right.add(v);
            }
        }
        //@ set absVal = \dl_sUnion(absVal, \dl_sSingleton(v));
    }

    /*@ normal_behavior
      @  ensures \result <==> \dl_sElementOf(v, absVal);
      @  assignable \strictly_nothing;
      @  measured_by \dl_sCard(absVal);
      @  accessible fp;
      @*/
    public boolean contains(int v) {
        if (v == value)
            return true;
        else if (v < value)
            return left != null && left.contains(v);
        else
            return right != null && right.contains(v);
    }
}
