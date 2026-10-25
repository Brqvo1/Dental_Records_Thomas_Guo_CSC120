import java.util.Scanner;
public class DentalRecordsThomasGuo {

    /** Largest number of people allowed in one family. */
    private static final int MAXIMUM_FAMILY_MEMBERS = 6;

    /** Number of tooth layers: upper and lower. */
    private static final int NUMBER_OF_TOOTH_LAYERS = 2;

    /** Largest number of teeth allowed in one layer. */
    private static final int MAXIMUM_TEETH_PER_LAYER = 8;

    /** Keyboard used for all input. */
    private static final Scanner KEYBOARD = new Scanner(System.in);

    /** Number of people in the current family. */
    private static int familyMemberCount;

    /** Name of each family member. */
    private static String[] familyMemberNames =
            new String[MAXIMUM_FAMILY_MEMBERS];

    /** Tooth types, indexed by person, layer, and tooth. */
    private static char[][][] familyMemberTeeth =
            new char[MAXIMUM_FAMILY_MEMBERS]
                    [NUMBER_OF_TOOTH_LAYERS]
                    [MAXIMUM_TEETH_PER_LAYER];

    /** Number of recorded teeth in each person's upper or lower layer. */
    private static int[][] familyMemberToothCounts =
            new int[MAXIMUM_FAMILY_MEMBERS][NUMBER_OF_TOOTH_LAYERS];

    /**
    * main method.*/
    public static void main(String[] arguments) {
        String menuSelection;
        boolean menuSelectionIsValid;

        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

        recordFamilyInformation();

        do {
            System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it : ");
            menuSelection = KEYBOARD.nextLine().trim().toUpperCase();
            menuSelectionIsValid = menuSelection.equals("P")
                    || menuSelection.equals("E")
                    || menuSelection.equals("R")
                    || menuSelection.equals("X");

            if (!menuSelectionIsValid) {
                System.out.println("Invalid menu option, try again");
            } else if (menuSelection.equals("P")) {
                printFamilyRecords();
            } else if (menuSelection.equals("E")) {
                extractTooth();
            } else if (menuSelection.equals("R")) {
                reportRootCanalIndices();
            }

        } while (!menuSelection.equals("X"));

        System.out.println("Exiting the Floridian Tooth Records :-)");
    }

    /** Record Family Information Method.
    Declare variables */
    private static void recordFamilyInformation() {
        int familyMemberIndex;
        int earlierFamilyMemberIndex;
        int toothLayerIndex;
        int toothIndex;
        String enteredName;
        String enteredTeeth;
        String namePrompt;
        String teethPrompt;
        boolean nameIsValid;
        boolean teethAreValid;
        char toothType;

        System.out.print("Please enter number of people in the family : ");
        familyMemberCount = Integer.parseInt(KEYBOARD.nextLine().trim());

        while (familyMemberCount < 1
                || familyMemberCount > MAXIMUM_FAMILY_MEMBERS) {
            System.out.print("Invalid number of people, try again : ");
            familyMemberCount = Integer.parseInt(KEYBOARD.nextLine().trim());
        }

        for (familyMemberIndex = 0; familyMemberIndex < familyMemberCount;
             familyMemberIndex++) {
            namePrompt = "Please enter the name for family member "
                    + (familyMemberIndex + 1) + " : ";

            do {
                System.out.print(namePrompt);
                enteredName = KEYBOARD.nextLine().trim();
                nameIsValid = !enteredName.isEmpty();

                for (earlierFamilyMemberIndex = 0;
                     earlierFamilyMemberIndex < familyMemberIndex;
                     earlierFamilyMemberIndex++) {
                    if (enteredName.equalsIgnoreCase(
                            familyMemberNames[earlierFamilyMemberIndex])) {
                        nameIsValid = false;
                    }
                }

                if (!nameIsValid) {
                    namePrompt = "Invalid or duplicate name, try again : ";
                }
            } while (!nameIsValid);

            familyMemberNames[familyMemberIndex] = enteredName;

            for (toothLayerIndex = 0;
                 toothLayerIndex < NUMBER_OF_TOOTH_LAYERS;
                 toothLayerIndex++) {
                if (toothLayerIndex == 0) {
                    teethPrompt = "Please enter the uppers for "
                            + enteredName + " : ";
                } else {
                    teethPrompt = "Please enter the lowers for "
                            + enteredName + " : ";
                }

                do {
                    System.out.print(teethPrompt);
                    enteredTeeth = KEYBOARD.nextLine().trim().toUpperCase();
                    teethAreValid = enteredTeeth.length() >= 1
                            && enteredTeeth.length() <= MAXIMUM_TEETH_PER_LAYER;

                    if (enteredTeeth.length() > MAXIMUM_TEETH_PER_LAYER) {
                        teethPrompt = "Too many teeth, try again : ";
                    } else {
                        for (toothIndex = 0;
                             toothIndex < enteredTeeth.length();
                             toothIndex++) {
                            toothType = enteredTeeth.charAt(toothIndex);
                            if (toothType != 'I' && toothType != 'B'
                                    && toothType != 'M') {
                                teethAreValid = false;
                            }
                        }
                        if (!teethAreValid) {
                            teethPrompt = "Invalid teeth types, try again : ";
                        }
                    }
                } while (!teethAreValid);

                familyMemberToothCounts[familyMemberIndex]
                        [toothLayerIndex] = enteredTeeth.length();

                for (toothIndex = 0; toothIndex < enteredTeeth.length();
                     toothIndex++) {
                    familyMemberTeeth[familyMemberIndex]
                            [toothLayerIndex][toothIndex] =
                            enteredTeeth.charAt(toothIndex);
                }
            }
        }
    }

