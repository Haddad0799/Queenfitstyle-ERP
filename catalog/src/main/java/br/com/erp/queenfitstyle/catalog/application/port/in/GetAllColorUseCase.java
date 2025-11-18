package br.com.erp.queenfitstyle.catalog.application.port.in;

import br.com.erp.queenfitstyle.catalog.domain.entity.Color;

import java.util.List;

public interface GetAllColorUseCase {

    List<Color> execute();
}
