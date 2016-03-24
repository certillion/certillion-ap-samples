package br.com.esec.icpm.samples.ap.core.partial;

import br.com.esec.icpm.samples.ap.core.utils.FileInfo;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Parse and dump the CSV configuration file.
 */
public class ConfigCsv {

	private final String path;
	private List<FileInfo> fileInfos = new ArrayList<FileInfo>();

	public ConfigCsv(String path) {
		this.path = path;
	}

	public void read() throws IOException {

		// read all lines of the file
		List<String> lines = IOUtils.readLines(new FileReader(path));

		// scan each line, separating tokens by comma
		for (String line : lines) {
			Scanner lineScanner = new Scanner(line);
			lineScanner.useDelimiter("\\s*,\\s*");

			// scan this line
			FileInfo info = new FileInfo(new File(lineScanner.next()));
			if (lineScanner.hasNext()) {
				info.setHash(lineScanner.next());
			}
			if (lineScanner.hasNext()) {
				info.setTransactionId(Long.parseLong(lineScanner.next()));
			}
			if (lineScanner.hasNext()) {
				info.setDetachedSignature(Base64.decodeBase64(lineScanner.next()));
			}

			// if the line was not empty, add this FileInfo to the list
			if (info.getPath() != null) {
				fileInfos.add(info);
			}

			lineScanner.close();
		}
	}

	public void write() throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(path);
		for (FileInfo info : fileInfos) {
			writer.printf("%s, %s, %s, %s\n",
					info.getPath(),
					info.getHash(),
					Long.toString(info.getTransactionId()),
					Base64.encodeBase64String(info.getDetachedSignature()) );
		}
		writer.close();
	}

	public List<FileInfo> getFileInfos() {
		return fileInfos;
	}

	public FileInfo findByName(String name) {
		for (FileInfo info : fileInfos) {
			if (FilenameUtils.getName(info.getPath()).equals(name)) {
				return info;
			}
		}
		return null;
	}

}
