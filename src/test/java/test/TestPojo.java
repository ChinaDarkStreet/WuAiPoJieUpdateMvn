package test;

import java.util.Arrays;

public class TestPojo {
    private String array;
    private String[] strs;

    @Override
    public String toString() {
        return "TestPojo{" +
                "array='" + array + '\'' +
                ", strs=" + Arrays.toString(strs) +
                '}';
    }

    public String getArray() {
        return array;
    }

    public void setArray(String array) {
        this.array = array;
    }

    public String[] getStrs() {
        return strs;
    }

    public void setStrs(String[] strs) {
        this.strs = strs;
    }
}
