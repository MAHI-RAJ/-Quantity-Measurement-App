public class app {

    public static void main(String[] args) {

        // Sample inputs (can be changed)
        QuantityLength q1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, Unit.INCHES);

        boolean result = q1.equals(q2);

        System.out.println("Comparing: ");
        System.out.println("Value 1: " + q1.value + " " + q1.unit);
        System.out.println("Value 2: " + q2.value + " " + q2.unit);

        if (result) {
            System.out.println("Result: Both quantities are equal.");
        } else {
            System.out.println("Result: Quantities are NOT equal.");
        }
    }
}

// Enum for unit types
enum Unit {
    FEET,
    INCHES
}

// Generic Quantity Length class (DRY applied)
class QuantityLength {

    double value;
    Unit unit;

    // Conversion constants
    private static final double INCH_TO_FEET = 1.0 / 12.0;

    public QuantityLength(double value, Unit unit) {

        // Validate numeric
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("Value must be numeric");
        }

        // Validate unit
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    // Convert everything to base unit (FEET)
    private double toFeet() {
        switch (unit) {
            case FEET:
                return value;
            case INCHES:
                return value * INCH_TO_FEET;
            default:
                throw new IllegalArgumentException("Unsupported unit");
        }
    }

    // Equality check after conversion
    public boolean equals(QuantityLength other) {
        double base1 = this.toFeet();
        double base2 = other.toFeet();

        return Math.abs(base1 - base2) < 0.0001;
    }
}