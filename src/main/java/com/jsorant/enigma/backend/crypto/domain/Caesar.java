package com.jsorant.enigma.backend.crypto.domain;

public class Caesar implements Engine {

  private static final int ASCII_CHAR_A = 65;
  private static final int ASCII_CHAR_Z = 90;
  private static final int ALPHABET_LETTERS_COUNT = 26;

  private final int shift;
  private final int increment;
  private int currentShift;

  public Caesar(int shift, int increment) {
    this.shift = shift;
    this.increment = increment;
  }

  @Override
  public String encrypt(String input) {
    return transformWith(Caesar::incrementChar, input);
  }

  @Override
  public String decrypt(String input) {
    return transformWith(Caesar::decrementChar, input);
  }

  private interface CharTransformationFunction {
    int run(int shift, char c);
  }

  private String transformWith(CharTransformationFunction transformationFunction, String input) {
    initializeCurrentShift();
    StringBuilder sb = new StringBuilder();
    for (char c : input.toCharArray()) {
      sb.append(transformChar(transformationFunction, c));
    }
    return sb.toString();
  }

  private char transformChar(CharTransformationFunction transformationFunction, char c) {
    int transformedCharAsciiCode = transformationFunction.run(currentShift, c);
    updateCurrentShiftWithIncrement();
    return (char) ensureAsciiCodeIsInAZRange(transformedCharAsciiCode);
  }

  private void initializeCurrentShift() {
    currentShift = shift;
  }

  private void updateCurrentShiftWithIncrement() {
    currentShift = currentShift + increment;
  }

  private static int incrementChar(int shift, char c) {
    return c + shift;
  }

  private static int decrementChar(int shift, char c) {
    return c - shift;
  }

  private static int ensureAsciiCodeIsInAZRange(int charAsciiCode) {
    while (charAsciiCode > ASCII_CHAR_Z) {
      charAsciiCode -= ALPHABET_LETTERS_COUNT;
    }
    while (charAsciiCode < ASCII_CHAR_A) {
      charAsciiCode += ALPHABET_LETTERS_COUNT;
    }
    return charAsciiCode;
  }
}
