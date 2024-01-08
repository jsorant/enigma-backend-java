package com.jsorant.enigma.backend.domain;

@UnitTest
class CaesarTest {

  @Test
  void shouldNotBuildWithoutShift() {
    //assertThatThrownBy(() -> new Caesar(null).isExactelyInstanceOf(MissingMandatoryValueException));
  }
}
