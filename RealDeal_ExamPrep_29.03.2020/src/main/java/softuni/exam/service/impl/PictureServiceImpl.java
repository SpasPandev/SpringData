package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.entity.Picture;
import softuni.exam.models.entity.dto.PictureSeedDto;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String JSON_PICTURES_FILE_PATH = "src/main/resources/files/json/pictures.json";
    private final PictureRepository pictureRepository;
    private final CarService carService;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, CarService carService, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.carService = carService;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {

        return pictureRepository.count() > 0 ;
    }

    @Override
    public String readPicturesFromFile() throws IOException {

        return Files.readString(Path.of(JSON_PICTURES_FILE_PATH));
    }

    @Override
    public String importPictures() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readPicturesFromFile(), PictureSeedDto[].class))
                .filter(pictureSeedDto -> {
                    boolean isValid = validationUtil.isValid(pictureSeedDto);

                    stringBuilder.append(isValid
                            ? String.format("Successfully import picture - %s", pictureSeedDto.getName())
                            : "Invalid picture")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(pictureSeedDto -> {
                    Picture picture = modelMapper.map(pictureSeedDto, Picture.class);
                    picture.setCar(carService.findCarById(pictureSeedDto.getCar()));

                    return picture;
                })
                .forEach(pictureRepository::save);

        return stringBuilder.toString();
    }
}
