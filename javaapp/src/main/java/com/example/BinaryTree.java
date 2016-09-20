package com.example;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by yanru.zhang on 16/9/19.
 * Email:yanru.zhang@renren-inc.com
 */
public class BinaryTree{
    public int data;
    public BinaryTree left;
    public BinaryTree right;

    public BinaryTree(int data){
        this.data = data;
        left = null;
        right = null;
    }

    //中序的有序树插入
    public void insert(int i){
        if(i > this.data){
            if(right == null){
                right = new BinaryTree(i);
            }else{
                right.insert(i);
            }
        }else{
            if(left == null){
                left = new BinaryTree(i);
            }else{
                left.insert(i);
            }
        }
    }

    //前序遍历
    public void preOrder(){
        System.out.print(data + " ");
        if(left != null){
            left.preOrder();
        }
        if(right != null){
            right.preOrder();
        }
    }

    //前序遍历的非递归遍历
    public void preOrder2(){
        Stack<BinaryTree> stack = new Stack<>();
        BinaryTree currentTree = this;
        if(currentTree != null){
            stack.push(currentTree);
            while (!stack.empty()){
                currentTree = stack.pop();
                System.out.print(currentTree.data + " ");
                if(currentTree.right != null){
                    stack.push(currentTree.right);
                }
                if(currentTree.left != null){
                    stack.push(currentTree.left);
                }
            }
        }
    }

    //前序遍历的非递归遍历
    public void preOrder3(){
        Stack<BinaryTree> stack = new Stack<>();
        BinaryTree currentTree = this;
        while (currentTree != null || !stack.empty()){
            while (currentTree != null){
                System.out.print(currentTree.data + " ");
                stack.push(currentTree);
                currentTree = currentTree.left;
            }
            currentTree = stack.pop();
            currentTree = currentTree.right;
        }
    }

    //中序遍历
    public void inOrder(){
        if(left != null){
            left.inOrder();
        }
        System.out.print(data + " ");
        if(right != null){
            right.inOrder();
        }
    }

    //中序遍历的非递归遍历
    public void inOrder2(){
        Stack<BinaryTree> stack = new Stack<>();
        BinaryTree currentTree = this;
        while (currentTree != null || !stack.empty()){
            while (currentTree!=null){
                stack.push(currentTree);
                currentTree = currentTree.left;
            }
            currentTree = stack.pop();
            System.out.print(currentTree.data + " ");
            currentTree = currentTree.right;
        }

    }

    //后序遍历
    public void postOrder(){
        if(left != null){
            left.postOrder();
        }
        if(right != null){
            right.postOrder();
        }
        System.out.print(data + " ");
    }

    //后序非递归遍历
    public void postOrder2(){
        Stack<BinaryTree> stack = new Stack<>();
        BinaryTree currentTree = this;
        BinaryTree preTree = null;
        while (currentTree != null) {
            stack.push(currentTree);
            currentTree = currentTree.left;
        }
        while (!stack.empty()) {
            currentTree = stack.pop();
            if(currentTree.right == null || currentTree.right == preTree){
                System.out.print(currentTree.data + " ");
                preTree = currentTree;
            }else{
                stack.push(currentTree);
                currentTree = currentTree.right;
                while (currentTree != null){
                    stack.push(currentTree);
                    currentTree = currentTree.left;
                }
            }
        }
    }

    //广度遍历
    public void travelOrder(){
        Queue<BinaryTree> queue = new LinkedList<>();
        BinaryTree currentTree = this;
        queue.add(this);
        while (!queue.isEmpty()){
            currentTree = queue.remove();
            if(currentTree != null){
                System.out.print(currentTree.data + " ");
                queue.add(currentTree.left);
                queue.add(currentTree.right);
            }
        }
    }
}
