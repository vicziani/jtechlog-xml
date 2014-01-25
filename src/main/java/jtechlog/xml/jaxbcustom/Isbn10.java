package jtechlog.xml.jaxbcustom;

public class Isbn10 {

    private String value;

    public Isbn10() {
    }

    public Isbn10(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isValid() {
        if (value.length() != 10) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            String digit = value.substring(i, i + 1);
            if (digit.equalsIgnoreCase("x")) {
                sum += 10 * (10 - i);
            }
            else {
                sum += Integer.valueOf(digit) * (10 - i);
            }
        }
        return sum % 11 == 0;
    }
}

