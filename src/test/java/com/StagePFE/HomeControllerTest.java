package com.StagePFE;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.StagePFE.Storage.StorageService;
import com.StagePFE.dao.AnnonceRepository;
import com.StagePFE.dao.EntrepreneurRepository;
import com.StagePFE.dao.EtudiantRepository;
import com.StagePFE.entities.Annonce;
import com.StagePFE.entities.Entrepreneur;
import com.StagePFE.entities.Etudiant;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
	@Autowired
	private AnnonceRepository annonceRepository;
	@Autowired
	private EntrepreneurRepository entrepreneurRepository;
	@Autowired
	private EtudiantRepository etudiantRepository;
	@Autowired
	private StorageService storageService;
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void loginRequestTest() throws Exception {
		mockMvc.perform(get("/login")).andExpect(model().attribute("isAuthenticated", equalTo(false)))
				.andExpect(model().attribute("isEntrepreneur", equalTo(false))).andExpect(status().isOk())
				.andExpect(view().name("login"));
	}

	@Test
	@Transactional
	public void annoncesIndexPageTest() throws Exception {
		String motcle = "";
		String lieu = "";
		int page = 0;
		Page<Annonce> annonces = annonceRepository.searchIndexPage("%" + motcle + "%", "%" + lieu + "%",
				PageRequest.of(page, 9));
		mockMvc.perform(get("/index").param("page", "0").param("motcle", "").param("localite", ""))
				.andExpect(model().attribute("annonces", annonces.getContent())).andExpect(status().isOk())
				.andExpect(view().name("index"));
	}

	// teste access of inscription page with the authentication as an "Entrepreneur"
	@WithMockUser(value = "abdeer@outlook.fr", password = "1")
	@Test
	public void InscriptionPageTestEntrepreneurAccess() throws Exception {
		Entrepreneur entrepreneur = entrepreneurRepository.findByEmail("abdeer@outlook.fr").get(0);
		mockMvc.perform(get("/pageInscription")).andExpect(model().attribute("isAuthenticated", equalTo(true)))
				.andExpect(model().attribute("isEntrepreneur", equalTo(true)))
				.andExpect(model().attribute("entrepreneur", equalTo(entrepreneur)))
				.andExpect(model().attribute("etudiant", equalTo(new Etudiant())))
				.andExpect(model().attribute("nvEntrepreneur", equalTo(new Entrepreneur())))
				.andExpect(model().attribute("nvEtudiant", equalTo(new Etudiant()))).andExpect(status().isOk())
				.andExpect(view().name("inscription"));
	}

	// teste access of inscription page with the authentication as an "Etudiant"
	@WithMockUser(value = "mosameh.meryem@Gmail.com", password = "2")
	@Test
	public void InscriptionPageTestEtudiantAccess() throws Exception {
		Etudiant etudiant = etudiantRepository.findByEmail("mosameh.meryem@Gmail.com").get(0);
		mockMvc.perform(get("/pageInscription")).andExpect(model().attribute("isAuthenticated", equalTo(true)))
				.andExpect(model().attribute("isEntrepreneur", equalTo(false)))
				.andExpect(model().attribute("entrepreneur", equalTo(new Entrepreneur())))
				.andExpect(model().attribute("etudiant", equalTo(etudiant)))
				.andExpect(model().attribute("nvEntrepreneur", equalTo(new Entrepreneur())))
				.andExpect(model().attribute("nvEtudiant", equalTo(new Etudiant()))).andExpect(status().isOk())
				.andExpect(view().name("inscription"));
	}

	// testing downloading cv of etudiant from the disque
	@Test
	public void testDownloadFile() throws Exception {
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/profileEtudiant/downloadCv").param("cvId", "1")
						.contentType(MediaType.APPLICATION_OCTET_STREAM))
				.andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
	}

	// teste redirection to "EntrepreneurProfile" for Entrepreneur authenticated
	@WithMockUser(value = "abdeer@outlook.fr", password = "1")
	@Test
	public void ProfileRedirectTest() throws Exception {
		mockMvc.perform(get("/profile")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/entrepreneurProfile"));
	}

}
