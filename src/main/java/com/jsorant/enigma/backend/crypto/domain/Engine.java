package com.jsorant.enigma.backend.crypto.domain;

interface Engine {
  String encrypt(String input);

  String decrypt(String input);
}
