class Seven {

    public static MultipleArgumentsLambda.SeptenaryStringFunction fun =
        (String s1, String s2, String s3, String s4, String s5, String s6, String s7) -> {
            return s1.toUpperCase().concat(s2.toUpperCase()).concat(s3.toUpperCase())
                .concat(s4.toUpperCase()).concat(s5.toUpperCase()).concat(s6.toUpperCase())
                .concat(s7.toUpperCase());
        };
}