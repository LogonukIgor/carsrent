package by.logonuk;

import by.logonuk.domain.User;
import by.logonuk.service.user.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext("by.logonuk");

        UserService userService = annotationConfigApplicationContext.getBean(UserService.class);

//findAll
//        List<User> all = userService.findAll();
//
//        for (User user : all) {
//            System.out.println(user);
//        }
//        System.out.println();

//update, create
//        User user = new User();
//        user.setUserName("Andrey");
//        user.setSurname("Dobri");
//        user.setCreationDate(new Timestamp(new Date().getTime()));
//        user.setModificationDate(new Timestamp(new Date().getTime()));
//        user.setIsDeleted(false);
//        user.setLogin("a@mail.ru");
//        user.setPassword("1234");
//        user.setLicenceId(5L);
//
//        System.out.println(user);
//        User user1 = userService.create(user);
//        System.out.println(user1);
//
//        user.setSurname("Dobriyy");
//        user.setModificationDate(new Timestamp(new Date().getTime()));
//        user.setLogin("an123@mail.ru");
//        user.setPassword("123467889854");
//        user.setId(user1.getId());
//
//        User user2 = userService.update(user);
//        System.out.println(user2);

    }
}
