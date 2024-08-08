package com.takc_tech;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.sshd.sftp.client.SftpClient.DirEntry;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SftpClient {

	private final SessionFactory<DirEntry> factory;

	SftpClient(SessionFactory<DirEntry> factory) {
		this.factory = factory;
	}

	/**
	 * テンプレートを作成する
	 * @return
	 */
	private SftpRemoteFileTemplate generateTemplate() {
		return new SftpRemoteFileTemplate(factory);
	}

	/**
	 * リモートに対象のパスが存在するか確認する
	 * @param remotePath 対象のパス
	 * @return リモートに対象のパスが存在するか
	 */
	public boolean exists(String remotePath) {
		var template = generateTemplate();
		return template.exists(remotePath);
	}

	/**
	 * リモートからファイルを取得する
	 * @param remotePath 対象のパス
	 * @param localPath 保存先のパス
	 * @param standardCopyOption コピー時オプション
	 * @return
	 */
	public boolean fetch(String remotePath, String localPath, StandardCopyOption standardCopyOption) {
		var template = generateTemplate();
		return template.get(remotePath, inputStream -> {
			if (standardCopyOption == null) {
				Files.copy(inputStream, Path.of(localPath));
			} else {
				Files.copy(inputStream, Path.of(localPath), standardCopyOption);
			}
		});
	}

	/**
	 * リモートからファイルを削除する
	 * @param remotePath 対象のパス
	 * @return
	 */
	public boolean remove(String remotePath) {
		var template = generateTemplate();
		return template.remove(remotePath);
	}

	/**
	 * ファイルを送信する
	 * @param uploadPath 送信元パス
	 * @param remotePath 送信先パス
	 * @param fileExistsMode ファイル存在時の挙動モード
	 * @return リモートパス
	 */
	public String send(String uploadPath, String remotePath, FileExistsMode fileExistsMode) {
		var template = generateTemplate();
		template.setRemoteDirectoryExpression(new SpelExpressionParser().parseExpression("headers['remote-dir']"));
		var msg = MessageBuilder
				.withPayload(new File(uploadPath))
				.setHeader("remote-dir", remotePath)
				.build();
		return template.send(msg, fileExistsMode);
	}

}
