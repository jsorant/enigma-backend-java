package com.jsorant.enigma.backend.crypto.domain;

import static org.assertj.core.api.Assertions.*;

import com.jsorant.enigma.backend.UnitTest;
import com.jsorant.enigma.backend.shared.error.domain.MissingMandatoryValueException;
import com.jsorant.enigma.backend.shared.error.domain.StringTooLongException;
import com.jsorant.enigma.backend.shared.error.domain.StringTooShortException;
import org.junit.jupiter.api.Test;

@UnitTest
public class RotorTest {

  @Test
  void shouldNotWorkWithBlankRotor() {
    assertThatThrownBy(() -> new Rotor(null)).isExactlyInstanceOf(MissingMandatoryValueException.class).hasMessageContaining("rotorValue");

    assertThatThrownBy(() -> new Rotor("")).isExactlyInstanceOf(MissingMandatoryValueException.class).hasMessageContaining("rotorValue");

    assertThatThrownBy(() -> new Rotor("                          "))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("rotorValue");
  }

  @Test
  void shouldNotWorkWithRotorOfWrongLength() {
    assertThatThrownBy(() -> new Rotor("ABC")).isExactlyInstanceOf(StringTooShortException.class).hasMessageContaining("rotorValue");

    assertThatThrownBy(() -> new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQOABC"))
      .isExactlyInstanceOf(StringTooLongException.class)
      .hasMessageContaining("rotorValue");
  }

  //TODO should contain all chars in AZ range, once

  @Test
  void shouldEncrypt() {
    Rotor rotor = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO");

    assertThat(rotor.encrypt("A")).isEqualTo("B");
    assertThat(rotor.encrypt("C")).isEqualTo("F");
    assertThat(rotor.encrypt("B")).isEqualTo("D");
    assertThat(rotor.encrypt("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isEqualTo("BDFHJLCPRTXVZNYEIWGAKMUSQO");
    assertThat(rotor.encrypt("DGM")).isEqualTo("HCZ");
  }

  @Test
  void shouldDecrypt() {
    Rotor rotor = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO");

    assertThat(rotor.decrypt("B")).isEqualTo("A");
    assertThat(rotor.decrypt("F")).isEqualTo("C");
    assertThat(rotor.decrypt("D")).isEqualTo("B");
    assertThat(rotor.decrypt("BDFHJLCPRTXVZNYEIWGAKMUSQO")).isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    assertThat(rotor.decrypt("HCZ")).isEqualTo("DGM");
  }
}
