package lotsize.impl;

import lotsize.AbstractVerfahren;
import lotsize.HeuristikEnum;
import lotsize.Input;

/**
 *  Das Stückkostenperiodenausgleichsverfahren nutzt die Eigenschaft der
 *  Kostenfunktionen beim klassischen Losgrößenverfahren, dass im Optimum
 *  das Produkt aus Lagerkosten pro Zeiteinheit und Zyklusdauer
 *  gleich dem Rüstkostensatz ist.
 */
public class StueckperiodenausgleichsVerfahren extends AbstractVerfahren {
    public StueckperiodenausgleichsVerfahren(HeuristikEnum heuristik, Input input) {
        super(heuristik, input);
    }

    /**
     *  Quotient aus Rüst- und Lagerkosten
     */
    @Override
    protected double calcV(Integer tau, Integer t) {
        return input.getSetupCostsConcrete() / input.getHoldingCostsConcrete();
    }

    /**
     * Berechnet die durch das Los verursachten Lagermengen in ME/ZE (auch bezeichnet als Stückperioden)
     */
    @Override
    protected double calcC(Integer tau, Integer t) {
        double sum_dj = 0;
        for (int j = tau + 1; j <= t; j++) {
            sum_dj += (j - tau) * input.getRequirements().get(j);
        }
        return sum_dj;
    }
}
