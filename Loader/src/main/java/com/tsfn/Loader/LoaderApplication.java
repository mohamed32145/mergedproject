package com.tsfn.Loader;

import com.tsfn.Loader.service.MarketingDataLinkedInService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class LoaderApplication {
	private static final Logger logger = LoggerFactory.getLogger(LoaderApplication.class);

	public static void main(String[] args) {
		String repoUrl = "https://github.com/fadykittan/tsofen_project_data_files.git";
		String localPath = "C:/Users/mohamad/OneDrive/Desktop/java_microservices_final_project/data2";

		try {
			FileUtils.deleteDirectory(new File(localPath));
		} catch (IOException e) {
			logger.info("Failed to delete existing directory: " + e.getMessage());
		}

		try {
			// Clone the repository
			Git.cloneRepository()
					.setURI(repoUrl)
					.setDirectory(new File(localPath))
					.call();
			logger.info("Repository cloned successfully.");
		} catch (GitAPIException e) {
		logger.info("Failed to clone repository: " + e.getMessage());
		}
		SpringApplication.run(LoaderApplication.class, args);
	}

}
