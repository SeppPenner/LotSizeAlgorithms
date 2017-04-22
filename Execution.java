package lotsize;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import lotsize.IOutput;
import javafx.concurrent.Task;

/**
 * Klasse zum Berechnen der Verfahren.
 */
public class Execution
{

    /**
     * Berechnet anhand des übergebenen Inputs und der Liste der verwendeten Heuristiken (Enum), die entsprechenden
     * Werte.
     *
     * @param input     Input Objekt {@see lotsize.Input}
     * @param iOutput   {@see lotsize.IOutput}
     * @param verfahren Liste von HeuristikEnums {@see lotsize.HeuristikEnum}
     */
    public static void calcAlgos(Input input, IOutput iOutput, Task<Boolean> executionTask, HeuristikEnum... verfahren) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException
    {
    	BigDecimal setupCosts;
     	BigDecimal setupCostsEnd = BigDecimal.valueOf(input.getSetupCostsEnd());
    	BigDecimal setupCostsDistance = BigDecimal.valueOf(input.getSetupCostsDistance());

    	BigDecimal holdingCosts;
    	BigDecimal holdingCostsEnd = BigDecimal.valueOf(input.getHoldingCostsEnd());
    	BigDecimal holdingCostsDistance = BigDecimal.valueOf(input.getHoldingCostsDistance());

    	for (setupCosts = BigDecimal.valueOf(input.getSetupCostsStart()); setupCosts.compareTo(setupCostsEnd) <= 0; setupCosts = setupCosts.add(setupCostsDistance))
    	{
    		for (holdingCosts = BigDecimal.valueOf(input.getHoldingCostsStart()); holdingCosts.compareTo(holdingCostsEnd) <= 0; holdingCosts = holdingCosts.add(holdingCostsDistance))
    		{
    			if (executionTask != null && executionTask.isDone())
    				return;

    			Input inputNew = new Input(input, setupCosts.doubleValue(), holdingCosts.doubleValue()); //Erstelle ein neues Input Objekt, was auch die konkreten Lager-/Rüstkosten beinhaltet.
    			Output output = new Output(inputNew);

                for (HeuristikEnum heuristik : verfahren) {
                    AbstractVerfahren x = heuristik.getVerfahren(inputNew);

                    output.addSolution(heuristik, x.doTheMagic());
                }

                iOutput.saveOutputAsync(output);
    		}
    	}
/*
        for (double setupCosts = round(input.getSetupCostsStart(), 2); round(setupCosts,2) <= round(input.getSetupCostsEnd(), 2); setupCosts += round(input.getSetupCostsDistance(), 2))
        {
            for (double holdingCosts = round(input.getHoldingCostsStart(),2); round(holdingCosts,2) <= round(input.getHoldingCostsEnd(),2); holdingCosts += round(input.getHoldingCostsDistance(),2))
            {
            	if (executionTask != null && executionTask.isDone())
            		return;

                Input inputNew = new Input(input, setupCosts, holdingCosts); //Erstelle ein neues Input Objekt, was auch die konkreten Lager-/Rüstkosten beinhaltet.
                Output output = new Output(inputNew);

                for (HeuristikEnum heuristik : verfahren) {
                    AbstractVerfahren x = heuristik.getVerfahren(inputNew);

                    output.addSolution(heuristik, x.doTheMagic());
                }

                iOutput.saveOutputAsync(output);
            }
        }
        // iOutput.finishWork();
*/
    }

    public static double round(double value, int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
