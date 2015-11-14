package org.theronin.nutcase.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

/**
 *
 */
@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkRestController {
    private final BookmarkRepository bookmarkRepository;
    private final AccountRepository accountRepository;

    @Autowired
    protected BookmarkRestController(BookmarkRepository bookmarkRepository, AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
        validateUser(userId);
        return this.accountRepository.findByUsername(userId)
                .map(account -> {
                    Bookmark result = bookmarkRepository.save(new Bookmark(account, input.getUri(), input.getDescription()));
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri());

                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();
    }

    @RequestMapping(value = "/{bookmarkId}", method = RequestMethod.GET)
    protected Bookmark readBookmark(@PathVariable String userId, @PathVariable Long bookmarkId) {
        validateUser(userId);
        return bookmarkRepository.findOne(bookmarkId);
    }

    @RequestMapping(method = RequestMethod.GET)
    protected Collection<Bookmark> readBookmarks(@PathVariable String userId) {
        validateUser(userId);
        return bookmarkRepository.findByAccountUsername(userId);
    }

    private void validateUser(String userId) {
        accountRepository.findByUsername(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}

class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("could not find user '" + userId + "'.");
    }
}
