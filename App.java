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

    public double to(LengthUnit targetUnit) {
        validateUnit(targetUnit, "targetUnit");
        double baseValue = this.toBaseUnit();
        return targetUnit.convertFromBaseUnit(baseValue);
    }

    public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
        validateValue(value);
        validateUnit(sourceUnit, "sourceUnit");
        validateUnit(targetUnit, "targetUnit");

        double baseValue = sourceUnit.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(baseValue);
    }

    public QuantityLength add(QuantityLength other) {
        validateLength(other, "other");

        double sumInBase = this.toBaseUnit() + other.toBaseUnit();
        double resultValue = this.unit.convertFromBaseUnit(sumInBase);

        return new QuantityLength(resultValue, this.unit);
    }

    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        validateLength(other, "other");
        validateUnit(targetUnit, "targetUnit");

        double sumInBase = this.toBaseUnit() + other.toBaseUnit();
        double resultValue = targetUnit.convertFromBaseUnit(sumInBase);

        return new QuantityLength(resultValue, targetUnit);
    }

    public static QuantityLength add(QuantityLength first, QuantityLength second) {
        validateLength(first, "first");
        validateLength(second, "second");

        double sumInBase = first.toBaseUnit() + second.toBaseUnit();
        double resultValue = first.unit.convertFromBaseUnit(sumInBase);

        return new QuantityLength(resultValue, first.unit);
    }

    public static QuantityLength add(QuantityLength first, QuantityLength second, LengthUnit targetUnit) {
        validateLength(first, "first");
        validateLength(second, "second");
        validateUnit(targetUnit, "targetUnit");

        double sumInBase = first.toBaseUnit() + second.toBaseUnit();
        double resultValue = targetUnit.convertFromBaseUnit(sumInBase);

        return new QuantityLength(resultValue, targetUnit);
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

public class App {
    public static void main(String[] args) {
        QuantityLength length1 = new QuantityLength(1, LengthUnit.FEET);
        QuantityLength length2 = new QuantityLength(12, LengthUnit.INCHES);
        QuantityLength length3 = new QuantityLength(1, LengthUnit.YARDS);
        QuantityLength length4 = new QuantityLength(100, LengthUnit.CENTIMETERS);

        // UC4 style equality
        System.out.println("1 FEET equals 12 INCHES: " + length1.sameAs(length2));

        // UC5 conversion
        System.out.println("1 YARD in INCHES: " + length3.to(LengthUnit.INCHES));
        System.out.println("100 CENTIMETERS in FEET: " + length4.to(LengthUnit.FEET));

        // UC6 addition in first operand unit
        QuantityLength result1 = length1.add(length2);
        System.out.println("1 FEET + 12 INCHES = " + result1);

        // UC7 addition in target unit
        QuantityLength result2 = length1.add(length2, LengthUnit.YARDS);
        System.out.println("1 FEET + 12 INCHES in YARDS = " + result2);

        // Static add
        QuantityLength result3 = QuantityLength.add(length3, length2, LengthUnit.FEET);
        System.out.println("1 YARD + 12 INCHES in FEET = " + result3);
    }
}