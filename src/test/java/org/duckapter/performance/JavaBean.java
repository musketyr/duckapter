package org.duckapter.performance;

/**
 * Based on {@link http://blog.smart-java.nl/blog/index.php/2010/04/25/reflection-slow-well-it-depends/}
 *
 */
public class JavaBean {
    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

