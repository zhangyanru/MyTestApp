package com.example;

/**
 * Created by yanru.zhang on 16/9/20.
 * Email:yanru.zhang@renren-inc.com
 *
 * 从小到大排序的有序链表
 */
public class LinkList<T extends Comparable<T>> {
    private Node<T> head = null;

    public boolean isEmpty(){
        return head==null;
    }

    public void insert(T data){
        Node<T> node = new Node<T>(data);
        Node<T> current = head,previous = null;

        while (current !=null && data.compareTo(current.data) > 0){
            previous = current;
            current = current.next;
        }
        if(previous == null){
            head = node;
        }else{
            previous.next = node;
        }
        node.next = current;
    }

    public Node<T> find(T t){
        Node<T> current = head;
        while (current !=null){
            if(current.data.equals(t)){
                return current;
            }
            current = current.next;
        }
        return current;
    }

    public Node<T> delete(T data){
        if(isEmpty())return null;
        Node<T> current = head,previous=null;
        while (current != null){
            if(current.data.equals(data)){
                //头结点和数据相等
                if(previous == null){
                    head = current.next;
                    return current;
                }
                previous.next = current.next;
                current.next = null;
                return current;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    @Override
    public String toString() {
        if(isEmpty()) return "";
        StringBuilder stringBuilder = new StringBuilder();
        Node<T> current = head;
        while (current != null){
            stringBuilder.append("," + current.data.toString());
            current = current.next;
        }
        return stringBuilder.toString();
    }

    class Node<T extends Comparable<T>>{
        T data;
        Node<T> next;
        public Node(T data) {
            this.data = data;
        }
    }
}
