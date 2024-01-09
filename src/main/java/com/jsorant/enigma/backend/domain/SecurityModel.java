package com.jsorant.enigma.backend.domain;

import com.jsorant.enigma.backend.shared.error.domain.Assert;
import java.util.ArrayList;

public class SecurityModel {

  private final String name;

  private final ArrayList<Engine> engines;

  private SecurityModel(String name, ArrayList<Engine> engines) {
    Assert.notBlank("name", name);

    this.name = name;
    this.engines = engines;
  }

  public static SecurityModelBuilder builder() {
    return new SecurityModelBuilder();
  }

  public String encrypt(String input) {
    String result = input;
    for (Engine engine : engines) {
      result = engine.encrypt(result);
    }
    return result;
  }

  public String decrypt(String input) {
    String result = input;
    for (Engine engine : engines.reversed()) {
      result = engine.decrypt(result);
    }
    return result;
  }

  public SecurityModelSnapshot snapshot() {
    return new SecurityModelSnapshot(this.name);
  }

  public static class SecurityModelBuilder {

    private String name;

    private final ArrayList<Engine> engines = new ArrayList<>();

    private SecurityModelBuilder() {}

    public SecurityModelBuilder name(String aName) {
      this.name = aName;
      return this;
    }

    public SecurityModelBuilder withCaesar(int shift, int increment) {
      engines.add(new Caesar(shift, increment));
      return this;
    }

    public SecurityModelBuilder withRotor(String value) {
      engines.add(new Rotor(value));
      return this;
    }

    public SecurityModel build() {
      return new SecurityModel(this.name, this.engines);
    }
  }
}
