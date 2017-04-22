package lotsize;

/**
 * Beinhaltet die zu bildenden Lose pro Heuristik bzw verbundenen Szenario/Input.
 */
public class Lot {

    public Lot(double lotSize, long productionCycle, double lotSetupCost, double lotHoldingCost) {
        this.lotSize = lotSize;
        this.productionCycle = productionCycle;
        this.lotSetupCost = lotSetupCost;
        this.lotHoldingCost = lotHoldingCost;
    }

    /**
     * Losgröße
     */
    private final double lotSize;

    /**
     * Produktionszyklus
     */
    private final long productionCycle;

    /**
     * Rüstkosten fürs Los
     */
    private final double lotSetupCost;

    /**
     * Lagerkosten fürs Los
     */
    private final double lotHoldingCost;

    //Getter und Setter
    public double getLotSize() {
        return lotSize;
    }

    public long getProductionCycle() {
        return productionCycle;
    }

    public double getLotSetupCost() {
        return lotSetupCost;
    }

    public double getLotHoldingCost() {
        return lotHoldingCost;
    }
}
