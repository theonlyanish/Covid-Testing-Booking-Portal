package application.TestTypes;

import application.Tests.TestResult;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class PCR implements TestType {

    public Date getDateOfResults() {
        //PCR comes next day
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    @Override
    public TestResult getTestResult() {
        Random r = new Random();
        TestResult[] possibleResults = {TestResult.POSITIVE, TestResult.NEGATIVE};
        return possibleResults[r.nextInt(possibleResults.length)];
    }
}