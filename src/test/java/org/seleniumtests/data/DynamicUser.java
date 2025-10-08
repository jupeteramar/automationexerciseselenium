package org.seleniumtests.data;

import com.github.javafaker.Faker;

public class DynamicUser {
    static Faker fakeUser = new Faker();

    public static final String NAME = fakeUser.name().firstName();
    public static final String EMAIL = fakeUser.internet().emailAddress();
    public static final String PASSWORD = fakeUser.internet().password();

    public static final String TITLE = "Mr.";

    public static final String DAY = "15";
    public static final String MONTH = "June";
    public static final String YEAR = "1995";

    public static final boolean NEWSLETTER = true;
    public static final boolean OFFERS = false;

    public static final String FIRST_NAME = "Glen";
    public static final String LAST_NAME = "Sturgis";
    public static final String COMPANY = "Cloud 9 Corporation";
    public static final String ADDRESS = "123 Main Street";
    public static final String ADDRESS2 = "Suite 456";
    public static final String COUNTRY = "United States";
    public static final String STATE = "Illinois";
    public static final String CITY = "St. Louis";
    public static final String ZIP = "12345";
    public static final String MOBILE_NUMBER = "+1234567890";
}
