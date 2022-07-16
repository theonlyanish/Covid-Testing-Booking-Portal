package application.TestTypes;

import application.Tests.TestResult;

import java.util.Date;
import java.util.Random;

public class RAT implements TestType {

    public Date getDateOfResults() {
        return new Date();
    }

    @Override
    public TestResult getTestResult() {
        Random r = new Random();
        TestResult[] possibleResults = {TestResult.POSITIVE, TestResult.NEGATIVE, TestResult.INVALID};
        return possibleResults[r.nextInt(possibleResults.length)];
    }
    
}