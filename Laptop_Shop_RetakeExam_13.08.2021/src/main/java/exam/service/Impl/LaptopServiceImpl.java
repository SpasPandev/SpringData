package exam.service.Impl;

import com.google.gson.Gson;
import exam.model.dto.LaptopSeedDto;
import exam.model.entity.Laptop;
import exam.repository.LaptopRepository;
import exam.service.LaptopService;
import exam.service.ShopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class LaptopServiceImpl implements LaptopService {

    private static final String LAPTOPS_FILE = "src/main/resources/files/json/laptops.json";
    private final LaptopRepository laptopRepository;
    private final ShopService shopService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopService shopService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.laptopRepository = laptopRepository;
        this.shopService = shopService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOPS_FILE));
    }

    @Override
    public String importLaptops() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        LaptopSeedDto[] laptopSeedDtos = gson.fromJson(readLaptopsFileContent(), LaptopSeedDto[].class);

        Arrays.stream(laptopSeedDtos)
                .filter(laptopSeedDto -> {
                    boolean isValid = validationUtil.isValid(laptopSeedDto)
                            && !isLaptopMacAddressExists(laptopSeedDto.getMacAddress())
                            && shopService.isShopNameExists(laptopSeedDto.getShop().getName());

                    stringBuilder
                            .append(isValid
                                    ? String.format("Successfully imported Laptop %s - %.2f - %d - %d",
                                    laptopSeedDto.getMacAddress(), laptopSeedDto.getCpuSpeed(), laptopSeedDto.getRam(),
                                    laptopSeedDto.getStorage())
                                    : "Invalid Laptop")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(laptopSeedDto -> {
                    Laptop laptop = modelMapper.map(laptopSeedDto, Laptop.class);
                    laptop.setShop(shopService.findShopByName(laptopSeedDto.getShop().getName()));

                    return laptop;
                })
                .forEach(laptopRepository::save);

        return stringBuilder.toString();
    }

    private boolean isLaptopMacAddressExists(String macAddress) {
        return laptopRepository.existsByMacAddress(macAddress);
    }

    @Override
    public String exportBestLaptops() {

        StringBuilder stringBuilder = new StringBuilder();

        laptopRepository.findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddress()
                .forEach(laptop -> stringBuilder
                        .append(String.format(
                                """
                                        Laptop - %s
                                        *Cpu speed - %.2f
                                        **Ram - %d
                                        ***Storage - %d
                                        ****Price - %.2f
                                        #Shop name - %s
                                        ##Town - %s
                                        """, laptop.getMacAddress(), laptop.getCpuSpeed(),
                                laptop.getRam(), laptop.getStorage(), laptop.getPrice(),
                                laptop.getShop().getName(), laptop.getShop().getTown().getName()))
                        .append(System.lineSeparator()));

        return stringBuilder.toString();
    }
}
