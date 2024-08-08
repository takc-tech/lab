package com.takc_tech;

import org.apache.sshd.sftp.client.SftpClient.DirEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

@Configuration
public class SftpConfig {

	@Autowired
	SftpProperties prop;

	@Bean
	SessionFactory<DirEntry> createTemplate() {

		// https://spring.pleiades.io/spring-integration/api/org/springframework/integration/sftp/session/DefaultSftpSessionFactory.html
		var factory = new DefaultSftpSessionFactory(true);

		factory.setHost(prop.getHost());
		factory.setPort(prop.getPort());
		factory.setUser(prop.getUser());
		factory.setPassword(prop.getPassword());

		// trueだと未知のホストへの接続を無条件に許可
		// falseにしてknown_hostsを使用する
		factory.setAllowUnknownKeys(prop.isAllowUnknownKeys());
		factory.setKnownHostsResource(getResource(prop.getKnownHosts()));

		// パスワードが無くても鍵を指定していれば通る
		factory.setPrivateKey(getResource(prop.getPrivateKey()));

		return new CachingSessionFactory<>(factory);

	}

	private Resource getResource(String path) {
		return new FileSystemResource(path);
	}
}
