// Assignment 2
// Root of Tree Class
// Brandon Vowell

package main;

public class TreeStructure {
    
    TreeNode root;
    static int nodeCount;
    static int minDepth;
    static int maxDepth;
    
    public TreeStructure(TreeNode value) {
        root = value;
        nodeCount = 1;
    }
    
    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode value) {
        root = value;
        return;
    }

    public void setRootColor(char c) {
        root.color = c;
        return;
    }

    public static int getNodeCount() {
        return nodeCount;
    }

    public TreeNode insertNewTreeNode(TreeNode startNode, TreeNode newValue) {
        if(startNode == null) {
            startNode = newValue;
            nodeCount++;
            return startNode;
        }
        int compareResult = newValue.value.compareTo(startNode.value); 
        if(compareResult == 0) {
            startNode.count++;
            newValue.location = 'I';
            return startNode;
        }
        if(compareResult < 0 && startNode.leftChild == null) {
            startNode.leftChild = newValue;
            newValue.parent = startNode;
            newValue.level++;
            newValue.location = 'L';
            nodeCount++;
            return newValue;
        }
        else if(compareResult < 0 && startNode.leftChild != null) {
            newValue.level++;
            return insertNewTreeNode(startNode.leftChild, newValue);
        }
        else if(compareResult > 0 && startNode.rightChild == null) {
            startNode.rightChild = newValue;
            newValue.parent = startNode;
            newValue.level++;
            newValue.location = 'R';
            nodeCount++;
            return newValue;
        }
        else {
            newValue.level++;
            return insertNewTreeNode(startNode.rightChild, newValue);
        }
    }

    public TreeNode findParticularWord(TreeNode start, String valueToFind) {
        if(start == null)
            return null;
        int compareResult = valueToFind.compareTo(start.value);
        if(compareResult == 0)
            return start;
        else if(compareResult < 0 && start.leftChild != null) 
            return findParticularWord(start.leftChild, valueToFind);
        else if(compareResult < 0 && start.leftChild == null)
            return null;
        else if(compareResult > 0 && start.rightChild != null)
            return findParticularWord(start.rightChild, valueToFind);
        else
            return null;
    }

    public TreeNode deleteParticularWord(TreeNode valueToDelete) {
        if(valueToDelete == null) {
            return null;
        }
        if(valueToDelete.isLeaf() == true) {
            return valueToDelete;
        }
        else {
            TreeNode originalValue = valueToDelete;
            TreeNode predecessor = valueToDelete;
            while(predecessor.isLeaf() == false) {
                predecessor = originalValue.findPredecessor(originalValue);
                String temp1 = originalValue.value;
                int temp2 = originalValue.count;
                originalValue.value = predecessor.value;
                predecessor.value = temp1;
                originalValue.count = predecessor.count;
                predecessor.count = temp2;
                originalValue = predecessor;
            }
            nodeCount--;
            return predecessor;
        }
    }

    public int findMinimumDepth(Queue q) {
        if(root == null)
            return 0;
        QueueNode nextQueueNode = new QueueNode(root);
        q.enqueue(nextQueueNode);
        while(q.getHead() != null) {
            TreeNode valueToCheck = q.peek();
            if(valueToCheck.isLeaf() == true)
                return valueToCheck.level;
            if(valueToCheck.leftChild != null) {
                nextQueueNode = new QueueNode(valueToCheck.leftChild);
                q.enqueue(nextQueueNode);
            }
            if(valueToCheck.rightChild != null) {
                nextQueueNode = new QueueNode(valueToCheck.rightChild);
                q.enqueue(nextQueueNode);
            }
            q.dequeue();
        }
        
        return 0;
    } 

    public int findMaximumDepth(Queue q) {
        if(root == null)
            return 0;
        QueueNode nextQueueNode = new QueueNode(root);
        q.enqueue(nextQueueNode);
        int maxLevel = 0;
        while(q.getHead() != null) {
            TreeNode valueToCheck = q.peek();
            if(valueToCheck.leftChild != null) {
                nextQueueNode = new QueueNode(valueToCheck.leftChild);
                q.enqueue(nextQueueNode);
            }
            if(valueToCheck.rightChild != null) {
                nextQueueNode = new QueueNode(valueToCheck.rightChild);
                q.enqueue(nextQueueNode);
            }
            if(valueToCheck.level > maxLevel)
                maxLevel = valueToCheck.level;
            q.dequeue();
        }

        return maxLevel;
    }
            
            




}
