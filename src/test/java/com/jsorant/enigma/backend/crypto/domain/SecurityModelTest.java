package com.jsorant.enigma.backend.crypto.domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jsorant.enigma.backend.UnitTest;
import com.jsorant.enigma.backend.shared.error.domain.MissingMandatoryValueException;
import org.junit.jupiter.api.Test;

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
  void shouldBuildWithAnEngine() {
    //SecurityModel.builder().name("").withEngine(new DummyEngine).build();
  }
}
