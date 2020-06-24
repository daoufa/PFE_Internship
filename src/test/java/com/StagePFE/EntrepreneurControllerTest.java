package com.StagePFE;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.net.URI;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.StagePFE.dao.AnnonceRepository;
import com.StagePFE.dao.EntrepreneurRepository;
import com.StagePFE.entities.Annonce;
import com.StagePFE.entities.Entrepreneur;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EntrepreneurControllerTest {
	@Autowired
	private AnnonceRepository annonceRepository;
	@Autowired
	private EntrepreneurRepository entrepreneurRepository;
	@Autowired
	private MockMvc mockMvc;
	@LocalServerPort
	int randomServerPort;
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private TestRestTemplate restTemplate1;

	@Test
	public void saveEntrepreneurTest() throws Exception {
		final String baseUrl = "http://localhost:" + randomServerPort + "/inscrireEntrepreneur";
		URI uri = new URI(baseUrl);
		Entrepreneur entrepreneur = new Entrepreneur("test@test", "test", "test", new Date(), null, null, "description",
				"adresse", "00000", "entreprise");

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<Entrepreneur> request = new HttpEntity<>(entrepreneur, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		// Verify request succeed
		assertThat(result.getStatusCodeValue() == 201);

	}

	@Test
	@WithMockUser(value = "abdeer@outlook.fr", password = "1")
	@Transactional
	public void entrepreneurProfileTest() throws Exception {
		int page = 0;
		Entrepreneur entrepreneur = entrepreneurRepository.findByEmail("abdeer@outlook.fr").get(0);
		Page<Annonce> annonces = annonceRepository.findByEntrepreneur(entrepreneur, PageRequest.of(page, 9));
		Page<Annonce> sliderAnnonces = annonceRepository.findByEntrepreneur(entrepreneur, PageRequest.of(0, 4));

		mockMvc.perform(get("/entrepreneurProfile")).andExpect(model().attribute("entrepreneur", equalTo(entrepreneur)))
				.andExpect(model().attribute("annonces", equalTo(annonces.getContent())))
				.andExpect(model().attribute("sliderAnnonces", equalTo(sliderAnnonces.getContent())))
				.andExpect(model().attribute("isAuthenticated", equalTo(true)))
				.andExpect(model().attribute("isEntrepreneur", equalTo(true))).andExpect(status().isOk())
				.andExpect(view().name("profile"));
	}

	@Test
	@WithMockUser(value = "abdeer@outlook.fr", password = "1")
	public void publierAnnonceTest() throws Exception {
		Annonce a = new Annonce(null, null, null, null, "Stage", "description Stage", null, null, true, "marrakech");

		final String baseUrl = "http://localhost:" + randomServerPort + "/publierAnnonce";
		URI uri = new URI(baseUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<Annonce> request = new HttpEntity<>(a, headers);

		ResponseEntity<String> result = this.restTemplate1.postForEntity(uri, request, String.class);

		// Verify request succeed
		assertThat(result.getStatusCodeValue() == 201);

	}
}
