import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;

public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void shouldReturnZeroIfEmptyStringIsPassed() throws Exception {
        Assert.assertThat(stringCalculator.add(""), is(0));
    }

    @Test
    public void shouldReturnSumWhenStringHasOneNumber() throws Exception {
        Assert.assertThat(stringCalculator.add("1"), is(1));
    }

    @Test
    public void shouldReturnSumWhenStringHasTwoNumbers() throws Exception {
        Assert.assertThat(stringCalculator.add("1,2"), is(3));
    }

    @Test
    public void shouldReturnSumWhenStringHasThreeNumbers() throws Exception {
        Assert.assertThat(stringCalculator.add("1,2,3"), is(6));
    }

    @Test
    public void shouldReturnSumWhenStringStartsWithComma() throws Exception {
        Assert.assertThat(stringCalculator.add(",1,2,3"), is(6));
    }

    @Test
    public void shouldReturnSumWhenStringHasMultipleDelimiterTypeAsWell() throws Exception {
        Assert.assertThat(stringCalculator.add("//[;,]\n1;2,3;8"), is(14));
    }

    @Test
    public void shouldReturnSumWhenStringHasMultipleDelimiterWithinDifferentSquareBracketsAsMarkUpsTypeAsWell() throws Exception {
        Assert.assertThat(stringCalculator.add("//[;,][:]\n1;2:3;8"), is(14));
        Assert.assertThat(stringCalculator.add("//[;,][:][.][']\n1;2:3;8.6'10"), is(30));
    }

    @Test
    public void shouldReturnSumWhenStringHasMultipleDelimiterWithMultipleLengthWithinDifferentSquareBracketsAsMarkUpsTypeAsWell() throws Exception {
        Assert.assertThat(stringCalculator.add("//[;,][:]\n1;2:3;8"), is(14));
        Assert.assertThat(stringCalculator.add("//[;,][:][.]['][***]\n1;2***:3;,8.6'***10"), is(30));
    }

    @Test
    public void shouldReturnCorrectSumWhenStringHasMultipleDelimiterWithVariableLengthWithinDifferentSquareBracketsAsMarkUpsTypeAsWellAndOutOfRangeNumbers() throws Exception {
        Assert.assertThat(stringCalculator.add("//[;,][:][.]['][***]\n1;2***:3;,8.6'***10'10000"), is(30));
        Assert.assertThat(stringCalculator.add("//[;,][:][.]['][***]\n1;2***:3;,8.6'***10'970"), is(1000));
    }

    @Test
    public void shouldThrowCustomExceptionWhenStringHasOneNegativeNumberWithMultipleDelimiterWithVariableLengthWithinDifferentSquareBracketsAsMarkUpsTypeAsWellAndOutOfRangeNumbers() throws Exception {
        expectedException.expect(NegativeNumberException.class);
        expectedException.expectMessage("Negatives not allowed - -1");
        stringCalculator.add("//[;,][:][.]['][***]\n1;2***:3.,8,-1,6'***,10000");
    }

    @Test
    public void shouldThrowCustomExceptionWhenStringHasMultipleNegativeNumbersWithMultipleDelimiterWithVariableLengthWithinDifferentSquareBracketsAsMarkUpsTypeAsWellAndOutOfRangeNumbers() throws Exception {
        expectedException.expect(NegativeNumberException.class);
        expectedException.expectMessage("Negatives not allowed - -1, -5, -13");
        stringCalculator.add("//[;,][:][.]['][***]\n1;2***:3.,8,-1,6'***-5'-13,10000");
    }

}
