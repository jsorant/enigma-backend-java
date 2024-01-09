package com.jsorant.enigma.backend.usecase;

import com.jsorant.enigma.backend.domain.SecurityModel;
import java.util.Optional;

public interface SecurityModelRepository {
  Optional<SecurityModel> getByName(String securityModelName);

  void save(SecurityModel securityModel);
}
