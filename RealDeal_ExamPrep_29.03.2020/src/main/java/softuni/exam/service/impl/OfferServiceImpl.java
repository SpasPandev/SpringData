package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.dto.OfferSeedDto;
import softuni.exam.models.entity.dto.OfferSeedRootDto;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OfferServiceImpl implements OfferService {

    private static final String XML_OFFERS_FILE_PATH = "src/main/resources/files/xml/offers.xml";
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {

        return Files.readString(Path.of(XML_OFFERS_FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {

        StringBuilder stringBuilder = new StringBuilder();

        xmlParser.fromFile(XML_OFFERS_FILE_PATH, OfferSeedRootDto.class)
                .getOffers()
                .stream()
                .filter(offerSeedDto -> {
                    boolean isValid = validationUtil.isValid(offerSeedDto);

                    stringBuilder.append(isValid ? String.format(
                                    "Successfully import offer %s - %s", offerSeedDto.getAddedOn(), offerSeedDto.getHasGoldStatus())
                                    : "Invalid offer")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(offerSeedDto -> modelMapper.map(offerSeedDto, Offer.class))
                .forEach(offerRepository::save);

        return stringBuilder.toString();
    }
}
