public class app {

    // Static method for Feet comparison
    public static boolean compareFeet(double value1, double value2) {
        Feet feet = new Feet(value1, value2);
        return feet.areEqual();
    }

    // Static method for Inches comparison
    public static boolean compareInches(double value1, double value2) {
        Inches inches = new Inches(value1, value2);
        return inches.areEqual();
    }

    public static void main(String[] args) {

        // Hard-coded values (as per UC2)
        double feetValue1 = 5.0;
        double feetValue2 = 5.0;

        double inchValue1 = 12.0;
        double inchValue2 = 10.0;

        // Feet comparison
        boolean feetResult = compareFeet(feetValue1, feetValue2);

        // Inches comparison
        boolean inchResult = compareInches(inchValue1, inchValue2);

        // Output
        System.out.println("Feet तुलना:");
        if (feetResult) {
            System.out.println("Feet values are equal.");
        } else {
            System.out.println("Feet values are NOT equal.");
        }

        System.out.println("\nInches तुलना:");
        if (inchResult) {
            System.out.println("Inch values are equal.");
        } else {
            System.out.println("Inch values are NOT equal.");
        }
    }
}

// Feet class
class Feet {
    private double value1;
    private double value2;

    public Feet(double value1, double value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public boolean areEqual() {
        return value1 == value2;
    }
}

// Inches class
class Inches {
    private double value1;
    private double value2;

    public Inches(double value1, double value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public boolean areEqual() {
        return value1 == value2;
    }
}