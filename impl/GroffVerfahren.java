package lotsize.impl;

import lotsize.AbstractVerfahren;
import lotsize.HeuristikEnum;
import lotsize.Input;

/**
 *  Das Groff-Verfahren nutzt die Eigenschaft der
 *  Kostenfunktionen beim klassischen Losgrößenverfahren,
 *  dass im Optimum die Rüstkosten pro Zeit- und Mengeneinheit (die Grenzrüstkosten)
 *  gleich den Lagerkosten pro Zeit- und Mengeneinheit (Grenzlagerkosten) sind.
 */
public class GroffVerfahren extends AbstractVerfahren {
    public GroffVerfahren(HeuristikEnum heuristik, Input input) {
        super(heuristik, input);
    }

    /**
     * Berechnet den Quotienten aus der Groffschen Approximation der
     * Grenzlagerkosten (h/2) und der Rüstkosten.
     * (Herleitung siehe Frank Herrmann -
     * Operative Planung in IT-Systemen für die Produktionsplanungund -steuerung - S. 277)
     */
    @Override
    protected double calcV(Integer tau, Integer t) {
        return 2 * input.getSetupCostsConcrete() / input.getHoldingCostsConcrete();
    }

    /**
     * Herleitung siehe Frank Herrmann -
     * Operative Planung in IT-Systemen für die Produktionsplanungund -steuerung - S. 277
     */
    @Override
    protected double calcC(Integer tau, Integer t) {
        int j = t - tau;
        return input.getRequirements().get(t) * j * (j + 1);
    }
}
