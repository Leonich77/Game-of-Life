import java.util.function.LongUnaryOperator;

class Operator {

    public static LongUnaryOperator unaryOperator = i -> {
      if (i % 2 == 0) {
          return i + 2;
      } else {
          return i + 1;
      }
    };
}