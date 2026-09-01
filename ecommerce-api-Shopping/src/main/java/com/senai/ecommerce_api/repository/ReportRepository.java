package com.senai.ecommerce_api.repository;

import java.time.LocalDate;
import java.util.List;

import com.senai.ecommerce_api.dto.ShopReportDTO;
import com.senai.ecommerce_api.model.Shop;

public interface ReportRepository {

    List<Shop> getShopByFilters(LocalDate dataInicio, LocalDate dataFim, Float valorMinimo);

    ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim);
}
