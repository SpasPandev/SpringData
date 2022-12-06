package com.example.advquerying;

import com.example.advquerying.entities.Size;
import com.example.advquerying.services.IngradientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngradientService ingradientService;

    public Main(ShampooService shampooService, IngradientService ingradientService) {

        this.shampooService = shampooService;
        this.ingradientService = ingradientService;
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter exercise number: ");
        int exersiceNum = scanner.nextInt();

        switch (exersiceNum) {
            case 1: exersice1();
                break;
            case 2: exersice2();
                break;
            case 3: exersice3();
                break;
            case 4: exersice4();
                break;
            case 5: exersice5();
                break;
            case 6: exersice6();
                break;
            case 7: exersice7();
                break;
            case 8: exersice8();
                break;
            case 9: exersice9();
                break;
            case 10: exersice10();
                break;
            case 11: exersice11();
                break;
        }

    }

    private void exersice11() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter ingradients names:");
        List<String> inputList = Arrays.asList(scanner.nextLine().split(" "));

        ingradientService.updatePriceOfIngrediantsWhichNamesAreIn(inputList);
    }

    private void exersice10() {

        ingradientService.increasePriceOfAllIngrediantWith10Percents();
    }

    private void exersice9() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter ingradient name: ");

        String name = scanner.nextLine();

        ingradientService.deleteIngradientsByGivenName(name);
    }

    private void exersice8() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number");

        int num = scanner.nextInt();

        shampooService.findAllShampoosWithIngradientsLessThan(num)
                .forEach(System.out::println);
    }

    private void exersice7() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter ingradients names:");
        List<String> inputList = Arrays.asList(scanner.nextLine().split(" "));

        shampooService.findAllShampoosWithIngradientsIncludedIn(inputList)
                .forEach(System.out::println);
    }

    private void exersice6() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter price:");

        BigDecimal inputPrice = scanner.nextBigDecimal();

        System.out.println(shampooService.findCountOfShampoosWithPriceLowerThan(inputPrice));
    }

    private void exersice5() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter starting letters:");
        List<String> inputList = Arrays.asList(scanner.nextLine().split(" "));

        ingradientService.findAllIngradientsContainedInGivenListOrderByPrice(inputList)
                .forEach(System.out::println);

    }

    private void exersice4() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter starting letters:");
        String letters = scanner.nextLine();

        ingradientService.findAllWithLettersStartsWith(letters)
                .forEach(System.out::println);

    }

    private void exersice3() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter price");
        int price = scanner.nextInt();

        shampooService.findAllOrderByPriceDescHigherThan(new BigDecimal(price))
                .forEach(System.out::println);
    }

    private void exersice2() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter one of (SMALL, MEDIUM, LARGE)");
        String inputSize = scanner.nextLine().toUpperCase();

        Size size = Size.valueOf(inputSize);

        System.out.println("Enter label id");

        Long inputLabelId = scanner.nextLong();

        shampooService.findAllBySizeOrLabelIdSortByPriceAsc(size, inputLabelId)
                .forEach(System.out::println);
    }

    private void exersice1() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter one of (SMALL, MEDIUM, LARGE)");
        String inputSize = scanner.nextLine().toUpperCase();

        Size size = Size.valueOf(inputSize);

        shampooService.findBySizeOrderById(size)
                .forEach(System.out::println);
    }


}
