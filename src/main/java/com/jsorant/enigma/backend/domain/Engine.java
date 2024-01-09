package com.jsorant.enigma.backend.domain;

interface Engine {
  String encrypt(String input);

  String decrypt(String input);
}
