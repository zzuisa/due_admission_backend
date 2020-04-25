package cn.zzuisa;

import lombok.Data;

import java.sql.*;
import java.util.Queue;

public class LinkedList<T> {

    int size;
    Node<T> head;
    Node<T> tail;

    @Data
    static class Node<T> {

        Node(T t) {
            this.data = t;
        }

        T data;
        Node<T> next;
    }

    LinkedList() {
    }

    LinkedList(Node<T> head) {
        this.head = this.tail = head;
        this.size++;
    }

    public Node<T> headInsert(Node<T> node) {
        if (this.head == null) {
            this.head = this.tail = node;
        } else {
            node.next = this.head;
            this.head = node;
        }
        this.size++;
        return this.head;
    }

    public Node<T> tailInsert(Node<T> node) {
        if (this.head == null) {
            this.head = this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
        this.size++;
        return this.head;
    }

    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> node = this.head;
        while (node != null) {
            sb.append(node.toString() + "\n");
            node = node.next;
        }
        return sb.toString();
    }

    public void rm(int index) {
        if (this.size < index + 1) {
            return;
        }
        if (index == 0) {
            if (this.size == 1) {
                this.head = this.tail = null;
                this.size--;
                return;
            }
            this.head = this.head.next;
            this.size--;
            return;
        }
        int i = 0;
        Node<T> node = this.head;
        while (node != null) {
            Node<T> n2 = node.next;
            if (index == i + 1) {
                node.next = node.next.next;
                if (node.next == null) {
                    this.tail = node;
                }
                this.size--;
                break;
            }
            node = node.next;
            i++;
        }

    }


    public void rm(Node<T> data) {
        if (this.size < 1) {
            return;
        }
        int i = 0;
        Node<T> node = this.head;
        while (node != null) {
            if (node.data == data.data) {
                this.rm(i);
                return;
            }
            node = node.next;
        }
    }


    public static void main(String[] args) {
//        LinkedList<Integer> linkedList = new LinkedList<>(new Node<Integer>(5));
//        linkedList.headInsert(new Node<>(6));
//        linkedList.headInsert(new Node<>(7));
//        linkedList.tailInsert(new Node<>(10));
//        linkedList.tailInsert(new Node<>(8));
//        linkedList.rm(4);
//        linkedList.tailInsert(new Node<>(9));
//        linkedList.rm(new Node<>(7));
//        System.out.println(linkedList);
        Connection connection = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/webnews", "root", "123456");
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};// 数组变量types
            rs = metaData.getTables("", "", null, null);
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
                ResultSet keys = metaData.getPrimaryKeys("", "", rs.getString("TABLE_NAME"));
                keys.next();
                System.out.println(keys.getString(4));
            }
            Queue queue = new java.util.LinkedList();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

}
