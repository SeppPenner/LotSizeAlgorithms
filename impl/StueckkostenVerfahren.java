package lotsize.impl;

import lotsize.AbstractVerfahren;
import lotsize.HeuristikEnum;
import lotsize.Input;

/**
 *  Das Stückkostenverfahren berechnet das optimale Los nach der Eigenschaft
 *  des klassischen Losgrößenverfahrens, dass der Quotient aus den Gesamtkosten
 *  in einem Zyklus und dem Los im Optimum minimal ist.
 */
public class StueckkostenVerfahren extends AbstractVerfahren {
    public StueckkostenVerfahren(HeuristikEnum heuristik, Input input) {
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
     * Berechnet für das Stückkostenverfahren die Durchschnittlichen Stückkosten
     * C aus dem Quotienten der Gesamtkosten und der Summe der Bedarfe zur Periode t.
     * Greift auf die Eingangsgrößen der Klasse {@link Input} zu.
     */
    @Override
	protected double calcC(Integer tau, Integer t) {
        double sum_d1 = 0.0;
        double sum_d2 = 0.0;

        for (int j = tau + 1; j <= t; j++) {
            sum_d1 += input.getRequirements().get(j) * (j - tau);
        }

        for (int j = tau; j <= t; j++) {
            sum_d2 += input.getRequirements().get(j);
        }

        return (input.getSetupCostsConcrete() + input.getHoldingCostsConcrete() * sum_d1) / sum_d2;
    }
}

