package com.jsorant.enigma.backend.usecase;

import com.jsorant.enigma.backend.crypto.domain.SecurityModel;
import com.jsorant.enigma.backend.shared.error.domain.Assert;
import java.util.Optional;

public class Encrypt {

  private final SecurityModelRepository securityModelRepository;

  Encrypt(SecurityModelRepository securityModelRepository) {
    Assert.notNull("securityModelRepository", securityModelRepository);

    this.securityModelRepository = securityModelRepository;
  }

  public String run(String securityModelName, String input) throws SecurityModelNotFoundException {
    Assert.notBlank("securityModelName", securityModelName);

    SecurityModel securityModel = findSecurityModelOrThrow(securityModelName);
    return securityModel.encrypt(input);
  }

  private SecurityModel findSecurityModelOrThrow(String securityModelName) throws SecurityModelNotFoundException {
    Optional<SecurityModel> securityModel = securityModelRepository.getByName(securityModelName);
    if (securityModel.isEmpty()) {
      throw SecurityModelNotFoundException.builder().securityModelName(securityModelName).build();
    }
    return securityModel.get();
  }
}
