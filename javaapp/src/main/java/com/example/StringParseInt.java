package com.example;

/**
 * Created by yanru.zhang on 16/9/29.
 * Email:yanru.zhang@renren-inc.com
 */
public class StringParseInt {

    public static void main(String[] args){
        int result = parseInt("-2147483649",10);
        System.out.println("result:" + result);
    }


    /**
     * Parses the string argument as a signed integer in the radix
     * specified by the second argument. The characters in the string
     * must all be digits of the specified radix (as determined by
     * whether {@link java.lang.Character#digit(char, int)} returns a
     * nonnegative value), except that the first character may be an
     * ASCII minus sign {@code '-'} (<code>'&#92;u002D'</code>) to
     * indicate a negative value or an ASCII plus sign {@code '+'}
     * (<code>'&#92;u002B'</code>) to indicate a positive value. The
     * resulting integer value is returned.
     *
     * <p>An exception of type {@code NumberFormatException} is
     * thrown if any of the following situations occurs:
     * <ul>
     * <li>The first argument is {@code null} or is a string of
     * length zero.
     *
     * <li>The radix is either smaller than
     * {@link java.lang.Character#MIN_RADIX} or
     * larger than {@link java.lang.Character#MAX_RADIX}.
     *
     * <li>Any character of the string is not a digit of the specified
     * radix, except that the first character may be a minus sign
     * {@code '-'} (<code>'&#92;u002D'</code>) or plus sign
     * {@code '+'} (<code>'&#92;u002B'</code>) provided that the
     * string is longer than length 1.
     *
     * <li>The value represented by the string is not a value of type
     * {@code int}.
     * </ul>
     *
     * <p>Examples:
     * <blockquote><pre>
     * parseInt("0", 10) returns 0
     * parseInt("473", 10) returns 473
     * parseInt("+42", 10) returns 42
     * parseInt("-0", 10) returns 0
     * parseInt("-FF", 16) returns -255
     * parseInt("1100110", 2) returns 102
     * parseInt("2147483647", 10) returns 2147483647
     * parseInt("-2147483648", 10) returns -2147483648
     * parseInt("2147483648", 10) throws a NumberFormatException
     * parseInt("99", 8) throws a NumberFormatException
     * parseInt("Kona", 10) throws a NumberFormatException
     * parseInt("Kona", 27) returns 411787
     * </pre></blockquote>
     *
     * @param      s   the {@code String} containing the integer
     *                  representation to be parsed
     * @param      radix   the radix to be used while parsing {@code s}.
     * @return     the integer represented by the string argument in the
     *             specified radix.
     * @exception  NumberFormatException if the {@code String}
     *             does not contain a parsable {@code int}.
     */
    public static int parseInt(String s, int radix)
            throws NumberFormatException
    {
        /*
         * WARNING: This method may be invoked early during VM initialization
         * before IntegerCache is initialized. Care must be taken to not use
         * the valueOf method.
         */

        if (s == null) {
            throw new NumberFormatException("null");
        }

        if (radix < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix +
                    " less than Character.MIN_RADIX");
        }

        if (radix > Character.MAX_RADIX) {
            throw new NumberFormatException("radix " + radix +
                    " greater than Character.MAX_RADIX");
        }

        int result = 0;
        boolean negative = false; //是不是负的
        int i = 0, len = s.length();
        int limit = -Integer.MAX_VALUE; // 正数的时候是：-2147483647，负数的时候是：-2147483648
        System.out.println("\nlimit:" + limit);
        int multmin;
        int digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Integer.MIN_VALUE;
                    System.out.println("limit:" + limit);
                } else if (firstChar != '+')
                    throw new RuntimeException("char not legal,must be 0-9 and + -");

                if (len == 1) // Cannot have lone "+" or "-"
                    throw new RuntimeException("Cannot have lone + or -");
                i++;
            }
            multmin = limit / radix; // limit去掉最后一位，这里是“-214748364”
            System.out.println("multmin:" + multmin);
            while (i < len) {
                System.out.println("------------------- i=" + i + " --------------------");
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(s.charAt(i++),radix);//每一位的数字
                System.out.println("digit:" + digit);
                if (digit < 0) {
                    throw new RuntimeException("digit < 0");
                }
                System.out.println("result < multmin : " + result + " < " + multmin + " " + (result < multmin) );
                if (result < multmin) {
                    throw new RuntimeException("result < multmin");
                }
                result *= radix;
                System.out.println("result:" + result);
                System.out.println("limit + digit :" + limit + " + " + digit + " = " + (limit + digit));
                if (result < limit + digit) {
                    throw new RuntimeException("result < limit + digit");
                }
                result -= digit;
            }
        } else {
            throw new RuntimeException("length less 0");
        }
        return negative ? result : -result;
    }

}
