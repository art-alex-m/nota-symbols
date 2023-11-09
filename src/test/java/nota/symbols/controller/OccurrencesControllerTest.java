package nota.symbols.controller;

import nota.symbols.service.OccurrencesService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OccurrencesController.class)
class OccurrencesControllerTest {

    private final String uri = "/occurrence";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OccurrencesService occurrencesService;

    @Test
    void whenValidData_thenOkStatus() throws Exception {
        String content = "aaaa";

        mockMvc.perform(post(uri).content(content).contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk());
        Mockito.verify(occurrencesService, Mockito.times(1)).compute(content);
    }

    @Test
    void whenInvalidData_thenBadRequestStatus() throws Exception {
        mockMvc.perform(post(uri).contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(is(not(emptyOrNullString()))));
        Mockito.verify(occurrencesService, Mockito.never()).compute(Mockito.anyString());
    }
}