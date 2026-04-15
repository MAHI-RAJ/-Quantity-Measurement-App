public class app {
    public static void main(String[] args) {

        // Example comparisons
        QuantityLength q1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, Unit.INCHES);

        QuantityLength q3 = new QuantityLength(1.0, Unit.YARDS);
        QuantityLength q4 = new QuantityLength(3.0, Unit.FEET);

        QuantityLength q5 = new QuantityLength(2.54, Unit.CM);
        QuantityLength q6 = new QuantityLength(1.0, Unit.INCHES);

        System.out.println("1 ft == 12 in : " + q1.equals(q2));
        System.out.println("1 yard == 3 ft : " + q3.equals(q4));
        System.out.println("2.54 cm == 1 in : " + q5.equals(q6));
    }
}

// Enum with conversion to base unit (INCHES)
enum Unit {

    FEET(12.0),          // 1 ft = 12 inches
    INCHES(1.0),         // base unit
    YARDS(36.0),         // 1 yard = 36 inches
    CM(0.393701);        // 1 cm = 0.393701 inches

    private final double toInchesFactor;

    Unit(double toInchesFactor) {
        this.toInchesFactor = toInchesFactor;
    }

    public double toInches(double value) {
        return value * toInchesFactor;
    }
}

// Generic Quantity class (DRY maintained)
class QuantityLength {

    double value;
    Unit unit;
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

    // Convert to base unit (INCHES)
    private double toBase() {
        return unit.toInches(value);
    }

    // Equality check
    public boolean equals(QuantityLength other) {
        double v1 = this.toBase();
        double v2 = other.toBase();

        return Math.abs(v1 - v2) < 0.0001;
    }

    // Equality check after conversion
    public boolean equals(QuantityLength other) {
        double base1 = this.toFeet();
        double base2 = other.toFeet();

        return Math.abs(base1 - base2) < 0.0001;
    }
}