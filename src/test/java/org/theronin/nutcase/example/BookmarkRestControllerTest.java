package org.theronin.nutcase.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BookmarkRestControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String username = "bdussault";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Account account;

    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    protected void setConverters(HttpMessageConverter<?>[] converters) {
        mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null", mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        bookmarkRepository.deleteAllInBatch();
        accountRepository.deleteAllInBatch();

        this.account = accountRepository.save(new Account(username, "password"));
        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + username, "A description")));
        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + username, "A description")));
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(post("/george/bookmarks/").content(json(new Bookmark(new Account(), "http://some_uri.com", "Some description"))).contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readSingleBookmark() throws Exception {
        mockMvc.perform(get("/" + username + "/bookmarks/" + bookmarkList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(bookmarkList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.uri", is("http://bookmark.com/1/" + username)))
                .andExpect(jsonPath("$.description", is("A description")));
    }

    @Test
    public void readBookmarks() throws Exception {
        mockMvc.perform(get("/" + username + "/bookmarks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(bookmarkList.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].uri", is(bookmarkList.get(0).getUri())))
                .andExpect(jsonPath("$[0].description", is(bookmarkList.get(0).getDescription())))
                .andExpect(jsonPath("$[1].id", is(bookmarkList.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].uri", is(bookmarkList.get(1).getUri())))
                .andExpect(jsonPath("$[1].description", is(bookmarkList.get(1).getDescription())));
    }

    @Test
    public void createBookmark() throws Exception {
        String bookmarkJson = json(new Bookmark(account, "http://spring.io", "a bookmark"));
        mockMvc.perform(post("/" + username + "/bookmarks").contentType(contentType).content(bookmarkJson))
                .andExpect(status().isCreated());
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}