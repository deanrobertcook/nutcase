package org.theronin.nutcase.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public CommandLineRunner bookmarks(AccountRepository accountRepository, BookmarkRepository bookmarkRepository) {
//        return args -> {
//            accountRepository.deleteAll();
//            bookmarkRepository.deleteAll();
//
//            Arrays.asList("jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
//                    .forEach(username -> {
//                        Account account = accountRepository.save(new Account(username, "password"));
//                        bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + username, "A description"));
//                        bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + username, "B description"));
//                    });
//        };
//    }
}
