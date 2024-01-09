package com.jsorant.enigma.backend.crypto.domain;

public class Caesar {

  private static final int ASCII_CHAR_A = 65;
  private static final int ASCII_CHAR_Z = 90;
  private static final int ALPHABET_LETTERS_COUNT = 26;

  public Caesar() {}

  public String encrypt(String input, int shift, int increment) {
    return transformWith(Caesar::incrementChar, input, shift, increment);
  }

  public String decrypt(String input, int shift, int increment) {
    return transformWith(Caesar::decrementChar, input, shift, increment);
  }

  private interface CharTransformationFunction {
    int run(int shift, char c);
  }

  private String transformWith(CharTransformationFunction transformationFunction, String input, int shift, int increment) {
    StringBuilder sb = new StringBuilder();
    for (char c : input.toCharArray()) {
      int newAsciiCharCode = transformationFunction.run(shift, c);
      shift = updateShiftWithIncrement(shift, increment);
      sb.append((char) (newAsciiCharCode));
    }
    return sb.toString();
  }

  private static int updateShiftWithIncrement(int shift, int increment) {
    return shift + increment;
  }

  private static int incrementChar(int shift, char c) {
    int newAsciiCode = c + shift;
    newAsciiCode = ensureCharIsInAZRange(newAsciiCode);
    return newAsciiCode;
  }

  private static int decrementChar(int shift, char c) {
    int newAsciiCode = c - shift;
    newAsciiCode = ensureCharIsInAZRange(newAsciiCode);
    return newAsciiCode;
  }

  private static int ensureCharIsInAZRange(int charAsciiCode) {
    while (charAsciiCode > ASCII_CHAR_Z) {
      charAsciiCode -= ALPHABET_LETTERS_COUNT;
    }
    while (charAsciiCode < ASCII_CHAR_A) {
      charAsciiCode += ALPHABET_LETTERS_COUNT;
    }
    return charAsciiCode;
  }
}
