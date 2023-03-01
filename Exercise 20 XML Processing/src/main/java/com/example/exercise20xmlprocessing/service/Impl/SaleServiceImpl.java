package com.example.exercise20xmlprocessing.service.Impl;

import com.example.exercise20xmlprocessing.model.dto.SalesInfoDto;
import com.example.exercise20xmlprocessing.model.dto.SalesInfoRootDto;
import com.example.exercise20xmlprocessing.model.entity.Sale;
import com.example.exercise20xmlprocessing.repository.SaleRepository;
import com.example.exercise20xmlprocessing.service.CarService;
import com.example.exercise20xmlprocessing.service.CustomerService;
import com.example.exercise20xmlprocessing.service.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final static double[] DISCONTS = new double[]{0, 0.05, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5};
    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository, CarService carService, CustomerService customerService, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSales() {

        if (saleRepository.count() > 0) {
            return;
        }

        for (int i = 0; i < 20; i++) {

            Sale sale = new Sale();

            sale.setCar(carService.getRandomCar());
            sale.setCustomer(customerService.getRandomCustomer());
            sale.setDiscount(getRandomDiscount());

            saleRepository.save(sale);
        }
    }

    @Override
    public SalesInfoRootDto findAllSales() {

        List<Sale> sales = saleRepository.findAll();

        SalesInfoRootDto salesInfoRootDto = new SalesInfoRootDto();

        salesInfoRootDto.setSales(sales
                .stream()
                .map(sale -> {
                    SalesInfoDto dto = modelMapper.map(sale, SalesInfoDto.class);

                    double price = sale.getCar().getParts()
                            .stream()
                            .mapToDouble(part -> Double.parseDouble(String.valueOf(part.getPrice())))
                            .sum();

                    dto.setPrice(new BigDecimal(price));

                    if (sale.getCustomer().getYoungDriver()) {
                        dto.setDiscount(dto.getDiscount() + 0.05);
                    }

                    double priceWithDiscount = price - (price * dto.getDiscount());

                    dto.setPriceWithDiscount(new BigDecimal(priceWithDiscount));

                    return dto;
                })
                .collect(Collectors.toList()));

        return salesInfoRootDto;
    }

    private Double getRandomDiscount() {

        int randomIndex = ThreadLocalRandom.current().nextInt(0, DISCONTS.length);

        return DISCONTS[randomIndex];
    }
}
