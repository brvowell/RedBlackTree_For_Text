// Assignment 2
// TreeNode Class
// Brandon Vowell

package main;

public class TreeNode {
    String value;
    int count;
    int level;
    char color;
    char location;
    TreeNode leftChild;
    TreeNode rightChild;
    TreeNode parent;

    public TreeNode(String s) {
        value = s;
        count = 1;
        level = 1;
        color = 'r';
        location = 'X';
        leftChild = null;
        rightChild = null;
        parent = null;
    }

    private TreeNode(char c) {
        value = null;
        count = 0;
        level = 0;
        color = c;
        location = 'N';
        leftChild = null;
        rightChild = null;
        parent = null;
    }

    public String filterString() {
        int i = 0;
        while(i < value.length()) {
            int ascii = (int) value.charAt(i);
            if(ascii >= 65 && ascii <= 90) {
                ascii += 32;
                char update = (char) ascii;
                if(i > 0)
                    value = value.substring(0, i - 1) + update + value.substring(i + 1);
                else
                    value = update + value.substring(1);
                i++;
            }
            else if(ascii < 97 || ascii > 122) {
                String result = value.substring(0, i) + value.substring(i + 1);
                value = result;
            }
            else {
                i++;
            }
        }
        
        return value;
    }

    public TreeNode updateNode(String next, int number, TreeNode current) {
       TreeNode valueToReturn = current;
       valueToReturn.value = next;
       valueToReturn.count = number;
       return valueToReturn;
    }

    public TreeNode updateNode(TreeNode newValue, TreeNode current) {
        TreeNode valueToReturn = current;
        valueToReturn = newValue;
        return valueToReturn;
    }

    public int getLevelValue() {
        return level;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String s) {
        value = s;
        return;
    }

    public char getLocation() {
        return location;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int i) {
        count = i;
    }

    public TreeNode getParent() {
        return parent;
    }

    public TreeNode getLeftChild() {
        return leftChild;
    }

    public TreeNode getRightChild() {
        return rightChild;
    }

    public int getLevel() {
        return level;
    }

    public static TreeNode findPredecessor(TreeNode node) {
        if(node == null) {
            return null;
        }
        if(node.leftChild != null) {
            return findMaximum(node.leftChild);
        }
        else
            return findMinimum(node.rightChild);
    }

    public static TreeNode findMaximum(TreeNode root) {
        if(root.leftChild == null && root.rightChild == null)
            return root;
        if(root.rightChild == null)
            return root;
        else
            return findMaximum(root.rightChild);
    }

    public static TreeNode findMinimum(TreeNode root) {
        if(root.leftChild == null && root.rightChild == null)
            return root;
        if(root.leftChild == null)
            return root;
        else
            return findMinimum(root.leftChild);
    }

    public boolean isLeaf() {
        if(leftChild == null && rightChild == null)
            return true;
        else
            return false;
    }

    public void setNodeToNull(TreeNode nulledOut, TreeStructure sT) {
        if(nulledOut.location == 'L') {
            nulledOut.parent.leftChild = null;
            nulledOut = null;
        }
        else if(nulledOut.location == 'R') {
            nulledOut.parent.rightChild = null;
            nulledOut = null;
        }
        else {
            sT.setRoot(null);
        }
            
    }

    private static void increaseLevelOnSubTree(TreeNode start) {
        if(start == null)
            return;
        start.level++;;
        if(start.leftChild != null)
            increaseLevelOnSubTree(start.getLeftChild());
        if(start.rightChild != null)
            increaseLevelOnSubTree(start.getRightChild());
        return;
    }

    private static void decreaseLevelOnSubTree(TreeNode start) {
        if(start == null)
            return;
        start.level--;
        if(start.leftChild != null)
            decreaseLevelOnSubTree(start.getLeftChild());
        if(start.rightChild != null)
            decreaseLevelOnSubTree(start.getRightChild());
        return;
    }
        


    public static void rotateRight(TreeNode x, TreeStructure sT) {
        TreeNode temp = x.rightChild;
        increaseLevelOnSubTree(x.getParent().getRightChild());
        decreaseLevelOnSubTree(x.getParent().getLeftChild());
        if(temp != null)
            temp.location = 'L';
        if(x.parent == sT.getRoot())
            sT.setRoot(x);
        x.rightChild = x.parent;
        x.rightChild.level++;
        x.location = x.rightChild.location;
        x.parent = x.rightChild.parent;
        if(x.parent != null) {
            if(x.rightChild.location == 'L')
                x.parent.leftChild = x;
            else
                x.parent.rightChild = x;
        }
        x.rightChild.location = 'R';
        x.rightChild.leftChild = temp;
        x.rightChild.parent = x;
        if(x.rightChild.leftChild != null) {
            x.rightChild.leftChild.parent = x.rightChild;
            increaseLevelOnSubTree(x.rightChild.leftChild);
        }
        return;
    }

