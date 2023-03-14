package exam.service.Impl;

import com.google.gson.Gson;
import exam.model.dto.CustomerSeedDto;
import exam.model.entity.Customer;
import exam.repository.CustomerRepository;
import exam.service.CustomerService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMERS_FILE = "src/main/resources/files/json/customers.json";
    private final CustomerRepository customerRepository;
    private final TownService townService;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public CustomerServiceImpl(CustomerRepository customerRepository, TownService townService, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.customerRepository = customerRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMERS_FILE));
    }

    @Override
    public String importCustomers() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        CustomerSeedDto[] customerSeedDtos = gson.fromJson(readCustomersFileContent(), CustomerSeedDto[].class);

        Arrays.stream(customerSeedDtos)
                .filter(customerSeedDto -> {
                    boolean isValid = validationUtil.isValid(customerSeedDto)
                            && !isCustomeEmailExists(customerSeedDto.getEmail())
                            && townService.isTownNameExists(customerSeedDto.getTown().getName());

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Customer %s %s - %s",
                                    customerSeedDto.getFirstName(), customerSeedDto.getLastName(),
                                    customerSeedDto.getEmail())
                                    : "Invalid Customer")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(customerSeedDto -> {
                    Customer customer = modelMapper.map(customerSeedDto, Customer.class);
                    customer.setTown(townService.findTownByName(customerSeedDto.getTown().getName()));

                    return customer;
                })
                .forEach(customerRepository::save);

        return stringBuilder.toString();
    }

    private boolean isCustomeEmailExists(String email) {
        return customerRepository.existsByEmail(email);
    }
}
