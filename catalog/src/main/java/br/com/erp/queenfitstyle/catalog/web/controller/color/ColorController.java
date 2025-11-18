package br.com.erp.queenfitstyle.catalog.web.controller.color;

import br.com.erp.queenfitstyle.catalog.domain.entity.Color;
import br.com.erp.queenfitstyle.catalog.application.port.in.GetAllColorUseCase;
import br.com.erp.queenfitstyle.catalog.web.dto.color.ColorDetailsDTO;
import br.com.erp.queenfitstyle.catalog.web.dto.color.ColorsDetailsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/colors")
public class ColorController {

    private final GetAllColorUseCase getAllColorUseCase;

    public ColorController(GetAllColorUseCase getColorUseCase, GetAllColorUseCase getAllColorUseCase) {
        this.getAllColorUseCase = getAllColorUseCase;
    }


    @GetMapping
    public ResponseEntity<ColorsDetailsDTO> getAllColors() {

        List<Color> colors = getAllColorUseCase.execute();

        List<ColorDetailsDTO> colorDetails = colors
                .stream()
                .map(c-> new ColorDetailsDTO(
                        c.getId(),
                        c.getDisplayName(),
                        c.getNormalizedName(),
                        c.getHexCode(),
                        c.isActive()))
                .toList();

        ColorsDetailsDTO response = new ColorsDetailsDTO(colorDetails);

        return ResponseEntity.ok(response);

    }
}
