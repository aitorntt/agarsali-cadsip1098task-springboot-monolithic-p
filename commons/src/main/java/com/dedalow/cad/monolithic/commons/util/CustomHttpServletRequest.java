package com.dedalow.cad.monolithic.commons.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CustomHttpServletRequest extends HttpServletRequestWrapper {

  private Map<String, String> customHeaderMap = null;

  public CustomHttpServletRequest(final HttpServletRequest request) {
    super(request);
    customHeaderMap = new HashMap<>();
  }

  public void addHeader(final String name, final String value) {
    customHeaderMap.put(name, value);
  }

  @Override
  public String getHeader(final String name) {
    String headerValue = super.getHeader(name);
    if (customHeaderMap.containsKey(name)) {
      headerValue = customHeaderMap.get(name);
    }
    return headerValue;
  }

  @Override
  public Enumeration<String> getHeaderNames() {
    final List<String> names = Collections.list(super.getHeaderNames());
    for (final String name : customHeaderMap.keySet()) {
      names.add(name);
    }
    return Collections.enumeration(names);
  }

  @Override
  public Enumeration<String> getHeaders(final String name) {
    final List<String> values = Collections.list(super.getHeaders(name));
    if (customHeaderMap.containsKey(name)) {
      values.add(customHeaderMap.get(name));
    }
    return Collections.enumeration(values);
  }
}
