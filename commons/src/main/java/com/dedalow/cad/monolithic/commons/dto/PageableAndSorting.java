package com.dedalow.cad.monolithic.commons.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@Builder
public class PageableAndSorting {

  private Integer page;
  private Integer size;
  private Sort.Direction orderDirection;
  private String orderBy;
}
