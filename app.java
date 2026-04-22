import java.math.BigDecimal;
import java.math.RoundingMode;

enum LengthUnit {
    FEET(1.0),                    // base unit
    INCHES(1.0 / 12.0),           // 1 inch = 1/12 feet
    YARDS(3.0),                   // 1 yard = 3 feet
    CENTIMETERS(0.03280839895);   // 1 cm = 0.03280839895 feet

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

    public double to(LengthUnit targetUnit) {
        return convert(this.value, this.unit, targetUnit);
    }

    public double to(LengthUnit targetUnit, int decimalPlaces) {
        return convert(this.value, this.unit, targetUnit, decimalPlaces);
    }

    public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
        validateValue(value);
        validateUnit(sourceUnit, "sourceUnit");
        validateUnit(targetUnit, "targetUnit");

        // Step 1: convert source value to base unit (feet)
        double valueInFeet = value * sourceUnit.getConversionFactor();

        // Step 2: convert from base unit to target unit
        return valueInFeet / targetUnit.getConversionFactor();
    }

    public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit, int decimalPlaces) {
        double convertedValue = convert(value, sourceUnit, targetUnit);
        return round(convertedValue, decimalPlaces);
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

    private static double round(double value, int decimalPlaces) {
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException("Decimal places cannot be negative.");
        }

        return BigDecimal.valueOf(value)
                .setScale(decimalPlaces, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

public class app{
    public static void main(String[] args) {
        // Static conversion examples
        System.out.println("UC5: Unit-to-Unit Conversion");
        System.out.println("--------------------------------");

        double feetToInches = QuantityLength.convert(2, LengthUnit.FEET, LengthUnit.INCHES);
        System.out.println("2 FEET = " + feetToInches + " INCHES");

        double yardsToInches = QuantityLength.convert(1, LengthUnit.YARDS, LengthUnit.INCHES);
        System.out.println("1 YARD = " + yardsToInches + " INCHES");

        double cmToFeet = QuantityLength.convert(100, LengthUnit.CENTIMETERS, LengthUnit.FEET, 4);
        System.out.println("100 CENTIMETERS = " + cmToFeet + " FEET");

        double inchesToFeet = QuantityLength.convert(24, LengthUnit.INCHES, LengthUnit.FEET, 2);
        System.out.println("24 INCHES = " + inchesToFeet + " FEET");

        // Instance-based conversion example
        QuantityLength length = new QuantityLength(3, LengthUnit.FEET);
        double converted = length.to(LengthUnit.INCHES);
        System.out.println("3 FEET = " + converted + " INCHES");

        // Rounded instance conversion
        double roundedConverted = length.to(LengthUnit.CENTIMETERS, 2);
        System.out.println("3 FEET = " + roundedConverted + " CENTIMETERS");
    }
}