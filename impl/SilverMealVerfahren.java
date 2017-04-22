package lotsize.impl;

import lotsize.AbstractVerfahren;
import lotsize.HeuristikEnum;
import lotsize.Input;

/**
 *  Beim Silver-Meal-Verfahren werden die Gesamtkosten eines Loses
 *  durch seine Reichweite dividiert (t - tau + 1).
 *  Im klassischen Modell handelt es sich um die Gesamtkosten pro Zeiteinheit für ein Los,
 *  die im Optimum minimal sind.
 */
public class SilverMealVerfahren extends AbstractVerfahren {
    public SilverMealVerfahren(HeuristikEnum heuristik, Input input) {
        super(heuristik, input);
    }

    /**
     * @return Kostenkriterium C der Vorperiode (t-1)
     */
    @Override
    protected double calcV(Integer tau, Integer t) {
        return calcC(tau, t - 1);
    }

    /**
     * Berechnet die durchschnittlichen Stückkosten je Zeiteinheit
     * C aus dem Quotienten der Gesamtkosten und der Reichweite des Loses (t - tau + 1).
     */
    @Override
    protected double calcC(Integer tau, Integer t) {
        double sum_dj = 0.0;
        for (int j = tau + 1; j <= t; j++) {
            sum_dj += (j - tau) * input.getRequirements().get(j);
        }
        return (input.getSetupCostsConcrete() + input.getHoldingCostsConcrete() * sum_dj) / (t - tau + 1);
    }
}
