package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Post;
import softuni.exam.instagraphlite.models.dto.PostSeedRootDto;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PostServiceImpl implements PostService {

    public static final String POSTS_FILE = "src/main/resources/files/posts.xml";
    private final PostRepository postRepository;
    private final PictureService pictureService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public PostServiceImpl(PostRepository postRepository, PictureService pictureService, UserService userService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.postRepository = postRepository;
        this.pictureService = pictureService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POSTS_FILE));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {

        StringBuilder stringBuilder = new StringBuilder();

        xmlParser.fromFile(POSTS_FILE, PostSeedRootDto.class)
                .getPosts()
                .stream()
                .filter(postSeedDto -> {
                    boolean isValid = validationUtil.isValid(postSeedDto)
                            && pictureService.isEntityExist(postSeedDto.getPicture().getPath())
                            && userService.isEntityExist(postSeedDto.getUser().getUsername());

                    stringBuilder
                            .append(isValid
                                    ? "Successfully imported Post, made by " + postSeedDto.getUser().getUsername()
                                    : "Invalid Post ")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(postSeedDto -> {
                    Post post =modelMapper.map(postSeedDto, Post.class);
                    post.setPicture(pictureService.findByPath(postSeedDto.getPicture().getPath()));
                    post.setUser(userService.findByUsername(postSeedDto.getUser().getUsername()));

                    return post;
                })
                .forEach(postRepository::save);

        return stringBuilder.toString();
    }
}
