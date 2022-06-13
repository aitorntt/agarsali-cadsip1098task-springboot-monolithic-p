package com.dedalow.cad.monolithic.commons.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncodeService {

  @Autowired PasswordEncoder passwordEncoder;

  public String encode(String pass) {
    return passwordEncoder.encode(pass);
  }

  public boolean verifyPassword(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }
}
