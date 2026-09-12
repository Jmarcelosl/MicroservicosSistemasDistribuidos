package com.ecommerce.shopping_api.service;

import com.ecommerce.shopping_api.converter.DTOConverter;
import com.ecommerce.shopping_api.dto.ShopDTO;
import com.ecommerce.shopping_api.dto.ShopReportDTO;
import com.ecommerce.shopping_api.model.Shop;
import com.ecommerce.shopping_api.repository.ShopRepository;
import com.ecommerce.shopping_api.dto.ItemDTO;
import com.ecommerce.shopping_client.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {
    //private final ShopRepository shopRepository;
    //private final ReportRepository reportRepository;

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;


    public List<ShopDTO> getAll() {
        List<Shop> shops = shopRepository.findAll();
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByUser(String userIdentifier) {
        List<Shop> shops = shopRepository
                .findAllByUserIdentifier(userIdentifier);
        return shops.stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public List<ShopDTO> getByDate(ShopDTO shopDTO) {
        List<Shop> shops = shopRepository
                .findAllByDateGreaterThan(shopDTO.getDate());
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }

    public ShopDTO findById(long ProductId) {
        Optional<Shop> shop = shopRepository
                .findById(ProductId);
        if (shop.isPresent()) {
            return ShopDTO.convert(shop.get());
        }
        return null;
    }

//    public ShopDTO save(ShopDTO shopDTO) {
//        shopDTO.setTotal(shopDTO.getItems()
//                .stream()
//                .map(x -> x.getPrice())
//                .reduce((float) 0, Float::sum));
//
//        Shop shop = Shop.convert(shopDTO);
//        shop.setDate(LocalDateTime.now());
//
//        shop = shopRepository.save(shop);
//        return ShopDTO.convert(shop);
//    }

    public List<ShopDTO> getShopsByFilter( LocalDate dataInicio,
                                           LocalDate dataFim, Float valorMinimo) {


        List<Shop> shops = shopRepository
                .getShopByFilters(dataInicio, dataFim, valorMinimo);
        return shops
                .stream()
                .map(ShopDTO::convert)
                .collect(Collectors.toList());
    }
    public ShopReportDTO getReportByDate(LocalDate dataInicio, LocalDate dataFim) {

        return shopRepository
                .getReportByDate(dataInicio, dataFim);
    }
    // comunicação com os microserviços
    public ShopDTO save(ShopDTO shopDTO) {
        if (userService
                .getUserByCpf(shopDTO.getUserIdentifier()) == null) {
            return null;
        }

        if (!validateProducts(shopDTO.getItems())) {
            return null;
        }

        shopDTO.setTotal(shopDTO.getItems()
                .stream()
                .map(x -> x.getPrice())
                .reduce((float) 0, Float::sum));

        Shop shop = Shop.convert(shopDTO);
        shop.setDate(LocalDateTime.now());

        shop = shopRepository.save(shop);
        return DTOConverter.convert(shop);
    }

    private boolean validateProducts(List<ItemDTO> items) {
        for (ItemDTO item : items) {
            ProductDTO productDTO = productService
                    .getProductByIdentifier( item.getProductIdentifier());
            if (productDTO == null) {
                return false;
            }
            item.setPrice(productDTO.getPreco());
        }
        return true;
    }



}

