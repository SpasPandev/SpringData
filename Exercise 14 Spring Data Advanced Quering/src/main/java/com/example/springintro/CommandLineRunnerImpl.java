package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.enums.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        //printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        pritnALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of exercise: ");
        int exNum = scanner.nextInt();

        switch (exNum) {

            case 1: exercise1();
                break;
            case 2: exercise2();
                break;
            case 3: exercise3();
                break;
            case 4: exercise4();
                break;
            case 5: exercise5();
                break;
            case 6: exercise6();
                break;
            case 7: exercise7();
                break;
            case 8: exercise8();
                break;
            case 9: exercise9();
                break;
            case 10: exercise10();
                break;
            case 11: exercise11();
                break;
            case 12: exercise12();
                break;
            case 13: exercise13();
                break;
            case 14: exercise14();
                break;
        }

    }

    private void exercise14() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter firstName: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter lastName: ");
        String lastName = scanner.nextLine();

        System.out.println(bookService.procedureGetNumberOfBooksWrittenByAuthorWithFNameAndLName(firstName, lastName));
    }

    private void exercise13() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number: ");

        int number = scanner.nextInt();

        System.out.println(bookService.removeBooksWithCopiesLessThan(number));
    }

    private void exercise12() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter date in format 'dd MMM yyyy': ");

        DateTimeFormatter df = new DateTimeFormatterBuilder()
                .appendPattern("dd MMM yyyy")
                .toFormatter(Locale.ENGLISH);

        LocalDate date = LocalDate.parse(scanner.nextLine(), df);

        System.out.println("Enter number: ");
        int number = scanner.nextInt();

        System.out.println(bookService.updateCopiesOfAllBooksreleasedAfterGivenDateWithGivenNumber(date, number));

    }

    private void exercise11() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter book title: ");

        String title = scanner.nextLine();

        bookService.findInformationForBookByGivenTitle(title)
                .forEach(System.out::println);

    }

    private void exercise10() {

        authorService.findTotalNumberOfBookCopiesByAuthor()
                .forEach(System.out::println);
    }

    private void exercise9() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number: ");

        int number = scanner.nextInt();

        System.out.println(bookService.findCountOfBooksWhoseTitelLenghtMoreThan(number));

    }

    private void exercise8() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string: ");

        String startWith = scanner.nextLine();

        bookService.findTitelsOfBooksWhoseAuthorsLastNameStartsWith(startWith)
                .forEach(System.out::println);
    }

    private void exercise7() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter string: ");

        String string = scanner.nextLine();

        bookService.findTitelOfBooksWhichTitelContains(string)
                .forEach(System.out::println);
    }

    private void exercise6() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter authors ending of first name: ");

        String endingString = scanner.nextLine();

        authorService.findAuthorsWhoseFirstNameEndsWith(endingString)
                .forEach(System.out::println);
    }

    private void exercise5() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter date: ");

        String[] dateInput = scanner.nextLine().split("-");

        int date = Integer.parseInt(dateInput[0]);
        int month = Integer.parseInt(dateInput[1]);
        int year = Integer.parseInt(dateInput[2]);

        bookService.findTitelsEditionTypeAndPriceOfBooksReleasedBefore(LocalDate.of(year, month, date))
                .forEach(System.out::println);
    }

    private void exercise4() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter year: ");

        int year = scanner.nextInt();

        bookService.findTitelsOfBooksThatAreNotReleasedInYear(year)
                .forEach(System.out::println);
    }

    private void exercise3() {

        bookService.findBooksTitelsAndPriceWithPriceLowerThanAndHigherThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .forEach(System.out::println);
    }

    private void exercise2() {

        bookService.findBookTitelsByEditionTypeAndNumberOfCopies(EditionType.GOLD, 5000)
                .forEach(System.out::println);
    }

    private void exercise1() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter one of the age restrictions (MINOR, TEEN, ADULT) : ");

        String ageRestrictionType = scanner.nextLine().toUpperCase();

        bookService.findBooksTitelsByAgeRestrictionMatchinWith(ageRestrictionType)
                .forEach(System.out::println);
    }

    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
