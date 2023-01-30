package org.example;

public class Problem {
    private static Problem problem = new Problem();

    private Problem(){}

    public static Problem getInstance(){return problem;}

    public void problemInternet()  {
        try {
            System.out.println("Problem Intenet");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
