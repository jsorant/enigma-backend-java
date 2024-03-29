package com.jsorant.enigma.backend.usecase;

import com.jsorant.enigma.backend.domain.SecurityModel;
import com.jsorant.enigma.backend.shared.error.domain.Assert;

public class Encrypt extends SecurityModelBasedUseCase {

  Encrypt(SecurityModelRepository securityModelRepository) {
    super(securityModelRepository);
  }

  public String run(String securityModelName, String input) throws SecurityModelNotFoundException {
    Assert.notBlank("securityModelName", securityModelName);

    SecurityModel securityModel = findSecurityModelOrThrow(securityModelName);
    return securityModel.encrypt(input);
  }
}
