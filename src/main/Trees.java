// CS 201 Assignment 2
// Trees File
// Contains MAIN
// Brandon Vowell

package main;

import java.util.Scanner;
import java.io.*;
import main.*;


class Trees {
    public static void main(String[] args) {
        
        String fileName = args[1];
        char treeType = args[0].charAt(1);
        String commandFileName = args[2];
        File file = new File(fileName);
        File file2 = new File(commandFileName);
        TreeNode nextTreeNode = new TreeNode("ToBeOverwritten");
        TreeStructure searchTree = new TreeStructure(nextTreeNode);
        Queue levelOrderStorage = new Queue();

        try {
            Scanner sc = new Scanner(file);
            if(!sc.hasNext())
                return;
            String upcomingString = sc.next();
            nextTreeNode = new TreeNode(upcomingString);
            String v = nextTreeNode.filterString();
            nextTreeNode.setValue(v);
            searchTree.setRoot(nextTreeNode);
            searchTree.setRootColor('b');
            while(sc.hasNext()) {
                upcomingString = sc.next();
                nextTreeNode = new TreeNode(upcomingString);
                v = nextTreeNode.filterString();
                nextTreeNode.setValue(v);
                nextTreeNode = searchTree.insertNewTreeNode(searchTree.getRoot(), nextTreeNode);
                if(treeType == '2')
                    nextTreeNode.insertionFixUp(nextTreeNode, searchTree);
            }
            sc.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        handleOptions(treeType,levelOrderStorage, searchTree, file2);
        
    }

    private static void handleOptions(char treeType, Queue q, TreeStructure sT, File f) {   
        try {
            Scanner commands = new Scanner(f);
            String choice = "NGH";
            String option;
            while(commands.hasNext()) {
                option = commands.next();
                if(option.charAt(0) != 's' && option.charAt(0) != 'r') {
                    choice = commands.next();
                    TreeNode temporary = new TreeNode(choice);
                    choice = temporary.filterString();

                }

                if(option.charAt(0) == 'i') // INSERT
                    insertWord(choice, treeType, sT);

                if(option.charAt(0) == 'd') //DELETE
                    deleteWord(choice, treeType, sT);

                if(option.charAt(0) == 'f') //FIND OR REPORT FREQUENCY
                    findWord(choice, sT);

                if(option.charAt(0) == 's') // SHOW TREE
                    showTree(treeType, q, sT);

                if(option.charAt(0) == 'r') // REPORT STATS
                    printStatistics(q, sT);

                if(option.charAt(0) == 'q') {
                    break;
                }
            
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void insertWord(String option, char treeType, TreeStructure sT) {
        String wordToBeInserted = option;
        TreeNode nextTreeNode = new TreeNode(wordToBeInserted);
        nextTreeNode.filterString();
        nextTreeNode = sT.insertNewTreeNode(sT.getRoot(), nextTreeNode);
        if(treeType == '2')
            nextTreeNode.insertionFixUp(nextTreeNode, sT);
        return;
    }

    private static void deleteWord(String option, char treeType, TreeStructure sT) {
        String wordToBeDeleted = option;
        TreeNode toDelete = sT.findParticularWord(sT.getRoot(), wordToBeDeleted);
        if(toDelete == null)
            System.out.println("The word " + wordToBeDeleted + " is not in the tree.");
        else if(toDelete.getCount() > 1) {
            int c = toDelete.getCount();
            toDelete.setCount(c - 1);
        }
        else {
            TreeNode predecessorChain = sT.deleteParticularWord(toDelete);
            if(treeType == '2') {
                predecessorChain.deletionFixUp(predecessorChain, sT);
            }
            predecessorChain.setNodeToNull(predecessorChain, sT);
        }
        return;
    }

    private static void findWord(String option, TreeStructure sT) {
        String wordToBeFound = option;
        TreeNode nodeToBeFound = sT.findParticularWord(sT.getRoot(), wordToBeFound);
        if(nodeToBeFound == null)
            System.out.println("The word " + wordToBeFound + " is not in the tree.");
        else
            System.out.println("Frequency of " + wordToBeFound + ": " + nodeToBeFound.getCount());
        return;
    }

    private static void showTree(char treeType, Queue q, TreeStructure sT) {
        if(sT.getRoot() != null) {
            QueueNode rootQueueNode = new QueueNode(sT.getRoot());
            q.enqueue(rootQueueNode);
        }
        q.printLevelOrder(treeType);
        return;
    }

    private static void printStatistics(Queue q, TreeStructure sT) {
        System.out.println("Number of Nodes: " + sT.getNodeCount());
        System.out.println("Minimum Depth of Tree: " + sT.findMinimumDepth(q));
        System.out.println("Maximum Depth of Tree: " + sT.findMaximumDepth(q));
        System.out.println();
        return;
    }
}
