package com.dedalow.cad.monolithic.commons.exception;

@SuppressWarnings("serial")
public class CadException extends Exception {

  private boolean force;

  public CadException() {
    super();
  }

  public CadException(boolean force) {
    super();
    this.force = force;
  }

  public CadException(final String errorMessage) {
    super(errorMessage);
  }

  public CadException(final String errorMessage, boolean force) {
    super(errorMessage);
    this.force = force;
  }

  public CadException(final String errorMessage, Throwable cause) {
    super(errorMessage, cause);
  }

  public boolean force() {
    return this.force;
  }
}
