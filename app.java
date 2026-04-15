public class app {

    // Method to check equality of two feet measurements
    public boolean areEqual(double value1, double value2) {
        return value1 == value2;
    }

    public static void main(String[] args) {

        app quantityMeasurementApp = new app();

        // Sample inputs (you can change these)
        double feetValue1 = 10.0;
        double feetValue2 = 10.0;

        // Validate inputs (basic numeric check is inherent since we use double)
        boolean result = quantityMeasurementApp.areEqual(feetValue1, feetValue2);

        // Output result
        System.out.println("Value 1 (feet): " + feetValue1);
        System.out.println("Value 2 (feet): " + feetValue2);

        if (result) {
            System.out.println("Result: Both values are equal.");
        } else {
            System.out.println("Result: Values are NOT equal.");
        }
    }
}