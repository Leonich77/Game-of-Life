import java.util.function.*;


class Operator {

    public static LongBinaryOperator binaryOperator = (i, j) -> {
        long out = 1;
        if (i == j) {
            out = i;
        } else {
            for (long z = i; z <= j; z++) {
                out = out * z;
            }
        }
        return out;
    };
}