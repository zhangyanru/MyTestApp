package com.example;

public class MainClass {
    public static void main(String[] args){
        BinaryTree binaryTree = new BinaryTree(5);
        binaryTree.insert(8);
        binaryTree.insert(9);
        binaryTree.insert(3);
        binaryTree.insert(2);
        binaryTree.insert(4);
        binaryTree.insert(1);
        binaryTree.insert(6);
        binaryTree.insert(7);
        binaryTree.insert(0);

        System.out.print("\n递归前序遍历\n");
        binaryTree.preOrder();
        System.out.print("\n非 递归前序遍历2\n");
        binaryTree.preOrder2();
        System.out.print("\n非 递归前序遍历3\n");
        binaryTree.preOrder3();


        System.out.print("\n递归中序遍历\n");
        binaryTree.inOrder();
        System.out.print("\n非 递归中序遍历\n");
        binaryTree.inOrder2();

        System.out.print("\n递归后序遍历\n");
        binaryTree.postOrder();
        System.out.print("\n非 递归后序遍历\n");
        binaryTree.postOrder2();

        System.out.print("\n广度遍历\n");
        binaryTree.travelOrder();
    }
}
