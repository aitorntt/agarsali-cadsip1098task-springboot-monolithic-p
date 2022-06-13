package com.dedalow.cad.monolithic.commons.util;

import com.dedalow.cad.monolithic.commons.dto.PageableAndSorting;
import java.util.Objects;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@UtilityClass
public class PageableUtil {

  public Pageable buildPageable(PageableAndSorting request) {
    if (Objects.nonNull(request.getOrderBy())) {
      return PageRequest.of(
          Optional.ofNullable(request.getPage()).orElse(0),
          Optional.ofNullable(request.getSize()).orElse(Integer.MAX_VALUE),
          retrieveSort(request));
    }
    return PageRequest.of(
        Optional.ofNullable(request.getPage()).orElse(0),
        Optional.ofNullable(request.getSize()).orElse(Integer.MAX_VALUE));
  }

  private Sort retrieveSort(PageableAndSorting request) {
    return Sort.by(
        Objects.nonNull(request.getOrderDirection()) ? request.getOrderDirection() : Direction.ASC,
        request.getOrderBy());
  }
}
