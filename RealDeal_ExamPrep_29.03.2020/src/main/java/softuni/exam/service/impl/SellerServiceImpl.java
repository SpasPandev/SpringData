package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Seller;
import softuni.exam.models.entity.dto.SellerSeedDto;
import softuni.exam.models.entity.dto.SellerSeedRootDto;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SellerServiceImpl implements SellerService {

    private static final String XML_SELLERS_FILE_PATH = "src/main/resources/files/xml/sellers.xml";
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {

        return Files.readString(Path.of(XML_SELLERS_FILE_PATH));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {

        StringBuilder stringBuilder = new StringBuilder();

        xmlParser.fromFile(XML_SELLERS_FILE_PATH, SellerSeedRootDto.class)
                .getSellers()
                .stream()
                .filter(sellerSeedDto -> {
                    boolean isValid = validationUtil.isValid(sellerSeedDto);

                    stringBuilder.append(isValid ? String.format("Successfully import seller %s - %s",
                            sellerSeedDto.getFirstName(), sellerSeedDto.getEmail())
                            : "Invalid seller")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(sellerSeedDto -> modelMapper.map(sellerSeedDto, Seller.class))
                .forEach(sellerRepository::save);

        return stringBuilder.toString();
    }
}
