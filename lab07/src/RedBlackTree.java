public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {
        RBTreeNode<T> parent;
        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
            this.parent=null;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        boolean parentcolor=node.isBlack;
        boolean childcolor=node.left.isBlack;
        node.isBlack=childcolor;
        node.left.isBlack=parentcolor;
        node.right.isBlack=parentcolor;
        root.isBlack=true;
    }/**这里只进行颜色翻转操作，条件的判断放到插入操作里面**/

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        if(node.left==null){
            return root;
        }
        RBTreeNode<T> left=node.left;
        boolean childcolor=left.isBlack;
        boolean parentcolor=node.isBlack;
        node.isBlack=childcolor;
        left.isBlack=parentcolor;
        if(node.parent!=null){
            if(node.parent.item.compareTo(node.item)>0){
                node.parent.left=left;
            }/**这种情况是在左边**/
            else{
                node.parent.right=left;
            }/**这种情况是在右边**/
        }/**有父母就把父母的左右换了**/
        left.parent=node.parent;/**无论如何都要把left的父母改为node的父母**/
        node.parent=left;
        if(left.right!=null){
        left.right.parent=node;/**这是对left的右边进行操作，注意null讨论**/
        }
        node.left=left.right;/**不管怎么样都要把node的左边改成left的右边**/
        left.right=node;
        if(node==root){
            this.root=left;
        }
        return left;
    }/**右转是把左边的放到原节点的位置并且换色，要处理parent**/

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
      RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        if(node.right==null){
            return null;
        }
        RBTreeNode<T> right=node.right;
        boolean childcolor=right.isBlack;
        boolean parentcolor=node.isBlack;
        node.isBlack=childcolor;
        right.isBlack=parentcolor;
        if(node.parent!=null){
            if(node.parent.item.compareTo(node.item)>0){
                node.parent.left=right;
            }/**这种情况是在左边**/
            else{
                node.parent.right=right;
            }/**这种情况是在右边**/
        }/**有父母就把父母的左右换了**/
        right.parent=node.parent;/**无论如何都要把left的父母改为node的父母**/
        node.parent=right;
        if(right.left!=null){
            right.left.parent=node;
        }
        node.right=right.left;
        right.left=node;
        if(node==root){
            this.root=right;
        }
        return right;
    }/**左转和右转类似**/

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
       if(root==null){
           root=new RBTreeNode(true,item,null,null);
       }/**root必定是黑色的**/
       else{
           insert(root,item);
       }
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private void insert(RBTreeNode<T> node, T item) {
        /**注意，这里默认了node不是null**/
        if(node.item.compareTo(item)>0){
            if(node.left!=null){
                insert(node.left,item);
            }
            else{
                node.left=new RBTreeNode(false,item,null,null);
                node.left.parent=node;
            }
        }
        else if(node.item.compareTo(item)<0){
            if(node.right!=null){
                insert(node.right,item);
            }
            else{
                node.right=new RBTreeNode(false,item,null,null);
                node.right.parent=node;
            }
        }/**这一部分解决了插入的问题**/
        if((node.left!=null)&&(node.isBlack==false)&&(node.left.isBlack==false)){
            rotateRight(node.parent);
            flipColors(node);
        }/**如果左边存在且左边和本身都是红色，此时一定有parent**/
        if((node.left==null||node.left.isBlack)&&(node.right!=null)&&(node.right.isBlack==false)){
            if(node.isBlack==true){
                rotateLeft(node);
            }
            else{
                rotateLeft(node);
                rotateRight(node.parent.parent);
                flipColors(node.parent);
            }
        }/**这种情况处理了左边没有或者为黑且右边是红的情况**/
        if((node.left!=null)&&(node.left.isBlack==false)&&(node.right!=null)&&(node.right.isBlack==false)){
            flipColors(node);
        }/**这种情况处理了两边都是红色的情况**/
    }/**注意，递归是从指定的node开始而不是限定从node开始，这样才方便递归。注意，新插入的节点必定是红色的**/

}
