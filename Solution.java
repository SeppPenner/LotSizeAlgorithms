import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.Map;

/**
 * In dieser Klasse ist das Ergebnis einer Heuristik zu genau einem Szenario enthalten.
 * <p>
 * Gestützt auf den Anforderungen der Solution Klasse von Alexander Setzer.
 */
public class Solution
{

    /**
     * Enum, damit man weiß, zu wem die Ergebnisse gehören.
     */
    private HeuristikEnum heuristikEnum;

    /*
     * Die Aufwände pro Periode
	 */
    private HashMap<Integer, Lot> lots = new HashMap<>();

    /*
     * Der Wert der errechneten Kennzahl
     */
    private Double kennzahl; //Todo: Kennzahl berechnen... Wie? Was ist diese Kennzahl?

    /*
     * Die gesamten Rüstkosten
     */
    private Double setupCosts;

    /*
     * Die gesamten Lagerkosten
     *
     */
    private Double holdingCosts;

    /*
     * Die Gesamtkosten
     */
    private Double totalCosts;

    /*
     * Lagerbestand am Ende der Periode
     *
     * @depcrecated
     */
    //public Double stockHoldingPeriodEnd = 0.0; //Todo: Lagerbestand am Ende der Periode berechnen.

    /**
     * Laufzeit zum Überprüfen und ggf. optimieren der Heuristiken
     */
    private Long runtime = null;

    private final Input input;

    /**
     * Konstruktor zum Erstellen einer neuen Lösung mit dazugehörigen Enum Wert.
     *
     * @param heuristikEnum Enum zu dem die Lösung gehört.
     */
    public Solution(HeuristikEnum heuristikEnum, Input input)
    {
        this.heuristikEnum = heuristikEnum;
        this.input = input;
    }

    /**
     * Berechnet die gesamten Lagerkosten für diese Heuristk aus den gebildeten Losen.
     */
    private void calcHoldingCosts()
    {
        holdingCosts = 0.0; //Sofern die Funktion öfters aufgerufen wird, damit immer das gleiche Ergebnis berechnet wird.
        for (Lot lot : lots.values()) {
            holdingCosts += lot.getLotHoldingCost();
        }
    }

    /**
     * Berechnet alle Kosten für die Heuristik.
     */
    public void calcCosts()
    {
        calcHoldingCosts();
        calcSetupCosts();
        calcTotalCosts();

    }

    /**
     * Berechnet die gesamten Rüstkosten für diese Heuristk aus den gebildeten Losen.
     */
    private void calcSetupCosts()
    {
        setupCosts = 0.0; //Sofern die Funktion öfters aufgerufen wird, damit immer das gleiche Ergebnis berechnet wird.
        for (Lot lot : lots.values()) {
            setupCosts += lot.getLotSetupCost();
        }
    }

    /**
     * Berechnet die gesamten Kosten für die Heuristk aus den gesamten Rüst- und Lagerkosten.
     */
    private void calcTotalCosts()
    {
        totalCosts = 0.0; //Sofern die Funktion öfters aufgerufen wird, damit immer das gleiche Ergebnis berechnet wird.
        totalCosts = setupCosts + holdingCosts;
    }


    //Getter und Setter

    /**
     * @return Laufzeit in Nanosekunden
     */
    public Long getRuntime()
    {
        return runtime;
    }

    public void setRuntime(Long runtime)
    {
        if (null == this.runtime && null != runtime)
            this.runtime = runtime;
    }

    public HeuristikEnum getHeuristikEnum()
    {
        return heuristikEnum;
    }

    public void setHeuristikEnum(HeuristikEnum heuristikEnum)
    {
        this.heuristikEnum = heuristikEnum;
    }

    public HashMap<Integer, Lot> getLots()
    {
        return lots;
    }

    public void setLots(HashMap<Integer, Lot> lots)
    {
        this.lots = lots;
    }

    public Double getKennzahl()
    {
        return kennzahl;
    }

    public void setKennzahl(Double kennzahl)
    {
        this.kennzahl = kennzahl;
    }

    public Double getSetupCosts()
    {
        return setupCosts;
    }

    public Double getHoldingCosts()
    {
        return holdingCosts;
    }

    public Double getTotalCosts()
    {
        return totalCosts;
    }


    public Double getStockHoldingPeriodEnd()
    {
        return getStockHoldingPeriodEnd(input.getRequirements().size());
    }

    public Double getStockHoldingPeriodEnd(Integer period)
    {
        double stock = 0.0;
        if(input.getRequirements().containsKey(period)){
            for(Integer i = 1; i <= period; i++){
                stock -= input.getRequirements().get(i);
                if(lots.containsKey(i)){
                    stock += lots.get(i).getLotSize();
                }
            }
        }else{
            return null;
        }
        return stock;
    }
}
