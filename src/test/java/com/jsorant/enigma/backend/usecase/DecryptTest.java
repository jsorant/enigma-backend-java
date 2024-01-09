package com.jsorant.enigma.backend.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.jsorant.enigma.backend.UnitTest;
import com.jsorant.enigma.backend.infrastructure.secondary.InMemorySecurityModelRepository;
import com.jsorant.enigma.backend.shared.error.domain.MissingMandatoryValueException;
import org.junit.jupiter.api.Test;

@UnitTest
public class DecryptTest {

  @Test
  void shouldThrowWithoutSecurityModelRepository() {
    assertThatThrownBy(() -> new Decrypt(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("securityModelRepository");
  }

  @Test
  void shouldThrowWithoutSecurityModelName() {
    SecurityModelRepository securityModelRepository = new InMemorySecurityModelRepository();
    assertThatThrownBy(() -> new Decrypt(securityModelRepository).run(null, "Input text"))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("securityModelName");

    assertThatThrownBy(() -> new Decrypt(securityModelRepository).run("", "Input text"))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("securityModelName");
  }

  @Test
  void shouldThrowIfNoSecurityModelFound() {
    SecurityModelRepository securityModelRepository = new InMemorySecurityModelRepository();

    assertThatThrownBy(() -> new Decrypt(securityModelRepository).run("AnyName", "Input text"))
      .isExactlyInstanceOf(SecurityModelNotFoundException.class)
      .hasMessageContaining("AnyName");
  }

  @Test
  void shouldDecrypt() {
    SecurityModelRepository securityModelRepository = Fixtures.securityModelRepositoryWithEnigma();
    Decrypt decrypt = new Decrypt(securityModelRepository);

    assertDoesNotThrow(() -> {
      assertThat(decrypt.run(Fixtures.enigmaSecurityModelName(), "MKDWDLTEUPWZBXMTWUUROXHBZBYJDAMZRUWXJZAACQ"))
        .isEqualTo("EVERYONEISWELCOMEHEREEVERYONEISWELCOMEHERE");
    });
  }
}
