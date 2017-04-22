package lotsize;

import java.util.HashMap;
import java.util.Map;

/**
 * In dieser Klasse sind alle Daten enthalten, die zum Speichern eines berechneten Szenarios benötigt werden. <br>
 * Es sind enthalten: <br>
 * -Das Szenario (der Input) <br>
 * -Die Lösungen aller Heuristiken zum entsprechenden Inputs<br>
 * <p>
 * Gestützt auf den Anforderungen der Solution Klasse von Alexander Setzer.
 */
public class Output {

    /*
     * Das Szenario des Outputs
	 */
    private Input input;

    /*
     * Die Ergebnisse der einzelnen Heuristiken
     */
    private Map<HeuristikEnum, Solution> heuristicsSolutions = new HashMap<>();

    public Output(Input input) {
        this.input = input;
    }

    public void addSolution(HeuristikEnum heuristik, Solution solution) {
        heuristicsSolutions.put(heuristik, solution);
    }

    //Getter und Setter
    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Map<HeuristikEnum, Solution> getHeuristicsSolutions() {
        return heuristicsSolutions;
    }

    public void setHeuristicsSolutions(Map<HeuristikEnum, Solution> heuristicsSolutions) {
        this.heuristicsSolutions = heuristicsSolutions;
    }

//    /**
//     * @param algoName Name des verwendetet Algorithmus
//     * @param input    Kopie des Input zu diesem Output
//     */
//    public Output(String algoName, Input input) {
//        this.algoName = algoName;
//        this.input = input;
//    }
//
//    /**
//     * Name des verwendeten Algorithmus
//     */
//    public final String algoName;
//
//
//    /**
//     * Eingangs Parameter
//     */
//    public final Input input;
//    /**
//     * Lots
//     */
//    public HashMap<Integer, Lot> lots = new HashMap<>();
//
//    /**
//     * Gesamtkosten
//     */
//    public Double sumTotal = null;
//
//    /**
//     * Laufzeit
//     */
//    private Long runtime = null;
//
//    /**
//     * @return Summe aller Loskosten
//     */
//    public Double sumTotal() {
//        if (null == sumTotal) {
//            sumTotal = 0.0;
//            for (Map.Entry<Integer, Lot> entry : lots.entrySet()) {
//                sumTotal += entry.getValue().sumTotal();
//            }
//        }
//        return sumTotal;
//    }
//
//    /**
//     * @return Laufzeit in Nanosekunden
//     */
//    public Long getRuntime() {
//        return runtime;
//    }
//
//    public void setRuntime(Long runtime) {
//        if (null == this.runtime && null != runtime)
//            this.runtime = runtime;
//    }
}
