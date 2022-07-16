package application.TestTypes;

import application.Tests.TestResult;

import java.util.Date;

public interface TestType {
    Date getDateOfResults();
    TestResult getTestResult();

}