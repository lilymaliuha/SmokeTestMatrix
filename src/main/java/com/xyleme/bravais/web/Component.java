package com.xyleme.bravais.web;

import org.openqa.selenium.WebDriver;

public abstract class Component<T extends Component<T>> {
    protected WebDriver driver;

    public Component(WebDriver driver) {
        this.driver = driver;
    }

    public abstract boolean isAvailable();

    @SuppressWarnings("unchecked")
    public T waitUntilAvailable() {
        return new Wait<T>().forComponent((T) this).toBeAvailable();
    }

    public T waitUntilAvailableFor(double timeOutInSeconds) {
        return new Wait<T>().forComponent((T) this).toBeAvailableWithin(timeOutInSeconds);
    }

    public void waitUntilAvailableSafe() {
        try {
            new Wait<T>().forComponent((T) this).toBeAvailable();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}