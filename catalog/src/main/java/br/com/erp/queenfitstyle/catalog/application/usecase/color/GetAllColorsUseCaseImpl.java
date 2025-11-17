package br.com.erp.queenfitstyle.catalog.application.usecase.color;

import br.com.erp.queenfitstyle.catalog.domain.entity.Color;
import br.com.erp.queenfitstyle.catalog.application.port.in.GetAllColorUseCase;
import br.com.erp.queenfitstyle.catalog.application.port.out.ColorRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllColorsUseCaseImpl implements GetAllColorUseCase {

    private final ColorRepositoryPort colorRepository;

    public GetAllColorsUseCaseImpl(ColorRepositoryPort colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public List<Color> execute() {


        return colorRepository.findAll();
    }
}
