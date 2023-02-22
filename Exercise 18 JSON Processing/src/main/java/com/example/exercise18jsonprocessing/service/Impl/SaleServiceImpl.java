package com.example.exercise18jsonprocessing.service.Impl;

import com.example.exercise18jsonprocessing.model.dto.SalesInfoAboutCarDto;
import com.example.exercise18jsonprocessing.model.entity.Sale;
import com.example.exercise18jsonprocessing.repository.SaleRepository;
import com.example.exercise18jsonprocessing.service.CarService;
import com.example.exercise18jsonprocessing.service.CustomerService;
import com.example.exercise18jsonprocessing.service.SaleService;
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
    public void seedSale() {

        if (saleRepository.count() > 0) {
            return;
        }

        for (int i = 0; i < 19; i++) {

            Sale sale = new Sale();

            sale.setCar(carService.getRandomCar());
            sale.setCustomer(customerService.getRandomCustomer());
            sale.setDiscount(getRandomDiscount());

            saleRepository.save(sale);
        }
    }

    @Override
    public List<SalesInfoAboutCarDto> findAllSales() {

        return saleRepository.findAll()
                .stream()
                .map(sale -> {
                    SalesInfoAboutCarDto salesInfoAboutCarDto = modelMapper.map(sale, SalesInfoAboutCarDto.class);

                    if (sale.getCustomer().isYoungDriver()) {
                        salesInfoAboutCarDto.setDiscount(salesInfoAboutCarDto.getDiscount() + 0.05);
                    }

                    double price = sale.getCar().getParts().stream()
                            .mapToDouble(part -> Double.parseDouble(String.valueOf(part.getPrice())))
                            .sum();

                    double priceWithDiscount = price - (price * salesInfoAboutCarDto.getDiscount());

                    salesInfoAboutCarDto.setPrice(BigDecimal.valueOf(price));
                    salesInfoAboutCarDto.setPriceWithDiscount(BigDecimal.valueOf(priceWithDiscount));

                    return salesInfoAboutCarDto;
                })
                .collect(Collectors.toList());
    }

    private double getRandomDiscount() {

        var o = DISCONTS.length;

        int randomIndex = ThreadLocalRandom.current().nextInt(0, DISCONTS.length);

        return DISCONTS[randomIndex];
    }
}
