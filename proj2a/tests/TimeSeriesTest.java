import ngrams.TimeSeries;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void yearsTest() {
        TimeSeries ts = new TimeSeries();
        ts.put(1991, 0.0);
        ts.put(1992, 100.0);
        ts.put(1994, 200.0);

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994));

        assertThat(ts.years()).isEqualTo(expectedYears);
        assertThat(ts.containsKey(1990)).isFalse();

        TimeSeries ts1 = new TimeSeries();

        assertThat(ts1.years()).isEqualTo(Arrays.asList());// equals to []
    }

    //cpy old ts to new ts in range of start and end years
    @Test
    public void copyOldTsTest() {
        TimeSeries oldTs = new TimeSeries();
        oldTs.put(1991, 0.0);
        oldTs.put(1992, 100.0);
        oldTs.put(1994, 200.0);

        TimeSeries newTs = new TimeSeries(oldTs, 1991, 1993);// const new ts in range of start and end years

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992));

        assertThat(newTs.years()).isEqualTo(expectedYears);
    }

    //cpy old ts to new ts in range of start and end years
    @Test
    public void copyOldTsInvalidInputTest() {
        TimeSeries oldTs = new TimeSeries();
        oldTs.put(1991, 0.0);
        oldTs.put(1992, 100.0);
        oldTs.put(1994, 200.0);

        TimeSeries newTs = new TimeSeries(oldTs, 1994, 1993);// bad years input, should create empty ts

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList());// equals to []

        assertThat(newTs.years()).isEqualTo(expectedYears);

        newTs = new TimeSeries(null, 1991, 1993);// old ts input is null, should create empty ts

        assertThat(newTs.years()).isEqualTo(expectedYears);
    }
    //check if data func has correct behavior
    @Test
    public void dataTest() {
        TimeSeries ts = new TimeSeries();
        ts.put(1991, 0.0);
        ts.put(1992, 100.0);
        ts.put(1994, 200.0);

        List<Double> expectedYears = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 200.0));

        assertThat(ts.data()).isEqualTo(expectedYears);

        //test for empty ts
        ts = new TimeSeries();

        expectedYears = new ArrayList<>
                (Arrays.asList());

        assertThat(ts.data()).isEqualTo(expectedYears);
    }
    //test plus func for expected output and for bad input
    @Test
    public void plusTest() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 200


        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 200.0));

        assertThat(totalPopulation.data()).isEqualTo(expectedTotal);

        dogPopulation = null;

        totalPopulation = catPopulation.plus(dogPopulation);

        expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 200.0));

        assertThat(totalPopulation.data()).isEqualTo(expectedTotal);
    }

    @Test
    public void divTest() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 100.0);
        catPopulation.put(1992, 200.0);
        catPopulation.put(1994, 400.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 400.0);
        dogPopulation.put(1992, 400.0);

        //mismatched years
        try {
            catPopulation.dividedBy(dogPopulation);
        } catch (IllegalArgumentException e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class);
            assertThat(e).hasMessageThat().contains("ts has mismatched years");
        }

        dogPopulation.put(1994, 400.0);

        //correct input check output
        TimeSeries proportions = catPopulation.dividedBy(dogPopulation);

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994));

        assertThat(proportions.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.25, 0.5, 1.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {//division doesn't always return corr values
            assertThat(proportions.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyDividedBy() {
        TimeSeries ts1 = new TimeSeries();
        TimeSeries ts2 = new TimeSeries();
        TimeSeries result = ts1.dividedBy(ts2);
        assertThat(result.years()).isEmpty();
    }

    @Test
    public void testPlusOverlappingYears() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(2000, 50.0);
        ts1.put(2001, 100.0);
        ts1.put(2002, 150.0);

        TimeSeries ts2 = new TimeSeries();
        ts2.put(2001, 200.0);
        ts2.put(2002, 300.0);
        ts2.put(2003, 400.0);

        TimeSeries result = ts1.plus(ts2);
        assertThat(result.get(2000)).isEqualTo(50.0);
        assertThat(result.get(2001)).isEqualTo(300.0);
        assertThat(result.get(2002)).isEqualTo(450.0);
        assertThat(result.get(2003)).isEqualTo(400.0);
    }

    @Test
    public void testYearsOrderIndependence() {
        TimeSeries ts = new TimeSeries();
        ts.put(2002, 200.0);
        ts.put(2000, 50.0);
        ts.put(2001, 100.0);

        List<Integer> expectedYears = Arrays.asList(2000, 2001, 2002);
        assertThat(ts.years()).isEqualTo(expectedYears);
    }

    @Test
    public void testDividedByZero() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(2000, 100.0);
        ts1.put(2001, 200.0);

        TimeSeries ts2 = new TimeSeries();
        ts2.put(2000, 0.0); // Division by zero scenario
        ts2.put(2001, 50.0);

        try {
            ts1.dividedBy(ts2);
        } catch (ArithmeticException e) {
            assertThat(e).isInstanceOf(ArithmeticException.class);
        }
    }
} 