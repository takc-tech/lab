package com.takc_tech;

import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.file.support.FileExistsMode;

@SpringBootApplication
public class Main implements CommandLineRunner {

	public static void main(String... args) {
		SpringApplication.run(Main.class, args);
	}

	@Autowired
	private SftpClient sftpClient;

	@Override
	public void run(String... args) {

		final String remoteDir = "";
		final String remotePath = remoteDir + "";
		final String localPath = "";
		final String uploadPath = "";

		sftpClient.send(uploadPath, remoteDir, FileExistsMode.REPLACE);

		// 存在確認
		if (sftpClient.exists(remotePath)) {
			// ダウンロード
			sftpClient.fetch(remotePath, localPath, StandardCopyOption.REPLACE_EXISTING);
			// 削除
			sftpClient.remove(remotePath);
		}
	}
}