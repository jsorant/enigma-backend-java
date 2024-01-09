package com.jsorant.enigma.backend.usecase;

import com.jsorant.enigma.backend.domain.SecurityModel;
import com.jsorant.enigma.backend.infrastructure.secondary.InMemorySecurityModelRepository;

public class Fixtures {

  public static SecurityModelRepository securityModelRepositoryWithEnigma() {
    InMemorySecurityModelRepository securityModelRepository = new InMemorySecurityModelRepository();
    securityModelRepository.save(enigmaSecurityModel());
    return securityModelRepository;
  }

  public static String enigmaSecurityModelName() {
    return "Enigma";
  }

  private static SecurityModel enigmaSecurityModel() {
    return SecurityModel
      .builder()
      .name(enigmaSecurityModelName())
      .withCaesar(9, 3)
      .withRotor("BDFHTXVZNYEIWGAKMUSQOJLCPR")
      .withRotor("AJDKSIRUXBGZNPYFVOELHWTMCQ")
      .withRotor("TOWYHXUSPAIBRCJEKMFLGDQVZN")
      .build();
  }
}
