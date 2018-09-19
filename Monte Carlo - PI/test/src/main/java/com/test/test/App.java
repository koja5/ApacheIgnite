package com.test.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntFunction;
import java.lang.*;
import java.util.IntSummaryStatistics;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.lang.IgniteClosure;
import org.apache.ignite.lang.IgniteReducer;


public class App 
{
	static int brojIteracija = 1000;
    static int brojPokusajaPoIteraciji = 100;
	public static void main(String[] args) throws IgniteException {
	    int i;
		try (Ignite ignite = Ignition.start()) {
			Collection<IgniteCallable<Integer>> pozivi = new ArrayList<>();
			
			// za svaki pokusaj kreiramo po jedan poziv
            for (i = 0; i < brojIteracija; i++) 
                pozivi.add(new IgniteCallable() {
                  public Integer call() throws Exception {
                      return 1;
                  }
              });

            //racunamo ukupnu sumu svih pogodaka
            Integer ukupnaSuma = ignite.compute().apply(
                    new IgniteClosure<Integer, Integer>() {
                        @Override public Integer apply(Integer word) {
                        	int pogodaka = 0;
                        	for(int i=0; i < brojPokusajaPoIteraciji; i++) {
                                double x = Math.random();
                                double y = Math.random();
                                if ((x*x + y*y) <= 1.0) {
                                	pogodaka++;
                                }

                                }
                        	 System.out.println("Broj pogodaka na cvoru je: " + pogodaka);
                             return pogodaka;
                        }
                    },
                    
                    //vrsimo pozivanje da bi izracunali i rasporedili posao
                    ignite.compute().call(pozivi),
                    
                    // Vrsimo sumiranje rezultata sa cvorova.
                    new IgniteReducer<Integer, Integer>() {
                        private AtomicInteger suma = new AtomicInteger();

                        // Poziva se za svaki rezultat koji se vraca sa cvorova.
                        @Override public boolean collect(Integer len) {
                            suma.addAndGet(len);

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
            //posto smo dobili sumu svih pogodaka, na kraju je potrebno samo da primenimo formulu
             double PI = 4.0 * (double)ukupnaSuma / ((double)brojIteracija * brojPokusajaPoIteraciji * 1.0);

            System.out.println("Vrednost za PI je: '" + PI + "'.");
          }

  }
}