    /** Print Family Records Method */
    private static void printFamilyRecords() {
        int familyMemberIndex;
        int toothLayerIndex;
        int toothIndex;
        String toothLayerName;

        System.out.println();
        for (familyMemberIndex = 0; familyMemberIndex < familyMemberCount;
             familyMemberIndex++) {
            System.out.println(familyMemberNames[familyMemberIndex]);

            for (toothLayerIndex = 0;
                 toothLayerIndex < NUMBER_OF_TOOTH_LAYERS;
                 toothLayerIndex++) {
                if (toothLayerIndex == 0) {
                    toothLayerName = "Uppers";
                } else {
                    toothLayerName = "Lowers";
                }
                System.out.print("  " + toothLayerName + ": ");

                for (toothIndex = 0;
                     toothIndex < familyMemberToothCounts
                             [familyMemberIndex][toothLayerIndex];
                     toothIndex++) {
                    System.out.print((toothIndex + 1) + ":"
                            + familyMemberTeeth[familyMemberIndex]
                            [toothLayerIndex][toothIndex] + "   ");
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    /**Extract Tooth Method */
    private static void extractTooth() {
        int chosenFamilyMemberIndex;
        int familyMemberIndex;
        int chosenToothLayerIndex;
        int chosenToothNumber;
        int toothIndex;
        String enteredName;
        String enteredLayer;
        String familyMemberPrompt;
        String toothLayerPrompt;
        String toothNumberPrompt;
        boolean toothIsAvailable;
        boolean toothNumberIsValid;

        chosenFamilyMemberIndex = -1;
        familyMemberPrompt = "Which family member : ";

        do {
            System.out.print(familyMemberPrompt);
            enteredName = KEYBOARD.nextLine().trim();

            for (familyMemberIndex = 0;
                 familyMemberIndex < familyMemberCount;
                 familyMemberIndex++) {
                if (enteredName.equalsIgnoreCase(
                        familyMemberNames[familyMemberIndex])) {
                    chosenFamilyMemberIndex = familyMemberIndex;
                }
            }
            if (chosenFamilyMemberIndex == -1) {
                familyMemberPrompt = "Invalid family member, try again : ";
            }
        } while (chosenFamilyMemberIndex == -1);

        chosenToothLayerIndex = -1;
        toothLayerPrompt = "Which tooth layer (U)pper or (L)ower : ";

        do {
            System.out.print(toothLayerPrompt);
            enteredLayer = KEYBOARD.nextLine().trim().toUpperCase();

            if (enteredLayer.equals("U")) {
                chosenToothLayerIndex = 0;
            } else if (enteredLayer.equals("L")) {
                chosenToothLayerIndex = 1;
            } else {
                toothLayerPrompt = "Invalid layer, try again : ";
            }
        } while (chosenToothLayerIndex == -1);

        toothIsAvailable = false;
        for (toothIndex = 0;
             toothIndex < familyMemberToothCounts
                     [chosenFamilyMemberIndex][chosenToothLayerIndex];
             toothIndex++) {
            if (familyMemberTeeth[chosenFamilyMemberIndex]
                    [chosenToothLayerIndex][toothIndex] != 'M') {
                toothIsAvailable = true;
            }
        }

        if (!toothIsAvailable) {
            System.out.println("No teeth available in this layer.");
            return;
        }

        toothNumberPrompt = "Which tooth number : ";
        do {
            System.out.print(toothNumberPrompt);
            chosenToothNumber = Integer.parseInt(KEYBOARD.nextLine().trim());
            toothNumberIsValid = chosenToothNumber >= 1
                    && chosenToothNumber <= familyMemberToothCounts
                    [chosenFamilyMemberIndex][chosenToothLayerIndex];

            if (!toothNumberIsValid) {
                toothNumberPrompt = "Invalid tooth number, try again : ";
            } else if (familyMemberTeeth[chosenFamilyMemberIndex]
                    [chosenToothLayerIndex][chosenToothNumber - 1] == 'M') {
                toothNumberIsValid = false;
                toothNumberPrompt = "Missing tooth, try again : ";
            }
        } while (!toothNumberIsValid);

        familyMemberTeeth[chosenFamilyMemberIndex]
                [chosenToothLayerIndex][chosenToothNumber - 1] = 'M';
    }

    /**Output Root Canal Indices Method
     * * Counts the family's tooth types and calculates
     *  * the root canal indices using a quadratic equation.*/
    private static void reportRootCanalIndices() {
        int familyMemberIndex;
        int toothLayerIndex;
        int toothIndex;
        int incisorCount;
        int bicuspidCount;
        int missingToothCount;
        char toothType;
        double discriminant;
        double firstRoot;
        double secondRoot;

        incisorCount = 0;
        bicuspidCount = 0;
        missingToothCount = 0;

        for (familyMemberIndex = 0; familyMemberIndex < familyMemberCount;
             familyMemberIndex++) {
            for (toothLayerIndex = 0;
                 toothLayerIndex < NUMBER_OF_TOOTH_LAYERS;
                 toothLayerIndex++) {
                for (toothIndex = 0;
                     toothIndex < familyMemberToothCounts
                             [familyMemberIndex][toothLayerIndex];
                     toothIndex++) {
                    toothType = familyMemberTeeth[familyMemberIndex]
                            [toothLayerIndex][toothIndex];

                    if (toothType == 'I') {
                        incisorCount++;
                    } else if (toothType == 'B') {
                        bicuspidCount++;
                    } else if (toothType == 'M') {
                        missingToothCount++;
                    }
                }
            }
        }

        if (incisorCount > 0) {
            discriminant = bicuspidCount * bicuspidCount
                    + 4.0 * incisorCount * missingToothCount;
            firstRoot = (-bicuspidCount + Math.sqrt(discriminant))
                    / (2.0 * incisorCount);
            secondRoot = (-bicuspidCount - Math.sqrt(discriminant))
                    / (2.0 * incisorCount);

            System.out.printf("One root canal at %.2f%n", firstRoot);
            System.out.printf("Another root canal at %.2f%n", secondRoot);
        } else if (bicuspidCount > 0) {
            firstRoot = (double) missingToothCount / bicuspidCount;
            System.out.printf("One root canal at %.2f%n", firstRoot);
        } else if (missingToothCount == 0) {
            System.out.println("Every number satisfies the equation.");
        } else {
            System.out.println("The equation has no root.");
        }
    }
}
