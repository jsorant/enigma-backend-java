package com.jsorant.enigma.backend.usecase;

public class SecurityModelNotFoundException extends Exception {

  SecurityModelNotFoundException(String message) {
    super(message);
  }

  public static SecurityModelNotFoundExceptionBuilder builder() {
    return new SecurityModelNotFoundExceptionBuilder();
  }

  public static class SecurityModelNotFoundExceptionBuilder {

    private String securityModelName;

    public SecurityModelNotFoundExceptionBuilder securityModelName(String name) {
      securityModelName = name;
      return this;
    }

    SecurityModelNotFoundException build() {
      StringBuilder sb = new StringBuilder();
      sb.append("Security model with name \"");
      sb.append(securityModelName);
      sb.append("\" was not found");

      return new SecurityModelNotFoundException(sb.toString());
    }
  }
}
