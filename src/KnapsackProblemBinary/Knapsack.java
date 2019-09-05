package KnapsackProblemBinary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 *
 * @author Sharkb8i
 */
public class Knapsack {
    private int capacity;                   // Capacidade da mochila.
    private ArrayList<Integer> knapsack;    // Quais itens foram pra mochila. 
    private ArrayList<Integer> benefits;    // Benefícios.
    private ArrayList<Integer> costs;       // Custos.
    private ArrayList<Float> cb;            // Custo-Benefício de cada item.
    private float tcb;                      // Custo-Benefício total.
    private ArrayList<Integer> index;       // Indices dos itens 
    
    //static String file = "C:\\Users\\Sharkb8i\\Desktop\\FACUL\\PAA\\TRABALHO2\\Proposta 3\\Mochila10.txt";
    static String file = "D:\\Aulas\\PAA\\trab2\\Proposta 3\\Mochila10.txt";
    
    public Knapsack() {
        this.knapsack = new ArrayList();
        this.benefits = new ArrayList();
        this.costs = new ArrayList();
        this.cb = new ArrayList();
        this.index = new ArrayList();
    }
    
    /*** SETTERS ***/
    public void setIndex(int index){
        this.index.add(index);
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public void setItem(int index) {
        this.knapsack.add(index);
    }
    
    public void setBenefit(int b) {
        this.benefits.add(b);
    }
    
    public void setCost(int c) {
        this.costs.add(c);
    }
    
    public void setBenefitPerCost(float cb) {
        this.cb.add(cb);
    }
    
    public void setTotalBenefitPerCost (float tcb) {
        this.tcb = tcb;
    }
    
    /*** GETTERS ***/
    public ArrayList<Integer> getIndex(){
        return this.index;
    }
    
    public int getCapacity() {
        return this.capacity;
    }
    
    public ArrayList<Integer> getItems() {
        return this.knapsack;
    }
    
    public ArrayList<Integer> getBenefits() {
        return this.benefits;
    }
    
    public ArrayList<Integer> getCosts() {
        return this.costs;
    }
    
    public ArrayList<Float> getBenefitPerCost() {
        return this.cb;
    }
    
    public float getTotalBenefitPerCost() {
        return this.tcb;
    }
    
    static int max(int a, int b) {
        return (a > b) ? a : b;
    }
    
    /**
     * Implementação estratégia gulosa sem ordenação
     */
    public static void GreedyStrategy(Knapsack k) throws IOException {
        //ArrayList<Integer> costs = k.getCosts();
        //ArrayList<Float> benefitPerCost = k.getBenefitPerCost();
        List<Integer> costs = new ArrayList<>(k.getCosts());
        List<Float> benefitPerCost = new ArrayList<>(k.getBenefitPerCost());
        float largest = 0;
        int counter = 0, index = 0;
        int id = 0;
        
        while(counter < k.getBenefits().size() && k.getCapacity() > k.getTotalBenefitPerCost()) {
            
            for(int i = 0; i < benefitPerCost.size(); i++) {
                // System.out.println("benefitPerCost.get: " + benefitPerCost.get(i));
                
                if(benefitPerCost.get(i) > largest) {
                    //System.out.println(benefitPerCost.get(i));
                    index = i;
                    id = k.getIndex().get(i);
                    largest = benefitPerCost.get(i);
                }
            }
            
            if((k.getTotalBenefitPerCost() + costs.get(index)) > k.getCapacity())
                break;
            
            /*
            System.out.println("Largest : " + largest);
            System.out.println("Index   : " + index);
            System.out.println("Cost    : " + costs.get(index) + "\n");
            */
            
            k.setItem(id);
            k.setTotalBenefitPerCost(k.getTotalBenefitPerCost() + costs.get(index));
            costs.set(index, -1);
            //costs.set(index, costs.get(index)*(-1));
            benefitPerCost.set(index, ((float)-1));
            index   = 0;
            largest = 0;
            counter++;
        }
    }
    
    /**
     * Method to call the Quicksort Algorithm.
     * @param k - knapsack class with the informations
     * @param start - knapsack first item position
     * @param end - knapsack last item position
     */
    public static void QuickSort(Knapsack k, int start, int end) throws IOException {
        /* The pivot can be elected in several ways: 
            - Fist element;
            - Last element;
            - Central (Middle) element;
            - Randomly. */
        if(start < end){
            int pivot = Split(k, start, end);
            QuickSort(k, start, pivot - 1);
            QuickSort(k, pivot + 1, end);
        }
    }
    
    /**
     * Method that separates the knapsack into smaller partitions
     * @param k - knapsack class with the informations
     * @param start - knapsack left item position
     * @param end - knapsack right item position
     * @return 
     */
    public static int Split(Knapsack k, int start, int end) throws IOException {
        int left = start + 1, right = end;
        float pivot = k.getBenefitPerCost().get(start);
        
        while(left <= right) {
            if(k.getBenefitPerCost().get(left) <= pivot) left++;
            else if(k.getBenefitPerCost().get(right) > pivot) right--;
            else if(left <= right) {
                Swap(k, left, right);
                left++;
                right--;
            }
        }
        Swap(k, start, right);
        return right;
    }
    
    /**
     * Method to switch positions.
     * @param k - knapsack class with the informations
     * @param start - posicao do primeiro item da mochila
     * @param end - posicao do ultimo item da mochila
     */
    public static void Swap(Knapsack k, int start, int end) {
        float temp = k.getBenefitPerCost().get(start);
        k.getBenefitPerCost().set(start, k.getBenefitPerCost().get(end));
        k.getBenefitPerCost().set(end, temp);
        
        int tp = k.getBenefits().get(start);
        k.getBenefits().set(start, k.getBenefits().get(end));
        k.getBenefits().set(end, tp);
        
        tp = k.getCosts().get(start);
        k.getCosts().set(start, k.getCosts().get(end));
        k.getCosts().set(end, tp);
        
        tp = k.getIndex().get(start);
        k.getIndex().set(start, k.getIndex().get(end));
        k.getIndex().set(end, tp);
    }
    
    /**
     * MAIN
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        /*
         *   Declaração de variáveis
         */
        BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
        Scanner in = new Scanner(System.in);
        
        Knapsack k = new Knapsack();
        String line;
        int count = 0;
        
        /*
         *   1. Leitura do arquivo
         *   2. Atribuição dos respectivos valores as suas respectivas variáveis
         */
        while((line = reader.readLine()) != null) {
            if(count == 0)
                k.setCapacity(Integer.parseInt(line));
            
            if(count == 1) {
                List<String> arrayListBenefits = new ArrayList<> (Arrays.asList(line.split("\t")));
                arrayListBenefits.forEach((i) -> {
                    k.setBenefit(Integer.parseInt(i));
                });
            }
            
            if(count == 2) {
                List<String> arrayListCosts = new ArrayList<> (Arrays.asList(line.split("\t")));
                arrayListCosts.forEach((i) -> {
                    k.setCost(Integer.parseInt(i));
                });
            }
            count++;
        }
        
        for(int i=1; i<=k.getBenefits().size(); i++)
            k.setIndex(i);
        
        /*
         *   Cálculos (Custo-Benefício)
         */
        for(int i = 0; i < k.getBenefits().size(); i++)
            k.setBenefitPerCost((float) k.getBenefits().get(i)/k.getCosts().get(i));
        
        System.out.println("Capacity of Knapsack: " + k.getCapacity());
        System.out.println("Benefits : " + k.getBenefits());
        System.out.println("Costs    : " + k.getCosts());
        System.out.println("CB       : " + k.getBenefitPerCost());
        System.out.println("Index    : " + k.getIndex());
        
        GreedyStrategy(k);
        System.out.println("\nFirst Greedy...");
        System.out.println("CB       : " + k.getBenefitPerCost());
        System.out.println("Benefits : " + k.getBenefits());
        System.out.println("Costs    : " + k.getCosts());
        System.out.println("Index    : " + k.getIndex());
        System.out.println("Total    : " + k.getTotalBenefitPerCost());
        k.setTotalBenefitPerCost(0);
        
        /*
         *   Otimização (utilizando QuickSort)
         */
        QuickSort(k, 0, k.getBenefitPerCost().size()-1);
        System.out.println("\nOrdering CB...");
        System.out.println("CB       : " + k.getBenefitPerCost());
        System.out.println("Benefits : " + k.getBenefits());
        System.out.println("Costs    : " + k.getCosts());
        System.out.println("Index    : " + k.getIndex());
        
        /*
         *   Programação Gulosa
         */
        GreedyStrategy(k);
        
        System.out.println("\nCB       : " + k.getBenefitPerCost());
        System.out.println("Items    : " + k.getItems());
        System.out.println("Total    : " + k.getTotalBenefitPerCost());
        System.out.println("Index    : " + k.getIndex());
        
        //  HOW TO COUNT THE TIME:
        
        // long startTime = System.currentTimeMillis();
        // CÓDIGO
        // long estimatedTime = System.currentTimeMillis() - startTime;
        // System.out.println("\nTempo de Execução (s): " + (estimatedTime/1000) + "s");
    }
}