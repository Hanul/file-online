package co.hanul.mr.dev.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 웹 어플리케이션에서 파일 업/다운로드를 쉽게 할 수 있도록 도와주는 모듈
 * 
 * @author Mr. 하늘
 */
public class FileOnline {

	private String basePath;

	public FileOnline(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * 파일 전체 경로를 구함
	 */
	public String getFullPath(String fileName) {
		return getFullPath(fileName, false);
	}

	/**
	 * 파일 전체 경로를 구함
	 */
	public String getFullPath(String fileName, boolean useMkdirs) {

		if (useMkdirs) {
			File dir = new File(basePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		return basePath + File.separatorChar + fileName;
	}

	/**
	 * 파일 저장
	 */
	public boolean save(File file, String fileName) throws IOException {
		return save(file, fileName, false);
	}

	/**
	 * 파일 저장
	 */
	public boolean save(File file, String fileName, boolean isOverride) throws IOException {

		if (file != null && !file.getName().equals("")) {
			FileInputStream fis = new FileInputStream(file);
			boolean isSaved = save(fis, fileName, isOverride);
			fis.close();

			return isSaved;
		} else {
			return false;
		}
	}

	/**
	 * 파일 저장
	 */
	public boolean save(InputStream is, String fileName) throws IOException {
		return save(is, fileName, false);
	}

	/**
	 * 파일 저장
	 */
	public boolean save(InputStream is, String fileName, boolean isOverride) throws IOException {

		String fullPath = getFullPath(fileName, true);

		if (isOverride || !(new File(fullPath).exists())) {
			FileOutputStream fos = new FileOutputStream(fullPath);

			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = is.read(buffer, 0, 1024)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}

			fos.close();

			return true;
		} else {
			return false;
		}
	}

	/**
	 * 파일 로드
	 */
	public InputStream load(String fileName) throws FileNotFoundException {
		return new FileInputStream(getFullPath(fileName));
	}

	/**
	 * 파일명 수정
	 */
	public boolean rename(String originFileName, String fileName) throws IOException {

		String originFullPath = getFullPath(originFileName);
		File originFile = new File(originFullPath);

		String fullPath = getFullPath(fileName);
		File file = new File(fullPath);

		return originFile.renameTo(file);
	}

	/**
	 * 파일 삭제
	 */
	public boolean delete(String fileName) {

		String fullPath = getFullPath(fileName);

		File file = new File(fullPath);
		return file.delete();
	}

}
