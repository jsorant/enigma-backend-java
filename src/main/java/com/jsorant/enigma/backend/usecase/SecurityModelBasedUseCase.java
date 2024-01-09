package com.jsorant.enigma.backend.usecase;

import com.jsorant.enigma.backend.domain.SecurityModel;
import com.jsorant.enigma.backend.shared.error.domain.Assert;
import java.util.Optional;

public class SecurityModelBasedUseCase {

  protected final SecurityModelRepository securityModelRepository;

  public SecurityModelBasedUseCase(SecurityModelRepository securityModelRepository) {
    Assert.notNull("securityModelRepository", securityModelRepository);

    this.securityModelRepository = securityModelRepository;
  }

  protected SecurityModel findSecurityModelOrThrow(String securityModelName) throws SecurityModelNotFoundException {
    Optional<SecurityModel> securityModel = securityModelRepository.getByName(securityModelName);
    if (securityModel.isEmpty()) {
      throw SecurityModelNotFoundException.builder().securityModelName(securityModelName).build();
    }
    return securityModel.get();
  }
}
