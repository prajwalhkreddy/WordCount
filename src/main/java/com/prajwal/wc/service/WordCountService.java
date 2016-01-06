package com.prajwal.wc.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Service;
import com.prajwal.wc.bean.ResponseBean;

@Service
public class WordCountService {

	Map<String, ResponseBean> countMap = new HashMap<String, ResponseBean>();
	ResponseBean rb = null;
	ResponseBean rq = null;

	
	/*This method is run once to load the word count values to the HashMap*/
	public void readFiles(String folderName) {

		// Get files from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(folderName).getFile());
		String[] fileNames = file.list();


		for (String fileName : fileNames) {
			if (new File(classLoader.getResource(folderName + "\\" + fileName)
					.getFile()).isFile()) {

				Scanner readFile = null;
				try {
					readFile = new Scanner(new File(classLoader.getResource(
							folderName + "\\" + fileName).getFile()))
							.useDelimiter("[^a-zA-Z0-9]+");

					while (readFile.hasNext()) {
						String word = readFile.next().toLowerCase();
						if (countMap.containsKey(word)) {
							rb = countMap.get(word);
							rb.setWordCount(rb.getWordCount() + 1);
							countMap.put(word, rb);

						} else {
							rb = new ResponseBean();
							rb.setWordCount(1);
							countMap.put(word, rb);
						}
					}

				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}

			}
		}
	}

/*This method is run every time a word count is requested for*/
	public String getCount(String word) {
		if (!countMap.containsKey(word)) {
			rq = new ResponseBean();
			rq.setRequestCount(1);
			countMap.put(word, rq);
			return "Word Not Found!" + "\nRequest Count: "
					+ rq.getRequestCount();
		}
		rb = countMap.get(word);
		rq = countMap.get(word);
		rq.setRequestCount(rq.getRequestCount() + 1);
		countMap.put(word, rq);
		return "Word Count: " + rb.getWordCount() + "\nRequest Count: "
				+ rq.getRequestCount();

	}
}
