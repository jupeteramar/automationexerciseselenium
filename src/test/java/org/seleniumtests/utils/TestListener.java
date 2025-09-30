package org.seleniumtests.utils;

import org.testng.ITestResult;
import org.testng.ITestContext;
import org.testng.ITestListener;

public class TestListener implements ITestListener{
    // ✅ Default constructor (required by TestNG)
    public TestListener() {
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test Failed: " + result.getName());
        // You’ll add screenshot logic here later
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("TestNG Suite Started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("TestNG Suite Finished: " + context.getName());
    }
}
