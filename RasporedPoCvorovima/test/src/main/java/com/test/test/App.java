package com.test.test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteClosure;
import org.apache.ignite.lang.IgniteReducer;

public class App {
  public static void main(String[] args) throws IgniteException {
    try (Ignite ignite = Ignition.start()) {
      
    	Integer ukupnaSuma = ignite.compute().apply(
                new IgniteClosure<String, Integer>() {
                    @Override public Integer apply(String rec) {
                        System.out.println();
                        System.out.println("Rec '" + rec + "' je rasporedjena na cvoru i ima duzinu " + rec.length() + ".");

                        // Vraca duzinu reci.
                        return rec.length();
                    }
                },

                //Delimo recenicu na reci i te reci rasporedjujemo na cvorove.
                Arrays.asList("Apache Ignite Operativni sistemi 2".split(" ")),

                // Vrsimo sumiranje rezultata sa cvorova.
                new IgniteReducer<Integer, Integer>() {
                    private AtomicInteger suma = new AtomicInteger();

                    // Poziva se za svaki rezultat koji se vraca sa cvorova
                    @Override public boolean collect(Integer duzina) {
                        suma.addAndGet(duzina);

                        // Ovde moramo da cekamo dok svi rezultati ne budu primljeni.
                        //Kada su svi rezultati primljeni mozemo da nastavimo dalje
                        return true;
                    }

                    // Spoji sve rezultate u jedan.
                    @Override public Integer reduce() {
                        return suma.get();
                    }
                }
            );
    		
    		//Ispisujemo ukupan rezultat
    		System.out.println("Ukupan broj karaktera u recenici je " + ukupnaSuma + ".");
    }
  }
}