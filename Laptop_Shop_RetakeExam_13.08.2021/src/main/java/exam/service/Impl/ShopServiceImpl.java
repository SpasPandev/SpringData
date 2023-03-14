package exam.service.Impl;

import exam.model.dto.ShopSeedRootDto;
import exam.model.entity.Shop;
import exam.repository.ShopRepository;
import exam.service.ShopService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ShopServiceImpl implements ShopService {

    private static final String SHOPS_FILE = "src/main/resources/files/xml/shops.xml";
    private final ShopRepository shopRepository;
    private final TownService townService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public ShopServiceImpl(ShopRepository shopRepository, TownService townService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.shopRepository = shopRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(Path.of(SHOPS_FILE));
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {

        StringBuilder stringBuilder = new StringBuilder();

        xmlParser.fromFile(SHOPS_FILE, ShopSeedRootDto.class)
                .getShops()
                .stream()
                .filter(shopSeedDto -> {
                    boolean isValid = validationUtil.isValid(shopSeedDto)
                            && !isShopNameExists(shopSeedDto.getName())
                            && townService.isTownNameExists(shopSeedDto.getTown().getName());

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Shop %s - %.0f",
                                    shopSeedDto.getName(), shopSeedDto.getIncome())
                                    : "Invalid shop")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(shopSeedDto -> {
                    Shop shop = modelMapper.map(shopSeedDto, Shop.class);
                    shop.setTown(townService.findTownByName(shop.getTown().getName()));

                    return shop;
                })
                .forEach(shopRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public boolean isShopNameExists(String name) {

        return shopRepository.existsByName(name);
    }

    @Override
    public Shop findShopByName(String name) {
        return shopRepository.findShopByName(name);
    }
}
