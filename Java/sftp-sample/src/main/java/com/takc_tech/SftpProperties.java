package com.takc_tech;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@PropertySource("classpath:sftp.properties")
public class SftpProperties {

	@Value("${host}")
	private String host;

	@Value("${port}")
	private int port;

	@Value("${user}")
	private String user;

	@Value("${password}")
	private String password;

	@Value("${allowUnknownKeys}")
	private boolean allowUnknownKeys;

	@Value("${knownHosts}")
	private String knownHosts;

	@Value("${privateKey}")
	private String privateKey;
}
