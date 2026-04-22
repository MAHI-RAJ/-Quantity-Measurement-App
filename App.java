enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.0328084);

    private final double conversionFactorToFeet;

    LengthUnit(double conversionFactorToFeet) {
        this.conversionFactorToFeet = conversionFactorToFeet;
    }

    public double convertToBaseUnit(double value) {
        validateValue(value);
        return value * conversionFactorToFeet;
    }

    public double convertFromBaseUnit(double baseValue) {
        validateValue(baseValue);
        return baseValue / conversionFactorToFeet;
    }

    private static void validateValue(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number.");
        }
    }
}

class QuantityLength {
    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 0.0001;

    public QuantityLength(double value, LengthUnit unit) {
        validateValue(value);
        validateUnit(unit, "unit");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public QuantityLength convertTo(LengthUnit targetUnit) {
        validateUnit(targetUnit, "targetUnit");
        double baseValue = toBaseUnit();
        return new QuantityLength(targetUnit.convertFromBaseUnit(baseValue), targetUnit);
    }

    public QuantityLength add(QuantityLength other) {
        validateLength(other, "other");
        double sumInBase = this.toBaseUnit() + other.toBaseUnit();
        return new QuantityLength(this.unit.convertFromBaseUnit(sumInBase), this.unit);
    }

    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        validateLength(other, "other");
        validateUnit(targetUnit, "targetUnit");
        double sumInBase = this.toBaseUnit() + other.toBaseUnit();
        return new QuantityLength(targetUnit.convertFromBaseUnit(sumInBase), targetUnit);
    }

    public boolean sameAs(QuantityLength other) {
        validateLength(other, "other");
        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;
        QuantityLength other = (QuantityLength) obj;
        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    @Override
    public int hashCode() {
        long rounded = Math.round(this.toBaseUnit() / EPSILON);
        return Long.hashCode(rounded);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    private static void validateValue(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number.");
        }
    }

    private static void validateUnit(LengthUnit unit, String fieldName) {
        if (unit == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }
    }

    private static void validateLength(QuantityLength length, String fieldName) {
        if (length == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }
    }
}

enum WeightUnit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double conversionFactorToKilogram;

    WeightUnit(double conversionFactorToKilogram) {
        this.conversionFactorToKilogram = conversionFactorToKilogram;
    }

    public double convertToBaseUnit(double value) {
        validateValue(value);
        return value * conversionFactorToKilogram;
    }

    public double convertFromBaseUnit(double baseValue) {
        validateValue(baseValue);
        return baseValue / conversionFactorToKilogram;
    }

    private static void validateValue(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number.");
        }
    }
}

class QuantityWeight {
    private final double value;
    private final WeightUnit unit;
    private static final double EPSILON = 0.0001;

    public QuantityWeight(double value, WeightUnit unit) {
        validateValue(value);
        validateUnit(unit, "unit");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public QuantityWeight convertTo(WeightUnit targetUnit) {
        validateUnit(targetUnit, "targetUnit");
        double baseValue = toBaseUnit();
        return new QuantityWeight(targetUnit.convertFromBaseUnit(baseValue), targetUnit);
    }

    public QuantityWeight add(QuantityWeight other) {
        validateWeight(other, "other");
        double sumInBase = this.toBaseUnit() + other.toBaseUnit();
        return new QuantityWeight(this.unit.convertFromBaseUnit(sumInBase), this.unit);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        validateWeight(other, "other");
        validateUnit(targetUnit, "targetUnit");
        double sumInBase = this.toBaseUnit() + other.toBaseUnit();
        return new QuantityWeight(targetUnit.convertFromBaseUnit(sumInBase), targetUnit);
    }

    public boolean sameAs(QuantityWeight other) {
        validateWeight(other, "other");
        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityWeight)) return false;
        QuantityWeight other = (QuantityWeight) obj;
        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    @Override
    public int hashCode() {
        long rounded = Math.round(this.toBaseUnit() / EPSILON);
        return Long.hashCode(rounded);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }

    private static void validateValue(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number.");
        }
    }

    private static void validateUnit(WeightUnit unit, String fieldName) {
        if (unit == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }
    }

    private static void validateWeight(QuantityWeight weight, String fieldName) {
        if (weight == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null.");
        }
    }
}

public class App {
    public static void main(String[] args) {
        // Length still works
        QuantityLength l1 = new QuantityLength(1, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12, LengthUnit.INCHES);

        System.out.println("Length Equality:");
        System.out.println("1 FEET equals 12 INCHES: " + l1.equals(l2));

        System.out.println("\nLength Conversion:");
        System.out.println("1 FEET in INCHES = " + l1.convertTo(LengthUnit.INCHES));

        System.out.println("\nLength Addition:");
        System.out.println("1 FEET + 12 INCHES = " + l1.add(l2));
        System.out.println("1 FEET + 12 INCHES in YARDS = " + l1.add(l2, LengthUnit.YARDS));

        // Weight
        QuantityWeight w1 = new QuantityWeight(1, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000, WeightUnit.GRAM);
        QuantityWeight w3 = new QuantityWeight(2.20462, WeightUnit.POUND);

        System.out.println("\nWeight Equality:");
        System.out.println("1 KILOGRAM equals 1000 GRAM: " + w1.equals(w2));
        System.out.println("1 KILOGRAM equals 2.20462 POUND: " + w1.equals(w3));

        System.out.println("\nWeight Conversion:");
        System.out.println("1 KILOGRAM in GRAM = " + w1.convertTo(WeightUnit.GRAM));
        System.out.println("1000 GRAM in KILOGRAM = " + w2.convertTo(WeightUnit.KILOGRAM));
        System.out.println("1 KILOGRAM in POUND = " + w1.convertTo(WeightUnit.POUND));

        System.out.println("\nWeight Addition:");
        System.out.println("1 KILOGRAM + 500 GRAM = " +
                w1.add(new QuantityWeight(500, WeightUnit.GRAM)));
        System.out.println("1 KILOGRAM + 500 GRAM in POUND = " +
                w1.add(new QuantityWeight(500, WeightUnit.GRAM), WeightUnit.POUND));
    }
}