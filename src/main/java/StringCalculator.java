import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class StringCalculator {
    int add(String numberString) throws NegativeNumberException {

        if (numberString.equals("")) {
            return 0;
        }

        String delimiter = ",";
        String numberPart = numberString;

        if (numberString.startsWith("//")) {
            delimiter = getDelimiter(numberString);
            numberPart = numberString.substring(numberString.indexOf("\n") + 1);
        }

        StringTokenizer numberTokenizer = new StringTokenizer(numberPart, delimiter);

        int sum = 0;
        List<String> negativeNumbersList = new LinkedList<>();
        while (numberTokenizer.hasMoreTokens()) {
            int currentNumber = Integer.parseInt(numberTokenizer.nextToken());
            if (currentNumber < 0) {
                negativeNumbersList.add(String.valueOf(currentNumber));
            } else {
                sum += validateRangeAndGet(currentNumber);
            }
        }

        if (!negativeNumbersList.isEmpty()) {
            throw new NegativeNumberException("Negatives not allowed - " + negativeNumbersList.stream().reduce((a, b) -> a + ", " + b).get());
        } else {
            return sum;
        }

    }

    private int validateRangeAndGet(int currentNumber) {
        return currentNumber < 1000 ? currentNumber : 0;
    }

    private String getDelimiter(String numberString) {

        String parsedNumberString = numberString.replaceAll("\\]\\[", "");
        return parsedNumberString.substring(parsedNumberString.indexOf("[") + 1, parsedNumberString.indexOf("]"));
    }
}

class NegativeNumberException extends Exception {

    private String exceptionDetails;

    NegativeNumberException(String exceptionDetails) {
        super(exceptionDetails);
        this.exceptionDetails = exceptionDetails;
    }

}