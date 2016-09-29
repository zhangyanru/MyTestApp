package com.example;

import java.util.ArrayList;
import java.util.List;

public class MainClass {
    public static void main(String[] args){

        //二叉树
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

        //计算N内的所有素数
        PrimeNumber primeNumber = new PrimeNumber();
        primeNumber.prime1(100);
        primeNumber.prime2(100);
        primeNumber.prime3(100);

        //有序链表
        LinkList<String> linkList = new LinkList<>();
        linkList.insert("a");
        linkList.insert("bcd");
        linkList.insert("d");
        linkList.insert("c");
        System.out.println("\nlinkList:" + linkList.toString());
        LinkList.Node find = linkList.find("c");
        if(find != null){
            System.out.println("find:" + find.data.toString());
        }
        LinkList.Node delete = linkList.delete("a");
        if(delete != null){
            System.out.println("delete:" + delete.data.toString());
        }
        System.out.println("linkList:" + linkList.toString());

        //循环数组
        CyclicOrderedArray array = new CyclicOrderedArray();
        int index = array.find(0,new int[]{8,9,0,1,2,3,4,5,6,7});
        System.out.println(index + "");
        index = array.find(0,new int[]{3,2,1,0,9,8,7,6,5,4});
        System.out.println(index + "");

        List<String> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(i+"");
        }
        System.out.println(list.toString());
        for(int i=list.size()-1;i>=0;i--){
            list.remove(i);
            System.out.println(list.toString());

        }
    }
}
