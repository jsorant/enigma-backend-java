package com.jsorant.enigma.backend.crypto.domain;

import com.jsorant.enigma.backend.shared.error.domain.Assert;

public class Rotor implements Engine {

  private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  private final String rotorValue;

  Rotor(String rotorValue) {
    Assert.notBlank("rotorValue", rotorValue);
    Assert.field("rotorValue", rotorValue).minLength(ALPHABET.length());
    Assert.field("rotorValue", rotorValue).maxLength(ALPHABET.length());

    this.rotorValue = rotorValue;
  }

  @Override
  public String encrypt(String input) {
    return transformString(input, ALPHABET, rotorValue);
  }

  @Override
  public String decrypt(String input) {
    return transformString(input, rotorValue, ALPHABET);
  }

  private String transformString(String input, String sourceSequence, String destinationSequence) {
    StringBuilder sb = new StringBuilder();
    for (char c : input.toCharArray()) {
      sb.append(transformChar(c, sourceSequence, destinationSequence));
    }
    return sb.toString();
  }

  private char transformChar(char c, String sourceSequence, String destinationSequence) {
    int indexOfCharInSource = sourceSequence.indexOf(c);
    return destinationSequence.charAt(indexOfCharInSource);
  }
}
