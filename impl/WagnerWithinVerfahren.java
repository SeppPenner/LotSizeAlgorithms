package lotsize.impl;

import java.util.ArrayList;
import java.util.HashMap;

import lotsize.AbstractVerfahren;
import lotsize.HeuristikEnum;
import lotsize.Input;
import lotsize.Lot;
import lotsize.Solution;

/**
 * Klasse zum Berechnen der optimalen Lösung nach Wagner Within.
 */

public class WagnerWithinVerfahren extends AbstractVerfahren {

    public WagnerWithinVerfahren(HeuristikEnum heuristik, Input input) {
        super(heuristik, input);
    }

    /**
     * Funktion zum Berechnen der optimalen Lösung nach dem Wagner Within Verfahren.
     *
     * @return Solution Objekt
     */
    @Override
    public Solution doTheMagic() {
        final long timeStart = System.nanoTime();
        Integer tau = 1;
        Integer T = input.getRequirements().size();

        Solution solution = new Solution(heuristik, input);

        HashMap<Integer, Double> C = new HashMap<Integer, Double>();
        Double C_Star = null;
        HashMap<Integer, Integer> j = new HashMap<>();

        for (int t = tau + 1; t <= T + 1; t++) {
            C.put(t, calcC(tau, t));
            j.put(t, 1);
        }

        do {
			tau = tau + 1;
			for (int t = tau + 1; t <= T + 1; t++) {
				C_Star = C.get(tau) + calcC(tau, t);
				if (C_Star < C.get(t)) {
					C.put(t, C_Star);
					j.put(t, tau);
				}
			}
		} while (tau < T);

        ArrayList<Integer> optimalerWeg = new ArrayList<Integer>();
        for (int i = T + 1; i > 1; i = j.get(i)) {
            optimalerWeg.add(0, i);
        }
        optimalerWeg.add(0, 1); //j_0 = 1

        for (int t = 0; t <= (optimalerWeg.size() - 2); t++) {
            int q = 0;
            for (int i = optimalerWeg.get(t); i <= (optimalerWeg.get(t + 1) - 1); i++) {
                q += input.getRequirements().get(i);
            }
            solution.getLots().put(optimalerWeg.get(t), new Lot(q, optimalerWeg.get(t), input.getSetupCostsConcrete(), calcC(optimalerWeg.get(t), optimalerWeg.get(t + 1)) - input.getSetupCostsConcrete()));
        }

        solution.setRuntime(System.nanoTime() - timeStart);

        solution.calcCosts(); //Alle Kosten berechnen.
        return solution;
    }

    /**
     * Diese Funktion wird für das Wager Within Verfahren nicht benötigt und nicht verwendet.
     */
    @Override
    protected double calcV(Integer tau, Integer t) {
        return 0;
    }

    /**
     * Hier werden die Gesamtkosten vom Knoten tau zum Knoten t berechnet.
     *
     * @param tau Startknoten
     * @param t Endknoten
     * @return Gesamtkosten vom Knoten tau zum Knoten t
     */
    @Override
    protected double calcC(Integer tau, Integer t) {
        double sum_dj = 0;
        for (int j = tau + 1; j <= (t - 1); j++) {
            sum_dj += (j - tau) * input.getRequirements().get(j);
        }
        return input.getSetupCostsConcrete() + input.getHoldingCostsConcrete() * (sum_dj);
    }
}
