package br.com.esec.icpm.samples.ap.core.partial;

import br.com.esec.icpm.samples.ap.core.utils.CertillionStatus;
import br.com.esec.icpm.samples.ap.core.utils.FileInfo;
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
			FileInfo info = new FileInfo();

			// scan this line
			if (lineScanner.hasNext()) {
				String filePath = lineScanner.next();
				info.setName(FilenameUtils.getName(filePath));
				info.setStream(new FileInputStream(filePath));
			}
			if (lineScanner.hasNext()) {
				info.setHash(lineScanner.next());
			}
			if (lineScanner.hasNext()) {
				info.setTransactionId(Long.parseLong(lineScanner.next()));
			}
			if (lineScanner.hasNext()) {
				info.setSignatureStatus(CertillionStatus.valueOf(lineScanner.next()));
			}

			// if the line was not empty, add this FileInfo to the list
			if (info.getName() != null) {
				fileInfos.add(info);
			}

			lineScanner.close();
		}
	}

	public void write() throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(path);
		for (FileInfo info : fileInfos) {

			if (info.getName() != null) {
				writer.print(info.getName() + ", ");
			}
			if (info.getHash() != null) {
				writer.print(info.getHash() + ", ");
			}
			if (info.getTransactionId() != 0) {
				writer.print(Long.toString(info.getTransactionId()) + ", ");
			}
			if (info.getSignatureStatus() != null) {
				writer.print(info.getSignatureStatus());
			}
		}

		writer.close();
	}

	public List<FileInfo> getFileInfos() {
		return fileInfos;
	}

}
