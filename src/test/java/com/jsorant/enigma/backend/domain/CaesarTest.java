package com.jsorant.enigma.backend.domain;

import static org.assertj.core.api.Assertions.*;

import com.jsorant.enigma.backend.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

@UnitTest
class CaesarTest {

  @ParameterizedTest(name = "Encrypt {0} with shift {1} and increment {2} should return {3}")
  @ArgumentsSource(EncryptArgumentsProvider.class)
  void shouldEncrypt(String input, int shift, int increment, String expectedResult) {
    Caesar caesar = new Caesar(shift, increment);
    assertThat(caesar.encrypt(input)).isEqualTo(expectedResult);
  }

  @ParameterizedTest(name = "Decrypt {0} with shift {1} and increment {2} should return {3}")
  @ArgumentsSource(DecryptArgumentsProvider.class)
  void shouldDecrypt(String input, int shift, int increment, String expectedResult) {
    Caesar caesar = new Caesar(shift, increment);
    assertThat(caesar.decrypt(input)).isEqualTo(expectedResult);
  }

  static class EncryptArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
      return Stream.of(
        Arguments.of("A", 0, 0, "A"),
        Arguments.of("A", 1, 0, "B"),
        Arguments.of("B", 1, 0, "C"),
        Arguments.of("C", 1, 0, "D"),
        Arguments.of("A", 2, 0, "C"),
        Arguments.of("B", 2, 0, "D"),
        Arguments.of("Z", 1, 0, "A"),
        Arguments.of("A", 26, 0, "A"),
        Arguments.of("C", 52, 0, "C"),
        Arguments.of("ABC", 1, 0, "BCD"),
        Arguments.of("XYZAB", 1, 0, "YZABC"),
        Arguments.of("AAA", 1, 1, "BCD"),
        Arguments.of("AAA", 0, 2, "ACE")
      );
    }
  }

  static class DecryptArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
      return Stream.of(
        Arguments.of("A", 0, 0, "A"),
        Arguments.of("B", 1, 0, "A"),
        Arguments.of("C", 1, 0, "B"),
        Arguments.of("D", 1, 0, "C"),
        Arguments.of("C", 2, 0, "A"),
        Arguments.of("D", 2, 0, "B"),
        Arguments.of("A", 1, 0, "Z"),
        Arguments.of("A", 26, 0, "A"),
        Arguments.of("C", 52, 0, "C"),
        Arguments.of("BCD", 1, 0, "ABC"),
        Arguments.of("YZABC", 1, 0, "XYZAB"),
        Arguments.of("ZZZ", 1, 1, "YXW")
      );
    }
  }
}
