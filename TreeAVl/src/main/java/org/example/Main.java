package org.example;

import structures.AVL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        AVL<Integer> avl = new AVL<>();

        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            int oper = sc.nextInt();
            if(oper == 1){
                int data = sc.nextInt();
                avl.AVLInsert(data);
            } else if(oper == 2){
                int data = sc.nextInt();
                avl.AVLDelete(data);
            } else {
                System.out.println(avl.preOrder());
            }
        }

    }
}