    public static void rotateLeft(TreeNode x, TreeStructure sT) {
        TreeNode temp = x.leftChild;
        increaseLevelOnSubTree(x.getParent().getLeftChild());
        decreaseLevelOnSubTree(x.getParent().getRightChild());
        if(temp != null)
            temp.location = 'R';
        if(x.parent == sT.getRoot())
            sT.setRoot(x);
        x.leftChild = x.parent;
        x.leftChild.level++;
        x.location = x.leftChild.location;
        x.parent = x.leftChild.parent;
        if(x.parent != null) {
            if(x.leftChild.location == 'L')
                x.parent.leftChild = x;
            else
                x.parent.rightChild = x;
        }
        x.leftChild.location = 'L';
        x.leftChild.rightChild = temp;
        x.leftChild.parent = x;
        if(x.leftChild.rightChild != null) {
            x.leftChild.rightChild.parent = x.leftChild;
            increaseLevelOnSubTree(x.leftChild.rightChild);
        }
        return;
    }
        


    public void insertionFixUp(TreeNode x, TreeStructure sT) {
        TreeNode uncle = new TreeNode('b');
        while(true) { 
            if(x == sT.getRoot())
                break;
            if(x.parent.color == 'b')
                break;
            if(x.parent.location == 'L')
                uncle = x.parent.parent.rightChild;
            if(x.parent.location == 'R')
                uncle = parent.parent.leftChild;


            if(uncle != null && uncle.color == 'r') {
                x.parent.color = 'b';
                uncle.color = 'b';
                x.parent.parent.color = 'r';
                x = x.parent.parent;
            }

            else {
                if(x.parent.parent != null) {
                    if(x.parent.parent.location == 'X' && x.location != x.parent.location) {
                        if(x.location == 'R') {
                            rotateLeft(x, sT);
                            x = x.leftChild;
                        }
                        else {
                            rotateRight(x, sT);
                            x = x.rightChild;
                        }
                    }
                    if(x.location != x.parent.location) {
                        if(x.location == 'R') {
                            rotateLeft(x, sT);
                            x = x.leftChild;
                        }
                        else {
                            rotateRight(x, sT);
                            x = x.rightChild;
                        }
                    }
                    
                    x.parent.color = 'b';
                    x.parent.parent.color = 'r';
                    if(x.parent.location == 'R')
                        rotateLeft(x.parent, sT);
                    else
                        rotateRight(x.parent, sT);
                }
            }
        }
        sT.setRootColor('b');
        return;
    }

    public void deletionFixUp(TreeNode x, TreeStructure sT) {
        TreeNode sibling = new TreeNode('b');
        TreeNode nephew = new TreeNode('b');
        TreeNode niece = new TreeNode('b');
        while(true) {
            if(x == sT.getRoot())
                break;
            if(x.color == 'r')
                break;
            if(x.location == 'R')
                sibling = x.parent.leftChild;
            else
                sibling = x.parent.rightChild;
            
            if(x.location == 'R')
                nephew = x.parent.leftChild.leftChild;
            else
                nephew = x.parent.rightChild.rightChild;
            
            if(x.location == 'R') 
                niece = x.parent.leftChild.rightChild;
            else
                niece = x.parent.rightChild.leftChild;

            if(sibling != null && sibling.color == 'r') {
                x.parent.color = 'r';
                sibling.color = 'b';
                if(x.location == 'R')
                    rotateRight(sibling, sT);
                else
                    rotateLeft(sibling, sT);
            }
            
            else if(nephew != null && nephew.color == 'r') {
                sibling.color = x.parent.color;
                x.parent.color = 'b';
                nephew.color = 'b';
                if(x.location == 'R')
                    rotateRight(sibling, sT);
                else
                    rotateLeft(sibling, sT);
                x = sT.getRoot();
            }

            else if(niece != null && niece.color == 'r') {
                niece.color = 'b';
                sibling.color = 'r';
                if(niece.location == 'R')
                    rotateLeft(niece, sT);
                else
                    rotateRight(niece, sT);
            }

            else {
                sibling.color = 'r';
                x = x.parent;
            }
        }
        x.color = 'b';
        return;
    }



}
