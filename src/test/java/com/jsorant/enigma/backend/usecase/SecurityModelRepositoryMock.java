package com.jsorant.enigma.backend.usecase;

import com.jsorant.enigma.backend.crypto.domain.SecurityModel;
import com.jsorant.enigma.backend.crypto.domain.SecurityModelSnapshot;
import java.util.HashMap;
import java.util.Optional;

public class SecurityModelRepositoryMock implements SecurityModelRepository {

  private final HashMap<String, SecurityModel> map = new HashMap<>();

  @Override
  public Optional<SecurityModel> getByName(String securityModelName) {
    if (map.containsKey(securityModelName)) {
      return Optional.ofNullable(map.get(securityModelName));
    }
    return Optional.empty();
  }

  @Override
  public void save(SecurityModel securityModel) {
    SecurityModelSnapshot snapshot = securityModel.snapshot();
    map.put(snapshot.name, securityModel);
  }
}
