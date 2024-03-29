package com.jsorant.enigma.backend.shared.error.domain;

import static org.assertj.core.api.Assertions.*;

import com.jsorant.enigma.backend.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class NullElementInCollectionExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    NullElementInCollectionException exception = new NullElementInCollectionException("myField");

    assertThat(exception.type()).isEqualTo(AssertionErrorType.NULL_ELEMENT_IN_COLLECTION);
    assertThat(exception.field()).isEqualTo("myField");
    assertThat(exception.parameters()).isEmpty();
    assertThat(exception.getMessage()).isEqualTo("The field \"myField\" contains a null element");
  }
}
