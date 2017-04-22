package lotsize.impl;

import lotsize.AbstractVerfahren;
import lotsize.HeuristikEnum;
import lotsize.Input;

/**
 *	Bei der dynamischen Planungsrechnung fasst das Verfahren, ausgehend vom Bedarfstermin
 *	Bedarfsmengen zu einem Los zusammen, bis die aktuellen Lagerkosten größer als die Rüstkosten sind.
 *	Als Vergleichsgröße V werden somit die Rüstkosten herangezogen.
 */
public class DynamischePlanungsrechnungVerfahren extends AbstractVerfahren {
    public DynamischePlanungsrechnungVerfahren(HeuristikEnum heuristik, Input input) {
        super(heuristik, input);
    }

    /**
     * Rüstkosten als Vegleichsgröße
     */
    @Override
    protected double calcV(Integer tau, Integer t) {
        return input.getSetupCostsConcrete();
    }

    /**
     * Berechnung der Lagerkosten des Bedarfs
     */
    @Override
    protected double calcC(Integer tau, Integer t) {
        int j = t - tau;
        return input.getRequirements().get(t) * j * input.getHoldingCostsConcrete();
    }
}
