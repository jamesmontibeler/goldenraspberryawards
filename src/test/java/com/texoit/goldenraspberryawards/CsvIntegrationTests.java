package com.texoit.goldenraspberryawards;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.texoit.goldenraspberryawards.resource.awardinterval.AwardInterval;
import com.texoit.goldenraspberryawards.resource.awardinterval.AwardIntervalDetail;
import com.texoit.goldenraspberryawards.service.csvimportdata.CsvImportDataService;
import com.texoit.goldenraspberryawards.service.producer.ProducerService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GoldenraspberryawardsapiApplication.class })
public class CsvIntegrationTests {

	@Autowired
	CsvImportDataService integrationService;
	
	@Autowired
	ProducerService producerService;
	
	private String getFilePath(String fileApplicationNamePath) {
		String path = "src\\test\\resources";

		File file = new File(path);
		return file.getAbsolutePath() + fileApplicationNamePath;
	}
	
	@Test
	public void defaultMovieListTest() {
		
		String dataFileCsvName = getFilePath("\\csv\\movielist.csv");
				
		integrationService.setDataFileCsvName(dataFileCsvName);		
		integrationService.importDataFromFile();		
		assertThat(integrationService.getQtyImportedRecords()).isEqualTo(206L);
		
		AwardInterval awardinterval = producerService.getAwardInterval();
		assertThat(awardinterval).isNotNull();
		assertThat(awardinterval.getMin()).isNotEmpty();
		assertThat(awardinterval.getMax()).isNotEmpty();
		assertThat(awardinterval.getMin().size()).isEqualTo(1);
		assertThat(awardinterval.getMax().size()).isEqualTo(1);		

		AwardIntervalDetail awardintervalDetail = awardinterval.getMin().iterator().next();
		assertThat(awardintervalDetail.getProducer()).isEqualToIgnoringCase("Joel Silver");
		assertThat(awardintervalDetail.getInterval()).isEqualTo(1);
		assertThat(awardintervalDetail.getPreviousWin()).isEqualTo(1990);
		assertThat(awardintervalDetail.getFollowingWin()).isEqualTo(1991);
		
		awardintervalDetail = awardinterval.getMax().iterator().next();
		assertThat(awardintervalDetail.getProducer()).isEqualToIgnoringCase("Matthew Vaughn");
		assertThat(awardintervalDetail.getInterval()).isEqualTo(13);
		assertThat(awardintervalDetail.getPreviousWin()).isEqualTo(2002);
		assertThat(awardintervalDetail.getFollowingWin()).isEqualTo(2015);
		
	}

}
