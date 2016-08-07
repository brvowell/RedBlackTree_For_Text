// Assignment 2
// Queue Class
// Brandon Vowell

package main;

import java.io.*;
import java.util.*;

public class Queue {
    
    QueueNode head;
    QueueNode tail;

    public Queue() {
        head = null;
        tail = null;
    }

    public void enqueue(QueueNode newValue) {
        if(head == null) {
            head = newValue;
            tail = newValue;
        }
        else {
            newValue.previous = tail;
            tail.next = newValue;
            tail = newValue;
        }
        return;
    }

    public void dequeue() {
        if(head == null)
            System.out.println("No value to dequeue");
        else {
            head = head.next;
            if(head != null)
                head.previous = null;
        }
        return;
    }

    public TreeNode peek() {
        return head.value;
    }

    public QueueNode getHead() {
        return head;
    }

    public void printLevelOrder(char whichTree) {
        if(head == null) {
            System.out.println("The Tree is empty");
            return;
        }
        int i = 1;
        System.out.print(i + ":" + "  ");
        while(head != null) {
            if(head.value.level != i) {
                System.out.println();
                i++;
                System.out.print(i + ":" + "  ");
            }

            if(head.value.leftChild == null && head.value.rightChild == null)
                System.out.print("=");
            System.out.print(head.value.value);
            if(head.value.color == 'r' && whichTree == '2')
                System.out.print("*");
            if(head.value.parent == null)
                System.out.print("(" + head.value.value + ")");
            else {
                System.out.print("(" + head.value.parent.value);
                if(head.value.parent.color == 'r' && whichTree == '2')
                    System.out.print("*");
                System.out.print(")");
            }
            System.out.print(head.value.count);
            System.out.print(head.value.location); System.out.print(" ");

            if(head.value.leftChild != null) {
                QueueNode leftNode = new QueueNode(head.value.leftChild);
                enqueue(leftNode);
            }
            if(head.value.rightChild != null) {
                QueueNode rightNode = new QueueNode(head.value.rightChild);
                enqueue(rightNode);
            }
            dequeue();
        }
        System.out.println(); System.out.println();
        return;
    }
            

    

        

}
