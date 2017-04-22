package lotsize;

public abstract class AbstractVerfahren
{

    protected final Input input;

    protected final HeuristikEnum heuristik;

    public AbstractVerfahren(HeuristikEnum heuristik, Input input)
    {
        this.heuristik = heuristik;
        this.input = input;
    }

    /**
     * Diese funktion enhält die Grundstruktur der Algorithmen. Die Abweichenden Funktionen für die Berechnung des
     * Kostenkriteriums und Vergleichsgröße werden in geerbten Klassen überschrieben.
     *
     * @return Output Objekt mit den entsprechenden Losen
     */
    public Solution doTheMagic()
    {
        final long timeStart = System.nanoTime();

        // Produktionszyklus
        Integer tau = 1;
        Integer T = input.getRequirements().size();

        Solution solution = new Solution(heuristik, input);

        //Output output = new Output(getName(), input);

        for (int t = tau + 1; t <= T; ) {
            double C_t = calcC(tau, t);
            double V_t = calcV(tau, t);
            boolean b = compareCV(C_t, V_t);
            // Schritt 4
            if (b && t < T) {
                t += 1;
                continue;
            }

            // Schritt 5

            if (!b) {
                double q_tau = 0.0;
                for (int i = tau; i <= (t - 1); i++) {
                    q_tau += input.getRequirements().get(i);
                }
                double h_tau = 0;
                for (int i = tau; i < t; i++) {
                    h_tau += input.getRequirements().get(i) * (i - tau) * input.getHoldingCostsConcrete();
                }
                solution.getLots().put(tau, new Lot(q_tau, tau, input.getSetupCostsConcrete(), h_tau));
                tau = t;
                t = tau + 1;
                if (t > T) {
                    q_tau = input.getRequirements().get(T);
                    solution.getLots().put(tau, new Lot(q_tau, tau, input.getSetupCostsConcrete(), 0.0));
                }
            } else if (t == T) {
                double q_tau = 0.0;
                for (int i = tau; i <= T; i++) {
                    q_tau += input.getRequirements().get(i);
                }
                double h_tau = 0;
                for (int i = tau; i <= t; i++) {
                    h_tau += input.getRequirements().get(i) * (i - tau) * input.getHoldingCostsConcrete();
                }
                solution.getLots().put(tau, new Lot(q_tau, tau, input.getSetupCostsConcrete(), h_tau));
                t += 1;
            }
        }

        /*
        // Lagerbesand am Ende aller Perioden
        // Loßgrößen aufaddieren
        for(Map.Entry<Integer, Lot> lot : solution.getLots().entrySet()){
            solution.stockHoldingPeriodEnd += lot.getValue().getLotSize();
        }

        // Bedarfe vom Lagerbestand wieder abziehen
        for(Map.Entry<Integer, Double> requirements : input.getRequirements().entrySet()){
            solution.stockHoldingPeriodEnd -= requirements.getValue();
        }
        // => ist immer 0! intressant ist wohl eher der Lagerbestand nach jeder einzelnen Periode
        */

        solution.setRuntime(System.nanoTime() - timeStart);

        solution.calcCosts(); //Alle Kosten berechnen.
        return solution;
    }

    /**
     * @param C Kostenkriterium
     * @param V Vergleichsgröße
     *
     * @return
     */
    protected boolean compareCV(double C, double V)
    {
        return C <= V;
    }

    /**
     * Vergleichsgröße berechnen
     *
     * @param tau
     * @param t
     *
     * @return
     */
    protected abstract double calcV(Integer tau, Integer t);

    /**
     * Kostenkriterium berechnen
     *
     * @param tau
     * @param t
     *
     * @return
     */
    protected abstract double calcC(Integer tau, Integer t);

    //Getter und Setter
    public Input getInput()
    {
        return input;
    }

    public HeuristikEnum getHeuristik()
    {
        return heuristik;
    }
}

