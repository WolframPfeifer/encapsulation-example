import universe.qual.*;

@DTI
public class TreeNode {
    private /*@ nullable */ @Rep TreeNode left;
    private /*@ nullable */ @Rep TreeNode right; // nullable
    private int value;

    // leaf
    /*@ normal_behavior
      @  ensures absVal == \dl_sSingleton(v);
      @  assignable \dl_repfp();
      @*/
    @RepOnly
    public TreeNode(int v) {
        value = v;
    }

    /*@ normal_behavior
      @  ensures absVal == \dl_sUnion(absVal, \dl_sSingleton(v));
      @  assignable \dl_repfp();
      @*/
    @RepOnly
    public void add(int v) {
        // TODO: in original CellTreeNode: method call (hashcode) on @Payload!!! "looks into" payload and thus breaks encapsulation, but: set abstraction does not depend on it, so maybe it is allowed?!
        if (v == value)
            return;
        else if (v < value) {
            if (left == null)
                left = new @Rep TreeNode(v);
            else
                left.add(v);
        } else {
            if (right == null)
                right = new @Rep TreeNode(v);
            else
                right.add(v);
        }
    }

    /*@ normal_behavior
      @  ensures \result <==> \dl_sElementOf(v, absVal);
      @  assignable \dl_repfp();
      @*/
    @RepOnly
    public boolean contains(int v) {
        if (v == value)
            return true;
        else if (v < value)
            return left != null && left.contains(v);
        else
            return right != null && right.contains(v);
    }

    @SuppressWarnings("enc")
    public String toString() {
        String res = "(";
        if (left != null) res += left.toString();
        else res += "null";
        res += " | " + value + " | ";
        if (right != null) res += right.toString();
        else res += "null";
        res += ")";
        return res;
    }
}
