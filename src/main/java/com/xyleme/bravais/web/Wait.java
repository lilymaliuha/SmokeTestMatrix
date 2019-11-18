package com.xyleme.bravais.web;

import org.openqa.selenium.TimeoutException;

import static com.xyleme.bravais.BaseTest.staticSleep;

public class Wait<T extends Component<T>> {
    private static final double DEFAULT_TIMEOUT_IN_SECONDS = 20;
    private static final double DEFAULT_RETRY_DELAY_IN_SECONDS = 0.25;
    private T component;

    public Wait() {
    }

    public Wait<T> forComponent(T component) {
        this.component = component;
        return this;
    }

    public T toBeAvailable() {
        return toBeAvailableWithin(DEFAULT_TIMEOUT_IN_SECONDS);
    }

    public T toBeAvailableWithin(double timeOutInSeconds) {
        double timePassed = 0;
        while (timePassed < timeOutInSeconds) {
            if (this.component.isAvailable()) {
                return this.component;
            }
            timePassed = timePassed + delay();
        }
        if (!this.component.isAvailable()) {
            throw new TimeoutException("Timed out after " + (int) timeOutInSeconds + " seconds waiting for " +
                    this.component.getClass().getSimpleName() + " to be available.");
        }
        return this.component;
    }

    private double delay() {
        staticSleep(DEFAULT_RETRY_DELAY_IN_SECONDS);
        return DEFAULT_RETRY_DELAY_IN_SECONDS;
    }
}