package com.jsorant.enigma.backend.crypto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.jsorant.enigma.backend.UnitTest;
import com.jsorant.enigma.backend.shared.error.domain.MissingMandatoryValueException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

@UnitTest
public class SecurityModelTest {

  @Test
  void shouldNotBuildWithoutName() {
    assertThatThrownBy(() -> SecurityModel.builder().name(null).build())
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("name");

    assertThatThrownBy(() -> SecurityModel.builder().name("").build())
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("name");
  }

  @Test
  void shouldBuildWithMultipleEngines() {
    assertDoesNotThrow(() ->
      SecurityModel.builder().name("SecurityModelTest").withCaesar(1, 1).withRotor("BDFHJLCPRTXVZNYEIWGAKMUSQO").build()
    );
  }

  @Test
  void shouldEncryptWithoutEngines() {
    SecurityModel securityModel = SecurityModel.builder().name("SecurityModelTest").build();

    assertThat(securityModel.encrypt("AAA")).isEqualTo("AAA");
  }

  @ParameterizedTest(name = "Encrypt {0} with shift {1}, increment {2}, rotor {3}, rotor {4} and rotor {5} should return {6}")
  @ArgumentsSource(SecurityModelTest.EncryptArgumentsProvider.class)
  void shouldEncrypt(String input, int shift, int increment, String rotor1, String rotor2, String rotor3, String expectedResult) {
    SecurityModel securityModel = SecurityModel
      .builder()
      .name("SecurityModelTest")
      .withCaesar(shift, increment)
      .withRotor(rotor1)
      .withRotor(rotor2)
      .withRotor(rotor3)
      .build();

    assertThat(securityModel.encrypt(input)).isEqualTo(expectedResult);
  }

  static class EncryptArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
      return Stream.of(
        Arguments.of("AAA", 4, 1, "BDFHJLCPRTXVZNYEIWGAKMUSQO", "AJDKSIRUXBLHWTMCQGZNPYFVOE", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", "KQF"),
        Arguments.of(
          "EVERYONEISWELCOMEHERE",
          9,
          1,
          "BDFHJLCPRTXVZNYEIWGAKMUSQO",
          "AJDKSIRUXBLHWTMCQGZNPYFVOE",
          "EKMFLGDQVZNTOWYHXUSPAIBRCJ",
          "PQSACVVTOISXFXCIAMQEM"
        ),
        Arguments.of(
          "EVERYONEISWELCOMEHEREEVERYONEISWELCOMEHERE",
          9,
          3,
          "BDFHTXVZNYEIWGAKMUSQOJLCPR",
          "AJDKSIRUXBGZNPYFVOELHWTMCQ",
          "TOWYHXUSPAIBRCJEKMFLGDQVZN",
          "MKDWDLTEUPWZBXMTWUUROXHBZBYJDAMZRUWXJZAACQ"
        )
      );
    }
  }

  @ParameterizedTest(name = "Decrypt {0} with shift {1}, increment {2}, rotor {3}, rotor {4} and rotor {5} should return {6}")
  @ArgumentsSource(SecurityModelTest.DecryptArgumentsProvider.class)
  void shouldDecrypt(String input, int shift, int increment, String rotor1, String rotor2, String rotor3, String expectedResult) {
    SecurityModel securityModel = SecurityModel
      .builder()
      .name("SecurityModelTest")
      .withCaesar(shift, increment)
      .withRotor(rotor1)
      .withRotor(rotor2)
      .withRotor(rotor3)
      .build();

    assertThat(securityModel.decrypt(input)).isEqualTo(expectedResult);
  }

  static class DecryptArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
      return Stream.of(
        Arguments.of("KQF", 4, 1, "BDFHJLCPRTXVZNYEIWGAKMUSQO", "AJDKSIRUXBLHWTMCQGZNPYFVOE", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", "AAA"),
        Arguments.of(
          "PQSACVVTOISXFXCIAMQEM",
          9,
          1,
          "BDFHJLCPRTXVZNYEIWGAKMUSQO",
          "AJDKSIRUXBLHWTMCQGZNPYFVOE",
          "EKMFLGDQVZNTOWYHXUSPAIBRCJ",
          "EVERYONEISWELCOMEHERE"
        ),
        Arguments.of(
          "MKDWDLTEUPWZBXMTWUUROXHBZBYJDAMZRUWXJZAACQ",
          9,
          3,
          "BDFHTXVZNYEIWGAKMUSQOJLCPR",
          "AJDKSIRUXBGZNPYFVOELHWTMCQ",
          "TOWYHXUSPAIBRCJEKMFLGDQVZN",
          "EVERYONEISWELCOMEHEREEVERYONEISWELCOMEHERE"
        )
      );
    }
  }
}
