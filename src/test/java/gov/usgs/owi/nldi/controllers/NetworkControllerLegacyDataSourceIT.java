package gov.usgs.owi.nldi.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONObjectAs;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import gov.usgs.owi.nldi.BaseIT;
import gov.usgs.owi.nldi.dao.LogDao;
import gov.usgs.owi.nldi.dao.LookupDao;
import gov.usgs.owi.nldi.dao.NavigationDao;
import gov.usgs.owi.nldi.dao.StreamingDao;
import gov.usgs.owi.nldi.services.ConfigurationService;
import gov.usgs.owi.nldi.services.LogService;
import gov.usgs.owi.nldi.services.Navigation;
import gov.usgs.owi.nldi.services.Parameters;
import gov.usgs.owi.nldi.springinit.DbTestConfig;
import gov.usgs.owi.nldi.springinit.SpringConfig;
import gov.usgs.owi.nldi.transform.FeatureTransformer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK,
		classes={DbTestConfig.class, SpringConfig.class, 
		NetworkController.class, LookupDao.class, StreamingDao.class,
		Navigation.class, NavigationDao.class, Parameters.class, 
		ConfigurationService.class, LogService.class, LogDao.class})
@DatabaseSetup("classpath:/testData/crawlerSource.xml")
@DatabaseSetup("classpath:/testData/featureWqp.xml")
public class NetworkControllerLegacyDataSourceIT extends BaseIT {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private static final String RESULT_FOLDER  = "network/feature/wqp/";

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	//UT Testing
	@Test
	public void getComidUtTest() throws Exception {
		MvcResult rtn = mockMvc.perform(get("/api/comid/13293474/navigate/UT/wqp?legacy=true"))
				.andExpect(status().isOk())
				.andExpect(header().string(FeatureTransformer.FEATURE_COUNT_HEADER, "22"))
				.andExpect(header().string(NetworkController.HEADER_CONTENT_TYPE, NetworkController.MIME_TYPE_GEOJSON))
				.andReturn();

		assertThat(new JSONObject(rtn.getResponse().getContentAsString()),
				sameJSONObjectAs(new JSONObject(getCompareFile(RESULT_FOLDER, "comid_13293474_UT.json"))).allowingAnyArrayOrdering());
	}

	@Test
	public void getComidUtDistanceTest() throws Exception {
		MvcResult rtn = mockMvc.perform(get("/api/comid/13297246/navigate/UT/wqp?distance=10&legacy=true"))
				.andExpect(status().isOk())
				.andExpect(header().string(FeatureTransformer.FEATURE_COUNT_HEADER, "6"))
				.andExpect(header().string(NetworkController.HEADER_CONTENT_TYPE, NetworkController.MIME_TYPE_GEOJSON))
				.andReturn();

		assertThat(new JSONObject(rtn.getResponse().getContentAsString()),
				sameJSONObjectAs(new JSONObject(getCompareFile(RESULT_FOLDER, "comid_13297246_UT_distance_10.json"))).allowingAnyArrayOrdering());
	}

	//UM Testing
	@Test
	public void getComidUmTest() throws Exception {
		MvcResult rtn = mockMvc.perform(get("/api/comid/13293474/navigate/UM/wqp?legacy=true"))
				.andExpect(status().isOk())
				.andExpect(header().string(FeatureTransformer.FEATURE_COUNT_HEADER, "17"))
				.andExpect(header().string(NetworkController.HEADER_CONTENT_TYPE, NetworkController.MIME_TYPE_GEOJSON))
				.andReturn();

		assertThat(new JSONObject(rtn.getResponse().getContentAsString()),
				sameJSONObjectAs(new JSONObject(getCompareFile(RESULT_FOLDER, "comid_13293474_UM.json"))).allowingAnyArrayOrdering());
	}

	@Test
	public void getComidUmDistanceTest() throws Exception {
		MvcResult rtn = mockMvc.perform(get("/api/comid/13297246/navigate/UM/wqp?distance=10&legacy=true"))
				.andExpect(status().isOk())
				.andExpect(header().string(FeatureTransformer.FEATURE_COUNT_HEADER, "6"))
				.andExpect(header().string(NetworkController.HEADER_CONTENT_TYPE, NetworkController.MIME_TYPE_GEOJSON))
				.andReturn();

		assertThat(new JSONObject(rtn.getResponse().getContentAsString()),
				sameJSONObjectAs(new JSONObject(getCompareFile(RESULT_FOLDER, "comid_13297246_UM_distance_10.json"))).allowingAnyArrayOrdering());
	}

