package br.com.esec.icpm.sample.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImportDocs {

	private static List<String> docs = null;
	private static HashMap<String, String> hashDocs = null;

	public static List<String> getListDocs(String path) {
		File root = new File(path + "\\docs");

		if (docs == null) {
			docs = new ArrayList<String>();

			for (File doc : root.listFiles()) {
				String pathD = "file:\\\\\\" + doc.getAbsolutePath();
				docs.add(pathD);
			}
		}

		return docs;
	}

	public static HashMap<String, String> getHashDocs(String path) {
		File root = new File(path + "\\docs");

		if (hashDocs == null) {
			hashDocs = new HashMap<String, String>();

			for (File doc : root.listFiles()) {
				String pathD = "file:\\\\\\" + doc.getAbsolutePath();
				hashDocs.put(doc.getName(), pathD);
			}
		}

		return hashDocs;
	}
}