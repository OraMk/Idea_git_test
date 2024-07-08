package calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class test {
    public static void main(String[] args) {
        BigDecimal result = BigDecimal.valueOf(1.0).divide(BigDecimal.valueOf(3),8,RoundingMode.HALF_UP);
        System.out.println(result);
    }
}
