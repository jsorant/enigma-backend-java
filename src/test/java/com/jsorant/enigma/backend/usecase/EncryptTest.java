package com.jsorant.enigma.backend.usecase;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.jsorant.enigma.backend.UnitTest;
import com.jsorant.enigma.backend.crypto.domain.Caesar;
import com.jsorant.enigma.backend.crypto.domain.Rotor;
import com.jsorant.enigma.backend.crypto.domain.SecurityModel;
import com.jsorant.enigma.backend.shared.error.domain.MissingMandatoryValueException;
import org.junit.jupiter.api.Test;

@UnitTest
public class EncryptTest {

  @Test
  void shouldThrowWithoutSecurityModelRepository() {
    assertThatThrownBy(() -> new Encrypt(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("securityModelRepository");
  }

  @Test
  void shouldThrowWithoutSecurityModelName() {
    SecurityModelRepository securityModelRepository = new SecurityModelRepositoryMock();
    assertThatThrownBy(() -> new Encrypt(securityModelRepository).run(null, "Input text"))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("securityModelName");

    assertThatThrownBy(() -> new Encrypt(securityModelRepository).run("", "Input text"))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("securityModelName");
  }

  @Test
  void shouldThrowIfNoSecurityModelFound() {
    SecurityModelRepository securityModelRepository = new SecurityModelRepositoryMock();
    assertThatThrownBy(() -> new Encrypt(securityModelRepository).run("AnyName", "Input text"))
      .isExactlyInstanceOf(SecurityModelNotFoundException.class)
      .hasMessageContaining("AnyName");
  }

  @Test
  void shouldEncrypt() {
    SecurityModelRepositoryMock securityModelRepository = new SecurityModelRepositoryMock();
    securityModelRepository.save(enigmaSecurityModel());
    Encrypt encrypt = new Encrypt(securityModelRepository);

    assertDoesNotThrow(() -> {
      assertThat(encrypt.run(enigmaSecurityModelName(), "EVERYONEISWELCOMEHEREEVERYONEISWELCOMEHERE"))
        .isEqualTo("MKDWDLTEUPWZBXMTWUUROXHBZBYJDAMZRUWXJZAACQ");
    });
  }

  private static SecurityModel enigmaSecurityModel() {
    return SecurityModel
      .builder()
      .name(enigmaSecurityModelName())
      .withEngine(new Caesar(9, 3))
      .withEngine(new Rotor("BDFHTXVZNYEIWGAKMUSQOJLCPR"))
      .withEngine(new Rotor("AJDKSIRUXBGZNPYFVOELHWTMCQ"))
      .withEngine(new Rotor("TOWYHXUSPAIBRCJEKMFLGDQVZN"))
      .build();
  }

  private static String enigmaSecurityModelName() {
    return "Enigma";
  }
}
