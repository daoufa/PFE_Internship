package com.StagePFE;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.StagePFE.dao.AnnonceRepository;
import com.StagePFE.entities.Annonce;
import com.StagePFE.entities.Etudiant;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EtudiantControllerTest {
	@Autowired
	private AnnonceRepository annonceRepository;
	@Autowired
	private MockMvc mockMvc;
	@LocalServerPort
	int randomServerPort;
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void saveEtudiantTest() throws Exception {
		final String baseUrl = "http://localhost:" + randomServerPort + "/inscrireEtudiant";
		URI uri = new URI(baseUrl);
		Etudiant etudiant = new Etudiant("test@test", "test", "test", new Date(), null, null, "description", "adresse",
				"00000", null);
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");

		HttpEntity<Etudiant> request = new HttpEntity<>(etudiant, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		// Verify request succeed
		assertThat(result.getStatusCodeValue() == 201);

	}

	@WithMockUser(value = "daoufa@mail.com", password = "2", roles = "ETUDIANT")
	@Test
	@Transactional
	public void postulerTest() throws Exception {
		mockMvc.perform(get("/postuler").param("annonce", "2")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/index?currentPage=0&motcle=&localite="));
		Annonce a = new Annonce();
		// get annonce
		Optional<Annonce> result = annonceRepository.findById(1l);
		if (result.isPresent()) {
			a = result.get();
		}

		assertThat(a.getEtudiantAnnonces().get(0).getEtudiant().getEmail().equals("daoufa@mail.com"));
	}

}
