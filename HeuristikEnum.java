package lotsize;

import lotsize.impl.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Enum für die Heuristiken
 *
 * Gestützt auf den Anforderungen der HeuristikEnum Klasse von Alexander Setzer.
 */
public enum HeuristikEnum {
    DYNAMISCHE_PLANUNGSRECHNUNG("Dynamische Planungsrechnung", DynamischePlanungsrechnungVerfahren.class),
    GROFF("Groff", GroffVerfahren.class),
    SILVER_MEAL("Silver-Meal", SilverMealVerfahren.class),
    STUECKKOSTEN("Stückkosten", StueckkostenVerfahren.class),
    STUECKPERIODENAUSGLEICH("Stückperiodenausgleich", StueckperiodenausgleichsVerfahren.class),
    WAGNER_WITHIN("Wagner-Within", WagnerWithinVerfahren.class);

    private String name;
    private Class<? extends AbstractVerfahren> clazz; //Klasse des jeweiligen Verfahrens

    HeuristikEnum(String name, Class<? extends AbstractVerfahren> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    /**
     * Gibt den Namen der Heuristik zurück
     *
     * @return Name der Heuristik
     */
    public String getName() {
        return name;
    }

    /**
     * Erzeugt eine Instance zu dem gewünschten Algorithmus
     *
     * @param input
     * @return Instance des Verfahrens
     */
    public AbstractVerfahren getVerfahren(Input input) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<? extends AbstractVerfahren> ctor = clazz.getConstructor(HeuristikEnum.class ,Input.class);
        AbstractVerfahren x = ctor.newInstance(this, input);
        return x;
    }
}