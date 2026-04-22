
enum LengthUnit {
    FEET(1.0),                  // base unit
    INCHES(1.0 / 12.0),         // 1 inch = 1/12 feet
    YARDS(3.0),                 // 1 yard = 3 feet
    CENTIMETERS(0.0328084);     // 1 cm = 0.0328084 feet

    private final double conversionFactorToFeet;

    LengthUnit(double conversionFactorToFeet) {
        this.conversionFactorToFeet = conversionFactorToFeet;
    }

    public double getConversionFactor() {
        return conversionFactorToFeet;
    }
}

class QuantityLength {
    private final double value;
    private final LengthUnit unit;

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

    // Convert current object to feet
    private double toFeet() {
        return this.value * this.unit.getConversionFactor();
    }

    // Generic convert method from UC5
    public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
        validateValue(value);
        validateUnit(sourceUnit, "sourceUnit");
        validateUnit(targetUnit, "targetUnit");

        double valueInFeet = value * sourceUnit.getConversionFactor();
        return valueInFeet / targetUnit.getConversionFactor();
    }

    // Instance method: add another length, result in unit of first operand
    public QuantityLength add(QuantityLength other) {
        validateLength(other, "other");

        double sumInFeet = this.toFeet() + other.toFeet();
        double resultValue = sumInFeet / this.unit.getConversionFactor();

        return new QuantityLength(resultValue, this.unit);
    }

    // Static method: add two lengths, result in unit of first operand
    public static QuantityLength add(QuantityLength length1, QuantityLength length2) {
        validateLength(length1, "length1");
        validateLength(length2, "length2");

        double sumInFeet = length1.toFeet() + length2.toFeet();
        double resultValue = sumInFeet / length1.unit.getConversionFactor();

        return new QuantityLength(resultValue, length1.unit);
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

    @Override
    public String toString() {
        return value + " " + unit;
    }
}

public class app{
    public static void main(String[] args) {
        QuantityLength length1 = new QuantityLength(1, LengthUnit.FEET);
        QuantityLength length2 = new QuantityLength(12, LengthUnit.INCHES);

        QuantityLength result1 = length1.add(length2);
        System.out.println("1 FEET + 12 INCHES = " + result1.getValue() + " " + result1.getUnit());

        QuantityLength length3 = new QuantityLength(1, LengthUnit.YARDS);
        QuantityLength length4 = new QuantityLength(2, LengthUnit.FEET);

        QuantityLength result2 = QuantityLength.add(length3, length4);
        System.out.println("1 YARDS + 2 FEET = " + result2.getValue() + " " + result2.getUnit());

        QuantityLength length5 = new QuantityLength(100, LengthUnit.CENTIMETERS);
        QuantityLength length6 = new QuantityLength(1, LengthUnit.FEET);

        QuantityLength result3 = length5.add(length6);
        System.out.println("100 CENTIMETERS + 1 FEET = " + result3.getValue() + " " + result3.getUnit());
    }
}