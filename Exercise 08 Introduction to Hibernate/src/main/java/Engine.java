import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Engine implements Runnable{

    private final EntityManager entityManager;

    private final BufferedReader bufferedReader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {

        System.out.println("Please enter number of exercise: ");

        try {
            int exercise = Integer.parseInt(bufferedReader.readLine());

            switch (exercise) {
                case 2 -> exerciseTwo();
                case 3 -> exerciseThree();
                case 4 -> exerciseFour();
                case 5 -> exerciseFive();
                case 6 -> exerciseSix();
                case 7 -> exerciseSeven();
                case 8 -> exerciseEight();
                case 9 -> exerciseNine();
                case 10 -> exerciseTen();
                case 11 -> exerciseEleven();
                case 12 -> exerciseTwelve();
                case 13 -> exerciseThirteen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exerciseTwo()
    {
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("UPDATE Town t " +
                "SET t.name = upper(t.name) " +
                "WHERE length(t.name) <= 5 ");

        query.executeUpdate();

        entityManager.getTransaction().commit();
    }

    private void exerciseThree() throws IOException {

        String[] name = bufferedReader.readLine().split("\\s+");
        String firstName = name[0];
        String lastName = name[1];

        Long result = entityManager.createQuery("SELECT COUNT (e) FROM Employee e " +
                "WHERE e.firstName = :fName AND e.lastName = :lName ", Long.class)
                .setParameter("fName", firstName)
                .setParameter("lName", lastName)
                .getSingleResult();

        System.out.println(result > 0 ? "YES" : "NO");
    }

    private void exerciseFour() {

        List<String> allEmployeesWithSalaryMoreThan = entityManager.createQuery("SELECT e.firstName FROM Employee e " +
                "WHERE e.salary > 50000").getResultList();

        for (String employee : allEmployeesWithSalaryMoreThan) {
            System.out.println(employee);
        }
    }

    private void exerciseFive() {

         entityManager.createQuery("SELECT e FROM Employee e " +
                "where e.department.name = 'Research and Development' " +
                "order by e.salary, e.id", Employee.class)
                 .getResultStream()
                 .forEach(element -> System.out.printf("%s %s from %s - %.2f \n", element.getFirstName(),
                         element.getLastName(), element.getDepartment().getName(), element.getSalary()));

    }

    private void exerciseSix() throws IOException {

        String name = bufferedReader.readLine();

        Address address = new Address();
        address.setText("Vitoshka 15");

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        Employee employee = entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.lastName = :name", Employee.class)
                .setParameter("name", name)
                .getSingleResult();

        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();

    }

    private void exerciseSeven() {

        List<Address> addressList = entityManager.createQuery("SELECT a FROM Address a " +
                "ORDER BY a.employees.size DESC ", Address.class)
                .setMaxResults(10)
                .getResultList();


                addressList.forEach(a -> System.out.printf("%s, %s - %d employees \n", a.getText(),
                        a.getTown().getName(), a.getEmployees().size()));

    }

    private void exerciseEight() throws IOException {

        System.out.println("Pleese Enter id of employee: ");
        int employeeId = Integer.parseInt(bufferedReader.readLine());

        Employee employee = entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.id = :employeeId", Employee.class)
                .setParameter("employeeId", employeeId)
                .getSingleResult();

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append(employee.getFirstName())
                        .append(" ")
                                .append(employee.getLastName())
                                        .append(" - ")
                                                .append(employee.getJobTitle())
                                                        .append("\n\t");

        employee
                .getProjects()
                .stream()
                .map(Project::getName)
                .sorted()
                .forEach(p -> stringBuilder.append(p).append("\n\t"));

        System.out.println(stringBuilder);
    }

    private void exerciseNine() {

        List<Project> result = entityManager.createQuery("SELECT p FROM Project p " +
                "ORDER BY p.startDate DESC ", Project.class)
                .setMaxResults(10)
                .getResultList();

        result.stream().sorted(Comparator.comparing(Project::getName)).forEach(project -> System.out.printf("Project name: %s\n" +
                "\tProject Description:%s\n" +
                "\tProject Start Date:%s\n" +
                "\tProject End Date:%s\n", project.getName(), project.getDescription(),
                project.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")),
                project.getEndDate()));
    }

    private void exerciseTen() {

        entityManager.getTransaction().begin();

        entityManager.createQuery("UPDATE Employee e " +
                "SET e.salary = e.salary +(e.salary * 0.12)" +
                "WHERE e.department.id IN :departmentId")
                .setParameter("departmentId", Arrays.asList(1, 2, 4, 11))
                .executeUpdate();

        entityManager.getTransaction().commit();

        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.id IN :departmentId", Employee.class)
                .setParameter("departmentId", Arrays.asList(1, 2, 4, 11))
                .getResultStream()
                .forEach(result -> System.out.printf("%s %s (%.2f)\n", result.getFirstName(), result.getLastName(), result.getSalary()));

    }

    private void exerciseEleven() throws IOException {

        System.out.println("Enter a pattern: ");

        String pattern = bufferedReader.readLine();

        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.firstName LIKE :pattern", Employee.class)
                .setParameter("pattern", pattern + "%")
                .getResultList()
                .forEach(result -> System.out.printf("%s %s - %s (%.2f)\n",
                        result.getFirstName(), result.getLastName(), result.getJobTitle(), result.getSalary()));
    }

    private void exerciseTwelve() {

        List<Object[]> departmentRows =
                entityManager.createNativeQuery("SELECT d.name, MAX(e.salary) AS maxSalary FROM employees e " +
                        "JOIN departments d on e.department_id = d.department_id " +
                        "GROUP BY e.department_id " +
                        "HAVING maxSalary NOT BETWEEN 30000 AND 70000")
                .getResultList();

                departmentRows.forEach(result -> System.out.printf("%s %.2f \n", result[0], result[1]));
    }

    private void exerciseThirteen() throws IOException {

        System.out.println("Enter town name: ");

        String townName = bufferedReader.readLine();

        Town town = entityManager.createQuery("SELECT t FROM Town t " +
                "WHERE t.name LIKE :townName", Town.class)
                .setParameter("townName", townName)
                .getSingleResult();

        List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a " +
                "WHERE a.town.name LIKE :townName", Address.class)
                .setParameter("townName", townName)
                .getResultList();

        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e " +
                        "WHERE e.address.town.name LIKE :townName", Employee.class)
                .setParameter("townName", townName)
                .getResultList();

        entityManager.getTransaction().begin();

        employees.forEach(employee -> {
            entityManager.detach(employee);
            employee.setAddress(null);
            entityManager.merge(employee);
        });

        entityManager.remove(town);
        addresses.forEach(entityManager::remove);

        entityManager.getTransaction().commit();

        System.out.printf("%d address in %s deleted", addresses.size(), town.getName());
    }
}