	//DM Testing
	@Test
	public void getComidDmTest() throws Exception {
		MvcResult rtn = mockMvc.perform(get("/api/comid/13296790/navigate/DM/wqp?legacy=true"))
				.andExpect(status().isOk())
				.andExpect(header().string(FeatureTransformer.FEATURE_COUNT_HEADER, "6"))
				.andExpect(header().string(NetworkController.HEADER_CONTENT_TYPE, NetworkController.MIME_TYPE_GEOJSON))
				.andReturn();

		assertThat(new JSONObject(rtn.getResponse().getContentAsString()),
				sameJSONObjectAs(new JSONObject(getCompareFile(RESULT_FOLDER, "comid_13296790_DM.json"))).allowingAnyArrayOrdering());
	}

	@Test
	public void getComidDmDistanceTest() throws Exception {
		MvcResult rtn = mockMvc.perform(get("/api/comid/13293474/navigate/DM/wqp?distance=10&legacy=true"))
				.andExpect(status().isOk())
				.andExpect(header().string(FeatureTransformer.FEATURE_COUNT_HEADER, "31"))
				.andExpect(header().string(NetworkController.HEADER_CONTENT_TYPE, NetworkController.MIME_TYPE_GEOJSON))
				.andReturn();

		assertThat(new JSONObject(rtn.getResponse().getContentAsString()),
				sameJSONObjectAs(new JSONObject(getCompareFile(RESULT_FOLDER, "comid_13293474_DM_distance_10.json"))).allowingAnyArrayOrdering());
	}

	//DD Testing
	@Test
	public void getComidDdTest() throws Exception {
		MvcResult rtn = mockMvc.perform(get("/api/comid/13294310/navigate/DD/wqp?legacy=true"))
				.andExpect(status().isOk())
				.andExpect(header().string(FeatureTransformer.FEATURE_COUNT_HEADER, "17"))
				.andExpect(header().string(NetworkController.HEADER_CONTENT_TYPE, NetworkController.MIME_TYPE_GEOJSON))
				.andReturn();

		assertThat(new JSONObject(rtn.getResponse().getContentAsString()),
				sameJSONObjectAs(new JSONObject(getCompareFile(RESULT_FOLDER, "comid_13294310_DD.json"))).allowingAnyArrayOrdering());
	}

	@Test
	public void getComidDdDistanceTest() throws Exception {
		MvcResult rtn = mockMvc.perform(get("/api/comid/13294310/navigate/DD/wqp?distance=11&legacy=true"))
				.andExpect(status().isOk())
				.andExpect(header().string(FeatureTransformer.FEATURE_COUNT_HEADER, "1"))
				.andExpect(header().string(NetworkController.HEADER_CONTENT_TYPE, NetworkController.MIME_TYPE_GEOJSON))
				.andReturn();

		assertThat(new JSONObject(rtn.getResponse().getContentAsString()),
				sameJSONObjectAs(new JSONObject(getCompareFile(RESULT_FOLDER, "comid_13294310_DD_distance_11.json"))).allowingAnyArrayOrdering());
	}

	//PP Testing
	@Test
	public void getComidPpStopComidInvalidTest() throws Exception {
		MvcResult rtn = mockMvc.perform(get("/api/comid/13297246/navigate/PP/wqp?stopComid=13297198&legacy=true"))
				.andExpect(status().isBadRequest())
				.andExpect(header().string(FeatureTransformer.FEATURE_COUNT_HEADER, (String)null))
				.andExpect(header().string(NetworkController.HEADER_CONTENT_TYPE, (String)null))
				.andReturn();

		assertThat(new JSONObject(rtn.getResponse().getErrorMessage()),
				sameJSONObjectAs(new JSONObject(getCompareFile(RESULT_FOLDER, "comid_13297246_PP_stop_13297198.json"))).allowingAnyArrayOrdering());
	}

	@Test
	public void getComidPpStopComidTest() throws Exception {
		MvcResult rtn = mockMvc.perform(get("/api/comid/13297198/navigate/PP/wqp?stopComid=13297246&legacy=true"))
				.andExpect(status().isOk())
				.andExpect(header().string(FeatureTransformer.FEATURE_COUNT_HEADER, "16"))
				.andExpect(header().string(NetworkController.HEADER_CONTENT_TYPE, NetworkController.MIME_TYPE_GEOJSON))
				.andReturn();

		assertThat(new JSONObject(rtn.getResponse().getContentAsString()),
				sameJSONObjectAs(new JSONObject(getCompareFile(RESULT_FOLDER, "comid_13297198_PP_stop_13297246.json"))).allowingAnyArrayOrdering());
	}

}
