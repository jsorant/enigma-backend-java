package com.jsorant.enigma.backend.crypto.domain;

import com.jsorant.enigma.backend.shared.error.domain.Assert;

public class SecurityModel {

  private final String name;

  public SecurityModel(String name) {
    Assert.notBlank("name", name);

    this.name = name;
  }

  public static SecurityModelBuilder builder() {
    return new SecurityModelBuilder();
  }

  public static class SecurityModelBuilder {

    private String name;

    private SecurityModelBuilder() {}

    public SecurityModelBuilder name(String aName) {
      this.name = aName;
      return this;
    }

    public SecurityModel build() {
      return new SecurityModel(this.name);
    }
  }
}
