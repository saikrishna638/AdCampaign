package unitTest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import controller.AdController;
import model.AdCampaign;
import service.AdService;

@RunWith(SpringRunner.class)

@WebMvcTest(AdController.class)

public class AdControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private AdService adService;
    private JacksonTester < AdCampaign > jsonTester;
    private AdCampaign adCampaign;
    @Before
    public void setup() {
        JacksonTester.initFields(this, objectMapper);
        adCampaign = new AdCampaign();
    }
    @Test
    public void persistPerson_IsValid_PersonPersisted() throws Exception {
        final String personDTOJson = jsonTester.write(adCampaign).getJson();
        given(adService.addNewAd(any(AdCampaign.class))).willReturn(adCampaign);
        mockMvc
            .perform(post("/persistPerson").content(personDTOJson).contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated());
        verify(adService).addNewAd(any(AdCampaign.class));
    }
    @Test
    public void persistPerson_IsNotValid_PersonNotPersisted() throws Exception {
        final String personDTOJson = jsonTester.write(adCampaign).getJson();
        given(adService.addNewAd(any(AdCampaign.class))).willReturn(null);
        mockMvc
            .perform(post("/persistPerson").content(personDTOJson).contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isIAmATeapot());
        verify(adService, times(0)).addNewAd(any(AdCampaign.class));
    }
}