package com.ecommerce.shopping_api.repository;

import com.ecommerce.shopping_api.dto.ShopReportDTO;
import com.ecommerce.shopping_api.model.Shop;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository {
    public List<Shop> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo);

    public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim);

}

