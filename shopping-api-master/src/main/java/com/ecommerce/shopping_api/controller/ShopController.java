package com.ecommerce.shopping_api.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shopping_api.dto.ShopDTO;
import com.ecommerce.shopping_api.dto.ShopReportDTO;
import com.ecommerce.shopping_api.service.ShopService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;
    @GetMapping("/shopping")
    public List<ShopDTO> getShops() {
        return shopService.getAll();
    }

    @GetMapping("/shopping/shopByUser/{userIdentifier}")
    public List<ShopDTO> getShops(@PathVariable String userIdentifier) {
        return shopService.getByUser(userIdentifier);
    }

    @GetMapping("/shopping/shopByDate")
    public List<ShopDTO> getShopsByDate(
            @Parameter(description = "Data de corte no formato ISO-8601", example = "2020-01-01T00:00:00")
            @RequestParam(name = "date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setDate(date);
        return shopService.getByDate(shopDTO);
    }

    @GetMapping("/shopping/{id}")
    public ShopDTO findById(@PathVariable Long id) {
        return shopService.findById(id);
    }

    @PostMapping("/shopping")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registra uma compra", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "userIdentifier": "12345678909",
                      "items": [
                        { "productIdentifier": "cafe-origem-serra-250g", "price": 34.90 },
                        { "productIdentifier": "cha-verde-jardim-20sache", "price": 34.90 }
                      ]
                    }
                    """))))
    public ShopDTO newShop(@Valid @RequestBody ShopDTO shopDTO) {
        return shopService.save(shopDTO);
    }

    @GetMapping("/shopping/search")
    public List<ShopDTO> getShopsByFilter(
            @Parameter(description = "Data inicial no formato dd/MM/yyyy", example = "01/09/2026")
            @RequestParam(name = "dataInicio", required=true)
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
            @Parameter(description = "Data final no formato dd/MM/yyyy", example = "30/09/2026")
            @RequestParam(name = "dataFim", required=false)
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFim ,
            @Parameter(description = "Valor mínimo da compra", example = "50.00")
            @RequestParam(name = "valorMinimo", required=false) Float valorMinimo) {
        return shopService.getShopsByFilter(dataInicio, dataFim, valorMinimo);
    }

    @GetMapping("/shopping/report")
    public ShopReportDTO getReportByDate(
            @Parameter(description = "Data inicial no formato dd/MM/yyyy", example = "01/09/2026")
            @RequestParam(name = "dataInicio", required=true)
            @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicio,
            @Parameter(description = "Data final no formato dd/MM/yyyy", example = "30/09/2026")
            @RequestParam(name = "dataFim", required=true) @DateTimeFormat(pattern = "dd/MM/yyyy")
                    LocalDate dataFim ){
        return shopService.getReportByDate(dataInicio, dataFim);
    }


}
