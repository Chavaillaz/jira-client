package com.chavaillaz.client.jira.domain;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Data;
import lombok.experimental.Delegate;

/**
 * Part of the XRay plugin
 */
@Data
@JsonFormat(shape = OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestExecution implements List<TestExecution.TestStatus> {

    private int count;

    @Delegate
    @JsonMerge
    private List<TestStatus> statuses;

    /**
     * Creates a new list of tests.
     *
     * @param testStatuses The tests to include in the list
     * @return The corresponding list of tests
     */
    public static TestExecution from(TestStatus... testStatuses) {
        return from(List.of(testStatuses));
    }

    /**
     * Creates a new list of tests.
     *
     * @param testStatuses The tests to include in the list
     * @return The corresponding list of tests
     */
    public static TestExecution from(List<TestStatus> testStatuses) {
        TestExecution container = new TestExecution();
        container.addAll(testStatuses);
        return container;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TestStatus {

        private String color;

        private String description;

        private int id;

        private boolean isFinal;

        private boolean isNative;

        private String name;

        private int statusCount;

        private float statusPercent;

        /**
         * Creates a new test status.
         *
         * @param name  The name of the status
         * @param count The number of elements corresponding to this status
         * @return The corresponding test status
         */
        public static TestStatus from(String name, int count) {
            TestStatus test = new TestStatus();
            test.setName(name);
            test.setStatusCount(count);
            return test;
        }

    }

}
