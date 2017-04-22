package lotsize;

import java.util.HashMap;

/**
 * Für die Durchführung der Berechnung benötigte Bedarfe, sowie Rüstkosten und Lagerkosten.
 * <p>
 * Gestützt auf den Anforderungen der Input Klasse von Alexander Setzer.
 * TODO: Kennzahlenname fehlt noch. Was auch immer das ist?!
 */
public class Input {
	/**
	 * Bedarfe
	 */
	private final HashMap<Integer, Double> requirements;

	/**
	 * Start des Rüstkostensatzes (Intervall)
	 */
	private final double setupCostsStart;

	/**
	 * Ende des Rüstkostensatzes (Intervall)
	 */
	private final double setupCostsEnd;

	/**
	 * Abstand des Rüstkostensatzes (Intervall)
	 */
	private final double setupCostsDistance;

	/**
	 * konkreter Rüstkostensatz
	 */
	private double setupCostsConcrete;

	/**
	 * Start des Lagerkostensatzes (Intervall)
	 */
	private final double holdingCostsStart;

	/**
	 * Ende des Lagerkostensatzes (Intervall)
	 */
	private final double holdingCostsEnd;

	/**
	 * Abstand des Lagerkostensatzes (Intervall)
	 */
	private final double holdingCostsDistance;

	/**
	 * konkreter Lagerkostensatz
	 */
	private double holdingCostsConcrete;

	/**
	 * Fixed Range
	 * Werden bisher nicht benötigt...
	 */
	private final int fixedRange = 0;

	/**
	 * Fixed Lot
	 * Werden bisher nicht benötigt...
	 */
	private final int fixedLot = 0;

	/**
	 * Konstruktor zum Erstellen eines neuen Input Objekts.
	 *
	 * @param requirements         Bedarfe als Hashmap {@see #convertBedarfeFromStringToHashMap(String)}
	 * @param setupCostsStart      Rüstkostensatz - Start
	 * @param setupCostsEnd        Rüstkostensatz - Ende
	 * @param setupCostsDistance   Rüstenkostensatz - Abstand (Muss > 0.0 sein)
	 * @param holdingCostsStart    Lagerkostensatz - Start
	 * @param holdingCostsEnd      Lagerkostensatz - Ende
	 * @param holdingCostsDistance Lagerkostensatz - Abstand (Muss > 0.0 sein)
	 */
	public Input(HashMap<Integer, Double> requirements, double setupCostsStart, double setupCostsEnd,
			double setupCostsDistance, double holdingCostsStart, double holdingCostsEnd, double holdingCostsDistance) {
		this.requirements = requirements;
		this.setupCostsStart = setupCostsStart;
		this.setupCostsEnd = setupCostsEnd;
		this.setupCostsDistance = setupCostsDistance;
		this.holdingCostsStart = holdingCostsStart;
		this.holdingCostsEnd = holdingCostsEnd;
		this.holdingCostsDistance = holdingCostsDistance;
	}

	/**
	 * Konstruktor zum Erstellen eines neuen Input Objekts mit konkreten Lager- bzw. Rüstkostensätzen. (Copy Constructor)
	 *
	 * @param input                Übergebene input Objekt, ohne konkrete Rüst- und Lagerkosten.
	 * @param setupCostsConcrete   die konkreten Rüstkosten für den Input
	 * @param holdingCostsConcrete die konrekten Lagerkosten für den Input
	 */
	public Input(Input input, Double setupCostsConcrete, Double holdingCostsConcrete) {
		this.requirements = input.getRequirements();
		this.setupCostsStart = input.getSetupCostsStart();
		this.setupCostsEnd = input.getSetupCostsEnd();
		this.setupCostsDistance = input.getSetupCostsDistance();
		this.holdingCostsStart = input.getHoldingCostsStart();
		this.holdingCostsEnd = input.getHoldingCostsEnd();
		this.holdingCostsDistance = input.getHoldingCostsDistance();
		this.setupCostsConcrete = setupCostsConcrete;
		this.holdingCostsConcrete = holdingCostsConcrete;
	}

	// Getter und Setter
	public HashMap<Integer, Double> getRequirements() {
		return requirements;
	}

	public double getSetupCostsStart() {
		return setupCostsStart;
	}

	public double getSetupCostsEnd() {
		return setupCostsEnd;
	}

	public double getSetupCostsDistance() {
		return setupCostsDistance;
	}

	public double getSetupCostsConcrete() {
		return setupCostsConcrete;
	}

	public double getHoldingCostsStart() {
		return holdingCostsStart;
	}

	public double getHoldingCostsEnd() {
		return holdingCostsEnd;
	}

	public double getHoldingCostsDistance() {
		return holdingCostsDistance;
	}

	public double getHoldingCostsConcrete() {
		return holdingCostsConcrete;
	}

	/**
	 * Generate HashMap from String "123;34;234;4234;67" => [1 => 123, 2 => 34, 3 => 234, ...]
	 * to use it for an Input.
	 *
	 * @param requirements String mit Bedarfen, getrennt durch Semikolon
	 * @return
	 */
	public static HashMap<Integer, Double> convertBedarfeFromStringToHashMap(String requirements) {
		String[] ds = requirements.split(";");
		HashMap<Integer, Double> returnValue = new HashMap<>();
		int i = 1;
		for (String s : ds) {
			returnValue.put(i++, Double.parseDouble(s));
		}
		return returnValue;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}
		if (!(other instanceof Input)) {
			return false;
		}
		Input otherInput = (Input) other;
		if (this.requirements != otherInput.requirements) {
			return false;
		}
		if (this.setupCostsStart != otherInput.setupCostsStart) {
			return false;
		}
		if (this.setupCostsEnd != otherInput.setupCostsEnd) {
			return false;
		}
		if (this.setupCostsDistance != otherInput.setupCostsDistance) {
			return false;
		}

		if (this.holdingCostsDistance != otherInput.holdingCostsDistance) {
			return false;
		}
		if (this.holdingCostsEnd != otherInput.holdingCostsEnd) {
			return false;
		}
		if (this.holdingCostsStart != otherInput.holdingCostsStart) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 31 * result + hashInt(Double.doubleToLongBits(setupCostsStart));
		result = 31 * result + hashInt(Double.doubleToLongBits(setupCostsEnd));
		result = 31 * result + hashInt(Double.doubleToLongBits(setupCostsDistance));
		result = 31 * result + hashInt(Double.doubleToLongBits(holdingCostsEnd));
		result = 31 * result + hashInt(Double.doubleToLongBits(holdingCostsStart));
		result = 31 * result + hashInt(Double.doubleToLongBits(holdingCostsDistance));
		result = 31 + result + requirements.hashCode();

		return result;
	}

	private int hashInt(long f){
		return (int) (f^(f>>>32));
	}
}
